package net.novauhc.dandadan.roles.acrobatique;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt2;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

public class CheveuxAbility extends UseAbiliy {
    @Override public String getName()       { return "Malédiction - Cheveux"; }
    @Override public Material getMaterial() { return Material.STRING; }

    @Override
    public boolean onEnable(Player player) {
        LangManager.get().send(DanDaDanLangExt2.ACRO_CHEVEUX_ACTIVATED, player);

        var taskId = new int[1];
        int[] elapsed = {0};
        taskId[0] = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(), () -> {
            elapsed[0]++;
            if (elapsed[0] >= 300) { Main.get().getServer().getScheduler().cancelTask(taskId[0]); return; }

            // Particules rouges (repoussent) et vertes (attirent)
            double angle = elapsed[0] * 0.2;
            for (int i = 0; i < 3; i++) {
                double a = angle + i * (2 * Math.PI / 3);
                Location red = player.getLocation().clone().add(Math.cos(a) * 3, 1, Math.sin(a) * 3);
                Location green = player.getLocation().clone().add(Math.cos(a + Math.PI) * 3, 1, Math.sin(a + Math.PI) * 3);

                new ParticleBuilder(ParticleEffect.REDSTONE)
                        .setColor(java.awt.Color.RED).setLocation(red).setAmount(2).display();
                new ParticleBuilder(ParticleEffect.REDSTONE)
                        .setColor(java.awt.Color.GREEN).setLocation(green).setAmount(2).display();

                // Collision rouge → repousse, vert → attire
                player.getWorld().getNearbyEntities(red, 1, 1, 1).stream()
                        .filter(e -> e instanceof Player && !e.equals(player))
                        .forEach(e -> e.setVelocity(e.getLocation().subtract(player.getLocation()).toVector().normalize().multiply(2)));
                player.getWorld().getNearbyEntities(green, 1, 1, 1).stream()
                        .filter(e -> e instanceof Player && !e.equals(player))
                        .forEach(e -> e.setVelocity(player.getLocation().subtract(e.getLocation()).toVector().normalize().multiply(1.5)));
            }
        }, 0L, 1L);

        setCooldown(300);
        return true;
    }
}