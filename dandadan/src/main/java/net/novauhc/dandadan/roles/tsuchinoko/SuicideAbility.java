package net.novauhc.dandadan.roles.tsuchinoko;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

public class SuicideAbility extends UseAbiliy {
    @Override public String getName()       { return "Suicide"; }
    @Override public Material getMaterial() { return Material.MAGMA_CREAM; }

    @Override
    public boolean onEnable(Player player) {
        LangManager.get().send(DanDaDanLangExt.TSUCHINOKO_SUICIDE_ACTIVATED, player);
        var taskId = new int[1];
        int[] elapsed = {0};
        taskId[0] = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(), () -> {
            elapsed[0]++;
            // Particules de la zone
            for (double a = 0; a < 2*Math.PI; a += Math.PI/16) {
                new ParticleBuilder(ParticleEffect.REDSTONE)
                        .setColor(java.awt.Color.GREEN)
                        .setLocation(player.getLocation().clone().add(Math.cos(a)*5,1,Math.sin(a)*5))
                        .setAmount(1).display();
            }
            // Toutes les 10s : les joueurs dans la zone qui ne sont pas en feu → Slowness
            if (elapsed[0] % 10 == 0) {
                player.getWorld().getNearbyEntities(player.getLocation(), 5, 5, 5)
                        .stream().filter(e -> e instanceof Player && !e.equals(player))
                        .forEach(e -> {
                            if (((Player)e).getFireTicks() <= 0) {
                                ((Player)e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 0));
                            }
                        });
            }
        }, 0L, 20L);
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () ->
                Main.get().getServer().getScheduler().cancelTask(taskId[0]), 20L * 600);
        setCooldown(600);
        return true;
    }
}