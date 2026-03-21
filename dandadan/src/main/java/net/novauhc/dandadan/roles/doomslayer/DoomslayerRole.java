package net.novauhc.dandadan.roles.doomslayer;

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

public class DoomslayerRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DOOMSLAYER_ABILITY_CRUCIBLE_NAME", type = VariableType.ABILITY)
    private Ability crucibleAbility = new CrucibleAbility();

    private final DoomPassive doomPassive  = new DoomPassive();


    public DoomslayerRole() {
        setCamp(DanDaDanCamps.SPECIAL);
        getAbilities().add(doomPassive);
    }

    @Override public String getName() { return "Doomslayer"; }
    @Override public Material getIconMaterial() { return Material.IRON_SWORD; }

    private String L(DanDaDanDescLang k) { return LangManager.get().get(k); }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(L(DanDaDanDescLang.ROLE_PREFIX) + L(DanDaDanDescLang.DOOMSLAYER_NAME));
        p.sendMessage(L(DanDaDanDescLang.CAMP_SPECIAL));
        p.sendMessage(L(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.DOOMSLAYER_DOOM_TEXT), L(DanDaDanDescLang.DOOMSLAYER_DOOM_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.DOOMSLAYER_CRUCIBLE_TEXT), L(DanDaDanDescLang.DOOMSLAYER_CRUCIBLE_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.DOOMSLAYER_WEAPON_TEXT), L(DanDaDanDescLang.DOOMSLAYER_WEAPON_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.IRON_SWORD).setName(LangManager.get().get(DanDaDanLang.ITEM_DOOMSLAYER_CCRUCIBLE_BLADE)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }

}
