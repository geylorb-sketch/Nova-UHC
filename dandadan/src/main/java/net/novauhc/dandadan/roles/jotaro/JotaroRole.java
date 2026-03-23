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
import net.novaproject.novauhc.scenario.role.RoleDescription;
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

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JOTARO_ABILITY_REACTION_NAME", type = VariableType.ABILITY)
    private Ability reactionPassive = new ReactionPassive();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JOTARO_ABILITY_CASQUETTE_NAME", type = VariableType.ABILITY)
    private Ability casquettePassive = new CasquettePassive();


    public JotaroRole() {
        setCamp(DanDaDanCamps.SPECIAL);
    }

    @Override public String getName() { return "Jotaro"; }
    @Override public Material getIconMaterial() { return Material.NETHER_STAR; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.JOTARO_NAME)
            .line(DanDaDanDescLang.CAMP_SPECIAL)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_PASSIFS)
            .hover(DanDaDanDescLang.JOTARO_REACTION_TEXT, DanDaDanDescLang.JOTARO_REACTION_HOVER)
            .hover(DanDaDanDescLang.JOTARO_DETECTIVE_TEXT, DanDaDanDescLang.JOTARO_DETECTIVE_HOVER)
            .hover(DanDaDanDescLang.JOTARO_CASQUETTE_TEXT, DanDaDanDescLang.JOTARO_CASQUETTE_HOVER)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.JOTARO_STAR_F_TEXT, DanDaDanDescLang.JOTARO_STAR_F_HOVER)
            .hover(DanDaDanDescLang.JOTARO_STAR_P_TEXT, DanDaDanDescLang.JOTARO_STAR_P_HOVER)
            .hover(DanDaDanDescLang.JOTARO_TIME_S_TEXT, DanDaDanDescLang.JOTARO_TIME_S_HOVER)
            .hover(DanDaDanDescLang.JOTARO_ORA_TEXT, DanDaDanDescLang.JOTARO_ORA_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
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
