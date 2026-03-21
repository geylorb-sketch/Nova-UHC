package net.novauhc.dandadan.roles.momo;

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

/**
 * Momo — Rôle DanDaDan
 *
 * Passif :
 *   - Serveuse : 2% annuler KB quand victime mange pomme en or
 *
 * Actifs :
 *   - Moe Moe : compteur de coups, 3 stades (🟢🟠🔴), max 30
 *   - Mémoire : /ddd memoire (5x), coordonnées joueur le plus proche
 *   - Psychokinésie : Main Droite (bloque joueur) / Main Gauche (3 blocs de pierre)
 */
public class MomoRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "MOMO_ABILITY_MOEMOE_NAME", type = VariableType.ABILITY)
    private Ability moeMoe = new MoeMoeAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "MOMO_ABILITY_MEMOIRE_NAME", type = VariableType.ABILITY)
    private Ability memoire = new MemoireCommand();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "MOMO_ABILITY_PSYCHO_NAME", type = VariableType.ABILITY)
    private Ability psychokinesie = new PsychokinesieAbility();

    private final ServeusePassive serveusePassive = new ServeusePassive();

    public MomoRole() {
        getAbilities().add(serveusePassive);
    }

    @Override public String getName()           { return "Momo"; }
    @Override public Material getIconMaterial() { return Material.STICK; }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.ROLE_PREFIX) + LangManager.get().get(DanDaDanDescLang.MOMO_NAME));
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.CAMP_YOKAI));
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, LangManager.get().get(DanDaDanDescLang.MOMO_SERVEUSE_TEXT), LangManager.get().get(DanDaDanDescLang.MOMO_SERVEUSE_HOVER));
        p.sendMessage(" ");
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, LangManager.get().get(DanDaDanDescLang.MOMO_MOEMOE_TEXT), LangManager.get().get(DanDaDanDescLang.MOMO_MOEMOE_HOVER));
        HoverUtils.sendHoverLine(p, LangManager.get().get(DanDaDanDescLang.MOMO_MEMOIRE_TEXT), LangManager.get().get(DanDaDanDescLang.MOMO_MEMOIRE_HOVER));
        HoverUtils.sendHoverLine(p, LangManager.get().get(DanDaDanDescLang.MOMO_PSYCHOKINESIE_TEXT), LangManager.get().get(DanDaDanDescLang.MOMO_PSYCHOKINESIE_HOVER));
        p.sendMessage(" ");
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SEPARATOR));
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(
                    new ItemCreator(Material.STICK).setName(LangManager.get().get(DanDaDanLang.ITEM_MOMO_MOEMOE)).getItemstack());
            player.getInventory().addItem(
                    new ItemCreator(Material.DIAMOND).setName(LangManager.get().get(DanDaDanLang.ITEM_MOMO_PSYCHO)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }

    @Override
    public void onHit(Entity entity, Entity dammager, EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker)) return;
        if (!(entity instanceof Player victim)) return;
        Player vp = victim.getPlayer();
        if (vp == null) return;

        // Serveuse : 2% annuler KB si victime mange gapple
        if (serveusePassive.shouldCancelKB(vp)) {
            event.setCancelled(false); // Le coup passe mais...
            vp.setVelocity(vp.getVelocity().multiply(0)); // KB annulé
            LangManager.get().send(DanDaDanLang.MOMO_SERVEUSE_TRIGGER, attacker);
        }
    }

}
