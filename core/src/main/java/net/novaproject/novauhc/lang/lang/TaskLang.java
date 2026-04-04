package net.novaproject.novauhc.lang.lang;

import net.novaproject.novauhc.lang.Lang;

import java.util.Map;

public enum TaskLang implements Lang {

    GAME_START("%servertag%§aLa partie commence !", "%servertag%§aThe game is starting!"),
    PREGEN_ACTION_BAR("§7Prégénération : §a%percent%% §8[%bar%§8]", "§7Pregeneration : §a%percent%% §8[%bar%§8]"),
    CLEAN_ACTION_BAR("§7Nettoyage : §a%percent%% §8[%bar%§8]","§7Cleaning: §a%percent%% §8[%bar%§8]"),
    FOREST_GENERATION_ACTION_BAR("§7Foret : §a%percent%% §8[%bar%§8]", "§7Forest : §a%percent%% §8[%bar%§8]"),
    END_GENERATION("§5Generation de l'End...", "End generation..."),
    NETHER_GENERATION("§5Generation du Nether...", "Nether generation..."),
    START_ACTION_BAR("§8●§fDébut de la partie dans §c%countdown% secondes§8●","§8●§fGame start within §c%countdown% secondes§8●")
    ;

    private final Map<String, String> translations;
    TaskLang(String fr, String en) { this.translations = Map.of("fr_FR", fr, "en_EN", en); }
    @Override public String getKey() { return "task." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
