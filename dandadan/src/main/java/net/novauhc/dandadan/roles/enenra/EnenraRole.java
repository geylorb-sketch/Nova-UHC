package net.novauhc.dandadan.roles.enenra;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import net.novauhc.dandadan.lang.DanDaDanVarLangExt4;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class EnenraRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ENENRA_ABILITY_SETTA_NAME", type = VariableType.ABILITY)
    private Ability setta = new SettaAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ENENRA_ABILITY_NINJA_NAME", type = VariableType.ABILITY)
    private Ability ninja = new NinjaAbility();

    // Passifs combo + corps de fumée → constructeur ✅
        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ENENRA_PASSIVE_COMBO_NAME", type = VariableType.ABILITY)
    private Ability comboPassive = new ComboPassive();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ENENRA_PASSIVE_CORPS_FUMEE_NAME", type = VariableType.ABILITY)
    private Ability corpsDeFumee = new CorpsDeFumeeAbility();


    @RoleVariable(lang = DanDaDanVarLangExt4.class, nameKey = "ENENRA_ABILITY_ESPACE_VIDE_NAME", type = VariableType.ABILITY)
    private Ability espaceVideEnenra = new EspaceVideEnenraAbility();
public EnenraRole() {
    }

    @Override public int getId()                { return 7; }
    @Override public String getName()           { return "Enenra"; }
    @Override public Material getIconMaterial() { return Material.INK_SACK; }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(DanDaDanLang.ENENRA_DESC, player);
    }
}
