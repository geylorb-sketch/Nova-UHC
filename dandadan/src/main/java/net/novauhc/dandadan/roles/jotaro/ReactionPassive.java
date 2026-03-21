package net.novauhc.dandadan.roles.jotaro;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.entity.Player;

public class ReactionPassive extends PassiveAbility {
    @Override public String getName() { return "Reaction"; }

    @Override
    public boolean onEnable(Player player) {
        return false;
    }
// Projectile cancellation handled in EntityDamageByEntityEvent.
}
