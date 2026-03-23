package net.novaproject.ultimate.legend.roles.abilities;

import net.novaproject.novauhc.ability.template.UseAbility;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class NainArmorActive extends UseAbility {

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "NAIN_ARMOR_DURATION_NAME", descKey = "NAIN_ARMOR_DURATION_DESC", type = VariableType.INTEGER)
    private int armorDuration = 30;

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "NAIN_ARMOR_PROT_LEVEL_NAME", descKey = "NAIN_ARMOR_PROT_LEVEL_DESC", type = VariableType.INTEGER)
    private int protLevel = 2;

    public NainArmorActive() { setCooldown(600); setMaxUse(-1); }

    @Override public String getName() { return "Armure du Nain"; }

    @Override
    public boolean onEnable(Player player) {
        if (protLevel <= 0) return false;
        PlayerInventory inv = player.getInventory();
        inv.setHelmet(piece(Material.DIAMOND_HELMET)); inv.setChestplate(piece(Material.DIAMOND_CHESTPLATE));
        inv.setLeggings(piece(Material.DIAMOND_LEGGINGS)); inv.setBoots(piece(Material.DIAMOND_BOOTS));
        player.updateInventory();
        Bukkit.getScheduler().runTaskLater(net.novaproject.novauhc.Main.get(), () -> removeArmor(player), 20L * armorDuration);
        return true;
    }

    private org.bukkit.inventory.ItemStack piece(Material m) {
        return new ItemCreator(m).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, protLevel)
                .setName("§bArmure Temporaire").getItemstack();
    }

    private void removeArmor(Player p) {
        if (p == null || !p.isOnline()) return;
        PlayerInventory inv = p.getInventory();
        if (isTemp(inv.getHelmet())) inv.setHelmet(null);
        if (isTemp(inv.getChestplate())) inv.setChestplate(null);
        if (isTemp(inv.getLeggings())) inv.setLeggings(null);
        if (isTemp(inv.getBoots())) inv.setBoots(null);
        p.updateInventory();
    }

    private boolean isTemp(org.bukkit.inventory.ItemStack i) {
        return i != null && i.hasItemMeta() && i.getItemMeta().hasDisplayName()
                && i.getItemMeta().getDisplayName().equals("§bArmure Temporaire");
    }
}
