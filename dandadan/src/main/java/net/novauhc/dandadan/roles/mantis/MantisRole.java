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
import net.novaproject.novauhc.utils.HoverUtils;
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

    private final BusinessPassive businessPassive  = new BusinessPassive();


    public MantisRole() {
        getAbilities().add(businessPassive);
    }

    @Override public String getName() { return "M. Mantis"; }
    @Override public Material getIconMaterial() { return Material.IRON_AXE; }

    private String L(DanDaDanDescLang k) { return LangManager.get().get(k); }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(L(DanDaDanDescLang.ROLE_PREFIX) + L(DanDaDanDescLang.MANTIS_NAME));
        p.sendMessage(L(DanDaDanDescLang.CAMP_YOKAI));
        p.sendMessage(L(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.MANTIS_BUSINESS_TEXT), L(DanDaDanDescLang.MANTIS_BUSINESS_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.MANTIS_BOXE_TEXT), L(DanDaDanDescLang.MANTIS_BOXE_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.MANTIS_UPPERCUT_TEXT), L(DanDaDanDescLang.MANTIS_UPPERCUT_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.MANTIS_JET_TEXT), L(DanDaDanDescLang.MANTIS_JET_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.MANTIS_CRABE_TEXT), L(DanDaDanDescLang.MANTIS_CRABE_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
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
