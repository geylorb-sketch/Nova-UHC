package net.novaproject.novauhc.ability.template;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public abstract class ReviveAbility extends Ability {

    @Override
    public Material getMaterial() {
        return null;
    }

    @Override
    public boolean onEnable(Player player) {
        return false;
    }

    /**
     * Override to restrict who can revive whom.
     * Default: no restriction.
     */
    public boolean canRevive(UHCPlayer reviver, UHCPlayer target) {
        return true;
    }

    /**
     * Called after the base revive (restore items/location/effects) is done.
     * Override to apply side effects (camp change, buff, etc.).
     */
    public void onRevive(UHCPlayer reviver, UHCPlayer target) {}

    /**
     * Lower value = higher priority (executes first when multiple revive abilities compete).
     */
    public int getPriority() {
        return 0;
    }
}
