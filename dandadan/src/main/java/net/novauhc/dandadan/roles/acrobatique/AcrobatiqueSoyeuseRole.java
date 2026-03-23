package net.novauhc.dandadan.roles.acrobatique;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanDescLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import net.novaproject.novauhc.scenario.role.RoleDescription;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class AcrobatiqueSoyeuseRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ACROBATIQUE_ABILITY_CHEVEUX_NAME", type = VariableType.ABILITY)
    private Ability cheveuxAbility = new CheveuxAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ACROBATIQUE_ABILITY_TRANSFOA_NAME", type = VariableType.ABILITY)
    private Ability transfoAAbility = new TransfoAAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ACROBATIQUE_ABILITY_ACROBATE_NAME", type = VariableType.ABILITY)
    private Ability acrobatePassive = new AcrobatePassive();


    public AcrobatiqueSoyeuseRole() {
    }

    @Override public String getName() { return "Acrobatique Soyeuse"; }
    @Override public Material getIconMaterial() { return Material.STRING; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.ACROBATIQUE_NAME)
            .line(DanDaDanDescLang.CAMP_YOKAI)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_PASSIFS)
            .hover(DanDaDanDescLang.ACROBATIQUE_ACRO_TEXT, DanDaDanDescLang.ACROBATIQUE_ACRO_HOVER)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.ACROBATIQUE_CHEVEUX_TEXT, DanDaDanDescLang.ACROBATIQUE_CHEVEUX_HOVER)
            .hover(DanDaDanDescLang.ACROBATIQUE_TRANSFO_A_TEXT, DanDaDanDescLang.ACROBATIQUE_TRANSFO_A_HOVER)
            .hover(DanDaDanDescLang.ACROBATIQUE_HEAT_TEXT, DanDaDanDescLang.ACROBATIQUE_HEAT_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        super.onGive(uhcPlayer);
    }

}
