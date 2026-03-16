package net.novaproject.novauhc.ability;

import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Ability déclenchée par commande (/ddd <key> [args]).
 * @AbilityVariable fonctionne normalement — AbbilityConfigUi remonte la hiérarchie.
 */
public abstract class CommandAbility extends Ability {

    public abstract String getCommandKey();
    public abstract boolean onCommand(Player player, String[] args);

    private String[] pendingArgs = new String[0];

    @Override public Material getMaterial() { return null; }

    @Override
    public boolean onEnable(Player player) {
        return onCommand(player, pendingArgs);
    }

    public boolean tryCommandUse(Player player, String[] args) {
        this.pendingArgs = args != null ? args : new String[0];
        return tryUse(player);
    }

    public UHCPlayer getUHCPlayer(Player player) {
        return UHCPlayerManager.get().getPlayer(player);
    }
}
