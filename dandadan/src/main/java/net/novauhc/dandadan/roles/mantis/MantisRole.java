package net.novauhc.dandadan.roles.mantis;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanDescLang;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import net.novaproject.novauhc.scenario.role.RoleDescription;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MantisRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "MANTIS_ABILITY_BOXE_NAME", type = VariableType.ABILITY)
    private Ability boxeAbility = new BoxeAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "MANTIS_ABILITY_UPPERCUT_NAME", type = VariableType.ABILITY)
    private Ability uppercutAbility = new UppercutAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "MANTIS_ABILITY_JETWATER_NAME", type = VariableType.ABILITY)
    private Ability jetWaterAbility = new JetWaterAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "MANTIS_ABILITY_CRABE_NAME", type = VariableType.ABILITY)
    private Ability crabeAbility = new CrabeAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "MANTIS_ABILITY_BUSINESS_NAME", type = VariableType.ABILITY)
    private Ability businessPassive = new BusinessPassive();


    public MantisRole() {
    }

    @Override public String getName() { return "M. Mantis"; }
    @Override public Material getIconMaterial() { return Material.IRON_AXE; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.MANTIS_NAME)
            .line(DanDaDanDescLang.CAMP_YOKAI)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_PASSIFS)
            .hover(DanDaDanDescLang.MANTIS_BUSINESS_TEXT, DanDaDanDescLang.MANTIS_BUSINESS_HOVER)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.MANTIS_BOXE_TEXT, DanDaDanDescLang.MANTIS_BOXE_HOVER)
            .hover(DanDaDanDescLang.MANTIS_UPPERCUT_TEXT, DanDaDanDescLang.MANTIS_UPPERCUT_HOVER)
            .hover(DanDaDanDescLang.MANTIS_JET_TEXT, DanDaDanDescLang.MANTIS_JET_HOVER)
            .hover(DanDaDanDescLang.MANTIS_CRABE_TEXT, DanDaDanDescLang.MANTIS_CRABE_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.IRON_AXE).setName(LangManager.get().get(DanDaDanLang.ITEM_MANTIS_CBOXE)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.IRON_SWORD).setName(LangManager.get().get(DanDaDanLang.ITEM_MANTIS_6UPPERCUT)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.WATER_BUCKET).setName(LangManager.get().get(DanDaDanLang.ITEM_MANTIS_9JET_WATER)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.PRISMARINE_SHARD).setName(LangManager.get().get(DanDaDanLang.ITEM_MANTIS_ECRABE)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }

}
