package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;


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
    ),

    
    WARNING_ONE_MINUTE(
        "§c[AcidRain] §fPluie acide dans 1 minute ! Trouvez un abri !",
        "§c[AcidRain] §fAcid rain in 1 minute! Find shelter!"
    ),
    WARNING_TEN_SECONDS(
        "§c[AcidRain] §fPluie acide dans 10 secondes !",
        "§c[AcidRain] §fAcid rain in 10 seconds!"
    ),
    ENDING_SOON(
        "§c[AcidRain] §fLa pluie acide s'arrête dans 10 secondes !",
        "§c[AcidRain] §fAcid rain stops in 10 seconds!"
    ),
    RAIN_STOPPED(
        "§c[AcidRain] §fLa pluie acide s'est arrêtée. Vous pouvez sortir en sécurité !",
        "§c[AcidRain] §fThe acid rain has stopped. You can go outside safely!"
    ),
    BURNING(
        "§c[AcidRain] §fVous êtes brûlé par la pluie acide ! Trouvez un abri !",
        "§c[AcidRain] §fYou are burning from acid rain! Find shelter!"
    ),
    ;

    private final Map<String, String> translations;

    AcidRainLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_EN", en);
    }

    @Override
    public String getKey() { return "acidrain." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}
