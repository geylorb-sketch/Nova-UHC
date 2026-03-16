package net.novauhc.dandadan.roles.nessie;

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

public class NessieRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "NESSIE_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability deluge = new DelugeAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "NESSIE_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability jetEau = new JetEauAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "NESSIE_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability cou = new CouCommand();

        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "NESSIE_PASSIVE_POISSON_NAME", type = VariableType.ABILITY)
    private Ability poissonPassive = new PoissonPassive();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "NESSIE_PASSIVE_COU_LARGE_NAME", type = VariableType.ABILITY)
    private Ability couLargePassive = new CouLargePassive();


    @RoleVariable(lang = DanDaDanVarLangExt4.class, nameKey = "NESSIE_ABILITY_ESPACE_VIDE_NAME", type = VariableType.ABILITY)
    private Ability espaceVideNessie = new EspaceVideNessieAbility();
public NessieRole() {
    }

    @Override public int getId()                { return 18; }
    @Override public String getName()           { return "Nessie"; }
    @Override public Material getIconMaterial() { return Material.WATER_BUCKET; }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(DanDaDanLangExt.NESSIE_DESC, player);
    }

    // Passif Poisson : Depth Strider 3 sur bottes

    // Passif Cou Large : double coup tous les 7 hits

    // Création du Déluge

    // Jet d'eau

    // /ddd cou
}
