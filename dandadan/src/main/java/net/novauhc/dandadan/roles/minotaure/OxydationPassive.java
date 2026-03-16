package net.novauhc.dandadan.roles.minotaure;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class OxydationPassive extends Ability {

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "OXYDATION_FIRE_PCT_NAME", descKey = "OXYDATION_FIRE_PCT_DESC", type = VariableType.PERCENTAGE)
    private int fireReductionPct = 100; // 100 = immunité totale au feu

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "OXYDATION_FIRE_AMP_NAME", descKey = "OXYDATION_FIRE_AMP_DESC", type = VariableType.INTEGER)
    private int fireResistanceAmp = 0;
    @Override public String getName()       { return "Oxydation"; }
    @Override public Material getMaterial() { return null; }

    @Override public boolean onEnable(Player player) { return true; }

    @Override public void onSec(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 40, fireResistanceAmp, false, false));
    }
}