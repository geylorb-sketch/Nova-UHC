package net.novauhc.dandadan.roles.reze;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BombePassive extends Ability {
    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "BOMBE_PASSIVE_EXPLOSION_POWER_NAME", descKey = "BOMBE_PASSIVE_EXPLOSION_POWER_DESC", type = VariableType.DOUBLE)
    private double explosionPower = 1.5; // puissance de l'explosion au contact

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "BOMBE_PASSIVE_TRIGGER_CHANCE_NAME", descKey = "BOMBE_PASSIVE_TRIGGER_CHANCE_DESC", type = VariableType.PERCENTAGE)
    private int triggerChancePct = 10; // % chance d'exploser sur coup reçu

    @Override public String getName()       { return "Bombe"; }
    @Override public Material getMaterial() { return null; }

    @Override public boolean onEnable(Player player) { return true; }

    @Override public void onSec(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 40, 0, false, false));
    }
}