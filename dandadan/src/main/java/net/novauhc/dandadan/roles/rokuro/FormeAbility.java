package net.novauhc.dandadan.roles.rokuro;

import net.novaproject.novauhc.ability.template.UseAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

public class FormeAbility extends UseAbility {
    @Override public String getName() { return "Forme Serpienne"; }
    @Override public Material getMaterial() { return Material.ENDER_PEARL; }
    @Override public boolean onEnable(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30*20, 1, false, true));
        p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 30*20, 0, false, true));
        LangManager.get().send(DanDaDanLang.GENERIC_ABILITY_ON, p, Map.of("%name%", getName()));
        setCooldown(300); return true; }
}
