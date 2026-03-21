package net.novauhc.dandadan.roles.kashimoto;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.template.PassiveAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Protecteur — Passif Kashimoto
 * Quand une piece d'armure casse, remplacee par fer Prot II pendant 5s.
 */
public class ProtecteurPassive extends PassiveAbility {

    @Override public String getName() { return "Protecteur"; }

    @Override
    public boolean onEnable(Player player) {
        return false;
    }


    /**
     * Appelé quand une piece d'armure est cassee.
     * Remplace par fer Prot II pendant 5s.
     */
    public void onArmorBreak(Player player, int slot) {
        ItemStack replacement = createProtArmor(slot);
        if (replacement == null) return;

        switch (slot) {
            case 0 -> player.getInventory().setBoots(replacement);
            case 1 -> player.getInventory().setLeggings(replacement);
            case 2 -> player.getInventory().setChestplate(replacement);
            case 3 -> player.getInventory().setHelmet(replacement);
        }

        LangManager.get().send(DanDaDanLang.KASHIMOTO_PROTECTEUR_TRIGGER, player);

        // Retirer apres 5s
        Bukkit.getScheduler().runTaskLater(Main.get(), () -> {
            if (!player.isOnline()) return;
            switch (slot) {
                case 0 -> player.getInventory().setBoots(null);
                case 1 -> player.getInventory().setLeggings(null);
                case 2 -> player.getInventory().setChestplate(null);
                case 3 -> player.getInventory().setHelmet(null);
            }
        }, 100L); // 5s
    }

    private ItemStack createProtArmor(int slot) {
        Material mat = switch (slot) {
            case 0 -> Material.IRON_BOOTS;
            case 1 -> Material.IRON_LEGGINGS;
            case 2 -> Material.IRON_CHESTPLATE;
            case 3 -> Material.IRON_HELMET;
            default -> null;
        };
        if (mat == null) return null;
        ItemStack item = new ItemStack(mat);
        item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        return item;
    }
}
