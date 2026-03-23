package net.novaproject.ultimate.legend.roles;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.Lang;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import net.novaproject.novauhc.lang.special.LegendLang;
import net.novaproject.ultimate.legend.LegendRole;
import net.novaproject.ultimate.legend.roles.abilities.SuccubeAbsorptionActive;
import net.novaproject.ultimate.legend.roles.abilities.SuccubeLifestealPassive;
import org.bukkit.Material;


public class Succube extends LegendRole {

    @RoleVariable(lang = ScenarioVarLang.class, nameKey = "SUCCUBE_LIFESTEAL_NAME", type = VariableType.ABILITY)
    public Ability lifestealPassive;

    @RoleVariable(lang = ScenarioVarLang.class, nameKey = "SUCCUBE_ABSORPTION_NAME", type = VariableType.ABILITY)
    public Ability absorptionActive;

    public Succube() {
        this.lifestealPassive = new SuccubeLifestealPassive();
        this.absorptionActive = new SuccubeAbsorptionActive();
    }

    @Override public int getId() { return 8; }
    @Override public String getName() { return "Succube"; }
    @Override public Material getIconMaterial() { return Material.REDSTONE; }
    @Override public ItemCreator getItem() { return new ItemCreator(Material.REDSTONE); }
    @Override public Lang getDescriptionLang() { return LegendLang.ROLE_DESC_SUCCUBE; }
}
