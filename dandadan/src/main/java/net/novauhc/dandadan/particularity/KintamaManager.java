package net.novauhc.dandadan.particularity;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novauhc.dandadan.events.MoriohRadio;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * KintamaManager
 * ─────────────────────────────────────────────────────────────
 * - 2 Kintamas apparaissent entre 20 et 40 minutes dans des coffres,
 *   dans le rayon des portails sur la map principale.
 * - 1ère Kintama : +1❤ permanent + -25% cooldowns.
 *   Possesseur reçoit la distance vers la 2ème toutes les 5 min.
 * - 2 Kintamas : +2❤ permanents + -50% cooldowns + passif Yokai (survie à mort certaine).
 */
public class KintamaManager implements Listener {

    private static KintamaManager instance;

    // Positions des coffres (définies lors du setup via setChestLocations)
    private final List<Location> chestLocations = new ArrayList<>();
    private final Map<UUID, Integer> kintamaCount = new HashMap<>();  // joueur → 0/1/2
    private boolean kintama1Placed = false;
    private boolean kintama2Placed = false;
    private Location kintama2Location = null;

    private int monitorTaskId = -1;
    private int distanceTaskId = -1;

    public static KintamaManager get() {
        if (instance == null) instance = new KintamaManager();
        return instance;
    }

    // ── Lifecycle ──────────────────────────────────────────────

    public void start(World world) {
        Bukkit.getPluginManager().registerEvents(this, Main.get());

        // Planifier le spawn entre 20 et 40 minutes
        int spawnTick1 = (20 * 60 + ThreadLocalRandom.current().nextInt(20 * 60)) * 20;
        int spawnTick2 = spawnTick1 + ThreadLocalRandom.current().nextInt(5 * 60 * 20); // 0-5min après

        Main.get().getServer().getScheduler().runTaskLater(Main.get(),
                () -> placeKintama(world, 1), spawnTick1);
        Main.get().getServer().getScheduler().runTaskLater(Main.get(),
                () -> placeKintama(world, 2), spawnTick2);

        // Toutes les 5 minutes : envoi de la distance vers kintama2 au possesseur de kintama1
        distanceTaskId = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(),
                this::broadcastKintama2Distance, 20L * 300, 20L * 300);
    }

    public void stop() {
        if (distanceTaskId != -1) {
            Main.get().getServer().getScheduler().cancelTask(distanceTaskId);
            distanceTaskId = -1;
        }
        kintamaCount.clear();
        kintama1Placed = false;
        kintama2Placed = false;
        kintama2Location = null;
        chestLocations.clear();
    }

    /** Appelé par DanDaDan.setup() pour enregistrer les positions des coffres de la map. */
    public void addChestLocation(Location loc) {
        chestLocations.add(loc);
    }

    // ── Placement ──────────────────────────────────────────────

    private void placeKintama(World world, int number) {
        if (chestLocations.isEmpty()) {
            // Fallback : placer aléatoirement sur la map
            int x = ThreadLocalRandom.current().nextInt(-200, 200);
            int z = ThreadLocalRandom.current().nextInt(-200, 200);
            Location loc = new Location(world, x, world.getHighestBlockYAt(x, z) + 1, z);
            loc.getBlock().setType(Material.CHEST);
            if (number == 2) kintama2Location = loc;
            fillChestWithKintama(loc, number);
        } else {
            Location loc = chestLocations.get(
                    ThreadLocalRandom.current().nextInt(chestLocations.size()));
            loc.getBlock().setType(Material.CHEST);
            if (number == 2) kintama2Location = loc;
            fillChestWithKintama(loc, number);
        }

        String msg = number == 1
                ? LangManager.get().get(DanDaDanLang.KINTAMA_SPAWNED_1)
                : LangManager.get().get(DanDaDanLang.KINTAMA_SPAWNED_2);
        UHCPlayerManager.get().getOnlineUHCPlayers().forEach(p -> {
            if (p.getPlayer() != null) p.getPlayer().sendMessage(msg);
        });

        if (number == 1) kintama1Placed = true;
        else kintama2Placed = true;
    }

    private void fillChestWithKintama(Location loc, int number) {
        if (!(loc.getBlock().getState() instanceof org.bukkit.block.Chest chest)) return;
        ItemStack kintama = createKintamaItem(number);
        chest.getBlockInventory().setItem(13, kintama); // slot central
        chest.update();
    }

    public static ItemStack createKintamaItem(int number) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName("§d✦ Kintama §5#" + number);
        meta.setLore(List.of(
                "§7+1❤ permanent",
                "§7-25% cooldowns",
                number == 2 ? "§5Combiné : +2❤ + -50% CD + passif Yokai" : "§8Trouvez la 2ème Kintama !"
        ));
        item.setItemMeta(meta);
        return item;
    }

    // ── Collecte et effets ──────────────────────────────────────

    @EventHandler
    public void onPickup(org.bukkit.event.player.PlayerPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();
        if (!isKintamaItem(item)) return;
        Player player = event.getPlayer();
        giveKintama(player);
        event.getItem().remove();
        event.setCancelled(true);
    }

    @EventHandler
    public void onChestOpen(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock() == null || event.getClickedBlock().getType() != Material.CHEST) return;
        // Laisse le joueur ouvrir normalement, la collecte se fait par pickup
    }

    public void giveKintama(Player player) {
        int count = kintamaCount.merge(player.getUniqueId(), 1, Integer::sum);

        if (count == 1) {
            // +1❤ permanent
            player.setMaxHealth(player.getMaxHealth() + 2);
            player.sendMessage(LangManager.get().get(DanDaDanLang.KINTAMA_COLLECTED_1, player));
            MoriohRadio.get().broadcast(DanDaDanLang.RADIO_KINTAMA_COLLECTED,
                    Map.of("%player%", player.getName()));
        } else if (count >= 2) {
            // +2❤ permanent total (déjà +1 avant) + -50% CD
            player.setMaxHealth(player.getMaxHealth() + 2);
            player.sendMessage(LangManager.get().get(DanDaDanLang.KINTAMA_COLLECTED_2, player));
            MoriohRadio.get().broadcast(DanDaDanLang.RADIO_KINTAMA_BOTH,
                    Map.of("%player%", player.getName()));
        }
    }

    /**
     * Retourne true si ce joueur doit survivre à un coup fatal (2 Kintamas).
     * Appelé depuis l'event de dommage.
     */
    public boolean tryYokaiSurvive(Player player) {
        int count = kintamaCount.getOrDefault(player.getUniqueId(), 0);
        if (count < 2) return false;
        player.setHealth(6.0); // 3❤
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 30, 0));
        player.sendMessage(LangManager.get().get(DanDaDanLang.KINTAMA_YOKAI_SURVIVE, player));
        return true;
    }

    public int getKintamaCount(Player player) {
        return kintamaCount.getOrDefault(player.getUniqueId(), 0);
    }

    private boolean isKintamaItem(ItemStack item) {
        if (item == null || item.getType() != Material.SKULL_ITEM) return false;
        ItemMeta meta = item.getItemMeta();
        return meta != null && meta.getDisplayName() != null
                && meta.getDisplayName().contains("Kintama");
    }

    private void broadcastKintama2Distance() {
        if (kintama2Location == null) return;
        kintamaCount.forEach((uuid, count) -> {
            if (count != 1) return; // seulement ceux qui ont exactement 1
            Player p = Bukkit.getPlayer(uuid);
            if (p == null || !p.isOnline()) return;
            double dist = p.getLocation().distance(kintama2Location);
            String dir  = getCardinalDirection(p.getLocation(), kintama2Location);
            String msg  = LangManager.get().get(DanDaDanLang.KINTAMA_DISTANCE, p)
                    .replace("%dist%", String.format("%.0f", dist))
                    .replace("%dir%",  dir);
            p.sendMessage(msg);
        });
    }

    private String getCardinalDirection(Location from, Location to) {
        double dx = to.getX() - from.getX();
        double dz = to.getZ() - from.getZ();
        double angle = Math.toDegrees(Math.atan2(dz, dx));
        if (angle < 0) angle += 360;
        if (angle < 22.5 || angle >= 337.5) return "Est";
        if (angle < 67.5)  return "Sud-Est";
        if (angle < 112.5) return "Sud";
        if (angle < 157.5) return "Sud-Ouest";
        if (angle < 202.5) return "Ouest";
        if (angle < 247.5) return "Nord-Ouest";
        if (angle < 292.5) return "Nord";
        return "Nord-Est";
    }
}
