package net.novauhc.dandadan.world;

import net.novaproject.novauhc.Common;
import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.utils.Titles;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;
import java.awt.Color;
import java.util.*;
import java.util.List;

/**
 * YokaiZoneManager — Gère les zones de TP dans DanDaDan.
 *
 * ═══ Architecture ═══
 *
 * 1. Une SEULE liste de zones (positions) chargée depuis dandadan.yml
 * 2. Au démarrage, les zones sont assignées :
 *    - Les Yokai actifs reçoivent chacun une zone (shuffle aléatoire)
 *    - Les zones excédentaires deviennent des ZONES ALÉATOIRES
 * 3. Une zone ne peut accueillir qu'UN joueur à la fois
 *    → zone occupée = le joueur doit chercher une autre zone libre
 * 4. Quand un joueur ACCEPTE → zone convertie en aléatoire + libérée
 * 5. Quand un joueur REFUSE  → zone reste identique + libérée
 */
public class YokaiZoneManager {

    private static YokaiZoneManager instance;
    public static YokaiZoneManager get() {
        if (instance == null) instance = new YokaiZoneManager();
        return instance;
    }

    private final Random random = new Random();

    // ══════════════════════════════════════════════════════════
    //  ZONE STATE
    // ══════════════════════════════════════════════════════════

    public static class ZoneState {
        private final int[] position;
        private YokaiRegistry yokai;   // null = zone aléatoire
        private UUID occupiedBy;       // null = libre

        public ZoneState(int[] position, YokaiRegistry yokai) {
            this.position = position;
            this.yokai = yokai;
            this.occupiedBy = null;
        }

        public int[] getPosition()          { return position; }
        public YokaiRegistry getYokai()     { return yokai; }
        public boolean isRandom()           { return yokai == null; }
        public boolean isFree()             { return occupiedBy == null; }
        public UUID getOccupiedBy()         { return occupiedBy; }

        public void setOccupied(UUID uuid)  { this.occupiedBy = uuid; }
        public void setFree()               { this.occupiedBy = null; }
        public void convertToRandom()       { this.yokai = null; }
    }

    /** Toutes les zones construites */
    private final List<ZoneState> allZones = new ArrayList<>();

    /** Joueur → zone dans laquelle il est en épreuve */
    private final Map<UUID, ZoneState> playerZone = new HashMap<>();

    /** Joueur → Yokai assigné pour l'épreuve en cours */
    private final Map<UUID, YokaiRegistry> playerTrial = new HashMap<>();

    /** Joueurs en cours d'invocation (countdown avant TP) */
    private final Set<UUID> invoking = new HashSet<>();

    // ══════════════════════════════════════════════════════════
    //  BUILD ZONES — appelé au démarrage de la partie
    // ══════════════════════════════════════════════════════════

    /**
     * Construit les zones à partir du config.
     * 1. Charge toutes les positions depuis dandadan.yml
     * 2. Collecte les Yokai actifs (non réclamés)
     * 3. Shuffle les positions
     * 4. Assigne un Yokai par position, le reste devient aléatoire
     */
    public void buildZones() {
        allZones.clear();
        playerZone.clear();
        playerTrial.clear();
        invoking.clear();

        // 1. Charger les positions
        List<int[]> positions = YokaiConfig.get().loadAllZones();
        if (positions.isEmpty()) {
            Bukkit.getLogger().warning("[DanDaDan] Aucune zone configurée !");
            return;
        }

        // 2. Collecter les Yokai actifs
        List<YokaiRegistry> activeYokai = new ArrayList<>();
        for (YokaiRegistry y : YokaiRegistry.values()) {
            if (y.isEnabled()) activeYokai.add(y);
        }

        // 3. Shuffle positions
        Collections.shuffle(positions, random);

        // 4. Assigner chaque zone à un Yokai aléatoire (ou null si plus de Yokai)
        List<YokaiRegistry> remainingYokai = new ArrayList<>(activeYokai);
        for (int[] pos : positions) {
            YokaiRegistry yokai = null;
            if (!remainingYokai.isEmpty()) {
                int idx = random.nextInt(remainingYokai.size());
                yokai = remainingYokai.remove(idx); // retire pour éviter doublons
            }
            allZones.add(new ZoneState(pos, yokai));
        }

        Bukkit.getLogger().info("[DanDaDan] Zones construites : "
                + activeYokai.size() + " Yokai assignés, "
                + (positions.size() - activeYokai.size()) + " zones aléatoires "
                + "(total " + positions.size() + " zones)");
    }

    // ══════════════════════════════════════════════════════════
    //  TICK — appelé chaque seconde
    // ══════════════════════════════════════════════════════════

    public void tick(World dandadanWorld) {
        if (dandadanWorld == null) return;

        for (UHCPlayer uhc : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
            Player player = uhc.getPlayer();
            if (player == null) continue;
            if (!player.getWorld().equals(dandadanWorld)) continue;
            if(player.getLocation().getY() < YokaiRegistry.VOID_Y_THRESHOLD) {
                if(isInTrial(player)) continue;

                World world = Common.get().getArena();
                WorldBorder worldBorder = world.getWorldBorder();
                Random random = new Random();

                double radius = worldBorder.getSize() / 2;
                double x = worldBorder.getCenter().getX() + (random.nextDouble() * 2 - 1) * radius;
                double z = worldBorder.getCenter().getZ() + (random.nextDouble() * 2 - 1) * radius;
                double y = world.getHighestBlockYAt((int)x, (int)z) + 1;

                Location location = new Location(world, x, y, z);
                player.teleport(location);
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 1f);

                continue;
            }
            UUID uuid = player.getUniqueId();
            if (invoking.contains(uuid)) continue;
            if (playerZone.containsKey(uuid)) continue;
            if (DanDaDan.get().getRoleByUHCPlayer(uhc) != null) continue;

            int detectRadius = DanDaDan.get().getZoneDetectRadius();

            for (ZoneState zone : allZones) {
                if (!zone.isFree()) continue;

                Location zoneLoc = new Location(dandadanWorld,
                        zone.getPosition()[0], zone.getPosition()[1], zone.getPosition()[2]);

                if (player.getLocation().distance(zoneLoc) > detectRadius) continue;

                // Déterminer le Yokai
                YokaiRegistry yokai;
                if (zone.isRandom()) {
                    yokai = pickRandomAvailableYokai();
                    if (yokai == null) continue;
                } else {
                    yokai = zone.getYokai();
                    if (DanDaDan.get().isRoleClaimed(yokai.getRoleClass())) {
                        // Yokai déjà réclamé → cette zone devient aléatoire
                        zone.convertToRandom();
                        yokai = pickRandomAvailableYokai();
                        if (yokai == null) continue;
                    }
                }

                startInvocation(player, yokai, zone, dandadanWorld);
                break;
            }
        }
    }

    public void tickTrials(World dandadanWorld) {
        if (dandadanWorld == null) return;

        List<UUID> toProcess = new ArrayList<>();
        for (UUID uuid : playerZone.keySet()) {
            if (invoking.contains(uuid)) continue;
            Player player = Bukkit.getPlayer(uuid);

            if (player == null || !player.isOnline()) {
                toProcess.add(uuid);
                continue;
            }
            if (player.getLocation().getY() < YokaiRegistry.VOID_Y_THRESHOLD) {
                toProcess.add(uuid);
            }
        }


        for (UUID uuid : toProcess) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null && player.isOnline()) {
                refusePact(player);
            } else {
                ZoneState zone = playerZone.remove(uuid);
                playerTrial.remove(uuid);
                if (zone != null) zone.setFree();
            }
        }
    }

    // ══════════════════════════════════════════════════════════
    //  INVOCATION
    // ══════════════════════════════════════════════════════════

    private void startInvocation(Player player, YokaiRegistry yokai, ZoneState zone, World dandadanWorld) {
        UUID uuid = player.getUniqueId();

        zone.setOccupied(uuid);
        playerZone.put(uuid, zone);
        playerTrial.put(uuid, yokai);
        invoking.add(uuid);

        int invocationSec = DanDaDan.get() != null ? DanDaDan.get().getInvocationTime() : 3;

        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, invocationSec * 20 + 20, 127, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, invocationSec * 20 + 20, 128, false, false));

        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 0.5f);
        LangManager.get().send(DanDaDanLang.YOKAI_ZONE_ENTERED, player,
                Map.of("%yokai%", yokai.name()));

        Location center = player.getLocation().clone();

        new BukkitRunnable() {

            int ticks = 0;
            int secondsLeft = invocationSec;

            @Override
            public void run() {

                // Player déco → stop propre
                if (!player.isOnline()) {
                    cleanup();
                    cancel();
                    return;
                }

                // Particules toutes les 5 ticks
                if (ticks % 5 == 0) {
                    drawCylinder(center, 2.0, 4.0, Color.MAGENTA);
                }

                // Chaque seconde
                if (ticks % 20 == 0) {
                    if (secondsLeft > 0) {
                        new Titles().sendTitle(player, "§5§l" + secondsLeft, yokai.name(), 20);
                        player.playSound(player.getLocation(), Sound.NOTE_BASS, 1f, secondsLeft == 1 ? 2f : 1f);
                        secondsLeft--;
                    } else {
                        // FIN INVOCATION
                        finishInvocation();
                        cancel();
                    }
                }

                ticks++;
            }

            private void finishInvocation() {
                invoking.remove(uuid);

                player.removePotionEffect(PotionEffectType.SLOW);
                player.removePotionEffect(PotionEffectType.JUMP);

                int[] trial = yokai.getTrialLocation();
                Location trialLoc = new Location(dandadanWorld, trial[0] + 0.5, trial[1], trial[2] + 0.5);

                player.teleport(trialLoc);
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 1.5f);

                TrialNpcManager.get().spawnTrialNpc(player, yokai, dandadanWorld);

                LangManager.get().send(DanDaDanLang.YOKAI_TRIAL_START, player,
                        Map.of("%yokai%", yokai.name()));

                new Titles().sendTitle(player,
                        "§5§l" + yokai.name(),
                        LangManager.get().get(DanDaDanLang.TRIAL_TITLE_SUBTITLE), 60);
            }

            private void cleanup() {
                invoking.remove(uuid);
                playerZone.remove(uuid);
                playerTrial.remove(uuid);
                zone.setFree();
            }

        }.runTaskTimer(Main.get(), 0L, 1L);
    }

    // ══════════════════════════════════════════════════════════
    //  ACCEPTER LE PACTE
    // ══════════════════════════════════════════════════════════

    public void acceptPact(Player player) {
        UUID uuid = player.getUniqueId();
        ZoneState zone = playerZone.remove(uuid);
        YokaiRegistry yokai = playerTrial.remove(uuid);
        if (zone == null || yokai == null) return;

        // Retirer le PNJ d'épreuve
        TrialNpcManager.get().removeTrialNpc(player);

        UHCPlayer uhc = UHCPlayerManager.get().getPlayer(player);
        if (uhc == null) { zone.setFree(); return; }

        boolean claimed = DanDaDan.get().claimRole(uhc, yokai.getRoleClass());
        if (!claimed) {
            player.sendMessage(LangManager.get().get(DanDaDanLang.YOKAI_ALREADY_CLAIMED));
            zone.setFree();
            tpToDandadan(player);
            return;
        }

        // Zone devient ALÉATOIRE + libérée
        zone.convertToRandom();
        zone.setFree();

        // TP GameWorld
        World gameWorld = net.novaproject.novauhc.Common.get().getArena();
        if (gameWorld != null) {
            player.teleport(gameWorld.getSpawnLocation().clone().add(0, 1, 0));
        }

        player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
        LangManager.get().send(DanDaDanLang.YOKAI_PACT_ACCEPTED, player,
                Map.of("%yokai%", yokai.name()));
        new Titles().sendTitle(player, LangManager.get().get(DanDaDanLang.PACT_ACCEPT_TITLE), "§f" + yokai.name(), 60);
    }

    // ══════════════════════════════════════════════════════════
    //  REFUSER LE PACTE
    // ══════════════════════════════════════════════════════════

    public void refusePact(Player player) {
        UUID uuid = player.getUniqueId();
        ZoneState zone = playerZone.remove(uuid);
        playerTrial.remove(uuid);

        // Retirer le PNJ d'épreuve
        TrialNpcManager.get().removeTrialNpc(player);

        // Zone reste identique (même Yokai), juste libérée
        if (zone != null) zone.setFree();

        tpToDandadan(player);
        player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0f, 0.8f);
        LangManager.get().send(DanDaDanLang.YOKAI_PACT_REFUSED, player);
    }

    // ══════════════════════════════════════════════════════════
    //  HELPERS
    // ══════════════════════════════════════════════════════════

    private YokaiRegistry pickRandomAvailableYokai() {
        List<YokaiRegistry> available = new ArrayList<>();
        for (YokaiRegistry y : YokaiRegistry.values()) {
            if (!y.isEnabled()) continue;
            if (DanDaDan.get().isRoleClaimed(y.getRoleClass())) continue;
            available.add(y);
        }
        if (available.isEmpty()) return null;
        return available.get(random.nextInt(available.size()));
    }

    private void tpToDandadan(Player player) {
        World w = DanDaDanWorldManager.get().getDandadanWorld();
        if (w != null) player.teleport(w.getSpawnLocation().clone().add(0, 1, 0));
    }

    public boolean isInTrial(Player player) { return playerZone.containsKey(player.getUniqueId()); }
    public boolean isInvoking(Player player) { return invoking.contains(player.getUniqueId()); }
    public List<ZoneState> getAllZones() { return allZones; }

    public YokaiRegistry getPlayerTrialYokai(Player player) {
        return playerTrial.get(player.getUniqueId());
    }

    private void drawCylinder(Location center, double radius, double height, Color color) {
        for (double y = 0; y < height; y += 0.5) {
            for (int i = 0; i < 24; i++) {
                double angle = Math.toRadians(i * 15);
                double x = center.getX() + radius * Math.cos(angle);
                double z = center.getZ() + radius * Math.sin(angle);
                Location pt = new Location(center.getWorld(), x, center.getY() + y, z);
                new ParticleBuilder(ParticleEffect.REDSTONE)
                        .setColor(color).setLocation(pt).setAmount(1).display();
            }
        }
    }
}
