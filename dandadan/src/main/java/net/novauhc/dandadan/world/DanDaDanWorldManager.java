package net.novauhc.dandadan.world;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.utils.ShortCooldownManager;
import net.novaproject.novauhc.world.utils.LobbyCreator;
import net.novaproject.novauhc.utils.UHCUtils;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.io.File;
import java.util.*;

/**
 * DanDaDanWorldManager — Dimension DanDaDan 300×300 + portails custom (schematic).
 *
 * Portails :
 *   Schematic "structure_portail_dandadan.schematic" chargé à 4 positions (N/S/E/O, rayon 60).
 *   Entrée détectée par proximité (2 blocs du centre) — aucun bloc PORTAL vanilla.
 *
 * Dimension :
 *   - Clone de "template_dandadan" → "dandadan_world"
 *   - PVP désactivé, keepInventory true
 *   - Bordure stricte 300×300
 *   - Joueur bloqué jusqu'à obtenir un yokai (claimRole → onYokaiObtained)
 */
public class DanDaDanWorldManager implements Listener {

    private static DanDaDanWorldManager instance;

    private static final String DANDADAN_TEMPLATE   = "template_dandadan";
    private static final String DANDADAN_WORLD_NAME = "dandadan_world";
    public  static final String PORTAL_SCHEMATIC    = "structure_portail_dandadan.schematic";

    public  static final int    WORLD_HALF_SIZE       = 150;
    private static final double PORTAL_TRIGGER_RADIUS = 2.0;
    private static final long   ENTER_COOLDOWN_MS     = 3000;

    private World            dandadanWorld;
    private final Set<UUID>           playersInDimension = new HashSet<>();
    private final Map<UUID, Location> returnLocations    = new HashMap<>();
    private final List<Location>      portalCenters      = new ArrayList<>();
    private final Map<UUID, Long>     lastEnterAttempt   = new HashMap<>();

    public static DanDaDanWorldManager get() {
        if (instance == null) instance = new DanDaDanWorldManager();
        return instance;
    }

    // ── Lifecycle ───────────────────────────────────────────────

    public void init() {
        Bukkit.getPluginManager().registerEvents(this, Main.get());
        LobbyCreator.cloneWorld(DANDADAN_TEMPLATE, DANDADAN_WORLD_NAME);
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            dandadanWorld = Bukkit.getWorld(DANDADAN_WORLD_NAME);
            if (dandadanWorld != null) {
                dandadanWorld.setPVP(false);
                dandadanWorld.setGameRuleValue("keepInventory", "true");
                Bukkit.getLogger().info("[DanDaDan] Dimension '" + DANDADAN_WORLD_NAME + "' chargee.");
            } else {
                Bukkit.getLogger().warning("[DanDaDan] Dimension non chargee ! "
                        + "Ajoutez le template '" + DANDADAN_TEMPLATE + "' sur le serveur.");
            }
        }, 40L);
    }

    /**
     * Charge le schematic de portail aux 4 points cardinaux (rayon 60 blocs du spawn).
     * Appelé depuis DanDaDan.onGameStart().
     */
    public void spawnPortals(World mainWorld) {
        File schematic = new File(Main.get().getDataFolder(),
                "api/schem/" + PORTAL_SCHEMATIC);

        int[][] offsets = {{60, 0}, {-60, 0}, {0, 60}, {0, -60}};

        for (int[] off : offsets) {
            int x = off[0], z = off[1];
            int y = mainWorld.getHighestBlockYAt(x, z) + 1;
            Location base = new Location(mainWorld, x, y, z);

            if (schematic.exists()) {
                UHCUtils.loadSchematic(Main.get(), schematic, base);
                Bukkit.getLogger().info("[DanDaDan] Portail custom place en (" + x + "," + y + "," + z + ")");
            } else {
                buildFallbackPortal(base);
                Bukkit.getLogger().warning("[DanDaDan] Schematic portail introuvable -> portail de remplacement.");
            }

            // Centre d'activation = 2 blocs au-dessus de la base (milieu visuel du schematic)
            portalCenters.add(base.clone().add(0, 2, 0));
        }
        Bukkit.getLogger().info("[DanDaDan] " + portalCenters.size() + " portails actifs.");
    }

    public void cleanup() {
        new HashSet<>(playersInDimension).forEach(uuid -> {
            Player p = Bukkit.getPlayer(uuid);
            if (p != null) returnToMain(p);
        });
        playersInDimension.clear();
        returnLocations.clear();
        portalCenters.clear();
        lastEnterAttempt.clear();

        if (dandadanWorld != null) {
            LobbyCreator.deleteWorld(DANDADAN_WORLD_NAME,
                    Bukkit.getWorlds().get(0).getSpawnLocation());
            dandadanWorld = null;
        }
    }

    // ── Portail de remplacement (schematic absent) ───────────────

    private void buildFallbackPortal(Location base) {
        World w = base.getWorld(); if (w == null) return;
        int x = base.getBlockX(), y = base.getBlockY(), z = base.getBlockZ();
        // 2 piliers + linteau
        for (int dy = 0; dy <= 3; dy++) {
            w.getBlockAt(x - 1, y + dy, z).setType(Material.SPONGE);
            w.getBlockAt(x + 1, y + dy, z).setType(Material.SPONGE);
        }
        for (int dx = -1; dx <= 1; dx++) w.getBlockAt(x + dx, y + 4, z).setType(Material.STONE);
        w.getBlockAt(x, y, z).setType(Material.STONE);
        // Panneau
        Location sign = new Location(w, x, y + 2, z - 1);
        sign.getBlock().setType(Material.WALL_SIGN);
        if (sign.getBlock().getState() instanceof org.bukkit.block.Sign s) {
            s.setLine(0, "§5§l[DanDaDan]"); s.setLine(1, "§7Approchez-vous");
            s.setLine(2, "§7pour entrer"); s.setLine(3, "§5→ Yokai"); s.update();
        }
    }

    // ── Détection par proximité ──────────────────────────────────

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location to = event.getTo(); if (to == null) return;

        // Entrée dans la dimension
        if (!isInDimension(player) && dandadanWorld != null
                && !to.getWorld().getName().equals(DANDADAN_WORLD_NAME)) {
            long now = System.currentTimeMillis();
            if (now - lastEnterAttempt.getOrDefault(player.getUniqueId(), 0L) < ENTER_COOLDOWN_MS) return;
            for (Location center : portalCenters) {
                if (!center.getWorld().equals(to.getWorld())) continue;
                if (center.distance(to) <= PORTAL_TRIGGER_RADIUS) {
                    lastEnterAttempt.put(player.getUniqueId(), now);
                    enterDimension(player); return;
                }
            }
        }

        // Bordure 300×300
        if (isInDimension(player) && dandadanWorld != null
                && to.getWorld().getName().equals(DANDADAN_WORLD_NAME)) {
            double x = to.getX(), z = to.getZ();
            if (Math.abs(x) > WORLD_HALF_SIZE || Math.abs(z) > WORLD_HALF_SIZE) {
                double cx = Math.max(-WORLD_HALF_SIZE + 5, Math.min(WORLD_HALF_SIZE - 5, x));
                double cz = Math.max(-WORLD_HALF_SIZE + 5, Math.min(WORLD_HALF_SIZE - 5, z));
                player.teleport(new Location(dandadanWorld, cx, to.getY(), cz, to.getYaw(), to.getPitch()));
                LangManager.get().send(DanDaDanLang.DANDADAN_WORLD_BORDER, player);
            }
        }
    }

    // ── PVP désactivé dans la dimension ─────────────────────────

    @EventHandler(priority = EventPriority.HIGH)
    public void onPvp(EntityDamageByEntityEvent event) {
        if (dandadanWorld == null) return;
        if (!(event.getEntity() instanceof Player victim)) return;
        if (!(event.getDamager() instanceof Player attacker)) return;
        if (victim.getWorld().getName().equals(DANDADAN_WORLD_NAME)) {
            event.setCancelled(true);
            LangManager.get().send(DanDaDanLang.DANDADAN_NO_PVP, attacker);
        }
    }

    // ── Entrée / Sortie ──────────────────────────────────────────

    private void enterDimension(Player player) {
        if (dandadanWorld == null) { player.sendMessage("§c[DanDaDan] §7Dimension indisponible."); return; }
        if (isInDimension(player)) return;
        if (DanDaDan.get() != null) {
            UHCPlayer uhcp = UHCPlayerManager.get().getPlayer(player);
            if (uhcp != null && DanDaDan.get().getRoleByUHCPlayer(uhcp) != null) {
                LangManager.get().send(DanDaDanLang.DANDADAN_ALREADY_HAS_ROLE, player); return;
            }
        }
        returnLocations.put(player.getUniqueId(), player.getLocation().clone());
        playersInDimension.add(player.getUniqueId());
        player.teleport(dandadanWorld.getSpawnLocation().clone().add(0, 1, 0));
        LangManager.get().send(DanDaDanLang.DANDADAN_ENTERED, player);
        UHCPlayerManager.get().getOnlineUHCPlayers().stream()
                .filter(p -> p.getPlayer() != null && !p.getPlayer().equals(player))
                .forEach(p -> p.getPlayer().sendMessage(
                        LangManager.get().get(DanDaDanLang.DANDADAN_PLAYER_ENTERED, p.getPlayer())
                                .replace("%player%", player.getName())));
    }

    /** Appelé par DanDaDan.claimRole() — libère le joueur de la dimension après 2s. */
    public void onYokaiObtained(Player player) {
        if (!isInDimension(player)) return;
        LangManager.get().send(DanDaDanLang.DANDADAN_YOKAI_OBTAINED_EXIT, player);
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> returnToMain(player), 40L);
    }

    private void returnToMain(Player player) {
        playersInDimension.remove(player.getUniqueId());
        Location ret = returnLocations.remove(player.getUniqueId());
        if (player.isOnline()) player.teleport(ret != null ? ret
                : Bukkit.getWorlds().get(0).getSpawnLocation().clone().add(0, 1, 0));
    }

    // ── Getters ─────────────────────────────────────────────────

    public boolean isInDimension(Player p)    { return playersInDimension.contains(p.getUniqueId()); }
    public World getDandadanWorld()            { return dandadanWorld; }
    public List<Location> getPortalLocations() { return Collections.unmodifiableList(portalCenters); }
    public Set<UUID> getPlayersInDimension()   { return Collections.unmodifiableSet(playersInDimension); }
}
