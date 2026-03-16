package net.novauhc.dandadan.roles.kira;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ExplosionImmunePassive extends Ability {
    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "EXPLOSION_IMMUNE_AMP_NAME", descKey = "EXPLOSION_IMMUNE_AMP_DESC", type = VariableType.INTEGER)
    private int fireResistAmp = 0;

    @Override public String getName()       { return "Explosion"; }
    @Override public Material getMaterial() { return null; }

    @Override public boolean onEnable(Player player) { return true; }

    @Override public void onSec(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 40, fireResistAmp, false, false));
    }
}