package net.novauhc.dandadan.roles.jotaro;

import net.novaproject.novauhc.ability.PassiveAbility;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Casquette — Passif Jotaro
 * Gitbook: Tous les casques de Jotaro sont incassables.
 * Implémentation: chaque seconde, set unbreakable sur le casque.
 */
public class CasquettePassive extends PassiveAbility {

    @Override public String getName() { return "Casquette"; }

    @Override
    public boolean onEnable(Player player) {
        ItemStack helmet = player.getInventory().getHelmet();
        if (helmet == null) return false;

        ItemMeta meta = helmet.getItemMeta();
        if (meta != null && !meta.spigot().isUnbreakable()) {
            meta.spigot().setUnbreakable(true);
            helmet.setItemMeta(meta);
            player.getInventory().setHelmet(helmet);
        }
        return false; // Passif, ne consomme pas d'usage
    }
}
