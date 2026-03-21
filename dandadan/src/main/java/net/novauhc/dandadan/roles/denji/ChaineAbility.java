package net.novauhc.dandadan.roles.denji;

import net.novaproject.novauhc.ability.template.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

public class ChaineAbility extends UseAbiliy {
    @Override public String getName() { return "Chaine"; }
    @Override public Material getMaterial() { return Material.TRIPWIRE_HOOK; }
    @Override public boolean onEnable(Player p) {
        Player target = null;
        for (Entity e : p.getNearbyEntities(8,8,8)) {
            if (e instanceof Player t) { target = t; break; }
        }
        if (target == null) return false;
        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 127, false, false));
        LangManager.get().send(DanDaDanLang.GENERIC_ABILITY_ON, p, Map.of("%name%", getName()));
        setCooldown(300); return true; }
}
