package net.novaproject.ultimate.legend.roles.abilities;

import net.novaproject.novauhc.ability.PassiveAbility;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class TankResistancePassive extends PassiveAbility {

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "TANK_RESIST_THRESHOLD_NAME", descKey = "TANK_RESIST_THRESHOLD_DESC", type = VariableType.DOUBLE)
    private double healthThreshold = 11.0;

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "TANK_RESIST_LEVEL_NAME", descKey = "TANK_RESIST_LEVEL_DESC", type = VariableType.INTEGER)
    private int resistLevel = 1;

    public TankResistancePassive() { setCooldown(0); }

    @Override public String getName() { return "Résistance Tank"; }
    @Override public Material getMaterial() { return null; }

    @Override
    public boolean onEnable(Player player) {
        if (resistLevel <= 0) return false;
        if (player.getHealth() < healthThreshold) {
            player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 80, resistLevel - 1, false, false));
            return true;
        }
        return false;
    }
}
