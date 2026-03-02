package net.novaproject.ultimate.legend.roles;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.Lang;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import net.novaproject.novauhc.lang.special.LegendLang;
import net.novaproject.ultimate.legend.LegendRole;
import net.novaproject.ultimate.legend.roles.abilities.ZeusEffectsActive;
import net.novaproject.ultimate.legend.roles.abilities.ZeusLightningPassive;
import org.bukkit.Material;


public class Zeus extends LegendRole {

    @RoleVariable(lang = ScenarioVarLang.class, nameKey = "ZEUS_LIGHTNING_NAME", type = VariableType.ABILITY)
    public Ability lightningPassive;

    @RoleVariable(lang = ScenarioVarLang.class, nameKey = "ZEUS_EFFECTS_NAME", type = VariableType.ABILITY)
    public Ability effectsActive;

    public Zeus() {
        this.lightningPassive = new ZeusLightningPassive();
        this.effectsActive    = new ZeusEffectsActive();
        getAbilities().add(lightningPassive);
        getAbilities().add(effectsActive);
    }

    @Override public int getId() { return 6; }
    @Override public String getName() { return "Zeus"; }
    @Override public Material getIconMaterial() { return Material.GOLD_AXE; }
    @Override public ItemCreator getItem() { return new ItemCreator(Material.GOLD_AXE); }
    @Override public Lang getDescriptionLang() { return LegendLang.ROLE_DESC_ZEUS; }
}
