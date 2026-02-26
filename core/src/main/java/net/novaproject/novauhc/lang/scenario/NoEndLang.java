package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

public enum NoEndLang implements Lang {

    BLOCKED("§cL'accès à l'End est désactivé !", "§cAccess to the End is disabled!"),
    ;

    private final Map<String, String> translations;
    NoEndLang(String fr, String en) { this.translations = Map.of("fr_FR", fr, "en_US", en); }
    @Override public String getKey() { return "noend." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
