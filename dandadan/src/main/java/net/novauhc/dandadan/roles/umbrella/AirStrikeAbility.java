package net.novauhc.dandadan.roles.umbrella;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.template.UseAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Map;

public class AirStrikeAbility extends UseAbility {
    @Override public String getName() { return "Air Strike"; }
    @Override public Material getMaterial() { return Material.FIREWORK; }
    @Override public boolean onEnable(Player p) {
        p.setVelocity(new Vector(0, 3.0, 0));
        Bukkit.getScheduler().runTaskLater(Main.get(), () -> {
            for (Entity e : p.getNearbyEntities(8,8,8)) {
                if (e instanceof Player t) { t.damage(6.0, p); }
            }
        }, 40L);
        LangManager.get().send(DanDaDanLang.GENERIC_ABILITY_ON, p, Map.of("%name%", getName()));
        setCooldown(420); return true; }
}
