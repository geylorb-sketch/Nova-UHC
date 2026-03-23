package net.novauhc.dandadan.roles.mantis;

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

public class JetWaterAbility extends UseAbility {
    @Override public String getName() { return "Jet Water"; }
    @Override public Material getMaterial() { return Material.WATER_BUCKET; }
    @Override public boolean onEnable(Player p) {
        for (Entity e : p.getNearbyEntities(10,10,10)) {
            if (e instanceof Player t) {
                Vector push = t.getLocation().toVector().subtract(p.getLocation().toVector()).normalize().multiply(2);
                t.setVelocity(push); t.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 1, false, true));
            }
        }
        LangManager.get().send(DanDaDanLang.GENERIC_ABILITY_ON, p, Map.of("%name%", getName()));
        setCooldown(300); return true; }
}
