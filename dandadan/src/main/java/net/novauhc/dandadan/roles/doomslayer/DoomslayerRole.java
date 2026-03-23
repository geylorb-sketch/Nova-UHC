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
import net.novaproject.novauhc.scenario.role.RoleDescription;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class DoomslayerRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DOOMSLAYER_ABILITY_CRUCIBLE_NAME", type = VariableType.ABILITY)
    private Ability crucibleAbility = new CrucibleAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DOOMSLAYER_ABILITY_DOOM_NAME", type = VariableType.ABILITY)
    private Ability doomPassive = new DoomPassive();


    public DoomslayerRole() {
        setCamp(DanDaDanCamps.SPECIAL);
    }

    @Override public String getName() { return "Doomslayer"; }
    @Override public Material getIconMaterial() { return Material.IRON_SWORD; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.DOOMSLAYER_NAME)
            .line(DanDaDanDescLang.CAMP_SPECIAL)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_PASSIFS)
            .hover(DanDaDanDescLang.DOOMSLAYER_DOOM_TEXT, DanDaDanDescLang.DOOMSLAYER_DOOM_HOVER)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.DOOMSLAYER_CRUCIBLE_TEXT, DanDaDanDescLang.DOOMSLAYER_CRUCIBLE_HOVER)
            .hover(DanDaDanDescLang.DOOMSLAYER_WEAPON_TEXT, DanDaDanDescLang.DOOMSLAYER_WEAPON_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
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
