package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

/**
 * Messages du scénario Blizzard.
 * Remplace l'ancienne classe scenario/lang/lang/BlizzardLang.java
 */
public enum BlizzardLang implements Lang {

    BLIZZARD_START(
        "§b[Blizzard] §fTempête de neige ! Visibilité réduite et ralentissement !",
        "§b[Blizzard] §fSnowstorm! Reduced visibility and slowness!"
    ),
    BLIZZARD_END(
        "§b[Blizzard] §fLa tempête de neige se calme.",
        "§b[Blizzard] §fThe snowstorm calms down."
    ),
    BLIZZARD_WARNING(
        "§b[Blizzard] §fTempête de neige dans %time% secondes !",
        "§b[Blizzard] §fSnowstorm in %time% seconds!"
    ),
    IN_BLIZZARD(
        "§b[Blizzard] §fVous êtes dans la tempête !",
        "§b[Blizzard] §fYou are in the storm!"
    )
    ;

    private final Map<String, String> translations;

    BlizzardLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_US", en);
    }

    @Override
    public String getKey() { return "blizzard." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}
