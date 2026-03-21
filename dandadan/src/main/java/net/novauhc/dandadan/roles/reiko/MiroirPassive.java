package net.novauhc.dandadan.roles.reiko;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.entity.Player;

import java.util.Random;

public class MiroirPassive extends PassiveAbility {
    @Override public String getName() { return "Miroir"; }

    @Override
    public boolean onEnable(Player player) {
        return false;
    }

    private final Random random = new Random();
    public boolean shouldReflect() { return random.nextDouble() < 0.10; }
}
