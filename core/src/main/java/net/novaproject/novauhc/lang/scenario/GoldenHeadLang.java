package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

public enum GoldenHeadLang implements Lang {

    ITEM_NAME("§6Golden Head", "§6Golden Head"),
    SKULL_NAME("§6✦ Tête de %player%", "§6✦ Head of %player%"),
    ;

    private final Map<String, String> translations;
    GoldenHeadLang(String fr, String en) { this.translations = Map.of("fr_FR", fr, "en_US", en); }
    @Override public String getKey() { return "goldenhead." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
