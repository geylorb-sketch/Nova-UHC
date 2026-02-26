package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

public enum LongShootLang implements Lang {

    LONG_SHOT("%servertag% §6Long shot !", "%servertag% §6Long shot!"),
    LONG_SHOT_REWARD("%servertag% §aLong shot !", "%servertag% §aLong shot!"),
    ;

    private final Map<String, String> translations;
    LongShootLang(String fr, String en) { this.translations = Map.of("fr_FR", fr, "en_US", en); }
    @Override public String getKey() { return "longshoot." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
