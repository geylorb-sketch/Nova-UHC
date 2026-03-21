package net.novauhc.dandadan.roles.enenra;

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
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EnenraRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ENENRA_ABILITY_SETTA_NAME", type = VariableType.ABILITY)
    private Ability setta = new SettaAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ENENRA_ABILITY_FUMEE_NAME", type = VariableType.ABILITY)
    private Ability fumee = new CorpsFumeeAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ENENRA_ABILITY_NINJA_NAME", type = VariableType.ABILITY)
    private Ability ninja = new NinjaAbility();

    private final ComboPassive comboPassive = new ComboPassive();


    public EnenraRole() {
        getAbilities().add(comboPassive);
    }

    @Override public String getName() { return "Enenra"; }
    @Override public Material getIconMaterial() { return Material.COAL; }

    private String L(DanDaDanDescLang k) { return LangManager.get().get(k); }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(L(DanDaDanDescLang.ROLE_PREFIX) + L(DanDaDanDescLang.ENENRA_NAME));
        p.sendMessage(L(DanDaDanDescLang.CAMP_YOKAI));
        p.sendMessage(L(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.ENENRA_COMBO_TEXT), L(DanDaDanDescLang.ENENRA_COMBO_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.ENENRA_SETTA_TEXT), L(DanDaDanDescLang.ENENRA_SETTA_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.ENENRA_FUMEE_TEXT), L(DanDaDanDescLang.ENENRA_FUMEE_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.ENENRA_NINJA_TEXT), L(DanDaDanDescLang.ENENRA_NINJA_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.COAL).setName(LangManager.get().get(DanDaDanLang.ITEM_ENENRA_8SETTA)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.FLINT).setName(LangManager.get().get(DanDaDanLang.ITEM_ENENRA_7CORPS_DE_FUMEE)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.ARMOR_STAND).setName(LangManager.get().get(DanDaDanLang.ITEM_ENENRA_5NINJA)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }


    @Override
    public void onHit(Entity entity, Entity dammager, EntityDamageByEntityEvent event) {
        if (!(entity instanceof Player victim)) return;
        if (!(event.getDamager() instanceof Player a)) return;
        if (victim.getPlayer() != null) comboPassive.onHit(a, victim.getPlayer(), event);
    }
}
