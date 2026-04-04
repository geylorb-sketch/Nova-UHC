package net.novauhc.dandadan.world;

import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.utils.Titles;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.ArrayList;
import java.util.List;

/**
 * PortalManager — Gère les portails entre le GameWorld et le monde DanDaDan.
 *
 * Les portails sont des structures 3x4 en obsidienne générées autour du spawn
 * du GameWorld. Quand un joueur marche dedans, il est TP dans DanDaDan.
 */
public class PortalManager {

    private static PortalManager instance;
    public static PortalManager get() {
        if (instance == null) instance = new PortalManager();
        return instance;
    }

    /** Positions des portails dans le GameWorld (centres, générés au setup) */
    private final List<Location> portalLocations = new ArrayList<>();

    /** Rayon de détection du portail */
    private static final double PORTAL_DETECT_RADIUS = 2.0;

    /**
     * Génère les portails autour du centre du GameWorld.
     * @param gameWorld Le monde principal
     * @param portalRadius Distance du centre
     * @param count Nombre de portails (4 par défaut)
     */
    public void generatePortals(World gameWorld, int portalRadius, int count) {
        portalLocations.clear();
        Location center = new Location(gameWorld, 0, 0, 0);

        for (int i = 0; i < count; i++) {
            double angle = Math.toRadians((360.0 / count) * i);
            int x = (int) (portalRadius * Math.cos(angle));
            int z = (int) (portalRadius * Math.sin(angle));
            int y = gameWorld.getHighestBlockYAt(x, z) + 1;

            Location portalLoc = new Location(gameWorld, x, y, z);
            portalLocations.add(portalLoc);

            // Construire une structure de portail simple (cadre obsidienne 3x4)
            buildPortalFrame(portalLoc);
        }

        Bukkit.getLogger().info("[DanDaDan] " + count + " portails générés à " + portalRadius + " blocs du centre.");
    }

    /**
     * Appelé chaque seconde. Vérifie si un joueur est près d'un portail.
     */
    public void tick() {
        World dandadanWorld = DanDaDanWorldManager.get().getDandadanWorld();
        if (dandadanWorld == null) return;

        for (UHCPlayer uhc : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
            Player player = uhc.getPlayer();
            if (player == null) continue;

            // GameWorld → DanDaDan
            for (Location portal : portalLocations) {
                if (!player.getWorld().equals(portal.getWorld())) continue;
                if (player.getLocation().distance(portal) <= PORTAL_DETECT_RADIUS) {
                    if(DanDaDan.get().getRoleByUHCPlayer(uhc) != null){
                        LangManager.get().send(DanDaDanLang.YOKAI_ALREADY_HAVE,player);
                        break;
                    }
                    teleportToDandadan(player, dandadanWorld);
                    break;
                }
            }
        }
    }

    /**
     * Appelé chaque seconde pour les particules des portails.
     */
    public void tickParticles() {
        for (Location portal : portalLocations) {
            // Particules violettes autour du portail
            for (int i = 0; i < 8; i++) {
                double angle = Math.toRadians(i * 45);
                Location pt = portal.clone().add(Math.cos(angle) * 1.5, 1.5, Math.sin(angle) * 1.5);
                new ParticleBuilder(ParticleEffect.PORTAL)
                        .setLocation(pt).setAmount(3).display();
            }
        }
    }

    private void teleportToDandadan(Player player, World dandadanWorld) {
        Location spawn = dandadanWorld.getSpawnLocation().clone().add(0, 1, 0);
        player.teleport(spawn);

        LangManager.get().send(DanDaDanLang.PORTAL_ENTER, player);
        new Titles().sendTitle(player,
                LangManager.get().get(DanDaDanLang.PORTAL_TITLE, player),
                LangManager.get().get(DanDaDanLang.PORTAL_SUBTITLE, player),
                60);
    }

    /** Construit un cadre de portail simple en obsidienne */
    private void buildPortalFrame(Location base) {
        World w = base.getWorld();
        if (w == null) return;

        int bx = base.getBlockX();
        int by = base.getBlockY();
        int bz = base.getBlockZ();

        // Cadre 3 large x 4 haut (face sud)
        for (int dx = -1; dx <= 1; dx++) {
            w.getBlockAt(bx + dx, by, bz).setType(Material.OBSIDIAN);      // sol
            w.getBlockAt(bx + dx, by + 4, bz).setType(Material.OBSIDIAN);  // plafond
        }
        for (int dy = 0; dy <= 4; dy++) {
            w.getBlockAt(bx - 1, by + dy, bz).setType(Material.OBSIDIAN);  // gauche
            w.getBlockAt(bx + 1, by + dy, bz).setType(Material.OBSIDIAN);  // droite
        }
        // Intérieur en ender portal frame (visuel)
        for (int dy = 1; dy <= 3; dy++) {
            Block inner = w.getBlockAt(bx, by + dy, bz);
            inner.setType(Material.ENDER_PORTAL_FRAME);
        }
    }

    public List<Location> getPortalLocations() { return portalLocations; }
}
