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
import net.novaproject.novauhc.scenario.role.RoleDescription;
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

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ENENRA_ABILITY_COMBO_NAME", type = VariableType.ABILITY)
    private Ability comboPassive = new ComboPassive();


    public EnenraRole() {
    }

    @Override public String getName() { return "Enenra"; }
    @Override public Material getIconMaterial() { return Material.COAL; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.ENENRA_NAME)
            .line(DanDaDanDescLang.CAMP_YOKAI)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_PASSIFS)
            .hover(DanDaDanDescLang.ENENRA_COMBO_TEXT, DanDaDanDescLang.ENENRA_COMBO_HOVER)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.ENENRA_SETTA_TEXT, DanDaDanDescLang.ENENRA_SETTA_HOVER)
            .hover(DanDaDanDescLang.ENENRA_FUMEE_TEXT, DanDaDanDescLang.ENENRA_FUMEE_HOVER)
            .hover(DanDaDanDescLang.ENENRA_NINJA_TEXT, DanDaDanDescLang.ENENRA_NINJA_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
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
        if (victim.getPlayer() != null && comboPassive instanceof ComboPassive cp) cp.onHit(a, victim.getPlayer(), event);
    }
}
