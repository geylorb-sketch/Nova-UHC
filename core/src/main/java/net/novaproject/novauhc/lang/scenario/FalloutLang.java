package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

/**
 * Messages du scénario Fallout.
 * Remplace l'ancienne classe scenario/lang/lang/FalloutLang.java
 */
public enum FalloutLang implements Lang {

    FALLOUT_STARTED(
        "§c§l[Fallout] §fLES RADIATIONS COMMENCENT !",
        "§c§l[Fallout] §fRADIATIONS BEGIN!"
    ),
    FALLOUT_INSTRUCTION(
        "§c[Fallout] §fDescendez sous Y=%safe_y% pour éviter les radiations !",
        "§c[Fallout] §fGo below Y=%safe_y% to avoid radiation!"
    ),
    FALLOUT_WARNING(
        "§c[Fallout] §fRadiations dans %time% !",
        "§c[Fallout] §fRadiations in %time%!"
    ),
    FALLOUT_PREPARE(
        "§c[Fallout] §fPréparez vos abris souterrains !",
        "§c[Fallout] §fPrepare your underground shelters!"
    ),
    RADIATION_LIGHT(
        "§c[Fallout] §fVous êtes exposé aux radiations !",
        "§c[Fallout] §fYou are exposed to radiation!"
    ),
    RADIATION_MODERATE(
        "§c[Fallout] §fRadiation modérée ! Trouvez un abri !",
        "§c[Fallout] §fModerate radiation! Find shelter!"
    ),
    RADIATION_SEVERE(
        "§c[Fallout] §fRadiation SÉVÈRE ! Descendez immédiatement !",
        "§c[Fallout] §fSEVERE radiation! Go underground immediately!"
    ),
    FALLOUT_FORCED(
        "§c[Fallout] §fRadiations forcées par un administrateur !",
        "§c[Fallout] §fRadiations forced by an administrator!"
    )
    ;

    private final Map<String, String> translations;

    FalloutLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_US", en);
    }

    @Override
    public String getKey() { return "fallout." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}
