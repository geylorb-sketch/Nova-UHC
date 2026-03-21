package net.novauhc.dandadan.roles.joseph;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.entity.Player;

import java.util.Random;

public class PredictionPassive extends PassiveAbility {
    @Override public String getName() { return "Prediction"; }

    @Override
    public boolean onEnable(Player player) {
        return false;
    }

    private final Random random = new Random();
    public boolean shouldPredict() { return random.nextDouble() < 0.05; }
}
