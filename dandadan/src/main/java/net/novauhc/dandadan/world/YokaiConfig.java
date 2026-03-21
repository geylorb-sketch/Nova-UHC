package net.novauhc.dandadan.world;

import net.novaproject.novauhc.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * YokaiConfig — Charge les zones et positions depuis dandadan.yml
 *
 * Structure YML :
 *   zones:           → Liste de TOUTES les positions de TP (une seule grande liste)
 *   trial_locations:  → Position d'épreuve propre à chaque Yokai (1 par Yokai)
 */
public class YokaiConfig {

    private static YokaiConfig instance;
    public static YokaiConfig get() {
        if (instance == null) instance = new YokaiConfig();
        return instance;
    }

    private static final String CONFIG_PATH = "api/scenario/special/dandadan.yml";
    private FileConfiguration config;

    public void init() {
        ConfigUtils.registerConfig(CONFIG_PATH);
        ConfigUtils.createDefaultFiles(CONFIG_PATH);
        reload();
    }

    public void reload() {
        config = ConfigUtils.getConfig(CONFIG_PATH);
        Bukkit.getLogger().info("[DanDaDan] Config chargée : " + CONFIG_PATH);
    }

    public void save() {
        try {
            ConfigUtils.saveConfig(config, CONFIG_PATH);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "[DanDaDan] Impossible de sauvegarder " + CONFIG_PATH, e);
        }
    }

    /**
     * Charge TOUTES les zones de TP depuis la section "zones".
     * @return Liste de int[3] {x, y, z}
     */
    public List<int[]> loadAllZones() {
        List<int[]> zones = new ArrayList<>();
        if (config == null) return zones;

        int index = 0;
        while (true) {
            String path = "zones." + index;
            if (!config.contains(path + ".x")) break;
            int x = config.getInt(path + ".x");
            int y = config.getInt(path + ".y");
            int z = config.getInt(path + ".z");
            zones.add(new int[]{x, y, z});
            index++;
        }

        Bukkit.getLogger().info("[DanDaDan] " + zones.size() + " zones chargées depuis le config.");
        return zones;
    }

    /**
     * Charge la position d'épreuve d'un Yokai spécifique.
     * @param yokaiName Nom enum (ex: "OKARUN")
     * @return int[3] {x, y, z} ou null si non configuré
     */
    public int[] loadTrialLocation(String yokaiName) {
        String path = "trial_locations." + yokaiName;
        if (config == null || !config.contains(path + ".x")) return null;
        return new int[]{
                config.getInt(path + ".x"),
                config.getInt(path + ".y"),
                config.getInt(path + ".z")
        };
    }

    /**
     * Charge toutes les trial_locations et les injecte dans YokaiRegistry.
     */
    public void loadTrialLocations() {
        for (YokaiRegistry yokai : YokaiRegistry.values()) {
            int[] trial = loadTrialLocation(yokai.name());
            if (trial != null) {
                yokai.setTrialLocation(trial);
            }
        }
    }
}
