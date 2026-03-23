package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioManager;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.lang.LangManager;

public class DoubleOre extends Scenario {

    private static final String PLAYER_PLACED_TAG = "playerPlaced";

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "DOUBLEORE_VAR_DOUBLE_IRON_NAME", descKey = "DOUBLEORE_VAR_DOUBLE_IRON_DESC", type = VariableType.BOOLEAN)
    private boolean doubleIron = true;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "DOUBLEORE_VAR_DOUBLE_GOLD_NAME", descKey = "DOUBLEORE_VAR_DOUBLE_GOLD_DESC", type = VariableType.BOOLEAN)
    private boolean doubleGold = true;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "DOUBLEORE_VAR_DOUBLE_DIAMOND_NAME", descKey = "DOUBLEORE_VAR_DOUBLE_DIAMOND_DESC", type = VariableType.BOOLEAN)
    private boolean doubleDiamond = true;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "DOUBLEORE_VAR_DOUBLE_EMERALD_NAME", descKey = "DOUBLEORE_VAR_DOUBLE_EMERALD_DESC", type = VariableType.BOOLEAN)
    private boolean doubleEmerald = true;

    @Override
    public String getName() {
        return "DoubleOre";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.DOUBLE_ORE, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.GOLD_BLOCK);
    }

    @Override
    public void onBreak(Player player, Block block, BlockBreakEvent event) {
        if (!isActive()) return;

        Material type = block.getType();
        List<Material> types = Arrays.asList(Material.IRON_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE);
        if (!types.contains(type)) return;

        if (block.hasMetadata(PLAYER_PLACED_TAG)) {
            block.removeMetadata(PLAYER_PLACED_TAG, Main.get());
            return;
        }

        event.setCancelled(true);
        block.setType(Material.AIR);

        Location loc = block.getLocation().clone().add(0.5, 0.5, 0.5);
        Optional<Scenario> cutCleanScenario = ScenarioManager.get().getScenarioByName("Cutclean");
        boolean cutCleanActive = cutCleanScenario.isPresent() && ScenarioManager.get().getActiveScenarios().contains(cutCleanScenario.get());

        switch (type) {
            case IRON_ORE:
                if (doubleIron) {
                    loc.getWorld().dropItemNaturally(loc, new ItemStack(cutCleanActive ? Material.IRON_INGOT : Material.IRON_ORE, 2));
                }
                break;
            case GOLD_ORE:
                if (doubleGold) {
                    loc.getWorld().dropItemNaturally(loc, new ItemStack(cutCleanActive ? Material.GOLD_INGOT : Material.GOLD_ORE, 2));
                }
                break;
            case DIAMOND_ORE:
                if (doubleDiamond) {
                    loc.getWorld().dropItemNaturally(loc, new ItemStack(Material.DIAMOND, 2));
                }
                break;
            case EMERALD_ORE:
                if (doubleEmerald) {
                    loc.getWorld().dropItemNaturally(loc, new ItemStack(Material.EMERALD, 2));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onPlace(Player player, Block block, BlockPlaceEvent event) {
        block.setMetadata(PLAYER_PLACED_TAG, new FixedMetadataValue(Main.get(), true));
    }
}
