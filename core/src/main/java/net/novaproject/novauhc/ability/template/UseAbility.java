package net.novaproject.novauhc.ability.template;

import net.novaproject.novauhc.ability.Ability;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public abstract class UseAbility extends Ability {

    private final ItemStack item;

    public UseAbility() {
        this.item = getItemStack();
    }

    @Override
    public void onClick(PlayerInteractEvent event, ItemStack item) {
        if (item == null) return;
        if (getOwner() == null || !event.getPlayer().equals(getOwner().getPlayer())) return;
        if (item.isSimilar(this.item)) {
            tryUse(event.getPlayer());
        }
    }
}
