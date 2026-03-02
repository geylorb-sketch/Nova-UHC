package net.novaproject.ultimate.legend.roles;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.Lang;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import net.novaproject.novauhc.lang.special.LegendLang;
import net.novaproject.ultimate.legend.LegendRole;
import net.novaproject.ultimate.legend.roles.abilities.SoldatEquipmentPassive;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

// onHit supprimé : SoldatEquipmentPassive gère lui-même le bonus dégâts via onAttack → tryUse → onEnable
public class Soldat extends LegendRole {

    @RoleVariable(lang = ScenarioVarLang.class, nameKey = "SOLDAT_EQUIPMENT_NAME", type = VariableType.ABILITY)
    public Ability equipmentPassive;

    public Soldat() {
        this.equipmentPassive = new SoldatEquipmentPassive();
        getAbilities().add(equipmentPassive);
    }

    @Override public int getId() { return 9; }
    @Override public String getName() { return "Soldat"; }
    @Override public Material getIconMaterial() { return Material.IRON_CHESTPLATE; }
    @Override public ItemCreator getItem() { return new ItemCreator(Material.IRON_CHESTPLATE); }
    @Override public Lang getDescriptionLang() { return LegendLang.ROLE_DESC_SOLDAT; }

    @Override
    public void onGive(UHCPlayer u) {
        super.onGive(u); Player p = u.getPlayer(); if (p == null) return;
        p.getInventory().setHelmet(new ItemCreator(Material.IRON_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).getItemstack());
        p.getInventory().setChestplate(new ItemCreator(Material.IRON_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).getItemstack());
        p.getInventory().setLeggings(new ItemCreator(Material.IRON_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).getItemstack());
        p.getInventory().setBoots(new ItemCreator(Material.IRON_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).getItemstack());
        p.getInventory().addItem(new ItemCreator(Material.IRON_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 1).getItemstack());
    }
}
