package net.novaproject.novauhc.utils;

import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_8_R3.ScoreboardTeam;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftScoreboard;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Gère la coloration per-viewer des noms dans le TAB.
 * Utilise des packets NMS pour envoyer des teams fakées uniquement au viewer concerné.
 */
public class PlayerColorManager {

    // viewer UUID → set de team names déjà créées (pour savoir si CREATE ou UPDATE)
    private static final Map<UUID, Set<String>> createdTeams = new HashMap<>();

    /**
     * Nom de team unique par target (max 16 chars pour 1.8.8).
     */
    private static String teamName(Player target) {
        String name = target.getName();
        return "clr_" + (name.length() > 11 ? name.substring(0, 11) : name);
    }

    /**
     * Applique la couleur choisie par le viewer pour le nom de target dans le TAB.
     * La modification est uniquement visible par le viewer.
     */
    public static void applyColor(Player viewer, Player target, ChatColor color) {
        if (!viewer.isOnline() || !target.isOnline()) return;

        // Stocker la préférence dans l'UHCPlayer du target (indexée par viewer)
        UHCPlayer targetUhc = UHCPlayerManager.get().getPlayer(target);
        if (targetUhc != null) {
            targetUhc.getTabColorPrefs().put(viewer.getUniqueId(), color);
        }

        sendColorPacket(viewer, target, color);
    }

    private static void sendColorPacket(Player viewer, Player target, ChatColor color) {
        String name = teamName(target);
        UUID viewerUUID = viewer.getUniqueId();

        net.minecraft.server.v1_8_R3.Scoreboard fakeScoreboard = new net.minecraft.server.v1_8_R3.Scoreboard();
        ScoreboardTeam nmsTeam = new ScoreboardTeam(fakeScoreboard, name);
        nmsTeam.setPrefix(color.toString());
        nmsTeam.setSuffix("§r");

        Set<String> created = createdTeams.computeIfAbsent(viewerUUID, k -> new HashSet<>());
        PacketPlayOutScoreboardTeam packet;

        if (!created.contains(name)) {
            // Premier appel : créer la team ET ajouter le joueur
            nmsTeam.getPlayerNameSet().add(target.getName());
            packet = new PacketPlayOutScoreboardTeam(nmsTeam, 0); // CREATE
            created.add(name);
        } else {
            // Mise à jour couleur uniquement
            packet = new PacketPlayOutScoreboardTeam(nmsTeam, 2); // UPDATE INFO
        }

        ((CraftPlayer) viewer).getHandle().playerConnection.sendPacket(packet);
    }

    /**
     * Réapplique toutes les couleurs stockées pour un viewer (appelé au (re)join du viewer).
     */
    public static void reapplyColorsForViewer(Player viewer) {
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (target.equals(viewer)) continue;
            UHCPlayer targetUhc = UHCPlayerManager.get().getPlayer(target);
            if (targetUhc == null) continue;
            ChatColor color = targetUhc.getTabColorPrefs().get(viewer.getUniqueId());
            if (color != null) {
                sendColorPacket(viewer, target, color);
            }
        }
    }

    /**
     * Réapplique la couleur que les autres viewers ont choisie pour ce target (appelé au join du target).
     */
    public static void reapplyColorsForTarget(Player target) {
        UHCPlayer targetUhc = UHCPlayerManager.get().getPlayer(target);
        if (targetUhc == null) return;
        for (Map.Entry<UUID, ChatColor> entry : targetUhc.getTabColorPrefs().entrySet()) {
            Player viewer = Bukkit.getPlayer(entry.getKey());
            if (viewer != null && viewer.isOnline()) {
                sendColorPacket(viewer, target, entry.getValue());
            }
        }
    }

    /**
     * Supprime la team fakée chez tous les viewers (appelé quand le target quitte).
     */
    public static void removeTeamForTarget(Player target) {
        String name = teamName(target);
        net.minecraft.server.v1_8_R3.Scoreboard fakeScoreboard = new net.minecraft.server.v1_8_R3.Scoreboard();
        ScoreboardTeam nmsTeam = new ScoreboardTeam(fakeScoreboard, name);
        PacketPlayOutScoreboardTeam removePacket = new PacketPlayOutScoreboardTeam(nmsTeam, 1); // REMOVE

        for (Player viewer : Bukkit.getOnlinePlayers()) {
            Set<String> created = createdTeams.get(viewer.getUniqueId());
            if (created != null && created.contains(name)) {
                ((CraftPlayer) viewer).getHandle().playerConnection.sendPacket(removePacket);
                created.remove(name);
            }
        }
    }

    /**
     * Supprime la coloration per-viewer et restaure le tag de team d'origine du target.
     */
    public static void removeColorForViewer(Player viewer, Player target) {
        if (!viewer.isOnline()) return;

        String fakeName = teamName(target);
        UUID viewerUUID = viewer.getUniqueId();

        // Supprimer la fake team
        Set<String> created = createdTeams.get(viewerUUID);
        if (created != null && created.contains(fakeName)) {
            net.minecraft.server.v1_8_R3.Scoreboard fakeScoreboard = new net.minecraft.server.v1_8_R3.Scoreboard();
            ScoreboardTeam fakeNmsTeam = new ScoreboardTeam(fakeScoreboard, fakeName);
            PacketPlayOutScoreboardTeam removePacket = new PacketPlayOutScoreboardTeam(fakeNmsTeam, 1); // REMOVE
            ((CraftPlayer) viewer).getHandle().playerConnection.sendPacket(removePacket);
            created.remove(fakeName);
        }

        // Effacer la préférence stockée
        UHCPlayer targetUhc = UHCPlayerManager.get().getPlayer(target);
        if (targetUhc != null) {
            targetUhc.getTabColorPrefs().remove(viewerUUID);
        }

        // Réassocier le joueur à sa team d'origine côté viewer
        if (TeamsTagsManager.scoreboard != null) {
            org.bukkit.scoreboard.Team origTeam = TeamsTagsManager.scoreboard.getPlayerTeam(target);
            if (origTeam != null) {
                // Récupérer le vrai NMS team depuis le CraftScoreboard (déjà peuplé avec le nom du joueur)
                net.minecraft.server.v1_8_R3.Scoreboard nmsScoreboard =
                        ((CraftScoreboard) TeamsTagsManager.scoreboard).getHandle();
                ScoreboardTeam nmsOrigTeam = nmsScoreboard.getTeam(origTeam.getName());
                if (nmsOrigTeam != null) {
                    PacketPlayOutScoreboardTeam addPacket = new PacketPlayOutScoreboardTeam(nmsOrigTeam, 3); // ADD_PLAYERS
                    ((CraftPlayer) viewer).getHandle().playerConnection.sendPacket(addPacket);
                }
            }
        }
    }

    /**
     * Nettoie les données d'un viewer qui quitte.
     */
    public static void cleanupViewer(UUID viewerUUID) {
        createdTeams.remove(viewerUUID);
    }
}
