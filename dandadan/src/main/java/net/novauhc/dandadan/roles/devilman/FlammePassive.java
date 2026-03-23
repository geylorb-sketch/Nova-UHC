package net.novauhc.dandadan.roles.devilman;

import net.novaproject.novauhc.ability.template.MeleeAbility;
import org.bukkit.entity.Player;

import java.util.Random;

public class FlammePassive extends MeleeAbility {
    public FlammePassive() {
        super();
    }

    @Override public String getName() { return "Flamme"; }

    @Override
    public boolean onEnable(Player player) {
        if (random.nextDouble() < 0.15) getTarget().getPlayer().setFireTicks(60);
        return true;
    }

    private final Random random = new Random();

}
