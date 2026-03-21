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
import net.novaproject.novauhc.utils.HoverUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class JiangshiRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JIANGSHI_ABILITY_INVOCATION_NAME", type = VariableType.ABILITY)
    private Ability invocationAbility = new InvocationAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JIANGSHI_ABILITY_ALLOUTJ_NAME", type = VariableType.ABILITY)
    private Ability allOutJAbility = new AllOutJAbility();

    private final  RevivePassive revivePassive = new RevivePassive();
    private final  KiRegenPassive kiPassive  = new KiRegenPassive ();


    public JiangshiRole() {
        getAbilities().add(revivePassive);
        getAbilities().add(kiPassive);
    }

    @Override public String getName() { return "Jiangshi"; }
    @Override public Material getIconMaterial() { return Material.SKULL_ITEM; }

    private String L(DanDaDanDescLang k) { return LangManager.get().get(k); }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(L(DanDaDanDescLang.ROLE_PREFIX) + L(DanDaDanDescLang.JIANGSHI_NAME));
        p.sendMessage(L(DanDaDanDescLang.CAMP_YOKAI));
        p.sendMessage(L(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.JIANGSHI_REVIVE_TEXT), L(DanDaDanDescLang.JIANGSHI_REVIVE_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.JIANGSHI_KI_TEXT), L(DanDaDanDescLang.JIANGSHI_KI_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.JIANGSHI_INVOC_TEXT), L(DanDaDanDescLang.JIANGSHI_INVOC_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.JIANGSHI_ALLOUT_J_TEXT), L(DanDaDanDescLang.JIANGSHI_ALLOUT_J_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
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
