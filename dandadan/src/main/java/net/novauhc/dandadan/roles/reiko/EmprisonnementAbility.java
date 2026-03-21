package net.novauhc.dandadan.roles.reiko;

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

public class EmprisonnementAbility extends UseAbiliy {
    @Override public String getName() { return "Emprisonnement"; }
    @Override public Material getMaterial() { return Material.IRON_FENCE; }
    @Override public boolean onEnable(Player p) {
        for (Entity e : p.getNearbyEntities(15,15,15)) {
            if (e instanceof Player t) {
                Vector toT = t.getLocation().toVector().subtract(p.getLocation().toVector()).normalize();
                if (p.getLocation().getDirection().normalize().dot(toT) > 0.85) {
                    t.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 127, false, false));
                    t.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100, 128, false, false)); break;
                }
            }
        }
        LangManager.get().send(DanDaDanLang.GENERIC_ABILITY_ON, p, Map.of("%name%", getName()));
        setCooldown(300); return true; }
}
