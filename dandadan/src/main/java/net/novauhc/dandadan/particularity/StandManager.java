package net.novauhc.dandadan.particularity;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * StandManager — Gère l'invocation de Stands (Jotaro, Dio, Kira, Polnareff).
 *
 * Gitbook (page Jotaro):
 * - Le joueur laisse un PNJ avec son skin et pseudo derrière lui
 * - Prend l'apparence de son Stand
 * - Ne peut pas s'éloigner à plus de 20 blocs du PNJ
 * - Reçoit des buffs spécifiques au Stand
 * - Items remplacés par ceux du Stand
 */
public class StandManager {

    private static StandManager instance;
    public static StandManager get() {
        if (instance == null) instance = new StandManager();
        return instance;
    }

    private static final int MAX_STAND_DISTANCE = 20;

    private final Map<UUID, StandSession> activeSessions = new HashMap<>();

    public boolean isInStandMode(Player player) {
        return activeSessions.containsKey(player.getUniqueId());
    }

    public StandSession getSession(Player player) {
        return activeSessions.get(player.getUniqueId());
    }

    /**
     * Active le mode Stand pour un joueur.
     * @param player     Le joueur
     * @param standName  Nom du Stand (ex: "Star Platinum", "The World")
     * @param standItems Items du Stand (remplacent l'inventaire)
     * @param speedPct   Bonus speed en % (ex: 15 pour 15%)
     * @param resistPct  Bonus résistance en % (ex: 10 pour 10%)
     * @param durationSec Durée en secondes
     * @return true si activé
     */
    public boolean activateStand(Player player, String standName, ItemStack[] standItems,
                                  int speedPct, int resistPct, int durationSec) {
        if (isInStandMode(player)) return false;

        Location npcLoc = player.getLocation().clone();

        // Créer le PNJ Citizens
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, player.getName());
        npc.spawn(npcLoc);

        // Sauvegarder l'inventaire
        ItemStack[] savedInventory = player.getInventory().getContents().clone();
        ItemStack[] savedArmor = player.getInventory().getArmorContents().clone();

        // Remplacer inventaire par items Stand
        if (standItems != null) {
            player.getInventory().clear();
            player.getInventory().setContents(standItems);
        }

        // Appliquer effets
        if (speedPct > 0) {
            int amp = speedPct >= 20 ? 1 : 0;
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, durationSec * 20, amp, false, false));
        }
        if (resistPct > 0) {
            int amp = resistPct >= 20 ? 1 : 0;
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, durationSec * 20, amp, false, false));
        }

        // Session
        StandSession session = new StandSession(npc, npcLoc, savedInventory, savedArmor, standName);
        activeSessions.put(player.getUniqueId(), session);

        // Timer pour désactiver automatiquement
        BukkitTask task = Bukkit.getScheduler().runTaskLater(Main.get(),
                () -> deactivateStand(player), 20L * durationSec);
        session.setTask(task);

        // Vérification distance chaque seconde
        BukkitTask distTask = Bukkit.getScheduler().runTaskTimer(Main.get(), () -> {
            if (!isInStandMode(player)) return;
            if (player.getLocation().distance(npcLoc) > MAX_STAND_DISTANCE) {
                // Ramener le joueur
                player.teleport(npcLoc.clone().add(
                        player.getLocation().toVector().subtract(npcLoc.toVector()).normalize().multiply(MAX_STAND_DISTANCE - 1)));
                player.sendMessage("§c✘ Tu ne peux pas t'éloigner à plus de " + MAX_STAND_DISTANCE + " blocs de ton corps !");
            }
        }, 20L, 20L);
        session.setDistanceTask(distTask);

        player.sendMessage("§d§l⭐ " + standName + " §r§dactivé !");
        return true;
    }

    /** Désactive le Stand et restaure l'inventaire */
    public void deactivateStand(Player player) {
        StandSession session = activeSessions.remove(player.getUniqueId());
        if (session == null) return;

        // Annuler les tasks
        if (session.getTask() != null) session.getTask().cancel();
        if (session.getDistanceTask() != null) session.getDistanceTask().cancel();

        // Supprimer le PNJ
        if (session.getNpc() != null) {
            session.getNpc().destroy();
        }

        // Restaurer inventaire
        player.getInventory().clear();
        player.getInventory().setContents(session.getSavedInventory());
        player.getInventory().setArmorContents(session.getSavedArmor());

        // Supprimer effets stand
        player.removePotionEffect(PotionEffectType.SPEED);
        player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);

        player.sendMessage("§7§l⭐ " + session.getStandName() + " §r§7désactivé.");
    }

    // ── Session interne ─────────────────────────────────────
    public static class StandSession {
        private final NPC npc;
        private final Location npcLocation;
        private final ItemStack[] savedInventory;
        private final ItemStack[] savedArmor;
        private final String standName;
        private BukkitTask task;
        private BukkitTask distanceTask;

        public StandSession(NPC npc, Location npcLocation, ItemStack[] savedInventory,
                           ItemStack[] savedArmor, String standName) {
            this.npc = npc;
            this.npcLocation = npcLocation;
            this.savedInventory = savedInventory;
            this.savedArmor = savedArmor;
            this.standName = standName;
        }

        public NPC getNpc() { return npc; }
        public Location getNpcLocation() { return npcLocation; }
        public ItemStack[] getSavedInventory() { return savedInventory; }
        public ItemStack[] getSavedArmor() { return savedArmor; }
        public String getStandName() { return standName; }
        public BukkitTask getTask() { return task; }
        public void setTask(BukkitTask task) { this.task = task; }
        public BukkitTask getDistanceTask() { return distanceTask; }
        public void setDistanceTask(BukkitTask distanceTask) { this.distanceTask = distanceTask; }
    }
}
