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

/**
 * Barrière intérieure — Clic-Droit
 * Cercle de particules 4x4 autour de Seiko. Si un joueur entre → feu permanent.
 * Durée 30s, +1min par kill.
 */
public class BarriereInterieurAbility extends UseAbiliy {

    private boolean barrierActive = false;
    private Location center;
    private static final int RADIUS = 4;


    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "BARRIERE_INT_RADIUS_NAME", descKey = "BARRIERE_INT_RADIUS_DESC", type = VariableType.INTEGER)
    private int barrierRadius = 5;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "BARRIERE_INT_DURATION_NAME", descKey = "BARRIERE_INT_DURATION_DESC", type = VariableType.TIME)
    private int duration = 30;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "BARRIERE_INT_COOLDOWN_NAME", descKey = "BARRIERE_INT_COOLDOWN_DESC", type = VariableType.TIME)
    private int cooldown = 600;

    @Override public String getName()       { return "Barrière intérieure"; }
    @Override public Material getMaterial() { return Material.GHAST_TEAR; }

    @Override
    public boolean onEnable(Player seiko) {
        barrierActive = true;
        center = seiko.getLocation().clone();
        LangManager.get().send(DanDaDanLang.SEIKO_BARRIER_INTERIEUR_ACTIVATED, seiko);

        var taskId = new int[1];
        taskId[0] = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(), () -> {
            if (!barrierActive) { Main.get().getServer().getScheduler().cancelTask(taskId[0]); return; }
            drawCircle(center);
            seiko.getWorld().getNearbyEntities(center, RADIUS, RADIUS, RADIUS)
                    .stream().filter(e -> e instanceof Player && !e.equals(seiko)
                            && e.getLocation().distance(center) <= RADIUS)
                    .forEach(e -> {
                        ((Player) e).setFireTicks(99999);
                        String msg = LangManager.get().get(DanDaDanLang.SEIKO_BARRIER_BURNED, (Player) e)
                                .replace("%seiko%", seiko.getName());
                        ((Player) e).sendMessage(msg);
                    });
        }, 0L, 10L);

        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            barrierActive = false;
            Main.get().getServer().getScheduler().cancelTask(taskId[0]);
        }, 20L * 30);

        setCooldown(600); // 10 mins
        return true;
    }

    public void addTime(int seconds) { /* géré via cooldown */ }
    public void deactivate() { barrierActive = false; }

    private void drawCircle(Location c) {
        for (double a = 0; a < 2 * Math.PI; a += Math.PI / 20) {
            new ParticleBuilder(ParticleEffect.REDSTONE)
                    .setColor(Color.RED)
                    .setLocation(c.clone().add(Math.cos(a) * RADIUS, 1, Math.sin(a) * RADIUS))
                    .setAmount(1).display();
        }
    }
}
