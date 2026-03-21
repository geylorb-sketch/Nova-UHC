package net.novauhc.dandadan.roles.umbrella;

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

public class UmbrellaBoyRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "UMBRELLA_ABILITY_CURSEU_NAME", type = VariableType.ABILITY)
    private Ability curseUAbility = new CurseUAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "UMBRELLA_ABILITY_PARASOL_NAME", type = VariableType.ABILITY)
    private Ability parasolAbility = new ParasolAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "UMBRELLA_ABILITY_AIRSTRIKE_NAME", type = VariableType.ABILITY)
    private Ability airStrikeAbility = new AirStrikeAbility();

    private final UmbrellaPassive umbrellaPassive  = new UmbrellaPassive();


    public UmbrellaBoyRole() {
        getAbilities().add(umbrellaPassive);
    }

    @Override public String getName() { return "Umbrella Boy"; }
    @Override public Material getIconMaterial() { return Material.DIAMOND_SWORD; }

    private String L(DanDaDanDescLang k) { return LangManager.get().get(k); }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(L(DanDaDanDescLang.ROLE_PREFIX) + L(DanDaDanDescLang.UMBRELLA_NAME));
        p.sendMessage(L(DanDaDanDescLang.CAMP_YOKAI));
        p.sendMessage(L(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.UMBRELLA_UMB_TEXT), L(DanDaDanDescLang.UMBRELLA_UMB_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.UMBRELLA_CURSE_U_TEXT), L(DanDaDanDescLang.UMBRELLA_CURSE_U_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.UMBRELLA_PARASOL_TEXT), L(DanDaDanDescLang.UMBRELLA_PARASOL_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.UMBRELLA_AIR_TEXT), L(DanDaDanDescLang.UMBRELLA_AIR_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.UMBRELLA_PROTEC_TEXT), L(DanDaDanDescLang.UMBRELLA_PROTEC_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.GHAST_TEAR).setName(LangManager.get().get(DanDaDanLang.ITEM_UMBRELLA_CCURSE)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.FEATHER).setName(LangManager.get().get(DanDaDanLang.ITEM_UMBRELLA_BPARASOL)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.FIREWORK).setName(LangManager.get().get(DanDaDanLang.ITEM_UMBRELLA_EAIR_STRIKE)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }

}
