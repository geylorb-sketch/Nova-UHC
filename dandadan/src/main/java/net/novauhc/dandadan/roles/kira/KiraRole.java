package net.novauhc.dandadan.roles.kira;

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

public class KiraRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KIRA_ABILITY_KILLERQUEEN_NAME", type = VariableType.ABILITY)
    private Ability killerQueenAbility = new KillerQueenAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KIRA_ABILITY_SHEERHEART_NAME", type = VariableType.ABILITY)
    private Ability sheerHeartAbility = new SheerHeartAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KIRA_ABILITY_BITESDUST_NAME", type = VariableType.ABILITY)
    private Ability bitesDustAbility = new BitesDustAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KIRA_ABILITY_STRAYCAT_NAME", type = VariableType.ABILITY)
    private Ability strayCatAbility = new StrayCatAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KIRA_ABILITY_MAIN_NAME", type = VariableType.ABILITY)
    private Ability mainPassive = new MainPassive();


    public KiraRole() {
        setCamp(DanDaDanCamps.SPECIAL);
    }

    @Override public String getName() { return "Kira"; }
    @Override public Material getIconMaterial() { return Material.SKULL_ITEM; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.KIRA_NAME)
            .line(DanDaDanDescLang.CAMP_SPECIAL)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_PASSIFS)
            .hover(DanDaDanDescLang.KIRA_MAIN_TEXT, DanDaDanDescLang.KIRA_MAIN_HOVER)
            .hover(DanDaDanDescLang.KIRA_EXPLO_IM_TEXT, DanDaDanDescLang.KIRA_EXPLO_IM_HOVER)
            .hover(DanDaDanDescLang.KIRA_NOM_TEXT, DanDaDanDescLang.KIRA_NOM_HOVER)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.KIRA_KILLER_TEXT, DanDaDanDescLang.KIRA_KILLER_HOVER)
            .hover(DanDaDanDescLang.KIRA_SHA_TEXT, DanDaDanDescLang.KIRA_SHA_HOVER)
            .hover(DanDaDanDescLang.KIRA_BTD_TEXT, DanDaDanDescLang.KIRA_BTD_HOVER)
            .hover(DanDaDanDescLang.KIRA_STRAY_TEXT, DanDaDanDescLang.KIRA_STRAY_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.SKULL_ITEM).setName(LangManager.get().get(DanDaDanLang.ITEM_KIRA_DKILLER_QUEEN)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.EXPLOSIVE_MINECART).setName(LangManager.get().get(DanDaDanLang.ITEM_KIRA_CSHEER_HEART_ATTACK)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.WATCH).setName(LangManager.get().get(DanDaDanLang.ITEM_KIRA_5BITES_THE_DUST)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.GLASS).setName(LangManager.get().get(DanDaDanLang.ITEM_KIRA_ASTRAYCAT)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }

}
