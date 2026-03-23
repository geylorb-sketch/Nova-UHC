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
import net.novaproject.novauhc.scenario.role.RoleDescription;
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

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DIO_ABILITY_VAMPIRE_NAME", type = VariableType.ABILITY)
    private Ability vampirePassive = new VampirePassive();


    public DioRole() {
        setCamp(DanDaDanCamps.SPECIAL);
    }

    @Override public String getName() { return "Dio"; }
    @Override public Material getIconMaterial() { return Material.REDSTONE; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.DIO_NAME)
            .line(DanDaDanDescLang.CAMP_SPECIAL)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_PASSIFS)
            .hover(DanDaDanDescLang.DIO_VAMPIRE_TEXT, DanDaDanDescLang.DIO_VAMPIRE_HOVER)
            .hover(DanDaDanDescLang.DIO_ETOILE_TEXT, DanDaDanDescLang.DIO_ETOILE_HOVER)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.DIO_COUTEAU_D_TEXT, DanDaDanDescLang.DIO_COUTEAU_D_HOVER)
            .hover(DanDaDanDescLang.DIO_ROAD_TEXT, DanDaDanDescLang.DIO_ROAD_HOVER)
            .hover(DanDaDanDescLang.DIO_THEWORLD_TEXT, DanDaDanDescLang.DIO_THEWORLD_HOVER)
            .hover(DanDaDanDescLang.DIO_TIME_D_TEXT, DanDaDanDescLang.DIO_TIME_D_HOVER)
            .hover(DanDaDanDescLang.DIO_TIMESKIP_TEXT, DanDaDanDescLang.DIO_TIMESKIP_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
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
