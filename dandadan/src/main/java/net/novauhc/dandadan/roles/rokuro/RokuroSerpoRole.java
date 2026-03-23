package net.novauhc.dandadan.roles.rokuro;

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

public class RokuroSerpoRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ROKURO_ABILITY_FORME_NAME", type = VariableType.ABILITY)
    private Ability formeAbility = new FormeAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ROKURO_ABILITY_ORGUE_NAME", type = VariableType.ABILITY)
    private Ability orgueAbility = new OrgueAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ROKURO_ABILITY_ZONEINCROYABLE_NAME", type = VariableType.ABILITY)
    private Ability zoneIncroyableAbility = new ZoneIncroyableAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ROKURO_ABILITY_ZONEINTOUCHABLE_NAME", type = VariableType.ABILITY)
    private Ability zoneIntouchableAbility = new ZoneIntouchableAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ROKURO_ABILITY_SERUPO_NAME", type = VariableType.ABILITY)
    private Ability serupoPassive = new SerupoPassive();


    public RokuroSerpoRole() {
    }

    @Override public String getName() { return "Rokuro Serpo"; }
    @Override public Material getIconMaterial() { return Material.ENDER_PEARL; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.ROKURO_NAME)
            .line(DanDaDanDescLang.CAMP_YOKAI)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_PASSIFS)
            .hover(DanDaDanDescLang.ROKURO_SERUPO_TEXT, DanDaDanDescLang.ROKURO_SERUPO_HOVER)
            .hover(DanDaDanDescLang.ROKURO_SEIJIN_TEXT, DanDaDanDescLang.ROKURO_SEIJIN_HOVER)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.ROKURO_FORME_TEXT, DanDaDanDescLang.ROKURO_FORME_HOVER)
            .hover(DanDaDanDescLang.ROKURO_ORGUE_TEXT, DanDaDanDescLang.ROKURO_ORGUE_HOVER)
            .hover(DanDaDanDescLang.ROKURO_ZONE_I_TEXT, DanDaDanDescLang.ROKURO_ZONE_I_HOVER)
            .hover(DanDaDanDescLang.ROKURO_ZONE_IT_TEXT, DanDaDanDescLang.ROKURO_ZONE_IT_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.ENDER_PEARL).setName(LangManager.get().get(DanDaDanLang.ITEM_ROKURO_5FORME_SERPIENNE)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.COMPASS).setName(LangManager.get().get(DanDaDanLang.ITEM_ROKURO_EORGUE_DES_SENS)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.EMERALD).setName(LangManager.get().get(DanDaDanLang.ITEM_ROKURO_AZONE_INCROYABLE)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.DIAMOND).setName(LangManager.get().get(DanDaDanLang.ITEM_ROKURO_BZONE_INTOUCHABLE)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }

}
