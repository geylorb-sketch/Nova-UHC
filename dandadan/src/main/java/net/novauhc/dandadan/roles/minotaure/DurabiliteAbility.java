package net.novauhc.dandadan.roles.minotaure;

import net.novaproject.novauhc.ability.template.UseAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

public class DurabiliteAbility extends UseAbility {
    @Override public String getName() { return "Durabilite Immense"; }
    @Override public Material getMaterial() { return Material.IRON_INGOT; }
    @Override public boolean onEnable(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 30*20, 1, false, true));
        LangManager.get().send(DanDaDanLang.GENERIC_ABILITY_ON, p, Map.of("%name%", getName()));
        setCooldown(300); return true; }
}
