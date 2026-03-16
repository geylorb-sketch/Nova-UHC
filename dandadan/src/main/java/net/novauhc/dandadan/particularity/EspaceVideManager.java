package net.novauhc.dandadan.particularity;

import lombok.Getter;
import lombok.Setter;
import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.world.utils.LobbyCreator;
import net.novauhc.dandadan.events.MoriohRadio;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

/**
 * EspaceVideManager — Système d'Espace Vide (dimension parallèle).
 * ─────────────────────────────────────────────────────────────
 * Rôles pouvant créer un Espace Vide :
 *   Reze (MéméTurbo), Bamora, Œil Maléfique, Monstre de Flatwoods,
 *   Reiko Kashima, Nessie.
 *
 * - Téléporte les joueurs dans un rayon dans une dimension tunnel.
 * - Configuration via /ddd vide (lava, flèches, rayon, max joueurs).
 * - Les joueurs sortent quand l'un d'eux meurt.
 */
@Getter @Setter
public class EspaceVideManager implements Listener {

    private static EspaceVideManager instance;

    private static final String VIDE_WORLD_TEMPLATE = "template_espace_vide";
    private static final String VIDE_WORLD_NAME     = "dandadan_vide";

    private World videWorld;
    private final Map<UUID, Location> savedLocations = new HashMap<>();
    private final Set<UUID> playersInVide            = new HashSet<>();

    // Config par owner
    private boolean allowLava     = false;
    private int     maxArrows     = 16;
    private int     radius        = 10;
    private int     maxPlayers    = 6;

    public static EspaceVideManager get() {
        if (instance == null) instance = new EspaceVideManager();
        return instance;
    }

    // ── Setup ───────────────────────────────────────────────────

    public void init() {
        Bukkit.getPluginManager().registerEvents(this, Main.get());
        LobbyCreator.cloneWorld(VIDE_WORLD_TEMPLATE, VIDE_WORLD_NAME);
        Main.get().getServer().getScheduler().runTaskLater(Main.get(),
                () -> videWorld = Bukkit.getWorld(VIDE_WORLD_NAME), 40L);
    }
    public void cleanup() {
        releaseAll();
        if (videWorld != null) {
            Location fallback = Bukkit.getWorlds().get(0).getSpawnLocation();
            LobbyCreator.deleteWorld(VIDE_WORLD_NAME, fallback);
            videWorld = null;
        }
    }

    // ── Activation ──────────────────────────────────────────────

    /**
     * Active l'Espace Vide autour du joueur owner.
     * Téléporte tous les joueurs dans le rayon (sauf alliés).
     */
    public boolean activate(Player owner) {
        if (videWorld == null) {
            owner.sendMessage("§c[Espace Vide] §7Dimension non disponible.");
            return false;
        }
        if (!playersInVide.isEmpty()) {
            owner.sendMessage("§c[Espace Vide] §7Un Espace Vide est déjà actif !");
            return false;
        }

        List<Player> targets = new ArrayList<>();
        for (UHCPlayer uhcp : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
            Player p = uhcp.getPlayer();
            if (p == null || p.equals(owner)) continue;
            if (p.getLocation().distance(owner.getLocation()) > radius) continue;
            targets.add(p);
            if (targets.size() >= maxPlayers) break;
        }

        if (targets.isEmpty()) {
            owner.sendMessage("§c[Espace Vide] §7Aucun joueur à portée.");
            return false;
        }

        Location center = videWorld.getSpawnLocation().clone().add(0, 1, 0);

        // Sauver position owner
        savedLocations.put(owner.getUniqueId(), owner.getLocation().clone());
        playersInVide.add(owner.getUniqueId());
        owner.teleport(center);
        applyVideEffects(owner);

        // Téléporter les cibles
        for (int i = 0; i < targets.size(); i++) {
            Player t = targets.get(i);
            savedLocations.put(t.getUniqueId(), t.getLocation().clone());
            playersInVide.add(t.getUniqueId());
            double angle = (2 * Math.PI / targets.size()) * i;
            Location pos = center.clone().add(Math.cos(angle) * 4, 0, Math.sin(angle) * 4);
            t.teleport(pos);
            applyVideMalus(t);
            LangManager.get().send(DanDaDanLang.ESPACE_VIDE_TELEPORTED, t);
        }

        LangManager.get().send(DanDaDanLang.ESPACE_VIDE_ACTIVATED, owner);
        MoriohRadio.get().broadcast(DanDaDanLang.RADIO_ESPACE_VIDE,
                Map.of("%players%", buildPlayerList(targets), "%owner%", owner.getName()));

        return true;
    }

    private void applyVideEffects(Player owner) {
        // Le propriétaire obtient les effets de sa malédiction
        owner.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
                Integer.MAX_VALUE, 0, false, false));
    }

    private void applyVideMalus(Player target) {
        // 40% chance de rater un coup, 40% chance d'arrêter de sprinter
        // (géré par listener de dommage avec random)
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player dead = event.getEntity();
        if (!playersInVide.contains(dead.getUniqueId())) return;
        // Libérer tout le monde
        Main.get().getServer().getScheduler().runTask(Main.get(), this::releaseAll);
    }

    private void releaseAll() {
        new HashSet<>(playersInVide).forEach(uuid -> {
            Player p = Bukkit.getPlayer(uuid);
            Location saved = savedLocations.get(uuid);
            if (p != null && saved != null) {
                p.teleport(saved);
                p.removePotionEffect(PotionEffectType.SPEED);
            }
        });
        playersInVide.clear();
        savedLocations.clear();
    }

    // ── /ddd vide config ────────────────────────────────────────

    public Set<UUID> getPlayersInVide()   { return Collections.unmodifiableSet(playersInVide); }

    private String buildPlayerList(List<Player> players) {
        StringBuilder sb = new StringBuilder();
        for (Player p : players) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(p.getName());
        }
        return sb.toString();
    }
}
