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
import net.novaproject.novauhc.scenario.role.RoleDescription;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class UmbrellaBoyRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "UMBRELLA_ABILITY_CURSEU_NAME", type = VariableType.ABILITY)
    private Ability curseUAbility = new CurseUAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "UMBRELLA_ABILITY_PARASOL_NAME", type = VariableType.ABILITY)
    private Ability parasolAbility = new ParasolAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "UMBRELLA_ABILITY_AIRSTRIKE_NAME", type = VariableType.ABILITY)
    private Ability airStrikeAbility = new AirStrikeAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "UMBRELLA_ABILITY_UMBRELLA_NAME", type = VariableType.ABILITY)
    private Ability umbrellaPassive = new UmbrellaPassive();


    public UmbrellaBoyRole() {
    }

    @Override public String getName() { return "Umbrella Boy"; }
    @Override public Material getIconMaterial() { return Material.DIAMOND_SWORD; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.UMBRELLA_NAME)
            .line(DanDaDanDescLang.CAMP_YOKAI)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_PASSIFS)
            .hover(DanDaDanDescLang.UMBRELLA_UMB_TEXT, DanDaDanDescLang.UMBRELLA_UMB_HOVER)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.UMBRELLA_CURSE_U_TEXT, DanDaDanDescLang.UMBRELLA_CURSE_U_HOVER)
            .hover(DanDaDanDescLang.UMBRELLA_PARASOL_TEXT, DanDaDanDescLang.UMBRELLA_PARASOL_HOVER)
            .hover(DanDaDanDescLang.UMBRELLA_AIR_TEXT, DanDaDanDescLang.UMBRELLA_AIR_HOVER)
            .hover(DanDaDanDescLang.UMBRELLA_PROTEC_TEXT, DanDaDanDescLang.UMBRELLA_PROTEC_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
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
