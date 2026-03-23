package net.novauhc.dandadan.roles.okarun;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.template.UseAbility;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Tunnel — Espace Vide d'Okarun (Clic-Droit, ENDER_PEARL)
 * TP joueurs dans un rayon de 30 blocs dans un tunnel pendant 1min.
 * Familiers PNJ attaquent. Okarun reçoit malédiction gratuite.
 * Malus : 40% chance 0.5❤ dégâts, 40% chance arrêt sprint.
 * Cooldown 10min.
 */
public class TunnelAbility extends UseAbility {

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "TUNNEL_RADIUS_NAME",
            descKey = "TUNNEL_RADIUS_DESC", type = VariableType.INTEGER)
    private int radius = 30;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "TUNNEL_DURATION_NAME",
            descKey = "TUNNEL_DURATION_DESC", type = VariableType.TIME)
    private int durationSec = 60;

    private final Random random = new Random();
    private final List<Player> playersInTunnel = new ArrayList<>();

    @Override public String getName()       { return "Tunnel"; }
    @Override public Material getMaterial() { return Material.ENDER_PEARL; }

    @Override
    public boolean onEnable(Player player) {
        Location center = player.getLocation();
        playersInTunnel.clear();

        // Collecter les joueurs dans le rayon
        for (UHCPlayer uhc : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
            Player p = uhc.getPlayer();
            if (p == null) continue;
            if (p.getLocation().distance(center) <= radius) {
                playersInTunnel.add(p);
            }
        }

        if (playersInTunnel.size() <= 1) {
            LangManager.get().send(DanDaDanLang.OKARUN_NO_PLAYERS, player);
            return false;
        }

        // TP tous les joueurs en hauteur (simulation du tunnel)
        Location tunnelBase = center.clone().add(0, 50, 0);
        // Créer un sol temporaire
        for (int x = -5; x <= 5; x++) {
            for (int z = -5; z <= 5; z++) {
                tunnelBase.clone().add(x, -1, z).getBlock().setType(Material.OBSIDIAN);
            }
        }

        for (Player p : playersInTunnel) {
            Location tp = tunnelBase.clone().add(
                    (random.nextDouble() - 0.5) * 8, 0, (random.nextDouble() - 0.5) * 8);
            p.teleport(tp);

            if (!p.equals(player)) {
                LangManager.get().send(DanDaDanLang.OKARUN_TUNNEL_ENTER, p);
            }
        }

        // Okarun reçoit la malédiction gratuite
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, durationSec * 20, 1, false, false));
        LangManager.get().send(DanDaDanLang.OKARUN_TUNNEL_ON, player, java.util.Map.of("%duration%", String.valueOf(durationSec)));

        setCooldown(600); // 10min

        // Timer : ramener les joueurs et nettoyer après la durée
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            // Nettoyer le sol
            for (int x = -5; x <= 5; x++) {
                for (int z = -5; z <= 5; z++) {
                    tunnelBase.clone().add(x, -1, z).getBlock().setType(Material.AIR);
                }
            }
            // TP retour
            for (Player p : playersInTunnel) {
                if (p.isOnline()) {
                    p.teleport(center);
                    LangManager.get().send(DanDaDanLang.OKARUN_TUNNEL_END, p);
                }
            }
            playersInTunnel.clear();
        }, 20L * durationSec);

        return true;
    }

    /** Pour les malus (appelé depuis OkarunRole.onHit) */
    public boolean isInTunnel(Player player) {
        return playersInTunnel.contains(player);
    }

    public double applyDamageMalus(double originalDmg) {
        return random.nextDouble() < 0.4 ? 1.0 : originalDmg; // 40% → 0.5❤
    }

    public boolean shouldStopSprint() {
        return random.nextDouble() < 0.4;
    }
}
