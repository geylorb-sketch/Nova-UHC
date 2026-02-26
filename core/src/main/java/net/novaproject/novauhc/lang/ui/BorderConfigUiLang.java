package net.novaproject.novauhc.lang.ui;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

public enum BorderConfigUiLang implements Lang {

    INITIAL_NAME("§8┃ §fBordure initiale §8(%main_color%%value%§8)", "§8┃ §fInitial border §8(%main_color%%value%§8)"),
    INITIAL_DESC1("  §8┃ §fCliquez ici pour définir la taille", "  §8┃ §fClick to set the size"),
    INITIAL_DESC2("  §8┃ §fde la bordure initiale de la partie.", "  §8┃ §fof the initial border."),
    SPEED_NAME("§8┃ §fVitesse de la bordure §8(%main_color%%value% bloc(s)/s§8)", "§8┃ §fBorder speed §8(%main_color%%value% block(s)/s§8)"),
    SPEED_DESC1("  §8┃ §fCliquez ici pour définir la vitesse", "  §8┃ §fClick to set the speed"),
    SPEED_DESC2("  §8┃ §fde réduction de la bordure.", "  §8┃ §fof border reduction."),
    FINAL_NAME("§8┃ §fBordure finale §8(%main_color%%value%§8)", "§8┃ §fFinal border §8(%main_color%%value%§8)"),
    FINAL_DESC1("  §8┃ §fCliquez ici pour définir la taille", "  §8┃ §fClick to set the size"),
    FINAL_DESC2("  §8┃ §fde la bordure finale de la partie.", "  §8┃ §fof the final border."),
    ;

    private final Map<String, String> translations;
    BorderConfigUiLang(String fr, String en) { this.translations = Map.of("fr_FR", fr, "en_US", en); }
    @Override public String getKey() { return "ui.border." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
