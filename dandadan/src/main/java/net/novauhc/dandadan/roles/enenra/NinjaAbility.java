package net.novauhc.dandadan.roles.enenra;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.template.UseAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class NinjaAbility extends UseAbility {
    @Override public String getName() { return "Ninja"; }
    @Override public Material getMaterial() { return Material.ARMOR_STAND; }

    @Override
    public boolean onEnable(Player player) {
        LangManager.get().send(DanDaDanLang.ENENRA_NINJA_ON, player);
        for (int i = 0; i < 10; i++) {
            Location loc = player.getLocation().clone().add((Math.random()-0.5)*10, 0, (Math.random()-0.5)*10);
            // Spawn ArmorStand clone (simplified - in prod use Citizens NPC)
            ArmorStand clone = player.getWorld().spawn(loc, ArmorStand.class);
            clone.setCustomName(player.getName());
            clone.setCustomNameVisible(true);
            clone.setGravity(true);
            Bukkit.getScheduler().runTaskLater(Main.get(), clone::remove, 60*20L);
        }
        setCooldown(600);
        return true;
    }

}
