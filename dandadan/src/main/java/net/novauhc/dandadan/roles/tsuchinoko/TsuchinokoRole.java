package net.novauhc.dandadan.roles.tsuchinoko;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanDescLang;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import net.novaproject.novauhc.utils.HoverUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class TsuchinokoRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "TSUCHINOKO_ABILITY_VENIN_NAME", type = VariableType.ABILITY)
    private Ability veninAbility = new VeninAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "TSUCHINOKO_ABILITY_ONDES_NAME", type = VariableType.ABILITY)
    private Ability ondesAbility = new OndesAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "TSUCHINOKO_ABILITY_SUICIDE_NAME", type = VariableType.ABILITY)
    private Ability suicideAbility = new SuicideAbility();

    private final VerMortPassive verMortPassive  = new VerMortPassive();


    public TsuchinokoRole() {
        getAbilities().add(verMortPassive);
    }

    @Override public String getName() { return "Tsuchinoko"; }
    @Override public Material getIconMaterial() { return Material.SLIME_BALL; }

    private String L(DanDaDanDescLang k) { return LangManager.get().get(k); }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(L(DanDaDanDescLang.ROLE_PREFIX) + L(DanDaDanDescLang.TSUCHINOKO_NAME));
        p.sendMessage(L(DanDaDanDescLang.CAMP_YOKAI));
        p.sendMessage(L(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.TSUCHINOKO_VERMORT_TEXT), L(DanDaDanDescLang.TSUCHINOKO_VERMORT_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.TSUCHINOKO_VENIN_TEXT), L(DanDaDanDescLang.TSUCHINOKO_VENIN_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.TSUCHINOKO_ONDES_TEXT), L(DanDaDanDescLang.TSUCHINOKO_ONDES_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.TSUCHINOKO_SUICIDE_TEXT), L(DanDaDanDescLang.TSUCHINOKO_SUICIDE_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.TSUCHINOKO_REGEN_TEXT), L(DanDaDanDescLang.TSUCHINOKO_REGEN_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.SPIDER_EYE).setName(LangManager.get().get(DanDaDanLang.ITEM_TSUCHINOKO_2VENIN)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.SLIME_BALL).setName(LangManager.get().get(DanDaDanLang.ITEM_TSUCHINOKO_AONDES)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.TNT).setName(LangManager.get().get(DanDaDanLang.ITEM_TSUCHINOKO_CSUICIDE)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }

}
