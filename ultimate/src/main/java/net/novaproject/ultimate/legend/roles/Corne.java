package net.novaproject.ultimate.legend.roles;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.Lang;

import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import net.novaproject.novauhc.lang.special.LegendLang;
import net.novaproject.ultimate.legend.LegendRole;
import net.novaproject.ultimate.legend.roles.abilities.*;
import org.bukkit.Material;

public class Corne extends LegendRole {

    @RoleVariable(lang = ScenarioVarLang.class, nameKey = "CORNE_WEAKNESS_NAME", type = VariableType.ABILITY)
    public Ability weaknessPassive;

    @RoleVariable(lang = ScenarioVarLang.class, nameKey = "CORNE_MELODIE_FEU_NAME", type = VariableType.ABILITY)
    public Ability melodieFeu;

    @RoleVariable(lang = ScenarioVarLang.class, nameKey = "CORNE_MELODIE_HEAL_NAME", type = VariableType.ABILITY)
    public Ability melodieHeal;

    @RoleVariable(lang = ScenarioVarLang.class, nameKey = "CORNE_MELODIE_METAL_NAME", type = VariableType.ABILITY)
    public Ability melodieMetal;

    @RoleVariable(lang = ScenarioVarLang.class, nameKey = "CORNE_MELODIE_AIR_NAME", type = VariableType.ABILITY)
    public Ability melodieAir;

    public Corne() {
        this.weaknessPassive = new CorneWeaknessPassive();
        this.melodieFeu      = new CorneMelodieFeu();
        this.melodieHeal     = new CorneMelodieHeal();
        this.melodieMetal    = new CorneMelodieMetal();
        this.melodieAir      = new CorneMelodieAir();
    }

    @Override public int getId() { return 16; }
    @Override public String getName() { return "Corne"; }
    @Override public Material getIconMaterial() { return Material.NOTE_BLOCK; }
    @Override public ItemCreator getItem() { return new ItemCreator(Material.NOTE_BLOCK); }
    @Override public Lang getDescriptionLang() { return LegendLang.ROLE_DESC_CORNE; }
}