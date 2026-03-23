package net.novauhc.dandadan.roles.reiko;

import net.novaproject.novauhc.ability.template.UseAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

public class MiroirActifAbility extends UseAbility {
    @Override public String getName() { return "Miroir Actif"; }
    @Override public Material getMaterial() { return Material.GLASS; }
    @Override public boolean onEnable(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*20, 1, false, true));
        LangManager.get().send(DanDaDanLang.GENERIC_ABILITY_ON, p, Map.of("%name%", getName()));
        setCooldown(420); return true; }
}
