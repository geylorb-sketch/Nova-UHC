package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

/**
 * Messages du scénario BloodLust.
 * Remplace l'ancienne classe scenario/lang/lang/BloodLustLang.java
 */
public enum BloodLustLang implements Lang {

    KILL_BOOST(
        "§c[BloodLust] §fVous ressentez la soif de sang ! Speed II et Strength I pendant 30 secondes !",
        "§c[BloodLust] §fYou feel the bloodlust! Speed II and Strength I for 30 seconds!"
    ),
    BOOST_EXPIRED(
        "§c[BloodLust] §fVotre soif de sang s'estompe...",
        "§c[BloodLust] §fYour bloodlust fades..."
    ),

    
    BLOODLUST_ACTIVATED(
        "§c[BloodLust] §f%player% §fest en état de soif de sang !",
        "§c[BloodLust] §f%player% §fis in a bloodlust state!"
    ),
    ENDING_TEN_SECONDS(
        "§c[BloodLust] §fSoif de sang se termine dans 10 secondes !",
        "§c[BloodLust] §fBloodlust ends in 10 seconds!"
    ),
    ENDING_FIVE_SECONDS(
        "§c[BloodLust] §fSoif de sang se termine dans 5 secondes !",
        "§c[BloodLust] §fBloodlust ends in 5 seconds!"
    ),
    BLOODLUST_ENDED(
        "§c[BloodLust] §fVotre soif de sang s'est calmée.",
        "§c[BloodLust] §fYour bloodlust has calmed down."
    ),
    ;

    private final Map<String, String> translations;

    BloodLustLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_US", en);
    }

    @Override
    public String getKey() { return "bloodlust." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}
