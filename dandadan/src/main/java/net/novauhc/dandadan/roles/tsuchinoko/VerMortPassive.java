package net.novauhc.dandadan.roles.tsuchinoko;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class VerMortPassive extends PassiveAbility {
    @Override public String getName() { return "VerMort"; }

    @Override
    public boolean onEnable(Player player) {
        return false;
    }

    private final Random random = new Random();
    public void onHit(Player victim) {
        if (random.nextDouble() < 0.10) victim.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80, 0, false, true));
    }
}
