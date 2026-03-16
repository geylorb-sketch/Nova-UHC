package net.novauhc.dandadan.roles.caesar;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BandanaPassive extends Ability {
    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "BANDANA_RESIST_AMP_NAME", descKey = "BANDANA_RESIST_AMP_DESC", type = VariableType.INTEGER)
    private int resistAmplifier = 0; // Résistance I

    @Override public String getName()       { return "Bandana de Caesar"; }
    @Override public Material getMaterial() { return null; }

    @Override public boolean onEnable(Player player) { return true; }

    @Override public void onSec(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40, resistAmplifier, false, false));
    }
}