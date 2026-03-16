package net.novauhc.dandadan.events;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;

import java.util.stream.Collectors;

/**
 * RandomEventScheduler — Orchestre tous les événements aléatoires.
 * ─────────────────────────────────────────────────────────────
 * - Vérifie chaque seconde si le Typhoon Human peut se déclencher.
 * - Diffuse des coordonnées aléatoires via la Radio Morioh-Cho (toutes les 3-7 min).
 */
public class RandomEventScheduler {

    private static RandomEventScheduler instance;
    private int mainTaskId   = -1;
    private int coordTaskId  = -1;
    private int secondsPlayed = 0;

    public static RandomEventScheduler get() {
        if (instance == null) instance = new RandomEventScheduler();
        return instance;
    }

    private boolean enableRadio   = true;
    private boolean enableTyphoon = true;

    public void setEnableRadio(boolean v)   { this.enableRadio   = v; }
    public void setEnableTyphoon(boolean v) { this.enableTyphoon = v; }

    public void start() {
        MoriohRadio.get().setEnabled(enableRadio);
        TyphoonHuman.get().init();

        // Tâche principale : chaque seconde
        mainTaskId = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(), () -> {
            secondsPlayed++;
            // Vérifie Typhoon Human
            if (secondsPlayed >= 120) { // Au moins 2 minutes de jeu avant de déclencher
                if (enableTyphoon) TyphoonHuman.get().checkAndTrigger();
            }
        }, 20L, 20L);

        // Tâche Radio coords : toutes les 3-7 minutes aléatoirement
        scheduleNextCoordsEvent();
    }

    public void stop() {
        if (mainTaskId != -1) {
            Main.get().getServer().getScheduler().cancelTask(mainTaskId);
            mainTaskId = -1;
        }
        if (coordTaskId != -1) {
            Main.get().getServer().getScheduler().cancelTask(coordTaskId);
            coordTaskId = -1;
        }
        TyphoonHuman.get().reset();
        secondsPlayed = 0;
    }

    private void scheduleNextCoordsEvent() {
        // Délai entre 3 et 7 minutes
        long delay = (long)((3 * 60 + Math.random() * 4 * 60) * 20);
        coordTaskId = Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            var onlinePlayers = UHCPlayerManager.get().getPlayingOnlineUHCPlayers().stream()
                    .map(p -> p.getPlayer())
                    .filter(p -> p != null)
                    .collect(Collectors.toList());
            MoriohRadio.get().broadcastRandomCoords(onlinePlayers);
            scheduleNextCoordsEvent(); // Replanifier
        }, delay).getTaskId();
    }

    public int getSecondsPlayed() { return secondsPlayed; }
}
