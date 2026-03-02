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
import net.novaproject.ultimate.legend.roles.abilities.OgrePassive;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Ogre extends LegendRole {

    @RoleVariable(lang = ScenarioVarLang.class, nameKey = "OGRE_EXTRA_HEARTS_NAME", descKey = "OGRE_EXTRA_HEARTS_DESC", type = VariableType.INTEGER)
    private int extraHearts = 10;
    @RoleVariable(lang = ScenarioVarLang.class, nameKey = "OGRE_START_GAPPLES_NAME", descKey = "OGRE_START_GAPPLES_DESC", type = VariableType.INTEGER)
    private int startGapples = 8;

    @RoleVariable(lang = ScenarioVarLang.class, nameKey = "OGRE_PASSIVE_NAME", type = VariableType.ABILITY)
    public Ability passive;

    public Ogre() {
        this.passive = new OgrePassive();
        getAbilities().add(passive);
    }

    @Override public int getId() { return 12; }
    @Override public String getName() { return "Ogre"; }
    @Override public Material getIconMaterial() { return Material.GOLDEN_APPLE; }
    @Override public ItemCreator getItem() { return new ItemCreator(Material.GOLDEN_APPLE); }
    @Override public Lang getDescriptionLang() { return LegendLang.ROLE_DESC_OGRE; }
    @Override protected String applyPlaceholders(String d) {
        return d.replace("%extra_hearts%", String.valueOf(extraHearts / 2)).replace("%start_gapples%", String.valueOf(startGapples));
    }

    @Override
    public void onGive(UHCPlayer u) {
        super.onGive(u); Player p = u.getPlayer(); if (p == null) return;
        p.setMaxHealth(20 + extraHearts); p.setHealth(20 + extraHearts);
        p.getInventory().addItem(new ItemCreator(Material.GOLDEN_APPLE).setAmount(startGapples).getItemstack());
    }
}