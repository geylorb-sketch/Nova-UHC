package net.novauhc.dandadan.roles.minotaure;

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

public class MinotaureRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "MINOTAURE_ABILITY_DURABILITE_NAME", type = VariableType.ABILITY)
    private Ability durabiliteAbility = new DurabiliteAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "MINOTAURE_ABILITY_KUNGFU_NAME", type = VariableType.ABILITY)
    private Ability kungFuAbility = new KungFuAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "MINOTAURE_ABILITY_OXYDATION_NAME", type = VariableType.ABILITY)
    private Ability oxyPassive = new OxydationPassive();


    public MinotaureRole() {
    }

    @Override public String getName() { return "Minotaure"; }
    @Override public Material getIconMaterial() { return Material.IRON_INGOT; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.MINOTAURE_NAME)
            .line(DanDaDanDescLang.CAMP_YOKAI)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_PASSIFS)
            .hover(DanDaDanDescLang.MINOTAURE_OXY_TEXT, DanDaDanDescLang.MINOTAURE_OXY_HOVER)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.MINOTAURE_DURA_TEXT, DanDaDanDescLang.MINOTAURE_DURA_HOVER)
            .hover(DanDaDanDescLang.MINOTAURE_KUNGFU_TEXT, DanDaDanDescLang.MINOTAURE_KUNGFU_HOVER)
            .hover(DanDaDanDescLang.MINOTAURE_FER_TEXT, DanDaDanDescLang.MINOTAURE_FER_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.IRON_INGOT).setName(LangManager.get().get(DanDaDanLang.ITEM_MINOTAURE_7DURABILITE)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.IRON_AXE).setName(LangManager.get().get(DanDaDanLang.ITEM_MINOTAURE_CKUNG_FU)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }

}
