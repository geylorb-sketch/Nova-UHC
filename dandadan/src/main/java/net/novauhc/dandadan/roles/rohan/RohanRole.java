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
import net.novaproject.novauhc.scenario.role.RoleDescription;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class RohanRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ROHAN_ABILITY_HEAVENSDOOR_NAME", type = VariableType.ABILITY)
    private Ability heavensDoorAbility = new HeavensDoorAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ROHAN_ABILITY_LIVRE_NAME", type = VariableType.ABILITY)
    private Ability livreAbility = new LivreAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ROHAN_ABILITY_ECRIVAIN_NAME", type = VariableType.ABILITY)
    private Ability ecrivainPassive = new EcrivainPassive();


    public RohanRole() {
        setCamp(DanDaDanCamps.SPECIAL);
    }

    @Override public String getName() { return "Rohan"; }
    @Override public Material getIconMaterial() { return Material.BOOK; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.ROHAN_NAME)
            .line(DanDaDanDescLang.CAMP_SPECIAL)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_PASSIFS)
            .hover(DanDaDanDescLang.ROHAN_ECRIVAIN_TEXT, DanDaDanDescLang.ROHAN_ECRIVAIN_HOVER)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.ROHAN_HEAVEN_TEXT, DanDaDanDescLang.ROHAN_HEAVEN_HOVER)
            .hover(DanDaDanDescLang.ROHAN_LIVRE_TEXT, DanDaDanDescLang.ROHAN_LIVRE_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
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
