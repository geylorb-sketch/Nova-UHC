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
import net.novaproject.novauhc.utils.HoverUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ReikoKashimaRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "REIKO_ABILITY_THORNS_NAME", type = VariableType.ABILITY)
    private Ability thornsAbility = new ThornsAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "REIKO_ABILITY_MIROIRACTIF_NAME", type = VariableType.ABILITY)
    private Ability miroirActifAbility = new MiroirActifAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "REIKO_ABILITY_EMPRISONNEMENT_NAME", type = VariableType.ABILITY)
    private Ability emprisonnementAbility = new EmprisonnementAbility();

    private final MiroirPassive miroirPassive  = new MiroirPassive();


    public ReikoKashimaRole() {
        getAbilities().add(miroirPassive);
    }

    @Override public String getName() { return "Reiko Kashima"; }
    @Override public Material getIconMaterial() { return Material.COMPASS; }

    private String L(DanDaDanDescLang k) { return LangManager.get().get(k); }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(L(DanDaDanDescLang.ROLE_PREFIX) + L(DanDaDanDescLang.REIKO_NAME));
        p.sendMessage(L(DanDaDanDescLang.CAMP_YOKAI));
        p.sendMessage(L(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.REIKO_MIROIR_TEXT), L(DanDaDanDescLang.REIKO_MIROIR_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.REIKO_BF_TEXT), L(DanDaDanDescLang.REIKO_BF_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.REIKO_THORNS_TEXT), L(DanDaDanDescLang.REIKO_THORNS_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.REIKO_MIROIR_A_TEXT), L(DanDaDanDescLang.REIKO_MIROIR_A_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.REIKO_PRISON_TEXT), L(DanDaDanDescLang.REIKO_PRISON_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
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
