package net.novauhc.dandadan.roles.seiko;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.template.UseAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;

/**
 * Barriere mystique — Actif Seiko (Clic-Droit, BARRIER)
 * Joueur vise bloque dans zone 3x3 de particules rouges 30s.
 * Sortir = enflamme + gapples ne soignent que 1 coeur. CD 5min.
 */
public class BarriereMystiqueAbility extends UseAbility {

    @Override public String getName()       { return "Barriere mystique"; }
    @Override public Material getMaterial() { return Material.BARRIER; }

    @Override
    public boolean onEnable(Player player) {
        Player target = getTargetPlayer(player, 20);
        if (target == null) {
            LangManager.get().send(DanDaDanLang.SEIKO_BARRIERE_NO_TARGET, player);
            return false;
        }

        Location center = target.getLocation().clone();
        LangManager.get().send(DanDaDanLang.SEIKO_BARRIERE_M_ON, player);

        // Particules rouges autour pendant 30s
        var task = Bukkit.getScheduler().runTaskTimer(Main.get(), () -> {
            for (double x = -1.5; x <= 1.5; x += 0.5) {
                for (double z = -1.5; z <= 1.5; z += 0.5) {
                    if (Math.abs(x) < 1.5 && Math.abs(z) < 1.5) continue; // Bords seulement
                    for (double y = 0; y < 3; y += 0.5) {
                        new ParticleBuilder(ParticleEffect.REDSTONE)
                                .setColor(Color.RED)
                                .setLocation(center.clone().add(x, y, z))
                                .setAmount(1).display();
                    }
                }
            }
            // Si la victime sort de la zone
            if (target.isOnline() && target.getLocation().distance(center) > 2.0) {
                target.setFireTicks(60); // Enflamme 3s
            }
        }, 0L, 10L);

        Bukkit.getScheduler().runTaskLater(Main.get(), task::cancel, 30 * 20L);

        setCooldown(300); // 5min
        return true;
    }

    private Player getTargetPlayer(Player player, double range) {
        for (Entity e : player.getNearbyEntities(range, range, range)) {
            if (!(e instanceof Player p)) continue;
            org.bukkit.util.Vector toTarget = p.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
            if (player.getLocation().getDirection().normalize().dot(toTarget) > 0.90) return p;
        }
        return null;
    }
}
