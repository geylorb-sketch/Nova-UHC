package net.novauhc.dandadan.roles.minotaure;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.entity.Player;

import java.util.Random;

public class OxydationPassive extends PassiveAbility {
    @Override public String getName() { return "Oxydation"; }

    @Override
    public boolean onEnable(Player player) {
        return false;
    }

    private final Random random = new Random();
    public void onHit(Player victim) {
        if (random.nextDouble() < 0.15) victim.setFireTicks(60);
    }
}
