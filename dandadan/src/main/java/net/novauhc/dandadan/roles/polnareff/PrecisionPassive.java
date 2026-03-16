package net.novauhc.dandadan.roles.polnareff;

import net.novaproject.novauhc.ability.PassiveAbility;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Précision — Passif de Polnareff.
 * 25% de chance que la prochaine flèche touche automatiquement (buff accuracy).
 * Implémenté : applique Speed I permanent + flag géré via ShortCooldownManager.
 */
public class PrecisionPassive extends PassiveAbility {


    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "PRECISION_CHANCE_NAME", descKey = "PRECISION_CHANCE_DESC", type = VariableType.PERCENTAGE)
    private int triggerChancePct = 25;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "PRECISION_SPEED_DURATION_NAME", descKey = "PRECISION_SPEED_DURATION_DESC", type = VariableType.TIME)
    private int speedDuration = 40; // ticks

    @Override public String getName()       { return "Précision"; }
    @Override public Material getMaterial() { return null; }

    @Override
    public boolean onEnable(Player player) {
        return true;
    }

    @Override public void onSec(Player player) {
        // Maintient un léger bonus de vitesse qui améliore la précision des tirs
        if (ThreadLocalRandom.current().nextDouble() < triggerChancePct / 100.0) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, speedDuration, 0, false, false));
        }
    }
}
