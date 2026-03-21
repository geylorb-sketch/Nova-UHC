package net.novauhc.dandadan.roles.reze;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.entity.Player;

public class BombePassive extends PassiveAbility {
    @Override public String getName() { return "Bombe"; }

    @Override
    public boolean onEnable(Player player) {
        return false;
    }
// Explosion immunity + double jump handled in events.
}
