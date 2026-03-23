package net.novauhc.dandadan.roles.kinta;

import net.novaproject.novauhc.ability.template.UseAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Great Kinta — Actif Kinta (Clic-Droit, GOLD_HELMET)
 * Armure en or avec +1 point d'armure. CD 10min +1min/kill.
 */
public class GreatKintaAbility extends UseAbility {

    private boolean active = false;

    @Override public String getName()       { return "Great Kinta"; }
    @Override public Material getMaterial() { return Material.GOLD_HELMET; }

    @Override
    public boolean onEnable(Player player) {
        active = true;
        player.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.GOLD_BOOTS));
        LangManager.get().send(DanDaDanLang.KINTA_GREAT_ON, player);
        setCooldown(600);
        return true;
    }

    public boolean isActive() { return active; }
    public void setActive(boolean a) { this.active = a; }
}
