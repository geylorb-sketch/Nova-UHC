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
import net.novaproject.ultimate.legend.roles.abilities.DragonFirePassive;
import net.novaproject.ultimate.legend.roles.abilities.DragonFireballActive;
import org.bukkit.Material;
import org.bukkit.entity.Player;


public class Dragon extends LegendRole {

    @RoleVariable(lang = ScenarioVarLang.class, nameKey = "DRAGON_EXTRA_HEARTS_NAME", descKey = "DRAGON_EXTRA_HEARTS_DESC", type = VariableType.INTEGER)
    private int extraHearts = 8;

    @RoleVariable(lang = ScenarioVarLang.class, nameKey = "DRAGON_FIRE_NAME", type = VariableType.ABILITY)
    public Ability firePassive;

    @RoleVariable(lang = ScenarioVarLang.class, nameKey = "DRAGON_FIREBALL_NAME", type = VariableType.ABILITY)
    public Ability fireballActive;

    public Dragon() {
        this.firePassive    = new DragonFirePassive();
        this.fireballActive = new DragonFireballActive();
        getAbilities().add(firePassive);
        getAbilities().add(fireballActive);
    }

    @Override public int getId() { return 13; }
    @Override public String getName() { return "Dragon"; }
    @Override public Material getIconMaterial() { return Material.DRAGON_EGG; }
    @Override public ItemCreator getItem() { return new ItemCreator(Material.DRAGON_EGG); }
    @Override public Lang getDescriptionLang() { return LegendLang.ROLE_DESC_DRAGON; }
    @Override protected String applyPlaceholders(String d) { return d.replace("%extra_hearts%", String.valueOf(extraHearts / 2)); }

    @Override
    public void onGive(UHCPlayer u) {
        super.onGive(u); Player p = u.getPlayer(); if (p == null) return;
        p.setMaxHealth(20 + extraHearts); p.setHealth(20 + extraHearts);
    }
}
