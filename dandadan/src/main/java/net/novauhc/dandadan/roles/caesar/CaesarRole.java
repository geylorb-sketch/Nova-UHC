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
import net.novaproject.novauhc.scenario.role.RoleDescription;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CaesarRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "CAESAR_ABILITY_SAVONLAUNCHER_NAME", type = VariableType.ABILITY)
    private Ability savonLauncherAbility = new SavonLauncherAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "CAESAR_ABILITY_SAVONLENSES_NAME", type = VariableType.ABILITY)
    private Ability savonLensesAbility = new SavonLensesAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "CAESAR_ABILITY_SAVONCUTTER_NAME", type = VariableType.ABILITY)
    private Ability savonCutterAbility = new SavonCutterAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "CAESAR_ABILITY_BANDANA_NAME", type = VariableType.ABILITY)
    private Ability bandanaPassive = new BandanaPassive();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "CAESAR_ABILITY_HAMON_NAME", type = VariableType.ABILITY)
    private Ability hamonPassive = new HamonPassive();


    public CaesarRole() {
        setCamp(DanDaDanCamps.SPECIAL);
    }

    @Override public String getName() { return "Caesar"; }
    @Override public Material getIconMaterial() { return Material.GLASS; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.CAESAR_NAME)
            .line(DanDaDanDescLang.CAMP_SPECIAL)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_PASSIFS)
            .hover(DanDaDanDescLang.CAESAR_BANDANA_TEXT, DanDaDanDescLang.CAESAR_BANDANA_HOVER)
            .hover(DanDaDanDescLang.CAESAR_HAMON_TEXT, DanDaDanDescLang.CAESAR_HAMON_HOVER)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.CAESAR_SAVON_L_TEXT, DanDaDanDescLang.CAESAR_SAVON_L_HOVER)
            .hover(DanDaDanDescLang.CAESAR_SAVON_LE_TEXT, DanDaDanDescLang.CAESAR_SAVON_LE_HOVER)
            .hover(DanDaDanDescLang.CAESAR_SAVON_C_TEXT, DanDaDanDescLang.CAESAR_SAVON_C_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
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
