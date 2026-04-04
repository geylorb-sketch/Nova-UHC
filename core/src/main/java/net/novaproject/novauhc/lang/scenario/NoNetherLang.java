package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

public enum NoNetherLang implements Lang {

    BLOCKED("§cL'accès au Nether est désactivé !", "§cAccess to the Nether is disabled!"),
    ;

    private final Map<String, String> translations;
    NoNetherLang(String fr, String en) { this.translations = Map.of("fr_FR", fr, "en_EN", en); }
    @Override public String getKey() { return "nonether." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
