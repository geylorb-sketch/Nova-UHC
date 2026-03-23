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

public class Renard extends LoupGarouRole {

    public Renard() {
        setCamp(LGCamps.VILLAGE);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.FEATHER);
    }

    @Override
    public String getName() {
        return "§aRenard";
    }

    @Override
    public void sendDescription(Player player) {
        RoleDescription.of(player)
                .raw("§8§m---------" + ChatColor.GREEN + "Renard§8§m----------§r")
                .raw("§fVotre Objectif : " + ChatColor.GREEN + "Gagnez avec le Village")
                .raw("§fVos Pouvoir : " + ChatColor.BLUE + "Vous possedez le pouvoir de flairer, et l'effet Speed 1 de nuit.")
                .raw("§fDescription du roles : " + ChatColor.DARK_PURPLE + " Vos Flaire sont ")
                .raw("-Utilisable 3x par partie avec la commande /lg flairer.")
                .raw("-Vous pouvez flairer uniquement la nuit et dans un rayon de 20 block.")
                .raw("-Les joueur flairer seront prévenu le jour suivant.")
                .raw("-Vous devez rester proche de la cible 5min")
                .raw(" -Vos flaire vous permettent de connaitre si le jouer appartient au camps des " + ChatColor.RED + "LoupGarou§r.")
                .raw("§8§m--------------------------")
                .send();
    }


    @Override
    public PotionEffect[] getNightEffects() {
        return new PotionEffect[]{
                new PotionEffect(PotionEffectType.SPEED, 80, 0, false, false),
        };
    }

}
