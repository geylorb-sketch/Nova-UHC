package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.uhcteam.UHCTeam;
import net.novaproject.novauhc.uhcteam.UHCTeamManager;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.lang.LangManager;

public class TeamInv extends Scenario {
    public static HashMap<UHCTeam, Inventory> inventory = new HashMap<>();

    @Override
    public String getName() {
        return "TeamInventory";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.TEAM_INV, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.CHEST);
    }

    @Override
    public void onStart(Player player) {
        for (UHCTeam t : UHCTeamManager.get().getTeams())
            inventory.put(t, Bukkit.createInventory(null, InventoryType.CHEST, "Team Inventory"));
    }

}
