package net.novauhc.dandadan.roles.flatwoods;

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

public class MonstreFlatwoodsRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "FLATWOODS_ABILITY_FUMEEF_NAME", type = VariableType.ABILITY)
    private Ability fumeeFAbility = new FumeeFAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "FLATWOODS_ABILITY_FLAT_NAME", type = VariableType.ABILITY)
    private Ability flatPassive = new FlatwoodsPassive();


    public MonstreFlatwoodsRole() {
    }

    @Override public String getName() { return "Monstre de Flatwoods"; }
    @Override public Material getIconMaterial() { return Material.LEATHER_HELMET; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.FLATWOODS_NAME)
            .line(DanDaDanDescLang.CAMP_YOKAI)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_PASSIFS)
            .hover(DanDaDanDescLang.FLATWOODS_FLAT_TEXT, DanDaDanDescLang.FLATWOODS_FLAT_HOVER)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.FLATWOODS_FUMEE_F_TEXT, DanDaDanDescLang.FLATWOODS_FUMEE_F_HOVER)
            .hover(DanDaDanDescLang.FLATWOODS_SUMO_TEXT, DanDaDanDescLang.FLATWOODS_SUMO_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.COAL).setName(LangManager.get().get(DanDaDanLang.ITEM_FLATWOODS_8FUMEE)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }

}
