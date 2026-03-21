package net.novauhc.dandadan.roles.jetbooster;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TeteHautePassive extends PassiveAbility {
    @Override public String getName() { return "TeteHaute"; }
    @Override public boolean onEnable(Player player) {
        if (player.getLocation().getY() > 80)
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 0, false, false));
        return false;
    }
}
