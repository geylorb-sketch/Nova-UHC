package net.novauhc.dandadan.roles.reiko;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanLangExt;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import net.novauhc.dandadan.lang.DanDaDanVarLangExt4;
import org.bukkit.Material;
import org.bukkit.entity.Player;

// ════════════════════════════════════════════
//  Reiko Kashima (id 16)
// ════════════════════════════════════════════
public class ReikoKashimaRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "REIKO_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability thorns = new ThornsAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "REIKO_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability miroir = new MiroirAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "REIKO_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability cage = new EmprisonnementAbility();

        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "REIKO_PASSIVE_MIROIR_NAME", type = VariableType.ABILITY)
    private Ability miroirPassive = new MiroirPassive();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "REIKO_PASSIVE_BOYFRIEND_NAME", type = VariableType.ABILITY)
    private Ability boyfriendPassive = new BoyfriendPassive();


    @RoleVariable(lang = DanDaDanVarLangExt4.class, nameKey = "REIKO_ABILITY_ESPACE_VIDE_NAME", type = VariableType.ABILITY)
    private Ability espaceVideReiko = new EspaceVideReikoAbility();
public ReikoKashimaRole() {
    }

    @Override public int getId()                { return 16; }
    @Override public String getName()           { return "Reiko Kashima"; }

    @Override
    public Material getIconMaterial() { return Material.GLASS; }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(DanDaDanLangExt.REIKO_DESC, player);
    }

    // Passif Miroir

    // Passif Boyfriend (bonus vs certains yokais)

    // Thorns (renvoie 50% dégâts)

    // Miroir Ability (compteur 6 coups → invoque miroir)

    // Emprisonnement
}
