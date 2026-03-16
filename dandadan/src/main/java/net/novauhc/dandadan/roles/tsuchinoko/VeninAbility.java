package net.novauhc.dandadan.roles.tsuchinoko;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class VeninAbility extends UseAbiliy {
    @Override public String getName()       { return "Venin"; }
    @Override public Material getMaterial() { return Material.FERMENTED_SPIDER_EYE; }

    @Override
    public boolean onEnable(Player player) {
        LangManager.get().send(DanDaDanLangExt.TSUCHINOKO_VENIN_ACTIVATED, player);
        List<Location> spongeLocs = new ArrayList<>();

        // Pose 6 blocs d'éponge aléatoires dans 15 blocs
        for (int i = 0; i < 6; i++) {
            double angle = ThreadLocalRandom.current().nextDouble() * 2 * Math.PI;
            double dist  = 5 + ThreadLocalRandom.current().nextDouble() * 10;
            Location loc = player.getLocation().clone().add(
                    Math.cos(angle) * dist, 0, Math.sin(angle) * dist);
            loc.getBlock().setType(Material.SPONGE);
            spongeLocs.add(loc.clone());
        }

        var taskId = new int[1];
        taskId[0] = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(), () -> {
            spongeLocs.forEach(loc -> {
                // Wither si dessus, Poison si proche
                loc.getWorld().getNearbyEntities(loc, 1, 1, 1).stream()
                        .filter(e -> e instanceof Player && !e.equals(player))
                        .forEach(e -> ((Player)e).addPotionEffect(
                                new PotionEffect(PotionEffectType.WITHER, 100, 0)));
                loc.getWorld().getNearbyEntities(loc, 3, 3, 3).stream()
                        .filter(e -> e instanceof Player && !e.equals(player))
                        .forEach(e -> ((Player)e).addPotionEffect(
                                new PotionEffect(PotionEffectType.POISON, 40, 0)));
            });
        }, 0L, 20L);

        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            Main.get().getServer().getScheduler().cancelTask(taskId[0]);
            spongeLocs.forEach(loc -> {
                if (loc.getBlock().getType() == Material.SPONGE)
                    loc.getBlock().setType(Material.AIR);
            });
        }, 20L * 600);

        setCooldown(600);
        return true;
    }
}