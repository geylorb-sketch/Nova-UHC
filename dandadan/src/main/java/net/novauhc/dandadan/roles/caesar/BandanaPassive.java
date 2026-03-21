package net.novauhc.dandadan.roles.caesar;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.entity.Player;

public class BandanaPassive extends PassiveAbility {
    @Override public String getName() { return "Bandana"; }

    @Override
    public boolean onEnable(Player player) {
        return false;
    }
}
