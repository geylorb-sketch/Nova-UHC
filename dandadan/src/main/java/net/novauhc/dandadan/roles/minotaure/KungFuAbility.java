package net.novauhc.dandadan.roles.minotaure;

import net.novaproject.novauhc.ability.template.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Map;

public class KungFuAbility extends UseAbiliy {
    @Override public String getName() { return "Kung Fu"; }
    @Override public Material getMaterial() { return Material.IRON_AXE; }
    @Override public boolean onEnable(Player p) {
        for (Entity e : p.getNearbyEntities(8,8,8)) {
            if (e instanceof Player t) {
                Vector push = t.getLocation().toVector().subtract(p.getLocation().toVector()).normalize().multiply(3);
                push.setY(1.0); t.setVelocity(push); t.damage(4.0, p);
            }
        }
        LangManager.get().send(DanDaDanLang.GENERIC_ABILITY_ON, p, Map.of("%name%", getName()));
        setCooldown(300); return true; }
}
