package net.novaproject.ultimate.legend.roles;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.Lang;

import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import net.novaproject.novauhc.lang.special.LegendLang;
import net.novaproject.ultimate.legend.LegendRole;
import net.novaproject.ultimate.legend.roles.abilities.MagePotionPassive;
import org.bukkit.Material;

public class Mage extends LegendRole {

    @RoleVariable(lang = ScenarioVarLang.class, nameKey = "MAGE_POTION_NAME", type = VariableType.ABILITY)
    public Ability potionPassive;

    public Mage() {
        this.potionPassive = new MagePotionPassive();
        getAbilities().add(potionPassive);
    }

    @Override public int getId() { return 2; }
    @Override public String getName() { return "Mage"; }
    @Override public Material getIconMaterial() { return Material.BLAZE_ROD; }
    @Override public ItemCreator getItem() { return new ItemCreator(Material.BLAZE_ROD); }
    @Override public Lang getDescriptionLang() { return LegendLang.ROLE_DESC_MAGE; }
}