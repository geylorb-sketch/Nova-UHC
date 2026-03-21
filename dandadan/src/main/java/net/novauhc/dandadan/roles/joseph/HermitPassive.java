package net.novauhc.dandadan.roles.joseph;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.entity.Player;

public class HermitPassive extends PassiveAbility {
    @Override public String getName() { return "Hermit"; }

    @Override
    public boolean onEnable(Player player) {
        return false;
    }
// Grappling hook implemented via PlayerInteractEvent.
}
