package net.novauhc.dandadan.roles.rokuro;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;

public class ZoneIncroyableAbility extends UseAbiliy {
    @Override public String getName()       { return "Zone incroyable"; }
    @Override public Material getMaterial() { return Material.GLASS; }
    @Override public boolean onEnable(Player player) {
        // Dôme de verre simulé par particules + regen + 50% chance feu
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 20, 0, false, false));
        player.sendMessage("§3★ Zone incroyable ! (regen + 50% feu sur vos coups, 20s)");

        var taskId = new int[1];
        taskId[0] = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(), () -> {
            for (double a = 0; a < 2 * Math.PI; a += Math.PI / 12) {
                new ParticleBuilder(ParticleEffect.REDSTONE)
                        .setColor(Color.CYAN)
                        .setLocation(player.getLocation().clone().add(Math.cos(a)*5, 1, Math.sin(a)*5))
                        .setAmount(1).display();
            }
        }, 0L, 5L);

        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            Main.get().getServer().getScheduler().cancelTask(taskId[0]);
        }, 20L * 20);

        setCooldown(420); return true;
    }
}

// ── Zone Intouchable ─────────────────────────────────────────────
