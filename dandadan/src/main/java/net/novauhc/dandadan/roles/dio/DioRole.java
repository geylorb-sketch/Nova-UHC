package net.novauhc.dandadan.roles.dio;

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

public class DioRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DIO_ABILITY_COUTEAUDIO_NAME", type = VariableType.ABILITY)
    private Ability couteauDioAbility = new CouteauDioAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DIO_ABILITY_ROADROLLER_NAME", type = VariableType.ABILITY)
    private Ability roadRollerAbility = new RoadRollerAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DIO_ABILITY_THEWORLD_NAME", type = VariableType.ABILITY)
    private Ability theWorldAbility = new TheWorldAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DIO_ABILITY_ARRETTEMPSD_NAME", type = VariableType.ABILITY)
    private Ability arretTempsDAbility = new ArretTempsDAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DIO_ABILITY_TIMESKIP_NAME", type = VariableType.ABILITY)
    private Ability timeSkipAbility = new TimeSkipAbility();

    private final  VampirePassive vampirePassive = new VampirePassive();


    public DioRole() {
        setCamp(DanDaDanCamps.SPECIAL);
        getAbilities().add(vampirePassive);
    }

    @Override public String getName() { return "Dio"; }
    @Override public Material getIconMaterial() { return Material.REDSTONE; }

    private String L(DanDaDanDescLang k) { return LangManager.get().get(k); }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(L(DanDaDanDescLang.ROLE_PREFIX) + L(DanDaDanDescLang.DIO_NAME));
        p.sendMessage(L(DanDaDanDescLang.CAMP_SPECIAL));
        p.sendMessage(L(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.DIO_VAMPIRE_TEXT), L(DanDaDanDescLang.DIO_VAMPIRE_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.DIO_ETOILE_TEXT), L(DanDaDanDescLang.DIO_ETOILE_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.DIO_COUTEAU_D_TEXT), L(DanDaDanDescLang.DIO_COUTEAU_D_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.DIO_ROAD_TEXT), L(DanDaDanDescLang.DIO_ROAD_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.DIO_THEWORLD_TEXT), L(DanDaDanDescLang.DIO_THEWORLD_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.DIO_TIME_D_TEXT), L(DanDaDanDescLang.DIO_TIME_D_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.DIO_TIMESKIP_TEXT), L(DanDaDanDescLang.DIO_TIMESKIP_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.SNOW_BALL).setName(LangManager.get().get(DanDaDanLang.ITEM_DIO_7COUTEAU)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.ANVIL).setName(LangManager.get().get(DanDaDanLang.ITEM_DIO_6ROAD_ROLLER)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.REDSTONE).setName(LangManager.get().get(DanDaDanLang.ITEM_DIO_CTHE_WORLD)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.WATCH).setName(LangManager.get().get(DanDaDanLang.ITEM_DIO_5ARRET_DU_TEMPS)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.ENDER_PEARL).setName(LangManager.get().get(DanDaDanLang.ITEM_DIO_DTIME_SKIP)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }

}
