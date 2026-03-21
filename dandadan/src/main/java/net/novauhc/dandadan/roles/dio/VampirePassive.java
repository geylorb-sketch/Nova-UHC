package net.novauhc.dandadan.roles.dio;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class VampirePassive extends PassiveAbility {
    @Override public String getName() { return "Vampire"; }
    @Override public boolean onEnable(Player player) {
        long time = player.getWorld().getTime();
        if (time > 13000 && time < 23000)
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 40, 0, false, false));
        return false;
    }
}
