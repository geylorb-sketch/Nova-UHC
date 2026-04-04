package net.novaproject.ultimate.skyhigt;

import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.special.SkyHighLang;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.uhcteam.UHCTeam;
import net.novaproject.novauhc.uhcteam.UHCTeamManager;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novaproject.novauhc.UHCManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class SkyHigh extends Scenario {

    @ScenarioVariable(
            nameKey = "SKYHIGH_VAR_FIRST_LEVEL_NAME",
            descKey = "SKYHIGH_VAR_FIRST_LEVEL_DESC",
            type = VariableType.INTEGER
    )
    private int firstLevel = 120;

    @ScenarioVariable(

            nameKey = "SKYHIGH_VAR_SECOND_LEVEL_NAME",
            descKey = "SKYHIGH_VAR_SECOND_LEVEL_DESC",
            type = VariableType.INTEGER
    )
    private int secondLevel = 80;

    @ScenarioVariable(
            
            nameKey = "SKYHIGH_VAR_THIRD_LEVEL_NAME",
            descKey = "SKYHIGH_VAR_THIRD_LEVEL_DESC",
            type = VariableType.INTEGER
    )
    private int thirdLevel = 40;

    @ScenarioVariable(

            nameKey = "SKYHIGH_VAR_FIRST_DAMAGE_NAME",
            descKey = "SKYHIGH_VAR_FIRST_DAMAGE_DESC",
            type = VariableType.INTEGER
    )
    private int firstDamage = 5;

    @ScenarioVariable(
            nameKey = "SKYHIGH_VAR_SECOND_DAMAGE_NAME",
            descKey = "SKYHIGH_VAR_SECOND_DAMAGE_DESC",
            type = VariableType.INTEGER
    )
    private int secondDamage = 3;

    @ScenarioVariable(
            nameKey = "SKYHIGH_VAR_THIRD_DAMAGE_NAME",
            descKey = "SKYHIGH_VAR_THIRD_DAMAGE_DESC",
            type = VariableType.INTEGER
    )
    private int thirdDamage = 1;

    @Override
    public String getName() {
        return "SkyHigh";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(SkyHighLang.WARNING_SKY_HIGH, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.PAPER);
    }

    @Override
    public void scatter(UHCPlayer uhcPlayer, Location location, HashMap<UHCTeam, Location> teamloc) {
        if (UHCManager.get().getTeam_size() != 1) {
            UHCTeamManager.get().scatterTeam(uhcPlayer, teamloc);
        } else {
            uhcPlayer.getPlayer().teleport(location);
        }
    }

    @Override
    public void onStart(Player player) {
        player.getInventory().addItem(new ItemStack(Material.DIRT));
    }

    @Override
    public void onSec(Player player) {
        int timer = UHCManager.get().getTimer();

        if (timer == UHCManager.get().getTimerborder() - 120) {
            LangManager.get().send(SkyHighLang.WARNING_SKY_HIGH, player);
        }
        int y = player.getLocation().getBlockY();
        if (timer >= UHCManager.get().getTimerborder()) {
            if (y < thirdLevel) {
                LangManager.get().send(SkyHighLang.DAMAGE_THIRD_LAYER, player);
                player.damage(thirdDamage);
            } else if (y < secondLevel) {
                LangManager.get().send(SkyHighLang.DAMAGE_SECOND_LAYER, player);
                player.damage(secondDamage);
            } else if (y < firstLevel) {
                LangManager.get().send(SkyHighLang.DAMAGE_FIRST_LAYER, player);
                player.damage(firstDamage);
            }
        }
    }

    @Override
    public void onPlace(Player player, Block block, BlockPlaceEvent event) {
        if (block.getType() == Material.DIRT) {
            int dirtCount = 0;
            for (ItemStack item : player.getInventory().getContents()) {
                if (item != null && item.getType() == Material.DIRT) {
                    dirtCount += item.getAmount();
                }
            }
            if (dirtCount - 2 < 64) {
                player.getInventory().addItem(new ItemStack(Material.DIRT, 2));
            }
        }
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}