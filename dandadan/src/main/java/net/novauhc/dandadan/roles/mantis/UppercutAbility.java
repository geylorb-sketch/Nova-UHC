package net.novauhc.dandadan.roles.mantis;

import net.novaproject.novauhc.ability.template.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Map;

public class UppercutAbility extends UseAbiliy {
    @Override public String getName() { return "Uppercut"; }
    @Override public Material getMaterial() { return Material.IRON_SWORD; }
    @Override public boolean onEnable(Player p) {
        for (Entity e : p.getNearbyEntities(5,5,5)) {
            if (e instanceof Player t) { t.setVelocity(new Vector(0, 2.0, 0)); t.damage(2.0, p); }
        }
        LangManager.get().send(DanDaDanLang.GENERIC_ABILITY_ON, p, Map.of("%name%", getName()));
        setCooldown(180); return true; }
}
