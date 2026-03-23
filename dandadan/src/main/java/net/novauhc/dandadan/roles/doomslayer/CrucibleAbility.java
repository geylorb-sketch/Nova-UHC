package net.novauhc.dandadan.roles.doomslayer;

import net.novaproject.novauhc.ability.template.UseAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

public class CrucibleAbility extends UseAbility {
    @Override public String getName() { return "Crucible Blade"; }
    @Override public Material getMaterial() { return Material.IRON_SWORD; }
    @Override public boolean onEnable(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 10*20, 2, false, true));
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10*20, 1, false, true));
        LangManager.get().send(DanDaDanLang.GENERIC_ABILITY_ON, p, Map.of("%name%", getName()));
        setCooldown(300); return true; }
}
