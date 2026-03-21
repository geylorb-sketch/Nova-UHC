package net.novauhc.dandadan.roles.jotaro;

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

public class JotaroRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JOTARO_ABILITY_STARFINGER_NAME", type = VariableType.ABILITY)
    private Ability starFingerAbility = new StarFingerAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JOTARO_ABILITY_STARPLATINUM_NAME", type = VariableType.ABILITY)
    private Ability starPlatinumAbility = new StarPlatinumAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JOTARO_ABILITY_ARRETTEMPSJ_NAME", type = VariableType.ABILITY)
    private Ability arretTempsJAbility = new ArretTempsJAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JOTARO_ABILITY_ORAORA_NAME", type = VariableType.ABILITY)
    private Ability oraOraAbility = new OraOraAbility();

    private final ReactionPassive reactionPassive  = new ReactionPassive();
    private final CasquettePassive casquettePassive  = new CasquettePassive();


    public JotaroRole() {
        setCamp(DanDaDanCamps.SPECIAL);
        getAbilities().add(reactionPassive);
        getAbilities().add(casquettePassive);
    }

    @Override public String getName() { return "Jotaro"; }
    @Override public Material getIconMaterial() { return Material.NETHER_STAR; }

    private String L(DanDaDanDescLang k) { return LangManager.get().get(k); }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(L(DanDaDanDescLang.ROLE_PREFIX) + L(DanDaDanDescLang.JOTARO_NAME));
        p.sendMessage(L(DanDaDanDescLang.CAMP_SPECIAL));
        p.sendMessage(L(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.JOTARO_REACTION_TEXT), L(DanDaDanDescLang.JOTARO_REACTION_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.JOTARO_DETECTIVE_TEXT), L(DanDaDanDescLang.JOTARO_DETECTIVE_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.JOTARO_CASQUETTE_TEXT), L(DanDaDanDescLang.JOTARO_CASQUETTE_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.JOTARO_STAR_F_TEXT), L(DanDaDanDescLang.JOTARO_STAR_F_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.JOTARO_STAR_P_TEXT), L(DanDaDanDescLang.JOTARO_STAR_P_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.JOTARO_TIME_S_TEXT), L(DanDaDanDescLang.JOTARO_TIME_S_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.JOTARO_ORA_TEXT), L(DanDaDanDescLang.JOTARO_ORA_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.IRON_SWORD).setName(LangManager.get().get(DanDaDanLang.ITEM_JOTARO_9STAR_FINGER)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.NETHER_STAR).setName(LangManager.get().get(DanDaDanLang.ITEM_JOTARO_DSTAR_PLATINUM)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.WATCH).setName(LangManager.get().get(DanDaDanLang.ITEM_JOTARO_5ARRET_DU_TEMPS)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.DIAMOND).setName(LangManager.get().get(DanDaDanLang.ITEM_JOTARO_6ORA_ORA)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }

}
