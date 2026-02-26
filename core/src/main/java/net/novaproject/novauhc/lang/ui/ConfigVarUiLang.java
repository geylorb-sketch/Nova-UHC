package net.novaproject.novauhc.lang.ui;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

public enum ConfigVarUiLang implements Lang {

    TITLE("§8┃ §fConfiguration", "§8┃ §fConfiguration"),
    CURRENT_VALUE("§8┃ §fValeur actuelle : §e%value%", "§8┃ §fCurrent value: §e%value%"),
    CURRENT_VALUE_DESC1("  §8┃ §fAffiche la valeur actuelle", "  §8┃ §fDisplays the current value"),
    CURRENT_VALUE_DESC2("  §8┃ §fde la configuration.", "  §8┃ §fof the configuration."),
    LIMITS_INFINITE("  §7Limites : %min% §7→ §a§l∞", "  §7Limits: %min% §7→ §a§l∞"),
    LIMITS_BOUNDED("  §7Limites : %min% §7→ §a%max%", "  §7Limits: %min% §7→ §a%max%"),
    PLUS_BUTTON_NAME("§8┃ §fAjouter §a%value%", "§8┃ §fAdd §a%value%"),
    MINUS_BUTTON_NAME("§8┃ §fRetirer %main_color%%value%", "§8┃ §fRemove %main_color%%value%"),
    PLUS_DESC1("  §8┃ §fPermet d'augmenter la valeur", "  §8┃ §fIncrease the value"),
    PLUS_DESC2("  §8┃ §fde §a%value%§f.", "  §8┃ §fby §a%value%§f."),
    MINUS_DESC1("  §8┃ §fPermet de diminuer la valeur", "  §8┃ §fDecrease the value"),
    MINUS_DESC2("  §8┃ §fde %main_color%%value%§f.", "  §8┃ §fby %main_color%%value%§f."),
    ;

    private final Map<String, String> translations;
    ConfigVarUiLang(String fr, String en) { this.translations = Map.of("fr_FR", fr, "en_US", en); }
    @Override public String getKey() { return "ui.configvar." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
