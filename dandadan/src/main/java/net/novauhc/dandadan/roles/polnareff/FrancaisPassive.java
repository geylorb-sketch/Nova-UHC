package net.novauhc.dandadan.roles.polnareff;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.entity.Player;

import java.util.Random;

public class FrancaisPassive extends PassiveAbility {
    @Override public String getName() { return "Francais"; }

    @Override
    public boolean onEnable(Player player) {
        return false;
    }

    private final Random random = new Random();
    public void onDamage(Player player) {
//        if (random.nextDouble() < 0.10) player.setAbsorptionAmount(Math.min(20, player.getAbsorptionAmount() + 2));
    }
}
