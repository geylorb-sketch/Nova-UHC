package net.novauhc.dandadan.roles.seiko;

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

public class SeikoRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "SEIKO_ABILITY_BARRIERE_M_NAME", type = VariableType.ABILITY)
    private Ability barriereM = new BarriereMystiqueAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "SEIKO_ABILITY_BARRIERE_I_NAME", type = VariableType.ABILITY)
    private Ability barriereI = new BarriereInterieureAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "SEIKO_ABILITY_MOTS_NAME", type = VariableType.ABILITY)
    private Ability motsPassive = new PouvoirDesMotsPassive();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "SEIKO_ABILITY_DIEU_NAME", type = VariableType.ABILITY)
    private Ability dieuPassive = new DieuRegionPassive();

    public SeikoRole() {
    }

    @Override public String getName()           { return "Seiko"; }
    @Override public Material getIconMaterial() { return Material.BARRIER; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.SEIKO_NAME)
            .line(DanDaDanDescLang.CAMP_YOKAI)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_PASSIFS)
            .hover(DanDaDanDescLang.SEIKO_MOTS_TEXT, DanDaDanDescLang.SEIKO_MOTS_HOVER)
            .hover(DanDaDanDescLang.SEIKO_DIEU_TEXT, DanDaDanDescLang.SEIKO_DIEU_HOVER)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.SEIKO_BARRIERE_M_TEXT, DanDaDanDescLang.SEIKO_BARRIERE_M_HOVER)
            .hover(DanDaDanDescLang.SEIKO_BARRIERE_I_TEXT, DanDaDanDescLang.SEIKO_BARRIERE_I_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.BARRIER).setName(LangManager.get().get(DanDaDanLang.ITEM_SEIKO_BARRIERE_M)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.STAINED_GLASS).setName(LangManager.get().get(DanDaDanLang.ITEM_SEIKO_BARRIERE_I)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }

    @Override
    public void onHit(Entity entity, Entity dammager, EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker)) return;
        if (!(entity instanceof Player victim)) return;
        if (victim.getPlayer() == null) return;
        if (motsPassive instanceof PouvoirDesMotsPassive pmp) pmp.onHit(attacker, victim.getPlayer(), event);
    }
}
