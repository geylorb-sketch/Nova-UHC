package net.novauhc.dandadan.roles.enenra;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.template.UseAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class SettaAbility extends UseAbility {
    @Override public String getName() { return "Setta"; }
    @Override public Material getMaterial() { return Material.COAL; }

    @Override
    public boolean onEnable(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 127, false, false));
        LangManager.get().send(DanDaDanLang.ENENRA_SETTA_ON, player);
        Bukkit.getScheduler().runTaskLater(Main.get(), () -> {
            if (!player.isOnline()) return;
            player.removePotionEffect(PotionEffectType.SLOW);
            Player target = null;
            for (Entity e : player.getNearbyEntities(30, 30, 30)) {
                if (e instanceof Player p) {
                    Vector toT = p.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
                    if (player.getLocation().getDirection().normalize().dot(toT) > 0.90) { target = p; break; }
                }
            }
            if (target == null) return;
            Location dest = target.getLocation().add(target.getLocation().getDirection().multiply(5));
            player.teleport(dest);
            target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 0, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 400, 0, false, false));
        }, 60L);
        setCooldown(900);
        return true;
    }

}
