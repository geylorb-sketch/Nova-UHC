package net.novaproject.novauhc.scenario.role.scenario.loupgarouuhc.roles;

import net.novaproject.novauhc.scenario.role.scenario.loupgarouuhc.LGCamps;
import net.novaproject.novauhc.scenario.role.scenario.loupgarouuhc.LoupGarouRole;
import net.novaproject.novauhc.scenario.role.RoleDescription;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LoupGarou extends LoupGarouRole {


    public LoupGarou() {
        setCamp(LGCamps.LOUP_GAROU);
    }

    @Override
    public String getName() {
        return "§4Loup Garou";
    }


    @Override
    public void sendDescription(Player player) {
        RoleDescription.of(player)
                .raw("§8§m---------" + ChatColor.RED + "Loup-Garouf§8§m----------§r")
                .raw("§fVotre Objectif : " + ChatColor.RED + "Gagnez avec les Loup-Garou")
                .raw("§fVos Pouvoir : " + ChatColor.BLUE + "Vous possédez Force 1 de nuit.")
                .raw("§fDescription du roles : " + ChatColor.DARK_PURPLE + "Vous possédez la liste de vos coéquipier.")
                .raw("§8§m--------------------------")
                .send();
    }



    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.BONE);
    }

    @Override
    public PotionEffect[] getNightEffects() {
        return new PotionEffect[]{
                new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 60, 0, false, false),
        };
    }
}
