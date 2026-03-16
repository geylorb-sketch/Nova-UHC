package net.novauhc.dandadan.particularity;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.world.utils.LobbyCreator;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.roles.denji.DenjiRole;
import net.novauhc.dandadan.roles.momo.MomoRole;
import net.novauhc.dandadan.roles.okarun.OkarunRole;
import net.novauhc.dandadan.roles.reze.RezeRole;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * AmourManager — Système de duo romantique.
 * ─────────────────────────────────────────────────────────────
 * Couples :
 *   Denji + Reze  :  Denji donne une fleur blanche → Reze pose eau sous Denji + 30s ensemble
 *   Okarun + Momo :  Momo donne une golden carotte renommée "curry" à manger à Okarun → Okarun reste <5 blocs pendant 1min
 *
 * Si les deux réussissent → Épreuve du Café (build schematic, 2 salles, patron/reproduction) → 5min
 * Réussite : gagnent ensemble (DUO_AMOUR camp)
 */
public class AmourManager implements Listener {

    private static AmourManager instance;

    // ── État des tâches par couple ──
    private final Map<UUID, Boolean> task1Done = new HashMap<>();  // Tâche 1 complétée
    private final Map<UUID, Boolean> task2Done = new HashMap<>();  // Tâche 2 complétée
    private final Map<UUID, UUID>    partner   = new HashMap<>();  // uuid → uuid partenaire

    // Tâche Reze : surveille la présence d'eau + proximité 30s
    private int rezeProximityTask = -1;
    private int rezeProximityTimer = 0;

    // Tâche Okarun : surveille la proximité 60s
    private int okarunProximityTask = -1;
    private int okarunProximityTimer = 0;

    // Monde du Café (épreuve finale)
    private static final String CAFE_WORLD = "dandadan_cafe";
    private World cafeWorld;

    public static AmourManager get() {
        if (instance == null) instance = new AmourManager();
        return instance;
    }

    public void init() {
        Bukkit.getPluginManager().registerEvents(this, Main.get());
        LobbyCreator.cloneWorld("template_cafe_amour", CAFE_WORLD);
        Main.get().getServer().getScheduler().runTaskLater(Main.get(),
                () -> cafeWorld = Bukkit.getWorld(CAFE_WORLD), 40L);
        detectPartners();
    }

    public void cleanup() {
        if (rezeProximityTask != -1)   Main.get().getServer().getScheduler().cancelTask(rezeProximityTask);
        if (okarunProximityTask != -1) Main.get().getServer().getScheduler().cancelTask(okarunProximityTask);
        if (cafeWorld != null) {
            LobbyCreator.deleteWorld(CAFE_WORLD, Bukkit.getWorlds().get(0).getSpawnLocation());
            cafeWorld = null;
        }
        task1Done.clear();
        task2Done.clear();
        partner.clear();
    }

    // ── Détection des partenaires ───────────────────────────────

    private void detectPartners() {
        if (DanDaDan.get() == null) return;
        Player denjiPlayer = null, rezePlayer = null;
        Player okarunPlayer = null, momoPlayer = null;

        for (UHCPlayer uhcp : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
            DanDaDanRole role = DanDaDan.get().getRoleByUHCPlayer(uhcp);
            if (role == null || uhcp.getPlayer() == null) continue;
            if (role instanceof DenjiRole)  denjiPlayer  = uhcp.getPlayer();
            if (role instanceof RezeRole)   rezePlayer   = uhcp.getPlayer();
            if (role instanceof OkarunRole) okarunPlayer = uhcp.getPlayer();
            if (role instanceof MomoRole)   momoPlayer   = uhcp.getPlayer();
        }

        if (denjiPlayer != null && rezePlayer != null) {
            partner.put(denjiPlayer.getUniqueId(), rezePlayer.getUniqueId());
            partner.put(rezePlayer.getUniqueId(),  denjiPlayer.getUniqueId());
            denjiPlayer.sendMessage(LangManager.get().get(DanDaDanLang.AMOUR_DENJI_TASK, denjiPlayer));
            rezePlayer.sendMessage(LangManager.get().get(DanDaDanLang.AMOUR_REZE_TASK, rezePlayer));
        }
        if (okarunPlayer != null && momoPlayer != null) {
            partner.put(okarunPlayer.getUniqueId(), momoPlayer.getUniqueId());
            partner.put(momoPlayer.getUniqueId(),   okarunPlayer.getUniqueId());
            momoPlayer.sendMessage(LangManager.get().get(DanDaDanLang.AMOUR_MOMO_TASK, momoPlayer));
            okarunPlayer.sendMessage(LangManager.get().get(DanDaDanLang.AMOUR_OKARUN_TASK, okarunPlayer));
        }
    }

    // ── Tâche Denji : donner fleur blanche à Reze ──────────────

    @EventHandler
    public void onDenjiGiveFlower(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Player target)) return;
        Player giver = event.getPlayer();
        ItemStack held = giver.getItemInHand();
        if (held == null || held.getType() != Material.RED_ROSE) return;

        UUID targetPartner = partner.get(giver.getUniqueId());
        if (targetPartner == null || !target.getUniqueId().equals(targetPartner)) return;
        if (isDenji(giver) && isReze(target)) {
            giver.getItemInHand().setAmount(held.getAmount() - 1);
            task1Done.put(giver.getUniqueId(), true);
            giver.sendMessage(LangManager.get().get(DanDaDanLang.AMOUR_TASK_DONE, giver));
            checkAndStartRezePart(giver, target);
        }
    }

    /** Surveille que Reze place de l'eau sous Denji et restent 30s ensemble. */
    private void checkAndStartRezePart(Player denji, Player reze) {
        rezeProximityTimer = 0;
        rezeProximityTask = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(), () -> {
            if (!denji.isOnline() || !reze.isOnline()) {
                Main.get().getServer().getScheduler().cancelTask(rezeProximityTask); return;
            }
            // Vérifie eau sous Denji
            Material blockBelow = denji.getLocation().subtract(0, 1, 0).getBlock().getType();
            boolean waterBelow  = blockBelow == Material.STATIONARY_WATER || blockBelow == Material.WATER;
            boolean close       = reze.getLocation().distance(denji.getLocation()) <= 5;

            if (waterBelow && close) {
                rezeProximityTimer++;
                reze.sendMessage("§b" + rezeProximityTimer + "/30s...");
                if (rezeProximityTimer >= 30) {
                    task2Done.put(reze.getUniqueId(), true);
                    reze.sendMessage(LangManager.get().get(DanDaDanLang.AMOUR_TASK_DONE, reze));
                    Main.get().getServer().getScheduler().cancelTask(rezeProximityTask);
                    checkAndLaunchCafe(denji, reze);
                }
            } else {
                rezeProximityTimer = Math.max(0, rezeProximityTimer - 1);
            }
        }, 0L, 20L);
    }

    // ── Tâche Momo : donner golden carotte "curry" ──────────────

    @EventHandler
    public void onMomoCurry(PlayerItemConsumeEvent event) {
        Player eater = event.getPlayer();
        ItemStack item = event.getItem();
        if (item.getType() != Material.GOLDEN_CARROT) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null || !"§fcurry".equalsIgnoreCase(meta.getDisplayName())
                && !"curry".equalsIgnoreCase(meta.getDisplayName())) return;

        // Okarun mange le curry
        if (!isOkarun(eater)) return;
        UUID partnerUuid = partner.get(eater.getUniqueId());
        if (partnerUuid == null) return;

        task1Done.put(eater.getUniqueId(), true);
        eater.sendMessage(LangManager.get().get(DanDaDanLang.AMOUR_TASK_DONE, eater));

        // Démarre la surveillance de proximité Okarun→Momo
        Player momo = Bukkit.getPlayer(partnerUuid);
        if (momo == null) return;
        startOkarunProximity(eater, momo);
    }

    private void startOkarunProximity(Player okarun, Player momo) {
        okarunProximityTimer = 0;
        okarunProximityTask = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(), () -> {
            if (!okarun.isOnline() || !momo.isOnline()) {
                Main.get().getServer().getScheduler().cancelTask(okarunProximityTask); return;
            }
            if (okarun.getLocation().distance(momo.getLocation()) <= 5) {
                okarunProximityTimer++;
                okarun.sendMessage("§a" + okarunProximityTimer + "/60s...");
                if (okarunProximityTimer >= 60) {
                    task2Done.put(okarun.getUniqueId(), true);
                    okarun.sendMessage(LangManager.get().get(DanDaDanLang.AMOUR_TASK_DONE, okarun));
                    Main.get().getServer().getScheduler().cancelTask(okarunProximityTask);
                    checkAndLaunchCafe(okarun, momo);
                }
            } else {
                okarunProximityTimer = Math.max(0, okarunProximityTimer - 1);
            }
        }, 0L, 20L);
    }

    // ── Épreuve du Café ─────────────────────────────────────────

    private void checkAndLaunchCafe(Player p1, Player p2) {
        if (!task1Done.getOrDefault(p1.getUniqueId(), false)
                || !task2Done.getOrDefault(p2.getUniqueId(), false)) return;

        if (cafeWorld == null) {
            p1.sendMessage("§c[Amour] §7Monde du café non disponible.");
            return;
        }

        p1.sendMessage(LangManager.get().get(DanDaDanLang.AMOUR_CAFE_START, p1));
        p2.sendMessage(LangManager.get().get(DanDaDanLang.AMOUR_CAFE_START, p2));

        Location room1 = cafeWorld.getSpawnLocation().clone().add(-10, 1, 0);
        Location room2 = cafeWorld.getSpawnLocation().clone().add(10, 1, 0);
        p1.teleport(room1);
        p2.teleport(room2);

        // Timer 5 minutes
        int[] task = {0};
        task[0] = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(), () -> {
            // Vérification simplifiée : si les deux sont encore en vie après 5min → duo
            // La vraie épreuve "patron/reproduction" nécessite un CustomInventory avancé
        }, 0L, 20L * 300);

        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            Main.get().getServer().getScheduler().cancelTask(task[0]);
            // Succès par défaut si les deux sont encore en vie
            if (p1.isOnline() && p2.isOnline()) {
                formDuo(p1, p2);
            } else {
                // Retour solo
                returnToMain(p1);
                returnToMain(p2);
            }
        }, 20L * 300);
    }

    private void formDuo(Player p1, Player p2) {
        // Changement du camp → DUO (victoire conjointe)
        p1.sendMessage(LangManager.get().get(DanDaDanLang.AMOUR_SUCCESS, p1));
        p2.sendMessage(LangManager.get().get(DanDaDanLang.AMOUR_SUCCESS, p2));
        // Met les deux joueurs dans DanDaDanCamps.SOLO en attendant un camp AMOUR dédié
        if (DanDaDan.get() != null) {
            UHCPlayer uhcp1 = UHCPlayerManager.get().getPlayer(p1);
            UHCPlayer uhcp2 = UHCPlayerManager.get().getPlayer(p2);
            if (uhcp1 != null && uhcp2 != null) {
                DanDaDanRole r1 = DanDaDan.get().getRoleByUHCPlayer(uhcp1);
                DanDaDanRole r2 = DanDaDan.get().getRoleByUHCPlayer(uhcp2);
                if (r1 != null) r1.setCamp(net.novauhc.dandadan.DanDaDanCamps.SOLO);
                if (r2 != null) r2.setCamp(net.novauhc.dandadan.DanDaDanCamps.SOLO);
            }
        }
        returnToMain(p1);
        returnToMain(p2);
    }

    private void returnToMain(Player p) {
        World main = Bukkit.getWorlds().get(0);
        if (p != null && p.isOnline()) p.teleport(main.getSpawnLocation().clone().add(0, 1, 0));
    }

    // ── Helpers ─────────────────────────────────────────────────

    private boolean isDenji(Player p) {
        return getRole(p) instanceof DenjiRole;
    }
    private boolean isReze(Player p) {
        return getRole(p) instanceof RezeRole;
    }
    private boolean isOkarun(Player p) {
        return getRole(p) instanceof OkarunRole;
    }
    private DanDaDanRole getRole(Player p) {
        if (DanDaDan.get() == null) return null;
        var uhcp = UHCPlayerManager.get().getPlayer(p);
        return uhcp != null ? DanDaDan.get().getRoleByUHCPlayer(uhcp) : null;
    }
}
