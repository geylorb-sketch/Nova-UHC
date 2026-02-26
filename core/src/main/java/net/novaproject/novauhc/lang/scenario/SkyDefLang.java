package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

/**
 * Messages du scénario SkyDef.
 * Remplace l'ancienne classe scenario/lang/lang/SkyDefLang.java
 */
public enum SkyDefLang implements Lang {

    SKYDEF_MESSAGE(
        "§b[SkyDef] §fProtégez votre base !",
        "§b[SkyDef] §fProtect your base!"
    )
    ;

    private final Map<String, String> translations;

    SkyDefLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_US", en);
    }

    @Override
    public String getKey() { return "skydef." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}
