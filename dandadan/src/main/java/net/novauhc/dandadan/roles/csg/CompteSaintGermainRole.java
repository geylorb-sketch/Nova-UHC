package net.novauhc.dandadan.roles.csg;

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

public class CompteSaintGermainRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "CSG_ABILITY_COUTEAU_NAME", type = VariableType.ABILITY)
    private Ability couteauAbility = new CouteauAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "CSG_ABILITY_FIOLE_NAME", type = VariableType.ABILITY)
    private Ability fioleAbility = new FioleAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "CSG_ABILITY_CARTE_NAME", type = VariableType.ABILITY)
    private Ability carteAbility = new CarteAbility();




    public CompteSaintGermainRole() {
        setCamp(DanDaDanCamps.SPECIAL);

    }

    @Override public String getName() { return "Compte de Saint-Germain"; }
    @Override public Material getIconMaterial() { return Material.DIAMOND_SWORD; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.CSG_NAME)
            .line(DanDaDanDescLang.CAMP_SPECIAL)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.CSG_COUTEAU_TEXT, DanDaDanDescLang.CSG_COUTEAU_HOVER)
            .hover(DanDaDanDescLang.CSG_FIOLE_TEXT, DanDaDanDescLang.CSG_FIOLE_HOVER)
            .hover(DanDaDanDescLang.CSG_CARTE_TEXT, DanDaDanDescLang.CSG_CARTE_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.DIAMOND_SWORD).setName(LangManager.get().get(DanDaDanLang.ITEM_CSG_FCOUTEAU)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.POTION).setName(LangManager.get().get(DanDaDanLang.ITEM_CSG_EFIOLE)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.MAP).setName(LangManager.get().get(DanDaDanLang.ITEM_CSG_6CARTE)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }

}
