package net.novaproject.novauhc.lang;

import net.novaproject.novauhc.Common;
import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.listener.player.PlayerConnectionEvent;
import net.novaproject.novauhc.scenario.ScenarioManager;
import net.novaproject.novauhc.scenario.role.ScenarioRole;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;

/**
 * Gestionnaire de traductions centralisé.
 *
 * Initialisation dans Main.onEnable() :
 * <pre>
 *   LangManager lm = new LangManager(getDataFolder(), "fr_FR");
 *   lm.register(CommonLang.values());
 *   lm.register(AcidRainLang.values());
 *   // ... tous les enums
 *   lm.generateAndLoad();
 * </pre>
 */
public class LangManager {

    // ── Singleton ─────────────────────────────────────────────────────────────
    private static LangManager instance;

    public static LangManager get() {
        return instance;
    }

    // ── Champs ────────────────────────────────────────────────────────────────
    private final File langFolder;
    private final String serverDefaultLocale;

    /** locale → (key → message traduit) */
    private final Map<String, Map<String, String>> translations = new HashMap<>();

    /** Toutes les Lang enums enregistrées */
    private final List<Lang> registered = new ArrayList<>();

    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    // ── Constructeur ──────────────────────────────────────────────────────────
    public LangManager(File dataFolder, String serverDefaultLocale) {
        this.langFolder = new File(dataFolder, "lang");
        this.serverDefaultLocale = serverDefaultLocale;
        instance = this;
    }

    // ── Enregistrement ────────────────────────────────────────────────────────

    /** Enregistre un enum de traductions. */
    public void register(Lang[] values) {
        registered.addAll(Arrays.asList(values));
    }

    // ── Génération & Chargement ───────────────────────────────────────────────

    /**
     * Génère les fichiers YAML manquants, ajoute les clés manquantes,
     * puis charge toutes les traductions en mémoire.
     * Appeler une seule fois au démarrage, après tous les register().
     */
    public void generateAndLoad() {
        if (!langFolder.exists()) langFolder.mkdirs();

        // Collecter toutes les locales utilisées
        Set<String> locales = new LinkedHashSet<>();
        locales.add(serverDefaultLocale);
        for (Lang lang : registered) {
            locales.addAll(lang.getTranslations().keySet());
        }

        // Pour chaque locale : charger le YAML, ajouter les clés manquantes, sauvegarder
        for (String locale : locales) {
            File file = new File(langFolder, locale + ".yml");
            YamlConfiguration yaml = file.exists()
                    ? YamlConfiguration.loadConfiguration(file)
                    : new YamlConfiguration();

            boolean dirty = false;
            for (Lang lang : registered) {
                String key = lang.getKey();
                if (!yaml.contains(key)) {
                    // Prendre la traduction pour cette locale, ou le fallback
                    String msg = lang.getTranslations().getOrDefault(locale, lang.getFallback());
                    yaml.set(key, msg);
                    dirty = true;
                }
            }

            if (dirty || !file.exists()) {
                try {
                    yaml.save(file);
                } catch (IOException e) {
                    Bukkit.getLogger().log(Level.SEVERE, "[LangManager] Impossible de sauvegarder " + file.getName(), e);
                }
            }

            // Charger en mémoire
            Map<String, String> map = new HashMap<>();
            for (String key : yaml.getKeys(true)) {
                if (!yaml.isConfigurationSection(key)) {
                    String value = yaml.getString(key, "");
                    map.put(key, value.replace("&", "§"));
                }
            }
            translations.put(locale, map);
        }

        Bukkit.getLogger().info("[LangManager] Chargé. Locales : " + locales + " | Clés : " + registered.size());
    }

    // ── Résolution ────────────────────────────────────────────────────────────

    /** Récupère le message brut traduit pour une locale donnée, sans placeholders. */
    public String getRaw(Lang key, String locale) {
        Map<String, String> map = translations.get(locale);
        if (map != null && map.containsKey(key.getKey())) {
            return map.get(key.getKey());
        }
        // Fallback vers locale serveur
        Map<String, String> def = translations.get(serverDefaultLocale);
        if (def != null && def.containsKey(key.getKey())) {
            return def.get(key.getKey());
        }
        return key.getFallback();
    }

    /**
     * Récupère le message traduit dans la langue du joueur,
     * avec les placeholders communs + les placeholders extra appliqués.
     */
    public String get(Lang key, Player player, Map<String, Object> extra) {
        UHCPlayer uhc = UHCPlayerManager.get() != null ? UHCPlayerManager.get().getPlayer(player) : null;
        String locale = (uhc != null) ? uhc.getLocale() : serverDefaultLocale;
        String msg = getRaw(key, locale);
        return applyPlaceholders(msg, buildPlaceholders(uhc, extra));
    }

    /** Récupère le message traduit dans la langue du joueur, sans extra placeholders. */
    public String get(Lang key, Player player) {
        return get(key, player, null);
    }

    /** Récupère le message dans la locale serveur, avec placeholders. */
    public String get(Lang key, Map<String, Object> extra) {
        String msg = getRaw(key, serverDefaultLocale);
        return applyPlaceholders(msg, buildPlaceholders(null, extra));
    }

    /** Récupère le message dans la locale serveur, sans extra placeholders. */
    public String get(Lang key) {
        return get(key, (Map<String, Object>) null);
    }

    // ── Envoi ─────────────────────────────────────────────────────────────────

    /** Envoie le message au joueur dans SA langue. */
    public void send(Lang key, Player player) {
        player.sendMessage(get(key, player));
    }

    /** Envoie le message au joueur dans SA langue avec des placeholders extra. */
    public void send(Lang key, Player player, Map<String, Object> extra) {
        player.sendMessage(get(key, player, extra));
    }

    /** Envoie à tous les joueurs en ligne — chacun dans SA langue. */
    public void sendAll(Lang key) {
        sendAll(key, null);
    }

    /** Envoie à tous les joueurs avec extra placeholders — chacun dans SA langue. */
    public void sendAll(Lang key, Map<String, Object> extra) {
        if (UHCPlayerManager.get() == null) return;
        for (UHCPlayer uhcPlayer : UHCPlayerManager.get().getOnlineUHCPlayers()) {
            Player p = uhcPlayer.getPlayer();
            if (p != null) {
                p.sendMessage(get(key, p, extra));
            }
        }
    }

    // ── Placeholders ─────────────────────────────────────────────────────────

    /**
     * Construit la map complète des placeholders communs.
     * Peut être appelée depuis l'extérieur pour construire des messages custom.
     */
    public Map<String, Object> buildPlaceholders(UHCPlayer uhcPlayer, Map<String, Object> extra) {
        Map<String, Object> ph = new HashMap<>();

        // Temps
        ph.put("%time%", TIME_FORMAT.format(new Date()));
        ph.put("%date%", DATE_FORMAT.format(new Date()));

        // Bordure
        try {
            if (Common.get() != null && Common.get().getArena() != null) {
                ph.put("%border%", String.valueOf((int) Common.get().getArena().getWorldBorder().getSize()));
            }
        } catch (Exception ignored) {}
        ph.putIfAbsent("%border%", "N/A");

        // UHCManager
        try {
            if (UHCManager.get() != null) {
                ph.put("%timer%", UHCManager.get().getTimerFormatted());
                ph.put("%gamestate%", UHCManager.get().getGameState().name());
                ph.put("%slot%", String.valueOf(UHCManager.get().getSlot()));
                ph.put("%diamond_limite%", String.valueOf(UHCManager.get().getDimamondLimit()));
            }
        } catch (Exception ignored) {}
        ph.putIfAbsent("%timer%", "00:00");
        ph.putIfAbsent("%gamestate%", "UNKNOWN");
        ph.putIfAbsent("%slot%", "0");
        ph.putIfAbsent("%diamond_limite%", "0");

        // Common (serveur)
        try {
            if (Common.get() != null) {
                ph.put("%main_color%", Common.get().getMainColor() != null ? Common.get().getMainColor() : "§e§l");
                ph.put("%infotag%", Common.get().getInfoTag() != null ? Common.get().getInfoTag() : "");
                ph.put("%servertag%", Common.get().getServertag() != null ? Common.get().getServertag() : "");
                ph.put("%servername%", Common.get().getServername() != null ? Common.get().getServername() : "NovaUHC");
                ph.put("%serveurname%", ph.get("%servername%"));
            }
        } catch (Exception ignored) {}
        ph.putIfAbsent("%main_color%", "§e§l");
        ph.putIfAbsent("%infotag%", "");
        ph.putIfAbsent("%servertag%", "");
        ph.putIfAbsent("%servername%", "NovaUHC");
        ph.putIfAbsent("%serveurname%", "NovaUHC");

        // Joueurs en ligne
        try {
            if (UHCPlayerManager.get() != null) {
                ph.put("%players%", String.valueOf(UHCPlayerManager.get().getPlayingOnlineUHCPlayers().size()));
            }
        } catch (Exception ignored) {}
        ph.putIfAbsent("%players%", "0");

        // Host
        try {
            ph.put("%host%", PlayerConnectionEvent.getHost() != null
                    ? PlayerConnectionEvent.getHost().getName() : "N/A");
        } catch (Exception ignored) {
            ph.put("%host%", "N/A");
        }

        // Placeholders liés au joueur
        if (uhcPlayer != null && uhcPlayer.getPlayer() != null) {
            ph.put("%player%", uhcPlayer.getPlayer().getName());
            ph.put("%kills%", String.valueOf(uhcPlayer.getKill()));
            ph.put("%health%", String.format("%.1f", uhcPlayer.getPlayer().getHealth()));
            ph.put("%mined_diamond%", String.valueOf(uhcPlayer.getMinedDiamond()));
            ph.put("%team%", uhcPlayer.getTeam().isPresent() ? uhcPlayer.getTeam().get().name() : "Solo");
            ph.put("%state%", uhcPlayer.isPlaying() ? "En jeu" : "Mort");
            ph.put("%killer%", uhcPlayer.getKiller() != null ? uhcPlayer.getKiller().getName() : "N/A");

            try {
                if (ScenarioManager.get() != null) {
                    String role = ScenarioManager.get().getActiveSpecialScenarios().stream()
                            .filter(s -> s instanceof ScenarioRole)
                            .findFirst()
                            .map(s -> ((ScenarioRole<?>) s).getRoleByUHCPlayer(uhcPlayer).getName())
                            .orElse("N/A");
                    ph.put("%role%", role);
                }
            } catch (Exception ignored) {}
            ph.putIfAbsent("%role%", "N/A");

            try {
                if (Common.get() != null && Common.get().getArena() != null) {
                    String arrow = uhcPlayer.getArrowDirection(
                            uhcPlayer.getPlayer().getLocation(),
                            Common.get().getArena().getHighestBlockAt(0, 0).getLocation(),
                            uhcPlayer.getPlayer().getLocation().getYaw()
                    );
                    ph.put("%arrow%", arrow);
                    ph.put("%distance%", String.valueOf(
                            uhcPlayer.getDistanceToCenter(Common.get().getArena().getHighestBlockAt(0, 0).getLocation())
                    ));
                }
            } catch (Exception ignored) {}
            ph.putIfAbsent("%arrow%", "→");
            ph.putIfAbsent("%distance%", "N/A");
        } else {
            ph.putIfAbsent("%player%", "Unknown");
            ph.putIfAbsent("%kills%", "0");
            ph.putIfAbsent("%health%", "0");
            ph.putIfAbsent("%mined_diamond%", "0");
            ph.putIfAbsent("%team%", "Solo");
            ph.putIfAbsent("%state%", "Unknown");
            ph.putIfAbsent("%killer%", "N/A");
            ph.putIfAbsent("%role%", "N/A");
            ph.putIfAbsent("%arrow%", "→");
            ph.putIfAbsent("%distance%", "N/A");
        }

        // Placeholders extra (prioritaires)
        if (extra != null) {
            extra.forEach((k, v) -> ph.put(k, v));
        }

        return ph;
    }

    /** Construit les placeholders sans joueur spécifique. */
    public Map<String, Object> buildPlaceholders(Map<String, Object> extra) {
        return buildPlaceholders(null, extra);
    }

    // ── Utilitaires ───────────────────────────────────────────────────────────

    public String getServerDefaultLocale() {
        return serverDefaultLocale;
    }

    public Set<String> getAvailableLocales() {
        return Collections.unmodifiableSet(translations.keySet());
    }

    private static String applyPlaceholders(String msg, Map<String, Object> placeholders) {
        for (Map.Entry<String, Object> e : placeholders.entrySet()) {
            if (e.getValue() != null) {
                msg = msg.replace(e.getKey(), e.getValue().toString());
            }
        }
        return msg;
    }
}
