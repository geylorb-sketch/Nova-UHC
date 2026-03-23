package net.novauhc.dandadan.roles.jiangshi;

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

public class JiangshiRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JIANGSHI_ABILITY_INVOCATION_NAME", type = VariableType.ABILITY)
    private Ability invocationAbility = new InvocationAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JIANGSHI_ABILITY_ALLOUTJ_NAME", type = VariableType.ABILITY)
    private Ability allOutJAbility = new AllOutJAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JIANGSHI_ABILITY_REVIVE_NAME", type = VariableType.ABILITY)
    private Ability revivePassive = new RevivePassive();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JIANGSHI_ABILITY_KIREGEN_NAME", type = VariableType.ABILITY)
    private Ability kiPassive = new KiRegenPassive();


    public JiangshiRole() {
    }

    @Override public String getName() { return "Jiangshi"; }
    @Override public Material getIconMaterial() { return Material.SKULL_ITEM; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.JIANGSHI_NAME)
            .line(DanDaDanDescLang.CAMP_YOKAI)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_PASSIFS)
            .hover(DanDaDanDescLang.JIANGSHI_REVIVE_TEXT, DanDaDanDescLang.JIANGSHI_REVIVE_HOVER)
            .hover(DanDaDanDescLang.JIANGSHI_KI_TEXT, DanDaDanDescLang.JIANGSHI_KI_HOVER)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.JIANGSHI_INVOC_TEXT, DanDaDanDescLang.JIANGSHI_INVOC_HOVER)
            .hover(DanDaDanDescLang.JIANGSHI_ALLOUT_J_TEXT, DanDaDanDescLang.JIANGSHI_ALLOUT_J_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.BONE).setName(LangManager.get().get(DanDaDanLang.ITEM_JIANGSHI_7INVOCATION)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.SKULL_ITEM).setName(LangManager.get().get(DanDaDanLang.ITEM_JIANGSHI_CALLOUT)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }

}
