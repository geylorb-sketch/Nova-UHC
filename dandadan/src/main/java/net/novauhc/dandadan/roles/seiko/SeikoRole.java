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
import net.novaproject.novauhc.utils.HoverUtils;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class SeikoRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "SEIKO_ABILITY_BARRIERE_M_NAME", type = VariableType.ABILITY)
    private Ability barriereM = new BarriereMystiqueAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "SEIKO_ABILITY_BARRIERE_I_NAME", type = VariableType.ABILITY)
    private Ability barriereI = new BarriereInterieureAbility();

    private final PouvoirDesMotsPassive motsPassive = new PouvoirDesMotsPassive();
    private final DieuRegionPassive dieuPassive = new DieuRegionPassive();

    public SeikoRole() {
        getAbilities().add(motsPassive);
        getAbilities().add(dieuPassive);
    }

    @Override public String getName()           { return "Seiko"; }
    @Override public Material getIconMaterial() { return Material.BARRIER; }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.ROLE_PREFIX) + LangManager.get().get(DanDaDanDescLang.SEIKO_NAME));
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.CAMP_YOKAI));
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, LangManager.get().get(DanDaDanDescLang.SEIKO_MOTS_TEXT), LangManager.get().get(DanDaDanDescLang.SEIKO_MOTS_HOVER));
        HoverUtils.sendHoverLine(p, LangManager.get().get(DanDaDanDescLang.SEIKO_DIEU_TEXT), LangManager.get().get(DanDaDanDescLang.SEIKO_DIEU_HOVER));
        p.sendMessage(" ");
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, LangManager.get().get(DanDaDanDescLang.SEIKO_BARRIERE_M_TEXT), LangManager.get().get(DanDaDanDescLang.SEIKO_BARRIERE_M_HOVER));
        HoverUtils.sendHoverLine(p, LangManager.get().get(DanDaDanDescLang.SEIKO_BARRIERE_I_TEXT), LangManager.get().get(DanDaDanDescLang.SEIKO_BARRIERE_I_HOVER));
        p.sendMessage(" ");
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SEPARATOR));
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
        motsPassive.onHit(attacker, victim.getPlayer(), event);
    }
}
