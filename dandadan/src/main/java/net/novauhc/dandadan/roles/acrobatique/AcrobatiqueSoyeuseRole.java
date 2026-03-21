package net.novauhc.dandadan.roles.acrobatique;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanDescLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import net.novaproject.novauhc.utils.HoverUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class AcrobatiqueSoyeuseRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ACROBATIQUE_ABILITY_CHEVEUX_NAME", type = VariableType.ABILITY)
    private Ability cheveuxAbility = new CheveuxAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ACROBATIQUE_ABILITY_TRANSFOA_NAME", type = VariableType.ABILITY)
    private Ability transfoAAbility = new TransfoAAbility();

    private final AcrobatePassive acrobatePassive  = new AcrobatePassive();


    public AcrobatiqueSoyeuseRole() {
        getAbilities().add(acrobatePassive);
    }

    @Override public String getName() { return "Acrobatique Soyeuse"; }
    @Override public Material getIconMaterial() { return Material.STRING; }

    private String L(DanDaDanDescLang k) { return LangManager.get().get(k); }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(L(DanDaDanDescLang.ROLE_PREFIX) + L(DanDaDanDescLang.ACROBATIQUE_NAME));
        p.sendMessage(L(DanDaDanDescLang.CAMP_YOKAI));
        p.sendMessage(L(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.ACROBATIQUE_ACRO_TEXT), L(DanDaDanDescLang.ACROBATIQUE_ACRO_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.ACROBATIQUE_CHEVEUX_TEXT), L(DanDaDanDescLang.ACROBATIQUE_CHEVEUX_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.ACROBATIQUE_TRANSFO_A_TEXT), L(DanDaDanDescLang.ACROBATIQUE_TRANSFO_A_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.ACROBATIQUE_HEAT_TEXT), L(DanDaDanDescLang.ACROBATIQUE_HEAT_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        super.onGive(uhcPlayer);
    }

}
