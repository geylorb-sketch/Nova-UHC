package net.novauhc.dandadan.roles.reze;

import net.novaproject.novauhc.ability.template.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Map;

public class BoumAbility extends UseAbiliy {
    @Override public String getName() { return "Boum"; }
    @Override public Material getMaterial() { return Material.TNT; }
    @Override public boolean onEnable(Player p) {
        p.getWorld().createExplosion(p.getLocation(), 4.0F);
        for (Entity e : p.getNearbyEntities(8,8,8)) {
            if (e instanceof Player t) t.damage(10.0, p);
        }
        p.setHealth(2.0);
        LangManager.get().send(DanDaDanLang.GENERIC_ABILITY_ON, p, Map.of("%name%", getName()));
        setCooldown(600); return true; }
}
