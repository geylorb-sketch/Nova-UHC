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
import net.novaproject.novauhc.scenario.role.RoleDescription;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class JetBoosterKurRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JETBOOSTER_ABILITY_AVIATE_NAME", type = VariableType.ABILITY)
    private Ability aviateAbility = new AviateAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JETBOOSTER_ABILITY_SUPERCHARGE_NAME", type = VariableType.ABILITY)
    private Ability superchargeAbility = new SuperchargeAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JETBOOSTER_ABILITY_BOOST_NAME", type = VariableType.ABILITY)
    private Ability boostAbility = new BoostAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JETBOOSTER_ABILITY_TETE_NAME", type = VariableType.ABILITY)
    private Ability tetePassive = new TeteHautePassive();


    public JetBoosterKurRole() {
    }

    @Override public String getName() { return "Jet Booster Kur"; }
    @Override public Material getIconMaterial() { return Material.FEATHER; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.JETBOOSTER_NAME)
            .line(DanDaDanDescLang.CAMP_YOKAI)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_PASSIFS)
            .hover(DanDaDanDescLang.JETBOOSTER_TETE_TEXT, DanDaDanDescLang.JETBOOSTER_TETE_HOVER)
            .hover(DanDaDanDescLang.JETBOOSTER_ESQUIVE_TEXT, DanDaDanDescLang.JETBOOSTER_ESQUIVE_HOVER)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.JETBOOSTER_AVIATE_TEXT, DanDaDanDescLang.JETBOOSTER_AVIATE_HOVER)
            .hover(DanDaDanDescLang.JETBOOSTER_SUPER_TEXT, DanDaDanDescLang.JETBOOSTER_SUPER_HOVER)
            .hover(DanDaDanDescLang.JETBOOSTER_BOOST_TEXT, DanDaDanDescLang.JETBOOSTER_BOOST_HOVER)
            .hover(DanDaDanDescLang.JETBOOSTER_ABAND_TEXT, DanDaDanDescLang.JETBOOSTER_ABAND_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
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
