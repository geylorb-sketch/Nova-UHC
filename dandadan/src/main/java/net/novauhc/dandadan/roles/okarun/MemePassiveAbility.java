package net.novauhc.dandadan.roles.okarun;

import net.novaproject.novauhc.ability.PassiveAbility;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MemePassiveAbility extends PassiveAbility {

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "OKARUN_MEME_TRIGGER_RANGE_NAME", descKey = "OKARUN_MEME_TRIGGER_RANGE_DESC", type = VariableType.INTEGER)
    private int triggerRange = 50;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "OKARUN_MEME_SPEED_RANGE_NAME", descKey = "OKARUN_MEME_SPEED_RANGE_DESC", type = VariableType.INTEGER)
    private int speedRange = 30;

    @Override public String getName() { return "Mémé 100km/H"; }

    @Override
    public boolean onEnable(Player player) {
        boolean anyNear = player.getWorld()
                .getNearbyEntities(player.getLocation(), triggerRange, triggerRange, triggerRange)
                .stream().filter(e -> e instanceof Player && !e.equals(player))
                .anyMatch(e -> e.getLocation().distance(player.getLocation()) <= triggerRange);

        if (!anyNear) { player.removePotionEffect(PotionEffectType.SPEED); return false; }

        boolean veryClose = player.getWorld()
                .getNearbyEntities(player.getLocation(), speedRange, speedRange, speedRange)
                .stream().filter(e -> e instanceof Player && !e.equals(player))
                .anyMatch(e -> e.getLocation().distance(player.getLocation()) <= speedRange);

        if (veryClose) player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 0, false, false));
        else player.removePotionEffect(PotionEffectType.SPEED);
        return true;
    }
}
