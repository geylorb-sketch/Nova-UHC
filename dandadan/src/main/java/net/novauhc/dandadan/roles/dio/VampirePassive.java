package net.novauhc.dandadan.roles.dio;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class VampirePassive extends Ability {

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "VAMPIRE_STRENGTH_AMP_NAME", descKey = "VAMPIRE_STRENGTH_AMP_DESC", type = VariableType.INTEGER)
    private int strengthAmplifier = 0; // 0 = Strength I

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "VAMPIRE_NIGHT_TIME_START_NAME", descKey = "VAMPIRE_NIGHT_TIME_START_DESC", type = VariableType.INTEGER)
    private int nightTimeStart = 12500;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "VAMPIRE_NIGHT_TIME_END_NAME", descKey = "VAMPIRE_NIGHT_TIME_END_DESC", type = VariableType.INTEGER)
    private int nightTimeEnd = 23000;
    @Override public String getName()       { return "Vampire"; }
    @Override public Material getMaterial() { return null; }

    @Override public boolean onEnable(Player player) { return true; }

    @Override public void onSec(Player player) {
        if (player.getWorld().getTime() > nightTimeStart || player.getWorld().getTime() < nightTimeEnd)
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 40, strengthAmplifier, false, false));
    }
}