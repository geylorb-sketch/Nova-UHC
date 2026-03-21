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
import net.novaproject.novauhc.utils.HoverUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MonstreFlatwoodsRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "FLATWOODS_ABILITY_FUMEEF_NAME", type = VariableType.ABILITY)
    private Ability fumeeFAbility = new FumeeFAbility();

    private final FlatwoodsPassive flatPassive  = new FlatwoodsPassive();


    public MonstreFlatwoodsRole() {
        getAbilities().add(flatPassive);
    }

    @Override public String getName() { return "Monstre de Flatwoods"; }
    @Override public Material getIconMaterial() { return Material.LEATHER_HELMET; }

    private String L(DanDaDanDescLang k) { return LangManager.get().get(k); }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(L(DanDaDanDescLang.ROLE_PREFIX) + L(DanDaDanDescLang.FLATWOODS_NAME));
        p.sendMessage(L(DanDaDanDescLang.CAMP_YOKAI));
        p.sendMessage(L(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.FLATWOODS_FLAT_TEXT), L(DanDaDanDescLang.FLATWOODS_FLAT_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.FLATWOODS_FUMEE_F_TEXT), L(DanDaDanDescLang.FLATWOODS_FUMEE_F_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.FLATWOODS_SUMO_TEXT), L(DanDaDanDescLang.FLATWOODS_SUMO_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
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
