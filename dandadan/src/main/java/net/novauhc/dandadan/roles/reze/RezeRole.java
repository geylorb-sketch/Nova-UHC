package net.novauhc.dandadan.roles.reze;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDanCamps;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanDescLang;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import net.novaproject.novauhc.scenario.role.RoleDescription;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class RezeRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "REZE_ABILITY_MALEREZE_NAME", type = VariableType.ABILITY)
    private Ability maleRezeAbility = new MaleRezeAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "REZE_ABILITY_BOUM_NAME", type = VariableType.ABILITY)
    private Ability boumAbility = new BoumAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "REZE_ABILITY_TETECHERC_NAME", type = VariableType.ABILITY)
    private Ability teteChercAbility = new TeteChercAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "REZE_ABILITY_TORPILLE_NAME", type = VariableType.ABILITY)
    private Ability torpilleAbility = new TorpilleAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "REZE_ABILITY_BOMBE_NAME", type = VariableType.ABILITY)
    private Ability bombePassive = new BombePassive();


    public RezeRole() {
        setCamp(DanDaDanCamps.SPECIAL);
    }

    @Override public String getName() { return "Reze"; }
    @Override public Material getIconMaterial() { return Material.TNT; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.REZE_NAME)
            .line(DanDaDanDescLang.CAMP_SPECIAL)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_PASSIFS)
            .hover(DanDaDanDescLang.REZE_BOMBE_TEXT, DanDaDanDescLang.REZE_BOMBE_HOVER)
            .hover(DanDaDanDescLang.REZE_EXPLO_DJ_TEXT, DanDaDanDescLang.REZE_EXPLO_DJ_HOVER)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.REZE_MALE_R_TEXT, DanDaDanDescLang.REZE_MALE_R_HOVER)
            .hover(DanDaDanDescLang.REZE_BOUM_TEXT, DanDaDanDescLang.REZE_BOUM_HOVER)
            .hover(DanDaDanDescLang.REZE_TETE_C_TEXT, DanDaDanDescLang.REZE_TETE_C_HOVER)
            .hover(DanDaDanDescLang.REZE_TORPILLE_TEXT, DanDaDanDescLang.REZE_TORPILLE_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.TNT).setName(LangManager.get().get(DanDaDanLang.ITEM_REZE_CMALEDICTION)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.TNT).setName(LangManager.get().get(DanDaDanLang.ITEM_REZE_6BOUM)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.FIREWORK).setName(LangManager.get().get(DanDaDanLang.ITEM_REZE_ETETE_CHERCHEUSE)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.BLAZE_ROD).setName(LangManager.get().get(DanDaDanLang.ITEM_REZE_CTORPILLE)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }

}
