package net.novauhc.dandadan.roles.payase;

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

public class PayaseRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "PAYASE_ABILITY_DARKNESS_NAME", type = VariableType.ABILITY)
    private Ability darknessForm = new DarknessFormAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "PAYASE_ABILITY_PERMUTATION_NAME", type = VariableType.ABILITY)
    private Ability permutation = new PermutationAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "PAYASE_ABILITY_OMBRE_NAME", type = VariableType.ABILITY)
    private Ability ombre = new OmbreAbility();

    // Passifs jour/nuit → constructeur ✅
        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "PAYASE_PASSIVE_JOUR_NAME", type = VariableType.ABILITY)
    private Ability payaseJourPassive = new PayaseJourPassive();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "PAYASE_PASSIVE_NUIT_NAME", type = VariableType.ABILITY)
    private Ability payaseNuitPassive = new PayaseNuitPassive();


    @RoleVariable(lang = DanDaDanVarLangExt4.class, nameKey = "PAYASE_ABILITY_ESPACE_VIDE_NAME", type = VariableType.ABILITY)
    private Ability espaceVidePayase = new EspaceVidePayaseAbility();
public PayaseRole() {
    }

    @Override public int getId()                { return 8; }
    @Override public String getName()           { return "Payase"; }
    @Override public Material getIconMaterial() { return Material.ENDER_PEARL; }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(DanDaDanLang.PAYASE_DESC, player);
    }
}
