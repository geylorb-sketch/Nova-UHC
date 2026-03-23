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
import net.novaproject.novauhc.scenario.role.RoleDescription;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class TsuchinokoRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "TSUCHINOKO_ABILITY_VENIN_NAME", type = VariableType.ABILITY)
    private Ability veninAbility = new VeninAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "TSUCHINOKO_ABILITY_ONDES_NAME", type = VariableType.ABILITY)
    private Ability ondesAbility = new OndesAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "TSUCHINOKO_ABILITY_SUICIDE_NAME", type = VariableType.ABILITY)
    private Ability suicideAbility = new SuicideAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "TSUCHINOKO_ABILITY_VERMORT_NAME", type = VariableType.ABILITY)
    private Ability verMortPassive = new VerMortPassive();


    public TsuchinokoRole() {
    }

    @Override public String getName() { return "Tsuchinoko"; }
    @Override public Material getIconMaterial() { return Material.SLIME_BALL; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.TSUCHINOKO_NAME)
            .line(DanDaDanDescLang.CAMP_YOKAI)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_PASSIFS)
            .hover(DanDaDanDescLang.TSUCHINOKO_VERMORT_TEXT, DanDaDanDescLang.TSUCHINOKO_VERMORT_HOVER)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.TSUCHINOKO_VENIN_TEXT, DanDaDanDescLang.TSUCHINOKO_VENIN_HOVER)
            .hover(DanDaDanDescLang.TSUCHINOKO_ONDES_TEXT, DanDaDanDescLang.TSUCHINOKO_ONDES_HOVER)
            .hover(DanDaDanDescLang.TSUCHINOKO_SUICIDE_TEXT, DanDaDanDescLang.TSUCHINOKO_SUICIDE_HOVER)
            .hover(DanDaDanDescLang.TSUCHINOKO_REGEN_TEXT, DanDaDanDescLang.TSUCHINOKO_REGEN_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
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
