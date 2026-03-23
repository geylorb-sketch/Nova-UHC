package net.novauhc.dandadan.roles.kira;

import net.novaproject.novauhc.ability.template.UseAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

public class BitesDustAbility extends UseAbility {
    @Override public String getName() { return "Bites the Dust"; }
    @Override public Material getMaterial() { return Material.WATCH; }
    @Override public boolean onEnable(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*20, 2, false, true));
        LangManager.get().send(DanDaDanLang.GENERIC_ABILITY_ON, p, Map.of("%name%", getName()));
        setCooldown(300); return true; }
}
