package net.novauhc.dandadan.roles.polnareff;

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

public class PolnareffRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "POLNAREFF_ABILITY_SILVERCHARIOT_NAME", type = VariableType.ABILITY)
    private Ability silverChariotAbility = new SilverChariotAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "POLNAREFF_ABILITY_SWORDLAUNCH_NAME", type = VariableType.ABILITY)
    private Ability swordLaunchAbility = new SwordLaunchAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "POLNAREFF_ABILITY_HORARUSH_NAME", type = VariableType.ABILITY)
    private Ability horaRushAbility = new HoraRushAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "POLNAREFF_ABILITY_IMAGEREMAN_NAME", type = VariableType.ABILITY)
    private Ability imageRemanAbility = new ImageRemanAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "POLNAREFF_ABILITY_FRANCAIS_NAME", type = VariableType.ABILITY)
    private Ability francaisPassive = new FrancaisPassive();


    public PolnareffRole() {
        setCamp(DanDaDanCamps.SPECIAL);
    }

    @Override public String getName() { return "Jean-Pierre Polnareff"; }
    @Override public Material getIconMaterial() { return Material.IRON_SWORD; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.POLNAREFF_NAME)
            .line(DanDaDanDescLang.CAMP_SPECIAL)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_PASSIFS)
            .hover(DanDaDanDescLang.POLNAREFF_FRANCAIS_TEXT, DanDaDanDescLang.POLNAREFF_FRANCAIS_HOVER)
            .hover(DanDaDanDescLang.POLNAREFF_PRECISION_TEXT, DanDaDanDescLang.POLNAREFF_PRECISION_HOVER)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.POLNAREFF_SILVER_TEXT, DanDaDanDescLang.POLNAREFF_SILVER_HOVER)
            .hover(DanDaDanDescLang.POLNAREFF_SWORD_L_TEXT, DanDaDanDescLang.POLNAREFF_SWORD_L_HOVER)
            .hover(DanDaDanDescLang.POLNAREFF_HORA_TEXT, DanDaDanDescLang.POLNAREFF_HORA_HOVER)
            .hover(DanDaDanDescLang.POLNAREFF_IMAGE_TEXT, DanDaDanDescLang.POLNAREFF_IMAGE_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.IRON_SWORD).setName(LangManager.get().get(DanDaDanLang.ITEM_POLNAREFF_7SILVER_CHARIOT)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.IRON_SWORD).setName(LangManager.get().get(DanDaDanLang.ITEM_POLNAREFF_BSWORD_LAUNCH)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.DIAMOND_SWORD).setName(LangManager.get().get(DanDaDanLang.ITEM_POLNAREFF_EHORA_RUSH)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.ARMOR_STAND).setName(LangManager.get().get(DanDaDanLang.ITEM_POLNAREFF_5IMAGE_REMANENTE)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }

}
