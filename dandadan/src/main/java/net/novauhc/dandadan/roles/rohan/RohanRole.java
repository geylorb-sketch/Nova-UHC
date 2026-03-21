package net.novauhc.dandadan.roles.rohan;

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
import net.novaproject.novauhc.utils.HoverUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class RohanRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ROHAN_ABILITY_HEAVENSDOOR_NAME", type = VariableType.ABILITY)
    private Ability heavensDoorAbility = new HeavensDoorAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ROHAN_ABILITY_LIVRE_NAME", type = VariableType.ABILITY)
    private Ability livreAbility = new LivreAbility();

    private final EcrivainPassive ecrivainPassive  = new EcrivainPassive();


    public RohanRole() {
        setCamp(DanDaDanCamps.SPECIAL);
        getAbilities().add(ecrivainPassive);
    }

    @Override public String getName() { return "Rohan"; }
    @Override public Material getIconMaterial() { return Material.BOOK; }

    private String L(DanDaDanDescLang k) { return LangManager.get().get(k); }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(L(DanDaDanDescLang.ROLE_PREFIX) + L(DanDaDanDescLang.ROHAN_NAME));
        p.sendMessage(L(DanDaDanDescLang.CAMP_SPECIAL));
        p.sendMessage(L(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.ROHAN_ECRIVAIN_TEXT), L(DanDaDanDescLang.ROHAN_ECRIVAIN_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.ROHAN_HEAVEN_TEXT), L(DanDaDanDescLang.ROHAN_HEAVEN_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.ROHAN_LIVRE_TEXT), L(DanDaDanDescLang.ROHAN_LIVRE_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.BOOK).setName(LangManager.get().get(DanDaDanLang.ITEM_ROHAN_BHEAVENS_DOOR)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.BOOK_AND_QUILL).setName(LangManager.get().get(DanDaDanLang.ITEM_ROHAN_ELIVRE)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }

}
