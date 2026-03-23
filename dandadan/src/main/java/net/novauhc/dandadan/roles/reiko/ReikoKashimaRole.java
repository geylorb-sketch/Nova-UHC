package net.novauhc.dandadan.roles.reiko;

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

public class ReikoKashimaRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "REIKO_ABILITY_THORNS_NAME", type = VariableType.ABILITY)
    private Ability thornsAbility = new ThornsAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "REIKO_ABILITY_MIROIRACTIF_NAME", type = VariableType.ABILITY)
    private Ability miroirActifAbility = new MiroirActifAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "REIKO_ABILITY_EMPRISONNEMENT_NAME", type = VariableType.ABILITY)
    private Ability emprisonnementAbility = new EmprisonnementAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "REIKO_ABILITY_MIROIR_NAME", type = VariableType.ABILITY)
    private Ability miroirPassive = new MiroirPassive();


    public ReikoKashimaRole() {
    }

    @Override public String getName() { return "Reiko Kashima"; }
    @Override public Material getIconMaterial() { return Material.COMPASS; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.REIKO_NAME)
            .line(DanDaDanDescLang.CAMP_YOKAI)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_PASSIFS)
            .hover(DanDaDanDescLang.REIKO_MIROIR_TEXT, DanDaDanDescLang.REIKO_MIROIR_HOVER)
            .hover(DanDaDanDescLang.REIKO_BF_TEXT, DanDaDanDescLang.REIKO_BF_HOVER)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.REIKO_THORNS_TEXT, DanDaDanDescLang.REIKO_THORNS_HOVER)
            .hover(DanDaDanDescLang.REIKO_MIROIR_A_TEXT, DanDaDanDescLang.REIKO_MIROIR_A_HOVER)
            .hover(DanDaDanDescLang.REIKO_PRISON_TEXT, DanDaDanDescLang.REIKO_PRISON_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.CACTUS).setName(LangManager.get().get(DanDaDanLang.ITEM_REIKO_2THORNS)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.GLASS).setName(LangManager.get().get(DanDaDanLang.ITEM_REIKO_FMIROIR_ACTIF)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.IRON_FENCE).setName(LangManager.get().get(DanDaDanLang.ITEM_REIKO_7EMPRISONNEMENT)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }

}
