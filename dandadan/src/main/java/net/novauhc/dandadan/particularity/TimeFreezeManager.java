package net.novauhc.dandadan.particularity;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.utils.Titles;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.Color;
import java.util.*;

/**
 * TimeFreezeManager — Arrêt du Temps (Jotaro / Dio)
 * 
 * Gitbook:
 * - Tous les joueurs immobilisés, ne peuvent plus agir
 * - 3 cercles autour du détenteur: vert (0-6), jaune (6-11), rouge (20+)
 * - Cercle vert: libre / jaune: slowness 1 / rouge: slowness 2
 * - Dégâts max pendant le freeze: 2.5❤ (5 HP)
 * - Durée dépend du rôle, +0.5s par kill
 */
public class TimeFreezeManager implements Listener {

    private static TimeFreezeManager instance;
    public static TimeFreezeManager get() {
        if (instance == null) instance = new TimeFreezeManager();
        return instance;
    }

    private static final double MAX_FREEZE_DAMAGE = 5.0; // 2.5❤
    private static final int GREEN_RADIUS = 6;
    private static final int YELLOW_RADIUS = 11;
    private static final int RED_RADIUS = 20;

    private boolean frozen = false;
    private Player freezer = null;
    private Location freezeCenter = null;
    private BukkitTask freezeTask = null;
    private int remainingTicks = 0;
    private final Map<UUID, Location> frozenPositions = new HashMap<>();
    private final Map<UUID, Double> frozenDamage = new HashMap<>();

    /**
     * Active le Time Freeze.
     * @param player Le joueur qui active (Jotaro ou Dio)
     * @param durationSeconds Durée en secondes
     */
    public void activate(Player player, int durationSeconds) {
        if (frozen) return; // Déjà actif

        frozen = true;
        freezer = player;
        freezeCenter = player.getLocation().clone();
        remainingTicks = durationSeconds * 20;
        frozenPositions.clear();
        frozenDamage.clear();

        // Geler tous les joueurs sauf le détenteur
        for (UHCPlayer uhc : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
            Player p = uhc.getPlayer();
            if (p == null || p.equals(player)) continue;
            frozenPositions.put(p.getUniqueId(), p.getLocation().clone());
            frozenDamage.put(p.getUniqueId(), 0.0);
            // Immobilisation via slowness extrême
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, durationSeconds * 20 + 40, 127, false, false));
            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, durationSeconds * 20 + 40, 128, false, false));
        }

        // Annonce
        LangManager.get().sendAll(DanDaDanLang.AMOUR_CAFE_START);

        // Timer tick
        freezeTask = Bukkit.getScheduler().runTaskTimer(Main.get(), () -> {
            remainingTicks -= 20;
            if (remainingTicks <= 0) {
                deactivate();
                return;
            }

            // ActionBar avec temps restant
            int secondsLeft = remainingTicks / 20;
            new Titles().sendActionText(freezer, "§b§l⏱ Time Freeze : " + secondsLeft + "s");

            // Cercles de particules
            drawCircle(freezeCenter, GREEN_RADIUS, Color.GREEN);
            drawCircle(freezeCenter, YELLOW_RADIUS, Color.YELLOW);
            drawCircle(freezeCenter, RED_RADIUS, Color.RED);

            // Slowness du détenteur selon zone
            double dist = freezer.getLocation().distance(freezeCenter);
            freezer.removePotionEffect(PotionEffectType.SLOW);
            if (dist > RED_RADIUS) {
                freezer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30, 1, false, false));
            } else if (dist > YELLOW_RADIUS) {
                freezer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30, 0, false, false));
            }
        }, 0L, 20L);
    }

    /** Désactive le Time Freeze et applique les dégâts accumulés */
    public void deactivate() {
        if (!frozen) return;
        frozen = false;

        if (freezeTask != null) {
            freezeTask.cancel();
            freezeTask = null;
        }

        // Libérer tous les joueurs + appliquer dégâts
        for (UHCPlayer uhc : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
            Player p = uhc.getPlayer();
            if (p == null || p.equals(freezer)) continue;
            p.removePotionEffect(PotionEffectType.SLOW);
            p.removePotionEffect(PotionEffectType.JUMP);

            // Appliquer dégâts accumulés (max 2.5❤)
            Double dmg = frozenDamage.getOrDefault(p.getUniqueId(), 0.0);
            if (dmg > 0) {
                double capped = Math.min(dmg, MAX_FREEZE_DAMAGE);
                p.damage(capped);
            }
        }

        freezer.removePotionEffect(PotionEffectType.SLOW);
        LangManager.get().sendAll(DanDaDanLang.SEIKO_REGION_NORTHEAST);

        frozenPositions.clear();
        frozenDamage.clear();
        freezer = null;
        freezeCenter = null;
    }

    public boolean isFrozen() { return frozen; }
    public Player getFreezer() { return freezer; }

    /** Accumule les dégâts pendant le freeze (appelé depuis l'event) */
    public void accumulateDamage(UUID victim, double damage) {
        frozenDamage.merge(victim, damage, Double::sum);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onMove(PlayerMoveEvent event) {
        if (!frozen) return;
        Player p = event.getPlayer();
        if (p.equals(freezer)) return;
        // Bloquer le mouvement des joueurs gelés
        if (frozenPositions.containsKey(p.getUniqueId())) {
            Location frozen = frozenPositions.get(p.getUniqueId());
            if (event.getTo() != null && (event.getTo().getX() != event.getFrom().getX()
                    || event.getTo().getZ() != event.getFrom().getZ())) {
                event.setTo(event.getFrom());
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!frozen) return;
        if (!(event.getEntity() instanceof Player victim)) return;
        if (!(event.getDamager() instanceof Player damager)) return;

        // Seul le freezer peut faire des dégâts pendant le freeze
        if (!damager.equals(freezer)) {
            event.setCancelled(true);
            return;
        }

        // Accumuler au lieu d'infliger directement
        accumulateDamage(victim.getUniqueId(), event.getFinalDamage());
        event.setCancelled(true);

        // Feedback visuel
        new Titles().sendActionText(freezer, "§c+" + String.format("%.1f", event.getFinalDamage() / 2) + "❤ accumulé");
    }

    private void drawCircle(Location center, int radius, Color color) {
        for (int i = 0; i < 36; i++) {
            double angle = Math.toRadians(i * 10);
            double x = center.getX() + radius * Math.cos(angle);
            double z = center.getZ() + radius * Math.sin(angle);
            Location pt = new Location(center.getWorld(), x, center.getY() + 0.1, z);
            new ParticleBuilder(ParticleEffect.REDSTONE)
                    .setColor(color).setLocation(pt).setAmount(1).display();
        }
    }
}
