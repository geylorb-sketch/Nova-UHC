package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.lang.CommonLang;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;

public class Rodless extends Scenario {
    @Override
    public String getName() {
        return "Rodless";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.RODLESS, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.FISHING_ROD);

    }

    @Override
    public void onPlayerInteract(Player player, PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item != null && item.getType() == Material.FISHING_ROD) {
            event.setCancelled(true);
            LangManager.get().send(CommonLang.DISABLE_ACTION, player);
        }
    }

    @Override
    public void onCraft(ItemStack result, CraftItemEvent event) {
        ItemStack item = event.getRecipe().getResult();
        if (item.getType() == Material.FISHING_ROD) {
            event.setCancelled(true);
            LangManager.get().send(CommonLang.BLOCKED_CRAFT_ITEM, (Player) event.getWhoClicked());
        }
    }
}
