package net.novaproject.novauhc.scenario.role.scenario.loupgarouuhc.roles;

import net.novaproject.novauhc.scenario.role.scenario.loupgarouuhc.LGCamps;
import net.novaproject.novauhc.scenario.role.scenario.loupgarouuhc.LoupGarouRole;
import net.novaproject.novauhc.scenario.role.RoleDescription;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Villageois extends LoupGarouRole {


    public Villageois() {
        setCamp(LGCamps.VILLAGE);
    }

    @Override
    public String getName() {
        return "§aVillageois";
    }

    @Override
    public void sendDescription(Player player) {
        RoleDescription.of(player)
                .raw("§8§m---------" + ChatColor.GREEN + "Villageois§8§m----------§r")
                .raw("§fVotre Objectif : " + ChatColor.GREEN + "Gagnez avec le Village")
                .raw("§fVos Pouvoir : " + ChatColor.BLUE + "Aucun")
                .raw("§fDescription du roles : " + ChatColor.DARK_PURPLE + " Vous avez aucun pouvoir. Bonne chance et Bonne survie !")
                .raw("§8§m--------------------------")
                .send();
    }


    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.EMERALD);
    }

}
