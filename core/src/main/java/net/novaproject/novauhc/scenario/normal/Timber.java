package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.Common;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashSet;
import java.util.Set;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.lang.LangManager;

public class Timber extends Scenario {
    @Override
    public String getName() {
        return "Timber";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.TIMBER, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.LOG);
    }

    @Override
    public void onBreak(Player player, Block block, BlockBreakEvent event) {
        if (block.getType() == Material.LOG ||block.getType() == Material.LOG_2) {
            if (!Common.get().getArena().getPVP()) {
                breakTree(event.getBlock());
            }
        }
    }

    private void breakTree(Block block) {
        Set<Location> visited = new HashSet<>();
        Set<Block> toBreak = new HashSet<>();
        recursivelyFindLogs(block, visited, toBreak);

        for (Block log : toBreak) {
            log.breakNaturally();
        }
    }

    private void recursivelyFindLogs(Block block, Set<Location> visited, Set<Block> toBreak) {
        Location loc = block.getLocation();
        if (visited.contains(loc)) return;
        visited.add(loc);

        if (block.getType() == Material.LOG || block.getType() == Material.LOG_2) {
            toBreak.add(block);

            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        recursivelyFindLogs(block.getRelative(dx, dy, dz), visited, toBreak);
                    }
                }
            }
        }
    }
}
