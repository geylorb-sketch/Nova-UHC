package net.novauhc.dandadan.roles.mantis;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class BusinessPassive extends PassiveAbility {
    @Override public String getName() { return "Business"; }

    @Override
    public boolean onEnable(Player player) {
        return false;
    }

    private final Random random = new Random();
    public void onHit(Player attacker) {
        if (random.nextDouble() < 0.15) attacker.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 60, 0, false, false));
    }
}
