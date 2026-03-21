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
import net.novaproject.novauhc.utils.HoverUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MinotaureRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "MINOTAURE_ABILITY_DURABILITE_NAME", type = VariableType.ABILITY)
    private Ability durabiliteAbility = new DurabiliteAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "MINOTAURE_ABILITY_KUNGFU_NAME", type = VariableType.ABILITY)
    private Ability kungFuAbility = new KungFuAbility();

    private final  OxydationPassive oxyPassive  = new OxydationPassive();


    public MinotaureRole() {
        getAbilities().add(oxyPassive);
    }

    @Override public String getName() { return "Minotaure"; }
    @Override public Material getIconMaterial() { return Material.IRON_INGOT; }

    private String L(DanDaDanDescLang k) { return LangManager.get().get(k); }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(L(DanDaDanDescLang.ROLE_PREFIX) + L(DanDaDanDescLang.MINOTAURE_NAME));
        p.sendMessage(L(DanDaDanDescLang.CAMP_YOKAI));
        p.sendMessage(L(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.MINOTAURE_OXY_TEXT), L(DanDaDanDescLang.MINOTAURE_OXY_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.MINOTAURE_DURA_TEXT), L(DanDaDanDescLang.MINOTAURE_DURA_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.MINOTAURE_KUNGFU_TEXT), L(DanDaDanDescLang.MINOTAURE_KUNGFU_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.MINOTAURE_FER_TEXT), L(DanDaDanDescLang.MINOTAURE_FER_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
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
