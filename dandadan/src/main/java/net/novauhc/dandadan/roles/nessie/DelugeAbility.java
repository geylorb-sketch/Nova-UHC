package net.novauhc.dandadan.roles.nessie;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.template.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Map;

public class DelugeAbility extends UseAbiliy {
    @Override public String getName() { return "Deluge"; }
    @Override public Material getMaterial() { return Material.WATER_BUCKET; }
    @Override public boolean onEnable(Player p) {
        Location center = p.getLocation();
        for (int x = -4; x <= 4; x++) for (int z = -4; z <= 4; z++) {
            Location bl = center.clone().add(x, 0, z);
            if (bl.getBlock().getType() == Material.AIR) bl.getBlock().setType(Material.WATER);
        }
        Bukkit.getScheduler().runTaskLater(Main.get(), () -> {
            for (int x = -4; x <= 4; x++) for (int z = -4; z <= 4; z++) {
                Location bl = center.clone().add(x, 0, z);
                if (bl.getBlock().getType() == Material.WATER) bl.getBlock().setType(Material.AIR);
            }
        }, 10*20L);
        LangManager.get().send(DanDaDanLang.GENERIC_ABILITY_ON, p, Map.of("%name%", getName()));
        setCooldown(420); return true; }
}
