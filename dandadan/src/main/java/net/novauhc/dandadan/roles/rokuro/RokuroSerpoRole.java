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
import net.novaproject.novauhc.utils.HoverUtils;
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

    private final SerupoPassive serupoPassive  = new SerupoPassive();


    public RokuroSerpoRole() {
        getAbilities().add(serupoPassive);
    }

    @Override public String getName() { return "Rokuro Serpo"; }
    @Override public Material getIconMaterial() { return Material.ENDER_PEARL; }

    private String L(DanDaDanDescLang k) { return LangManager.get().get(k); }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(L(DanDaDanDescLang.ROLE_PREFIX) + L(DanDaDanDescLang.ROKURO_NAME));
        p.sendMessage(L(DanDaDanDescLang.CAMP_YOKAI));
        p.sendMessage(L(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.ROKURO_SERUPO_TEXT), L(DanDaDanDescLang.ROKURO_SERUPO_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.ROKURO_SEIJIN_TEXT), L(DanDaDanDescLang.ROKURO_SEIJIN_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.ROKURO_FORME_TEXT), L(DanDaDanDescLang.ROKURO_FORME_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.ROKURO_ORGUE_TEXT), L(DanDaDanDescLang.ROKURO_ORGUE_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.ROKURO_ZONE_I_TEXT), L(DanDaDanDescLang.ROKURO_ZONE_I_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.ROKURO_ZONE_IT_TEXT), L(DanDaDanDescLang.ROKURO_ZONE_IT_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
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
