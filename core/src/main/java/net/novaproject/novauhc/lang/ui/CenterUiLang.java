package net.novaproject.novauhc.lang.ui;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

public enum CenterUiLang implements Lang {

    BIOME_LABEL("§8┃ §fBiome : %main_color%%name%", "§8┃ §fBiome: %main_color%%name%"),
    SOON_LABEL(" §8§l┃ §f§lSoon", " §8§l┃ §f§lSoon"),
    CONFIRM_TEXT("Etes vous sur de votre choix ?", "Are you sure of your choice?"),
    ;

    private final Map<String, String> translations;
    CenterUiLang(String fr, String en) { this.translations = Map.of("fr_FR", fr, "en_US", en); }
    @Override public String getKey() { return "ui.center." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
