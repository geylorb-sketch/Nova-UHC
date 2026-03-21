package net.novauhc.dandadan.roles.devilman;

import net.novaproject.novauhc.ability.template.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Map;

public class CrocAbility extends UseAbiliy {
    @Override public String getName() { return "Croc"; }
    @Override public Material getMaterial() { return Material.IRON_SWORD; }
    @Override public boolean onEnable(Player p) {
        for (Entity e : p.getNearbyEntities(5,5,5)) {
            if (e instanceof Player t) {
                t.damage(4.0, p);
                p.setHealth(Math.min(p.getMaxHealth(), p.getHealth() + 4.0)); break;
            }
        }
        LangManager.get().send(DanDaDanLang.GENERIC_ABILITY_ON, p, Map.of("%name%", getName()));
        setCooldown(300); return true; }
}
