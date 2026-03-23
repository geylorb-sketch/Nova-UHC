package net.novauhc.dandadan.roles.tsuchinoko;

import net.novaproject.novauhc.ability.template.UseAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Map;

public class OndesAbility extends UseAbility {
    @Override public String getName() { return "Ondes"; }
    @Override public Material getMaterial() { return Material.SLIME_BALL; }
    @Override public boolean onEnable(Player p) {
        for (Entity e : p.getNearbyEntities(10,10,10)) {
            if (e instanceof Player t) {
                Vector push = t.getLocation().toVector().subtract(p.getLocation().toVector()).normalize().multiply(2.5);
                push.setY(0.5); t.setVelocity(push);
            }
        }
        LangManager.get().send(DanDaDanLang.GENERIC_ABILITY_ON, p, Map.of("%name%", getName()));
        setCooldown(300); return true; }
}
