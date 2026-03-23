package net.novauhc.dandadan.roles.oeilmalefique;

import net.novaproject.novauhc.ability.template.UseAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Map;

public class MaledictionOAbility extends UseAbility {
    @Override public String getName() { return "Malediction"; }
    @Override public Material getMaterial() { return Material.FERMENTED_SPIDER_EYE; }
    @Override public boolean onEnable(Player p) {
        for (Entity e : p.getNearbyEntities(15,15,15)) {
            if (e instanceof Player t) {
                Vector toT = t.getLocation().toVector().subtract(p.getLocation().toVector()).normalize();
                if (p.getLocation().getDirection().normalize().dot(toT) > 0.85) {
                    t.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 200, 0, false, true));
                    t.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 0, false, true)); break;
                }
            }
        }
        LangManager.get().send(DanDaDanLang.GENERIC_ABILITY_ON, p, Map.of("%name%", getName()));
        setCooldown(420); return true; }
}
