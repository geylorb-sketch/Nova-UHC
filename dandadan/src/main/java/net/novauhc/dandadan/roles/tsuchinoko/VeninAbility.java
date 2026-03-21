package net.novauhc.dandadan.roles.tsuchinoko;

import net.novaproject.novauhc.ability.template.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Map;

public class VeninAbility extends UseAbiliy {
    @Override public String getName() { return "Venin"; }
    @Override public Material getMaterial() { return Material.SPIDER_EYE; }
    @Override public boolean onEnable(Player p) {
        for (Entity e : p.getNearbyEntities(10,10,10)) {
            if (e instanceof Player t) {
                Vector toT = t.getLocation().toVector().subtract(p.getLocation().toVector()).normalize();
                if (p.getLocation().getDirection().normalize().dot(toT) > 0.85) {
                    t.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 1, false, true)); break;
                }
            }
        }
        LangManager.get().send(DanDaDanLang.GENERIC_ABILITY_ON, p, Map.of("%name%", getName()));
        setCooldown(300); return true; }
}
