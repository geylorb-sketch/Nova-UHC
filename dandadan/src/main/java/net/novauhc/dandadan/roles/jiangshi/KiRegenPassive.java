package net.novauhc.dandadan.roles.jiangshi;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.entity.Player;

public class KiRegenPassive extends PassiveAbility {
    @Override public String getName() { return "KiRegen"; }

    @Override
    public boolean onEnable(Player player) {
        return false;
    }
// Ki regen handled in onSec tick.
}
