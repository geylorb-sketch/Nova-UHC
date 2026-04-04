package net.novaproject.novauhc.lang.ui;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;


public enum UiTitleLang implements Lang {

    
    MAIN_TITLE      ("§f(§c!§f) §6§lConfiguration Principale",          "§f(§c!§f) §6§lMain Configuration"),
    PRECONFIG_TITLE ("§f(§c!§f) §e§lGestion des Configurations",         "§f(§c!§f) §e§lConfiguration Management"),
    GAME_TITLE      ("§f(§c!§f) §a§lConfiguration de Partie",            "§f(§c!§f) §a§lGame Configuration"),
    WHITELIST_TITLE ("§f(§c!§f) §d§lGestion de la Whitelist",            "§f(§c!§f) §d§lWhitelist Management"),
    MUMBLE_TITLE    ("§f(§c!§f) §8§lConfiguration Mumble",               "§f(§c!§f) §8§lMumble Configuration"),

    
    TEAM_CONFIG_TITLE ("§f(§c!§f) §9§lConfiguration des Équipes",        "§f(§c!§f) §9§lTeam Configuration"),
    TEAM_INGAME_TITLE ("§f(§c!§f) §b§lSélection d'Équipe",              "§f(§c!§f) §b§lTeam Selection"),

    
    STUFF_TITLE     ("§f(§c!§f) §e§lÉquipement et Limites",              "§f(§c!§f) §e§lGear & Limits"),
    ENCHANT_TITLE   ("§f(§c!§f) §5§lLimites d'Enchantements de %player%","§f(§c!§f) §5§lEnchantment Limits of %player%"),
    DROP_TITLE      ("§f(§c!§f) §6§lTaux de Drop des Objets",            "§f(§c!§f) §6§lItem Drop Rates"),
    POTION_TITLE    ("§f(§c!§f) §3§lConfiguration des Potions",          "§f(§c!§f) §3§lPotion Configuration"),

    
    SCENARIO_TITLE           ("§f(§c!§f) §2§lScénarios Standards",             "§f(§c!§f) §2§lStandard Scenarios"),
    SCENARIO_IG_TITLE        ("§f(§c!§f) §2§lScénarios §7(En Jeu)",            "§f(§c!§f) §2§lScenarios §7(In Game)"),
    SCENARIO_SPECIAL_TITLE   ("§f(§c!§f) §c§lScénarios Spéciaux",              "§f(§c!§f) §c§lSpecial Scenarios"),
    SCENARIO_SPECIAL_IG_TITLE("§f(§c!§f) §c§lScénarios Spéciaux §7(En Jeu)",  "§f(§c!§f) §c§lSpecial Scenarios §7(In Game)"),

    
    WORLD_TITLE    ("§f(§c!§f) §a§lGestion du Monde",                    "§f(§c!§f) §a§lWorld Management"),
    BORDER_TITLE   ("§f(§c!§f) §1§lConfiguration de la Bordure",         "§f(§c!§f) §1§lBorder Configuration"),
    OREBOOST_TITLE ("§f(§c!§f) §3§lBoost des Minerais",                  "§f(§c!§f) §3§lOre Boost"),
    CENTER_TITLE   ("§f(§c!§f) §a§lType de Centre",                      "§f(§c!§f) §a§lCenter Type"),

    
    CONFIGVAR_TITLE("§8┃ §fConfiguration",                               "§8┃ §fConfiguration"),
    CONFIRM_TITLE  ("§8┃ §fConfirmation",                                 "§8┃ §fConfirmation"),

    
    DEATHNOTE_CONFIG_TITLE("§c§lConfiguration Death Note UHC",           "§c§lDeath Note UHC Configuration"),
    CLOUDNET_TITLE        ("CloudNet • Paramètres",                          "CloudNet • Settings"),
    ;

    private final Map<String, String> translations;
    UiTitleLang(String fr, String en) { this.translations = Map.of("fr_FR", fr, "en_EN", en); }
    @Override public String getKey() { return "ui.title." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
