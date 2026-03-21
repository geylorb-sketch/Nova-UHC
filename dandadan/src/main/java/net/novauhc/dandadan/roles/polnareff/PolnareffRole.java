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
import net.novaproject.novauhc.utils.HoverUtils;
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

    private final FrancaisPassive francaisPassive  = new FrancaisPassive();


    public PolnareffRole() {
        setCamp(DanDaDanCamps.SPECIAL);
        getAbilities().add(francaisPassive);
    }

    @Override public String getName() { return "Jean-Pierre Polnareff"; }
    @Override public Material getIconMaterial() { return Material.IRON_SWORD; }

    private String L(DanDaDanDescLang k) { return LangManager.get().get(k); }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(L(DanDaDanDescLang.ROLE_PREFIX) + L(DanDaDanDescLang.POLNAREFF_NAME));
        p.sendMessage(L(DanDaDanDescLang.CAMP_SPECIAL));
        p.sendMessage(L(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.POLNAREFF_FRANCAIS_TEXT), L(DanDaDanDescLang.POLNAREFF_FRANCAIS_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.POLNAREFF_PRECISION_TEXT), L(DanDaDanDescLang.POLNAREFF_PRECISION_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.POLNAREFF_SILVER_TEXT), L(DanDaDanDescLang.POLNAREFF_SILVER_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.POLNAREFF_SWORD_L_TEXT), L(DanDaDanDescLang.POLNAREFF_SWORD_L_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.POLNAREFF_HORA_TEXT), L(DanDaDanDescLang.POLNAREFF_HORA_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.POLNAREFF_IMAGE_TEXT), L(DanDaDanDescLang.POLNAREFF_IMAGE_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
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
