package net.novaproject.ultimate.legend.roles.abilities;

import net.novaproject.novauhc.ability.PassiveAbility;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class PrisonnierSpeedPassive extends PassiveAbility {

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "PRISONNIER_SPEED_LEVEL_NAME", descKey = "PRISONNIER_SPEED_LEVEL_DESC", type = VariableType.INTEGER)
    private int speedLevel = 1;

    public PrisonnierSpeedPassive() { setCooldown(0); }

    @Override public String getName() { return "Vitesse Prisonnier"; }
    @Override public Material getMaterial() { return null; }

    @Override
    public boolean onEnable(Player player) {
        if (speedLevel <= 0) return false;
        player.removePotionEffect(PotionEffectType.SPEED);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, speedLevel - 1, false, false));
        return true;
    }
}
