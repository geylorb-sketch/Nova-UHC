package net.novauhc.dandadan.events;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.roles.caesar.CaesarRole;
import net.novauhc.dandadan.roles.jiji.JijiRole;
import net.novauhc.dandadan.roles.joseph.JosephRole;
import net.novauhc.dandadan.roles.oeilmalefique.OeilMalefiqueRole;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * TyphoonHuman — Événement aléatoire.
 * ─────────────────────────────────────────────────────────────
 * Se déclenche quand TOUS les joueurs ont un yokai ou un Stand.
 * 2 joueurs aléatoires (hors Jiji+Œil pactés, hors Joseph/Caesar)
 * sont téléportés sur une île à 200 blocs de hauteur.
 * Ils restent là jusqu'à ce que l'un d'eux meure.
 */
public class TyphoonHuman implements Listener {

    private static TyphoonHuman instance;
    private boolean triggered = false;

    private final Set<UUID> islandPlayers = new HashSet<>();
    private final Map<UUID, Location> savedLocations = new HashMap<>();

    // Paires de joueurs exemptés (pactés)
    private final Set<UUID> exemptedUuids = new HashSet<>();

    public static TyphoonHuman get() {
        if (instance == null) instance = new TyphoonHuman();
        return instance;
    }

    public void init() {
        Bukkit.getPluginManager().registerEvents(this, Main.get());
        triggered = false;
    }

    public void reset() {
        triggered = false;
        islandPlayers.clear();
        savedLocations.clear();
        exemptedUuids.clear();
    }

    /**
     * Appelé par DanDaDan.onSec() ou par DanDaDanCMD.
     * Vérifie si tous les joueurs ont un rôle, puis déclenche l'événement.
     */
    public void checkAndTrigger() {
        if (triggered || DanDaDan.get() == null) return;

        var playingPlayers = UHCPlayerManager.get().getPlayingOnlineUHCPlayers();
        if (playingPlayers.isEmpty()) return;

        // Tous ont-ils un rôle ?
        boolean allHaveRole = playingPlayers.stream()
                .allMatch(uhcp -> DanDaDan.get().getRoleByUHCPlayer(uhcp) != null);
        if (!allHaveRole) return;

        triggered = true;
        trigger(playingPlayers);
    }

    private void trigger(List<UHCPlayer> players) {
        // Calculer les exemptés (Jiji+Œil pactés, Joseph/Caesar ensemble)
        computeExempted(players);

        // Filtrer les candidats
        List<UHCPlayer> candidates = players.stream()
                .filter(uhcp -> uhcp.getPlayer() != null
                        && !exemptedUuids.contains(uhcp.getPlayer().getUniqueId()))
                .toList();

        if (candidates.size() < 2) {
            triggered = false; // Pas assez de candidats, on réessaie plus tard
            return;
        }

        // Choisir 2 aléatoirement
        List<UHCPlayer> chosen = new ArrayList<>(candidates);
        Collections.shuffle(chosen);
        Player p1 = chosen.get(0).getPlayer();
        Player p2 = chosen.get(1).getPlayer();

        assert p1 != null;
        assert p2 != null;

        // Broadcast radio
        MoriohRadio.get().onTyphoonHuman(p1.getName(), p2.getName());

        // Broadcast global
        LangManager.get().sendAll(DanDaDanLang.TYPHOON_HUMAN_START);

        // Créer l'île à 200 blocs de hauteur
        World world = p1.getWorld();
        Location island = new Location(world,
                ThreadLocalRandom.current().nextInt(-100, 100),
                200,
                ThreadLocalRandom.current().nextInt(-100, 100));

        buildIsland(island);

        // Sauver et téléporter
        savedLocations.put(p1.getUniqueId(), p1.getLocation().clone());
        savedLocations.put(p2.getUniqueId(), p2.getLocation().clone());
        islandPlayers.add(p1.getUniqueId());
        islandPlayers.add(p2.getUniqueId());

        Location spot1 = island.clone().add(-2, 1, 0);
        Location spot2 = island.clone().add(2, 1, 0);
        p1.teleport(spot1);
        p2.teleport(spot2);

        p1.sendMessage(LangManager.get().get(DanDaDanLang.TYPHOON_HUMAN_YOU, p1)
                .replace("%opponent%", p2.getName()));
        p2.sendMessage(LangManager.get().get(DanDaDanLang.TYPHOON_HUMAN_YOU, p2)
                .replace("%opponent%", p1.getName()));
    }

    /** Construit une petite île circulaire de 7 blocs de rayon en Pierre. */
    private void buildIsland(Location center) {
        for (int dx = -4; dx <= 4; dx++) {
            for (int dz = -4; dz <= 4; dz++) {
                if (dx * dx + dz * dz <= 16) {
                    Location loc = center.clone().add(dx, 0, dz);
                    loc.getBlock().setType(Material.STONE);
                }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player dead = event.getEntity();
        if (!islandPlayers.contains(dead.getUniqueId())) return;

        // L'un des deux est mort → libérer le survivant
        Main.get().getServer().getScheduler().runTask(Main.get(), () -> {
            islandPlayers.forEach(uuid -> {
                if (uuid.equals(dead.getUniqueId())) return;
                Player survivor = Bukkit.getPlayer(uuid);
                Location saved = savedLocations.get(uuid);
                if (survivor != null && saved != null) {
                    survivor.teleport(saved);
                    survivor.sendMessage(LangManager.get().get(DanDaDanLang.TYPHOON_HUMAN_SURVIVED, survivor));
                }
            });
            islandPlayers.clear();
            savedLocations.clear();
        });
    }

    private void computeExempted(List<UHCPlayer> players) {
        exemptedUuids.clear();
        boolean jijiPact = false, oeilPact = false;
        for (UHCPlayer uhcp : players) {
            DanDaDanRole role = DanDaDan.get().getRoleByUHCPlayer(uhcp);
            if (role == null || uhcp.getPlayer() == null) continue;
            // Joseph & Caesar → exemptés ensemble
            if (role instanceof CaesarRole || role instanceof JosephRole) {
                exemptedUuids.add(uhcp.getPlayer().getUniqueId());
            }
            if (role instanceof JijiRole) jijiPact = true;
            if (role instanceof OeilMalefiqueRole) oeilPact = true;
        }
        // Jiji + Œil pactés → exemptés si les deux existent
        if (jijiPact && oeilPact) {
            for (UHCPlayer uhcp : players) {
                DanDaDanRole role = DanDaDan.get().getRoleByUHCPlayer(uhcp);
                if (role instanceof JijiRole || role instanceof OeilMalefiqueRole) {
                    if (uhcp.getPlayer() != null) exemptedUuids.add(uhcp.getPlayer().getUniqueId());
                }
            }
        }
    }

    public boolean isOnIsland(UUID uuid) {
        return islandPlayers.contains(uuid);
    }
}
