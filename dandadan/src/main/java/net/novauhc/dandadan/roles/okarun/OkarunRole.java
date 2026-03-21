package net.novauhc.dandadan.roles.okarun;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
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
 * Okarun — Rôle DanDaDan
 *
 * Passifs :
 *   - Mémé 100km/H : speed si joueur proche
 *   - Toubillion : < 3❤ + coup → spectateur 5s + regen 2❤
 *
 * Actifs :
 *   - Malédiction : 30% speed (clic-droit BLAZE_POWDER)
 *   - All-Out : dash 20 blocs (clic-gauche BLAZE_POWDER pendant malédiction)
 *   - Rythme : 5 coups → no hit delay 60% 7s (clic-droit NETHER_STAR)
 *   - Course : /ddd course (3x/partie)
 *   - Tunnel : Espace Vide, TP 30 blocs (clic-droit ENDER_PEARL)
 *
 * Variables rôle :
 *   - curseMaxTime : durée max de la malédiction (10min)
 *   - killBonusTime : +1min par kill
 */
public class OkarunRole extends DanDaDanRole {

    // ── Variables configurables ──

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "OKARUN_CURSE_MAX_TIME_NAME",
            descKey = "OKARUN_CURSE_MAX_TIME_DESC", type = VariableType.TIME)
    private int curseMaxTime = 600; // 10min

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "OKARUN_KILL_BONUS_NAME",
            descKey = "OKARUN_KILL_BONUS_DESC", type = VariableType.TIME)
    private int killBonusTime = 60; // +1min par kill

    private int remainingCurseTime;

    // ── Abilities @RoleVariable (ajoutées par RoleVariableProcessor) ──

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "OKARUN_ABILITY_MALEDICTION_NAME", type = VariableType.ABILITY)
    private Ability malediction = new MaledictionAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "OKARUN_ABILITY_ALLOUT_NAME", type = VariableType.ABILITY)
    private Ability allOut = new AllOutAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "OKARUN_ABILITY_RYTHME_NAME", type = VariableType.ABILITY)
    private Ability rythme = new RythmeAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "OKARUN_ABILITY_COURSE_NAME", type = VariableType.ABILITY)
    private Ability course = new CourseCommand();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "OKARUN_ABILITY_TUNNEL_NAME", type = VariableType.ABILITY)
    private Ability tunnel = new TunnelAbility();

    // ── Passifs sans @RoleVariable → ajoutés dans le constructeur ──

    private final MemePassive memePassive = new MemePassive();
    private final ToubillionPassive toubillionPassive = new ToubillionPassive();

    public OkarunRole() {
        getAbilities().add(memePassive);
        getAbilities().add(toubillionPassive);
    }

    // ── Identité ──

    @Override public String getName()           { return "Okarun"; }
    @Override public Material getIconMaterial() { return Material.BLAZE_POWDER; }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.ROLE_PREFIX) + LangManager.get().get(DanDaDanDescLang.OKARUN_NAME));
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.CAMP_YOKAI));
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, LangManager.get().get(DanDaDanDescLang.OKARUN_MEME_TEXT), LangManager.get().get(DanDaDanDescLang.OKARUN_MEME_HOVER));
        HoverUtils.sendHoverLine(p, LangManager.get().get(DanDaDanDescLang.OKARUN_TOUBILLION_TEXT), LangManager.get().get(DanDaDanDescLang.OKARUN_TOUBILLION_HOVER));
        p.sendMessage(" ");
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, LangManager.get().get(DanDaDanDescLang.OKARUN_MALEDICTION_TEXT), LangManager.get().get(DanDaDanDescLang.OKARUN_MALEDICTION_HOVER));
        HoverUtils.sendHoverLine(p, LangManager.get().get(DanDaDanDescLang.OKARUN_ALLOUT_TEXT), LangManager.get().get(DanDaDanDescLang.OKARUN_ALLOUT_HOVER));
        HoverUtils.sendHoverLine(p, LangManager.get().get(DanDaDanDescLang.OKARUN_RYTHME_TEXT), LangManager.get().get(DanDaDanDescLang.OKARUN_RYTHME_HOVER));
        HoverUtils.sendHoverLine(p, LangManager.get().get(DanDaDanDescLang.OKARUN_COURSE_TEXT), LangManager.get().get(DanDaDanDescLang.OKARUN_COURSE_HOVER));
        p.sendMessage(" ");
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SECTION_ESPACE));
        HoverUtils.sendHoverLine(p, LangManager.get().get(DanDaDanDescLang.OKARUN_TUNNEL_TEXT), LangManager.get().get(DanDaDanDescLang.OKARUN_TUNNEL_HOVER));
        p.sendMessage(" ");
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SEPARATOR));
    }

    // ── Lifecycle ──

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        remainingCurseTime = curseMaxTime;
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            // Donner les items des abilities
            player.getInventory().addItem(
                    new net.novaproject.novauhc.utils.ItemCreator(Material.BLAZE_POWDER)
                            .setName(LangManager.get().get(DanDaDanLang.ITEM_OKARUN_CURSE)).getItemstack());
            player.getInventory().addItem(
                    new net.novaproject.novauhc.utils.ItemCreator(Material.NETHER_STAR)
                            .setName(LangManager.get().get(DanDaDanLang.ITEM_OKARUN_RYTHME)).getItemstack());
            player.getInventory().addItem(
                    new net.novaproject.novauhc.utils.ItemCreator(Material.ENDER_PEARL)
                            .setName(LangManager.get().get(DanDaDanLang.ITEM_OKARUN_TUNNEL)).setAmount(1).getItemstack());
        }
        super.onGive(uhcPlayer); // Envoie description + onGive des abilities
    }

    // ── Hooks ──

    @Override
    public void onKill(UHCPlayer killer, UHCPlayer victim) {
        super.onKill(killer, victim);
        addCurseTime(killBonusTime);
        Player bp = killer.getPlayer();
        if (bp != null) {
            LangManager.get().send(DanDaDanLang.OKARUN_KILL_BONUS, bp, java.util.Map.of("%time%", String.valueOf(remainingCurseTime / 60)));
        }
    }

    @Override
    public void onHit(Entity entity, Entity dammager, EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;
        if(!(entity instanceof Player victim)) return;

        // Malus Tunnel : 40% chance demi-coeur + 40% stop sprint
        if (tunnel instanceof TunnelAbility ta && ta.isInTunnel(victim.getPlayer())) {
            event.setDamage(ta.applyDamageMalus(event.getDamage()));
            if (ta.shouldStopSprint() && victim.getPlayer() != null) {
                victim.getPlayer().setSprinting(false);
            }
        }
        if (player.getHealth() - event.getFinalDamage() <= 6.0) {
            toubillionPassive.triggerToubillion(player);
        }
    }


    // ── Helpers malédiction ──

    public int getRemainingCurseTime()  { return remainingCurseTime; }
    public int getCurseMaxTime()        { return curseMaxTime; }
    public void decrementCurseTime()    { if (remainingCurseTime > 0) remainingCurseTime--; }
    public void addCurseTime(int sec)   { remainingCurseTime = Math.min(curseMaxTime, remainingCurseTime + sec); }
}
