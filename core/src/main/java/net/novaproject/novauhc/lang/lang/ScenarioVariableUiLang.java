package net.novaproject.novauhc.lang.lang;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

public enum ScenarioVariableUiLang implements Lang {

    CURRENT_VALUE     ("§7Valeur actuelle: §b%value%",           "§7Current value: §b%value%"),
    CLICK_CHANGE      ("§a▶ Clic pour changer",                  "§a▶ Click to change"),
    NOT_DEFINED       ("§cNon défini",                           "§cNot defined"),
    ANVIL_NEW_VALUE   ("Nouvelle valeur",                        "New value"),

    CONFIG_BUTTON     ("§8» §fConfigurer %name% §8«",            "§8» §fConfigure %name% §8«"),
    CONFIG_LORE       ("§8┃ §fCliquez pour configurer",          "§8┃ §fClick to configure"),
    CONFIG_ABILITY_DESC1("  §8┃ §fPermet de configurer les options de", "  §8┃ §fConfigure the options of"),
    CONFIG_ABILITY_DESC2("  §8┃ §fle pouvoir %name%",           "  §8┃ §fthe abilities %name%"),

    CONFIG_TITLE      ("§8Config: §6%name%",                     "§8Config: §6%name%"),
    ROLE_CONFIG_TITLE ("Configuration de %name%",                "Configuration of %name%"),
    ROLE_UI_TITLE     ("§f§l | Rôles du camp : %camp%",         "§f§l | Camp roles: %camp%"),
    CAMP_UI_TITLE     ("%scenario% §f§l| Choisir un Camp",       "%scenario% §f§l| Choose a Camp"),
    ;

    private final Map<String, String> translations;
    ScenarioVariableUiLang(String fr, String en) { this.translations = Map.of("fr_FR", fr, "en_EN", en); }
    @Override public String getKey() { return "ui.scenarvar." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}