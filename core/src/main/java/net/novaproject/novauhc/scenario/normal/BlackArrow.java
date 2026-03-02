package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.lang.LangManager;

public class BlackArrow extends Scenario {
    @Override
    public String getName() {
        return "BlackArrow";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.BLACK_ARROW, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.ARROW);
    }

    @Override
    public void onBreak(Player player, Block block, BlockBreakEvent event) {


        Location loc = block.getLocation().clone().add(0.5D, 0.5D, 0.5D);

        if (block.getType() == Material.COAL_ORE) {
            loc.getWorld().dropItemNaturally(loc, new ItemStack(Material.ARROW));
        }
    }
}
