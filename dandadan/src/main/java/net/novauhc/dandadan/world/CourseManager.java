package net.novauhc.dandadan.world;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.ConfigUtils;
import net.novaproject.novauhc.utils.Titles;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * CourseManager — Gère les courses d'Okarun.
 *
 * Config dans dandadan.yml :
 *   course:
 *     start: { x: 0, y: 100, z: 300 }
 *     end:   { x: 50, y: 100, z: 400 }
 *     checkpoints:
 *       0: { x: 10, y: 100, z: 320 }
 *       1: { x: 25, y: 100, z: 350 }
 *       2: { x: 40, y: 100, z: 380 }
 *     finish_radius: 3
 *     checkpoint_radius: 4
 *
 * Flow :
 *   1. Okarun lance /ddd course <pseudo>
 *   2. Les 2 joueurs sont TP au start
 *   3. Countdown 3-2-1-GO
 *   4. Ils doivent passer par les checkpoints dans l'ordre
 *   5. Premier à atteindre le end gagne
 *   6. Gagnant : +1❤ permanent. Okarun gagne 30s de malédiction bonus.
 */
public class CourseManager {

    private static CourseManager instance;
    public static CourseManager get() {
        if (instance == null) instance = new CourseManager();
        return instance;
    }

    // Config
    private int[] startPos;
    private int[] endPos;
    private List<int[]> checkpoints = new ArrayList<>();
    private int finishRadius = 3;
    private int checkpointRadius = 4;

    // State
    private boolean raceActive = false;
    private Player racer1, racer2;
    private int racer1Checkpoint = 0, racer2Checkpoint = 0;
    private BukkitTask raceTickTask;

    public void loadConfig() {
        FileConfiguration config = DanDaDan.get().getConfig();
        if (config == null || !config.contains("course.start.x")) {
            Bukkit.getLogger().warning("[DanDaDan] Pas de config course dans dandadan.yml");
            return;
        }
        startPos = new int[]{config.getInt("course.start.x"), config.getInt("course.start.y"), config.getInt("course.start.z")};
        endPos = new int[]{config.getInt("course.end.x"), config.getInt("course.end.y"), config.getInt("course.end.z")};
        finishRadius = config.getInt("course.finish_radius", 3);
        checkpointRadius = config.getInt("course.checkpoint_radius", 4);

        checkpoints.clear();
        int i = 0;
        while (config.contains("course.checkpoints." + i + ".x")) {
            checkpoints.add(new int[]{
                    config.getInt("course.checkpoints." + i + ".x"),
                    config.getInt("course.checkpoints." + i + ".y"),
                    config.getInt("course.checkpoints." + i + ".z")
            });
            i++;
        }
        Bukkit.getLogger().info("[DanDaDan] Course chargée : " + checkpoints.size() + " checkpoints");
    }

    public boolean isConfigured() {
        return startPos != null && endPos != null;
    }

    /**
     * Lance une course entre 2 joueurs.
     */
    public boolean startRace(Player okarun, Player target) {
        if (raceActive) return false;
        if (!isConfigured()) return false;

        raceActive = true;
        racer1 = okarun;
        racer2 = target;
        racer1Checkpoint = 0;
        racer2Checkpoint = 0;

        World world = okarun.getWorld();
        Location start = new Location(world, startPos[0] + 0.5, startPos[1], startPos[2] + 0.5);

        // TP les 2 joueurs au start
        racer1.teleport(start.clone().add(2, 0, 0));
        racer2.teleport(start.clone().add(-2, 0, 0));

        // Immobiliser pendant le countdown
        racer1.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 127, false, false));
        racer2.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 127, false, false));

        // Countdown 3-2-1-GO
        for (int i = 3; i >= 1; i--) {
            int sec = i;
            Bukkit.getScheduler().runTaskLater(Main.get(), () -> {
                String color = sec == 3 ? "§c" : sec == 2 ? "§6" : "§a";
                new Titles().sendTitle(racer1, color + "§l" + sec, "§7Course !", 20);
                new Titles().sendTitle(racer2, color + "§l" + sec, "§7Course !", 20);
                racer1.playSound(racer1.getLocation(), Sound.NOTE_STICKS, 1f, sec == 1 ? 2f : 1f);
                racer2.playSound(racer2.getLocation(), Sound.NOTE_STICKS, 1f, sec == 1 ? 2f : 1f);
            }, 20L * (3 - sec));
        }

        // GO !
        Bukkit.getScheduler().runTaskLater(Main.get(), () -> {
            racer1.removePotionEffect(PotionEffectType.SLOW);
            racer2.removePotionEffect(PotionEffectType.SLOW);
            racer1.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60 * 20, 1, false, true));
            racer2.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60 * 20, 1, false, true));
            new Titles().sendTitle(racer1, "§a§lGO !", " ", 20);
            new Titles().sendTitle(racer2, "§a§lGO !", " ", 20);
            racer1.playSound(racer1.getLocation(), Sound.ENDERDRAGON_GROWL, 0.5f, 2f);
            racer2.playSound(racer2.getLocation(), Sound.ENDERDRAGON_GROWL, 0.5f, 2f);

            // Tick de la course
            raceTickTask = Bukkit.getScheduler().runTaskTimer(Main.get(), this::tickRace, 0L, 5L);
        }, 60L); // 3s

        // Timeout 60s
        Bukkit.getScheduler().runTaskLater(Main.get(), () -> {
            if (raceActive) endRace(null);
        }, 60 * 20L + 60L);

        return true;
    }

    private void tickRace() {
        if (!raceActive) { raceTickTask.cancel(); return; }
        if (!racer1.isOnline() || !racer2.isOnline()) { endRace(null); return; }

        World world = racer1.getWorld();

        // Dessiner les checkpoints
        int totalCheckpoints = checkpoints.size();
        for (int i = 0; i < totalCheckpoints; i++) {
            int[] cp = checkpoints.get(i);
            Location cpLoc = new Location(world, cp[0], cp[1], cp[2]);
            Color color = (i < racer1Checkpoint && i < racer2Checkpoint) ? Color.GRAY :
                    (i < racer1Checkpoint || i < racer2Checkpoint) ? Color.YELLOW : Color.CYAN;
            drawCheckpointRing(cpLoc, color);
        }

        // Dessiner la ligne d'arrivée
        Location endLoc = new Location(world, endPos[0], endPos[1], endPos[2]);
        drawCheckpointRing(endLoc, Color.GREEN);

        // Vérifier les checkpoints pour chaque joueur
        racer1Checkpoint = checkProgress(racer1, racer1Checkpoint);
        racer2Checkpoint = checkProgress(racer2, racer2Checkpoint);

        // Vérifier l'arrivée
        if (racer1Checkpoint >= totalCheckpoints &&
                racer1.getLocation().distance(endLoc) <= finishRadius) {
            endRace(racer1);
        } else if (racer2Checkpoint >= totalCheckpoints &&
                racer2.getLocation().distance(endLoc) <= finishRadius) {
            endRace(racer2);
        }
    }

    private int checkProgress(Player player, int currentCheckpoint) {
        if (currentCheckpoint >= checkpoints.size()) return currentCheckpoint;
        int[] cp = checkpoints.get(currentCheckpoint);
        Location cpLoc = new Location(player.getWorld(), cp[0], cp[1], cp[2]);
        if (player.getLocation().distance(cpLoc) <= checkpointRadius) {
            player.playSound(player.getLocation(), Sound.EXPLODE, 1f, 1.5f);
            LangManager.get().send(DanDaDanLang.COURSE_CHECKPOINT, player,
                    Map.of("%num%", String.valueOf(currentCheckpoint + 1),
                            "%total%", String.valueOf(checkpoints.size())));
            return currentCheckpoint + 1;
        }
        return currentCheckpoint;
    }

    private void endRace(Player winner) {
        raceActive = false;
        if (raceTickTask != null) raceTickTask.cancel();

        if (winner == null) {
            // Timeout ou déconnexion
            if (racer1 != null && racer1.isOnline())
                LangManager.get().send(DanDaDanLang.COURSE_TIMEOUT, racer1);
            if (racer2 != null && racer2.isOnline())
                LangManager.get().send(DanDaDanLang.COURSE_TIMEOUT, racer2);
        } else {
            Player loser = winner.equals(racer1) ? racer2 : racer1;

            // Gagnant : +1❤ permanent
            winner.setMaxHealth(winner.getMaxHealth() + 2);
            winner.setHealth(Math.min(winner.getMaxHealth(), winner.getHealth() + 2));
            winner.playSound(winner.getLocation(), Sound.LEVEL_UP, 1f, 1f);
            new Titles().sendTitle(winner, "§a§l✦ VICTOIRE !", "§f+1❤ permanent", 60);
            LangManager.get().send(DanDaDanLang.COURSE_WIN, winner);

            if (loser != null && loser.isOnline()) {
                loser.playSound(loser.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
                new Titles().sendTitle(loser, "§c§l✦ DÉFAITE", " ", 60);
                LangManager.get().send(DanDaDanLang.COURSE_LOSE, loser);
            }
        }

        // TP retour au GameWorld
        Location spawn = net.novaproject.novauhc.Common.get().getArena() != null
                ? net.novaproject.novauhc.Common.get().getArena().getSpawnLocation()
                : Bukkit.getWorlds().get(0).getSpawnLocation();
        if (racer1 != null && racer1.isOnline()) racer1.teleport(spawn);
        if (racer2 != null && racer2.isOnline()) racer2.teleport(spawn);
    }

    private void drawCheckpointRing(Location center, Color color) {
        for (int i = 0; i < 16; i++) {
            double angle = Math.toRadians(i * 22.5);
            new ParticleBuilder(ParticleEffect.REDSTONE)
                    .setColor(color)
                    .setLocation(center.clone().add(Math.cos(angle) * 2, 1, Math.sin(angle) * 2))
                    .setAmount(1).display();
        }
    }

    public boolean isRaceActive() { return raceActive; }
}
