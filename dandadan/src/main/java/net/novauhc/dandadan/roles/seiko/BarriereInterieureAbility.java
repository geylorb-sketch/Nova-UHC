package net.novauhc.dandadan.roles.seiko;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.template.UseAbiliy;
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
 * Barriere interieure — Actif Seiko (Clic-Droit, STAINED_GLASS)
 * Cercle 4x4 autour de Seiko 30s.
 * Joueur qui entre = enflamme + gapples 1 coeur. CD 10min +1min/kill.
 */
public class BarriereInterieureAbility extends UseAbiliy {

    @Override public String getName()       { return "Barriere interieure"; }
    @Override public Material getMaterial() { return Material.STAINED_GLASS; }

    @Override
    public boolean onEnable(Player player) {
        Location center = player.getLocation().clone();
        LangManager.get().send(DanDaDanLang.SEIKO_BARRIERE_I_ON, player);

        var task = Bukkit.getScheduler().runTaskTimer(Main.get(), () -> {
            // Dessiner le cercle
            for (int i = 0; i < 36; i++) {
                double angle = Math.toRadians(i * 10);
                for (double y = 0; y < 3; y += 0.5) {
                    new ParticleBuilder(ParticleEffect.REDSTONE)
                            .setColor(Color.ORANGE)
                            .setLocation(center.clone().add(Math.cos(angle) * 4, y, Math.sin(angle) * 4))
                            .setAmount(1).display();
                }
            }
            // Joueurs qui entrent dans le cercle
            for (Entity e : player.getWorld().getNearbyEntities(center, 4, 3, 4)) {
                if (!(e instanceof Player p)) continue;
                if (p.equals(player)) continue;
                if (p.getLocation().distance(center) <= 4.0) {
                    p.setFireTicks(40); // 2s enflamme
                }
            }
        }, 0L, 10L);

        Bukkit.getScheduler().runTaskLater(Main.get(), task::cancel, 30 * 20L);

        setCooldown(600); // 10min
        return true;
    }
}
