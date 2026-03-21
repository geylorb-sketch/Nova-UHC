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
import net.novaproject.novauhc.utils.HoverUtils;
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

    private final MainPassive mainPassive  = new MainPassive();


    public KiraRole() {
        setCamp(DanDaDanCamps.SPECIAL);
        getAbilities().add(mainPassive);
    }

    @Override public String getName() { return "Kira"; }
    @Override public Material getIconMaterial() { return Material.SKULL_ITEM; }

    private String L(DanDaDanDescLang k) { return LangManager.get().get(k); }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(L(DanDaDanDescLang.ROLE_PREFIX) + L(DanDaDanDescLang.KIRA_NAME));
        p.sendMessage(L(DanDaDanDescLang.CAMP_SPECIAL));
        p.sendMessage(L(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.KIRA_MAIN_TEXT), L(DanDaDanDescLang.KIRA_MAIN_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.KIRA_EXPLO_IM_TEXT), L(DanDaDanDescLang.KIRA_EXPLO_IM_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.KIRA_NOM_TEXT), L(DanDaDanDescLang.KIRA_NOM_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.KIRA_KILLER_TEXT), L(DanDaDanDescLang.KIRA_KILLER_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.KIRA_SHA_TEXT), L(DanDaDanDescLang.KIRA_SHA_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.KIRA_BTD_TEXT), L(DanDaDanDescLang.KIRA_BTD_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.KIRA_STRAY_TEXT), L(DanDaDanDescLang.KIRA_STRAY_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
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
