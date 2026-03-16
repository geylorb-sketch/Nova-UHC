package net.novauhc.dandadan.roles.seiko;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Barrière mystique — Clic-Droit sur le joueur visé.
 * Zone 3x3 autour de la cible. Si elle sort → feu permanent + pomme réduite à 1❤.
 * Durée : 30s.
 */
public class BarriereMystiqueAbility extends UseAbiliy {

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "SEIKO_BARRIER_DURATION_NAME", descKey = "SEIKO_BARRIER_DURATION_DESC", type = VariableType.TIME)
    private int duration = 30;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "SEIKO_BARRIER_RADIUS_NAME", descKey = "SEIKO_BARRIER_RADIUS_DESC", type = VariableType.INTEGER)
    private int radius = 3;

    // UUID cible → centre de la barrière
    private final Map<UUID, Location> barrierTargets = new HashMap<>();

    @Override public String getName()       { return "Barrière mystique"; }
    @Override public Material getMaterial() { return Material.NETHER_STAR; }

    @Override
    public boolean onEnable(Player seiko) {
        // Cherche le joueur visé via raytrace (heuristique : joueur le plus proche dans la direction)
        Player target = getTargetPlayer(seiko, 10);
        if (target == null) { seiko.sendMessage("§c✘ Aucun joueur visé."); return false; }

        Location center = target.getLocation().clone();
        barrierTargets.put(target.getUniqueId(), center);

        String msg = LangManager.get().get(DanDaDanLang.SEIKO_BARRIER_MYSTIQUE_ACTIVATED, seiko)
                .replace("%target%", target.getName());
        seiko.sendMessage(msg);

        // Particules rouges périodiques
        var taskId = new int[1];
        taskId[0] = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(), () -> {
            drawBarrierParticles(center, radius);
            // Vérifie si la cible sort de la zone
            if (target.isOnline() && target.getLocation().distance(center) > radius) {
                applyBarrierPenalty(target, seiko.getName());
            }
        }, 0L, 20L);

        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            Main.get().getServer().getScheduler().cancelTask(taskId[0]);
            barrierTargets.remove(target.getUniqueId());
        }, 20L * duration);

        setCooldown(300); // 5 mins
        return true;
    }

    private void applyBarrierPenalty(Player victim, String seikoName) {
        victim.setFireTicks(99999); // feu permanent (annulé quand barrière expire)
        String msg = LangManager.get().get(DanDaDanLang.SEIKO_BARRIER_BURNED, victim)
                .replace("%seiko%", seikoName);
        victim.sendMessage(msg);
        // La réduction de soin des pommes doit être gérée dans l'event PlayerItemConsumeEvent
        // via un flag UUID → dans un listener global DanDaDan
    }

    private void drawBarrierParticles(Location center, int r) {
        for (double angle = 0; angle < 2 * Math.PI; angle += Math.PI / 16) {
            Location point = center.clone().add(Math.cos(angle) * r, 1, Math.sin(angle) * r);
            new ParticleBuilder(ParticleEffect.REDSTONE)
                    .setColor(Color.RED)
                    .setLocation(point).setAmount(2).display();
        }
    }

    private Player getTargetPlayer(Player seiko, double range) {
        return seiko.getWorld().getNearbyEntities(seiko.getLocation(), range, range, range)
                .stream().filter(e -> e instanceof Player && !e.equals(seiko))
                .map(e -> (Player) e)
                .min((a, b) -> Double.compare(
                        a.getLocation().distance(seiko.getLocation()),
                        b.getLocation().distance(seiko.getLocation())))
                .orElse(null);
    }
}
