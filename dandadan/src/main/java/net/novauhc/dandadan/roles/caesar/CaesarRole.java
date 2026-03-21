package net.novauhc.dandadan.roles.caesar;

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

public class CaesarRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "CAESAR_ABILITY_SAVONLAUNCHER_NAME", type = VariableType.ABILITY)
    private Ability savonLauncherAbility = new SavonLauncherAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "CAESAR_ABILITY_SAVONLENSES_NAME", type = VariableType.ABILITY)
    private Ability savonLensesAbility = new SavonLensesAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "CAESAR_ABILITY_SAVONCUTTER_NAME", type = VariableType.ABILITY)
    private Ability savonCutterAbility = new SavonCutterAbility();

    private final  BandanaPassive bandanaPassive = new BandanaPassive();
    private final  HamonPassive hamonPassive= new HamonPassive();


    public CaesarRole() {
        setCamp(DanDaDanCamps.SPECIAL);
        getAbilities().add(hamonPassive);
        getAbilities().add(bandanaPassive);
    }

    @Override public String getName() { return "Caesar"; }
    @Override public Material getIconMaterial() { return Material.GLASS; }

    private String L(DanDaDanDescLang k) { return LangManager.get().get(k); }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(L(DanDaDanDescLang.ROLE_PREFIX) + L(DanDaDanDescLang.CAESAR_NAME));
        p.sendMessage(L(DanDaDanDescLang.CAMP_SPECIAL));
        p.sendMessage(L(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.CAESAR_BANDANA_TEXT), L(DanDaDanDescLang.CAESAR_BANDANA_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.CAESAR_HAMON_TEXT), L(DanDaDanDescLang.CAESAR_HAMON_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.CAESAR_SAVON_L_TEXT), L(DanDaDanDescLang.CAESAR_SAVON_L_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.CAESAR_SAVON_LE_TEXT), L(DanDaDanDescLang.CAESAR_SAVON_LE_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.CAESAR_SAVON_C_TEXT), L(DanDaDanDescLang.CAESAR_SAVON_C_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.GLASS).setName(LangManager.get().get(DanDaDanLang.ITEM_CAESAR_BSAVON_LAUNCHER)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.GLASS_BOTTLE).setName(LangManager.get().get(DanDaDanLang.ITEM_CAESAR_ESAVON_LENSES)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.SHEARS).setName(LangManager.get().get(DanDaDanLang.ITEM_CAESAR_CSAVON_CUTTER)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }

}
