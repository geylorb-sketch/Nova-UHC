package net.novaproject.novauhc.ability.template;

import net.novaproject.novauhc.ability.Ability;
import org.bukkit.entity.Player;

public abstract class PassiveAbility extends Ability {

    @Override
    public void onSec(Player player) {
        tryUse(player);
    }
}
