package net.novauhc.dandadan.roles.acrobatique;

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

public class CheveuxAbility extends UseAbility {

    public CheveuxAbility (){
        setCooldown(300);
    }
    @Override public String getName() { return "Cheveux"; }
    @Override public Material getMaterial() { return Material.STRING; }
    @Override public boolean onEnable(Player p) {
        for (Entity e : p.getNearbyEntities(15,15,15)) {
            if (e instanceof Player t) {
                Vector toT = t.getLocation().toVector().subtract(p.getLocation().toVector()).normalize();
                if (p.getLocation().getDirection().normalize().dot(toT) > 0.85) {
                    t.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 127, false, false)); break;
                }
            }
        }
        LangManager.get().send(DanDaDanLang.GENERIC_ABILITY_ON, p, Map.of("%name%", getName()));
        return true;
    }
}
