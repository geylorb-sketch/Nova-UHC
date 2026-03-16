package net.novauhc.dandadan.roles.devilman;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt2;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

public class ChaleurAbility extends UseAbiliy {
    @Override public String getName()       { return "Chaleur"; }
    @Override public Material getMaterial() { return Material.FIREBALL; }
    @Override public boolean onEnable(Player player) {
        LangManager.get().send(DanDaDanLangExt2.DEVIL_CHALEUR_ACTIVATED, player);
        var taskId = new int[1];
        int[] elapsed = {0};
        taskId[0] = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(), () -> {
            elapsed[0]++;
            if (elapsed[0] >= 30) { Main.get().getServer().getScheduler().cancelTask(taskId[0]); return; }
            for (double a=0; a<2*Math.PI; a+=Math.PI/12) {
                Location pt = player.getLocation().clone().add(Math.cos(a)*3,1,Math.sin(a)*3);
                new ParticleBuilder(ParticleEffect.FLAME).setLocation(pt).setAmount(2).display();
                player.getWorld().getNearbyEntities(pt,1,1,1).stream()
                        .filter(e->e instanceof Player&&!e.equals(player))
                        .forEach(e->((Player)e).setFireTicks(40));
            }
        }, 0L, 20L);
        setCooldown(600); return true;
    }
}