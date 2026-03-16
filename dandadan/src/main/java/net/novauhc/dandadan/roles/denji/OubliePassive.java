package net.novauhc.dandadan.roles.denji;

import net.novaproject.novauhc.ability.Ability;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Oublie — Passif de Denji.
 * Quand Denji tue un joueur, son stuff tombe directement dans son inventaire.
 * Géré via onKill dans DenjiRole. Cette ability signale simplement le passif actif.
 */
public class OubliePassive extends Ability {

    @Override public String getName()       { return "Oublie"; }
    @Override public Material getMaterial() { return null; }

    @Override
    public boolean onEnable(Player player) { return true; }

    /**
     * Appelé manuellement depuis DenjiRole.onKill pour transférer le loot.
     */
    public void collectLoot(Player denji, java.util.Collection<ItemStack> drops) {
        for (ItemStack item : drops) {
            if (item == null || item.getType() == Material.AIR) continue;
            java.util.HashMap<Integer, ItemStack> leftover = denji.getInventory().addItem(item);
            leftover.values().forEach(i -> denji.getWorld().dropItem(denji.getLocation(), i));
        }
    }
}
