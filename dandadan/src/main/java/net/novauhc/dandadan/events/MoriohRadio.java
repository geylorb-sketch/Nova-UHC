package net.novauhc.dandadan.events;

import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * MoriohRadio — Système de diffusion de la radio Morioh-Cho.
 * ─────────────────────────────────────────────────────────────
 * Diffuse des informations (mort, yokai, stand, kintama, espace vide,
 * time freeze, typhoon, coordonnées) avec une probabilité configurable.
 */
public class MoriohRadio {

    private static MoriohRadio instance;

    /** Probabilité d'activation de la radio pour chaque type d'événement (0.0-1.0). */
    private double broadcastChance = 0.5;
    private boolean enabled = true;

    public void setEnabled(boolean v) { this.enabled = v; }

    public static MoriohRadio get() {
        if (instance == null) instance = new MoriohRadio();
        return instance;
    }

    public void setBroadcastChance(double chance) {
        this.broadcastChance = Math.max(0, Math.min(1, chance));
    }

    /**
     * Broadcast un message radio à tous les joueurs avec une chance configurable.
     * @param langKey  La clé de lang à utiliser
     * @param placeholders Map des placeholders à remplacer
     */
    public void broadcast(DanDaDanLang langKey, Map<String, String> placeholders) {
        if (!enabled || Math.random() > broadcastChance) return;
        String prefix = "§7[§6Radio Morioh-Cho§7] §f";
        String msg    = LangManager.get().get(langKey);
        if (placeholders != null) {
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                msg = msg.replace(entry.getKey(), entry.getValue());
            }
        }
        final String finalMsg = prefix + msg;
        UHCPlayerManager.get().getOnlineUHCPlayers().forEach(p -> {
            if (p.getPlayer() != null) p.getPlayer().sendMessage(finalMsg);
        });
    }

    /** Surcharge sans placeholders. */
    public void broadcast(DanDaDanLang langKey) {
        broadcast(langKey, null);
    }

    // ── Événements spécifiques ───────────────────────────────────

    /** Diffuse la mort d'un joueur (révèle le tueur). */
    public void onPlayerDeath(String victim, String killer) {
        broadcast(DanDaDanLang.RADIO_PLAYER_DEATH, Map.of("%victim%", victim, "%killer%", killer));
    }

    /** Diffuse l'obtention d'un yokai. */
    public void onYokaiObtained(String player, String yokai) {
        broadcast(DanDaDanLang.RADIO_YOKAI_OBTAINED, Map.of("%player%", player, "%yokai%", yokai));
    }

    /** Diffuse l'obtention d'un Stand. */
    public void onStandObtained(String player, String stand) {
        broadcast(DanDaDanLang.RADIO_STAND_OBTAINED, Map.of("%player%", player, "%stand%", stand));
    }

    /** Diffuse le Typhoon Human. */
    public void onTyphoonHuman(String player1, String player2) {
        broadcast(DanDaDanLang.RADIO_TYPHOON_HUMAN, Map.of("%p1%", player1, "%p2%", player2));
    }

    /** Diffuse l'activation du Time Freeze. */
    public void onTimeFreeze(String player) {
        broadcast(DanDaDanLang.RADIO_TIME_FREEZE, Map.of("%player%", player));
    }

    /** Diffuse les coordonnées d'un joueur aléatoire. */
    public void broadcastRandomCoords(java.util.List<Player> players) {
        if (players.isEmpty() || Math.random() > broadcastChance) return;
        Player target = players.get((int)(Math.random() * players.size()));
        int x = (int) target.getLocation().getX();
        int z = (int) target.getLocation().getZ();
        broadcast(DanDaDanLang.RADIO_COORDS,
                Map.of("%player%", target.getName(), "%x%", String.valueOf(x), "%z%", String.valueOf(z)));
    }
}
