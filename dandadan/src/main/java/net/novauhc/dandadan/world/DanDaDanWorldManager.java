package net.novauhc.dandadan.world;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.world.utils.LobbyCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * DanDaDanWorldManager — Clone et gère le monde DanDaDan.
 *
 * Le monde DanDaDan est une map unique clonée depuis un template.
 * Toutes les positions (zones Yokai, épreuves, portails) sont hardcodées
 * dans YokaiRegistry et PortalManager.
 */
public class DanDaDanWorldManager {

    private static DanDaDanWorldManager instance;
    public static DanDaDanWorldManager get() {
        if (instance == null) instance = new DanDaDanWorldManager();
        return instance;
    }

    private static final String TEMPLATE_NAME = "template_dandadan";
    private static final String WORLD_NAME    = "dandadan_world";

    private World dandadanWorld;


    public void init() {
        LobbyCreator.cloneWorld(TEMPLATE_NAME, WORLD_NAME);

        // Le monde est chargé de façon asynchrone, on le récupère après un délai
        Bukkit.getScheduler().runTaskLater(Main.get(), () -> {
            dandadanWorld = Bukkit.getWorld(WORLD_NAME);
            if (dandadanWorld != null) {
                Bukkit.getLogger().info("[DanDaDan] Monde DanDaDan chargé : " + WORLD_NAME);
            } else {
                Bukkit.getLogger().severe("[DanDaDan] ERREUR : Monde DanDaDan non trouvé après clonage !");
            }
        }, 60L); // 3 secondes pour laisser le clone se terminer
    }

    /**
     * Supprime le monde DanDaDan.
     * Appelé en fin de partie.
     */
    public void cleanup(Location fallback) {
        if (dandadanWorld != null) {
            LobbyCreator.deleteWorld(WORLD_NAME, fallback);
            dandadanWorld = null;
        }
    }

    public World getDandadanWorld() { return dandadanWorld; }
    public String getWorldName()    { return WORLD_NAME; }
}
