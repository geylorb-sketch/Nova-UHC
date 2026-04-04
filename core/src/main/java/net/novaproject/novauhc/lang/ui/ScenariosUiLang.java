package net.novaproject.novauhc.lang.ui;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

public enum ScenariosUiLang implements Lang {

    SCENARIO_ACTIVE("§2Activé", "§2Enabled"),
    SCENARIO_DISABLED("§cDésactivé", "§cDisabled"),
    OPEN_CONFIG_LORE("§8» §a§lOuvrir la configuration", "§8» §a§lOpen configuration"),
    ;

    private final Map<String, String> translations;
    ScenariosUiLang(String fr, String en) { this.translations = Map.of("fr_FR", fr, "en_EN", en); }
    @Override public String getKey() { return "ui.scenarios." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
