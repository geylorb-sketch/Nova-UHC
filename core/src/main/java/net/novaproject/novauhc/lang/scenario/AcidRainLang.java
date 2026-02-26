package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

/**
 * Messages du scénario AcidRain.
 * Remplace l'ancienne classe scenario/lang/lang/AcidRainLang.java
 */
public enum AcidRainLang implements Lang {

    ACID_RAIN_START(
        "§2[AcidRain] §fPluie acide ! Abritez-vous sous des blocs !",
        "§2[AcidRain] §fAcid rain! Take shelter under blocks!"
    ),
    ACID_RAIN_END(
        "§2[AcidRain] §fLa pluie acide s'arrête.",
        "§2[AcidRain] §fThe acid rain stops."
    ),
    ACID_RAIN_WARNING(
        "§2[AcidRain] §fPluie acide dans %time% secondes !",
        "§2[AcidRain] §fAcid rain in %time% seconds!"
    ),
    TAKING_DAMAGE(
        "§2[AcidRain] §cVous prenez des dégâts de la pluie acide !",
        "§2[AcidRain] §cYou are taking acid rain damage!"
    ),
    SAFE_SHELTER(
        "§2[AcidRain] §fVous êtes à l'abri de la pluie acide.",
        "§2[AcidRain] §fYou are sheltered from the acid rain."
    )
    ;

    private final Map<String, String> translations;

    AcidRainLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_US", en);
    }

    @Override
    public String getKey() { return "acidrain." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}
