package net.novauhc.dandadan.roles.okarun;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Mémé 100km/H — Passif Okarun
 * Si un joueur a moins de 50 blocs d'Okarun, il obtient 30% de speed à 30 blocs.
 */
public class MemePassive extends PassiveAbility {

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "MEME_TRIGGER_RANGE_NAME",
            descKey = "MEME_TRIGGER_RANGE_DESC", type = VariableType.INTEGER)
    private int triggerRange = 50;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "MEME_SPEED_RANGE_NAME",
            descKey = "MEME_SPEED_RANGE_DESC", type = VariableType.INTEGER)
    private int speedRange = 30;

    @Override public String getName() { return "Meme 100km/H"; }

    @Override
    public boolean onEnable(Player player) {
        boolean anyInTriggerRange = false;
        boolean anyInSpeedRange = false;

        for (Entity entity : player.getNearbyEntities(triggerRange, triggerRange, triggerRange)) {
            if (!(entity instanceof Player other)) continue;
            if (other.equals(player)) continue;
            double dist = other.getLocation().distance(player.getLocation());
            if (dist <= triggerRange) anyInTriggerRange = true;
            if (dist <= speedRange) anyInSpeedRange = true;
        }

        if (!anyInTriggerRange) {
            player.removePotionEffect(PotionEffectType.SPEED);
            return false;
        }

        if (anyInSpeedRange) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 0, false, false));
        } else {
            player.removePotionEffect(PotionEffectType.SPEED);
        }
        return false; // Passif pur, ne consomme pas d'usage
    }
}
