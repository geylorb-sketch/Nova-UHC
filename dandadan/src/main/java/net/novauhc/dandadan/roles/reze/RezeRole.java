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
import net.novaproject.novauhc.utils.HoverUtils;
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

    private final BombePassive bombePassive  = new BombePassive();


    public RezeRole() {
        setCamp(DanDaDanCamps.SPECIAL);
        getAbilities().add(bombePassive);
    }

    @Override public String getName() { return "Reze"; }
    @Override public Material getIconMaterial() { return Material.TNT; }

    private String L(DanDaDanDescLang k) { return LangManager.get().get(k); }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(L(DanDaDanDescLang.ROLE_PREFIX) + L(DanDaDanDescLang.REZE_NAME));
        p.sendMessage(L(DanDaDanDescLang.CAMP_SPECIAL));
        p.sendMessage(L(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.REZE_BOMBE_TEXT), L(DanDaDanDescLang.REZE_BOMBE_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.REZE_EXPLO_DJ_TEXT), L(DanDaDanDescLang.REZE_EXPLO_DJ_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.REZE_MALE_R_TEXT), L(DanDaDanDescLang.REZE_MALE_R_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.REZE_BOUM_TEXT), L(DanDaDanDescLang.REZE_BOUM_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.REZE_TETE_C_TEXT), L(DanDaDanDescLang.REZE_TETE_C_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.REZE_TORPILLE_TEXT), L(DanDaDanDescLang.REZE_TORPILLE_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
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
