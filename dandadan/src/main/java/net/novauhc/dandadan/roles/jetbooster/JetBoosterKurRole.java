package net.novauhc.dandadan.roles.jetbooster;

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

public class JetBoosterKurRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JETBOOSTER_ABILITY_AVIATE_NAME", type = VariableType.ABILITY)
    private Ability aviateAbility = new AviateAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JETBOOSTER_ABILITY_SUPERCHARGE_NAME", type = VariableType.ABILITY)
    private Ability superchargeAbility = new SuperchargeAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JETBOOSTER_ABILITY_BOOST_NAME", type = VariableType.ABILITY)
    private Ability boostAbility = new BoostAbility();

    private final  TeteHautePassive tetePassive  = new TeteHautePassive();


    public JetBoosterKurRole() {
        getAbilities().add(tetePassive);
    }

    @Override public String getName() { return "Jet Booster Kur"; }
    @Override public Material getIconMaterial() { return Material.FEATHER; }

    private String L(DanDaDanDescLang k) { return LangManager.get().get(k); }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(L(DanDaDanDescLang.ROLE_PREFIX) + L(DanDaDanDescLang.JETBOOSTER_NAME));
        p.sendMessage(L(DanDaDanDescLang.CAMP_YOKAI));
        p.sendMessage(L(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.JETBOOSTER_TETE_TEXT), L(DanDaDanDescLang.JETBOOSTER_TETE_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.JETBOOSTER_ESQUIVE_TEXT), L(DanDaDanDescLang.JETBOOSTER_ESQUIVE_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.JETBOOSTER_AVIATE_TEXT), L(DanDaDanDescLang.JETBOOSTER_AVIATE_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.JETBOOSTER_SUPER_TEXT), L(DanDaDanDescLang.JETBOOSTER_SUPER_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.JETBOOSTER_BOOST_TEXT), L(DanDaDanDescLang.JETBOOSTER_BOOST_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.JETBOOSTER_ABAND_TEXT), L(DanDaDanDescLang.JETBOOSTER_ABAND_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.FEATHER).setName(LangManager.get().get(DanDaDanLang.ITEM_JETBOOSTER_BAVIATE)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.GLOWSTONE_DUST).setName(LangManager.get().get(DanDaDanLang.ITEM_JETBOOSTER_ESUPERCHARGE)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.SUGAR).setName(LangManager.get().get(DanDaDanLang.ITEM_JETBOOSTER_FBOOST)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }

}
