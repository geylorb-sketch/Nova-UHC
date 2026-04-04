package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;


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
    ),

    
    RADIATION_START(
        "§c§l[Fallout] §fLES RADIATIONS COMMENCENT !",
        "§c§l[Fallout] §fRADIATION BEGINS!"
    ),
    GO_UNDERGROUND(
        "§c[Fallout] §fDescendez sous Y=%level% pour éviter les radiations !",
        "§c[Fallout] §fGet below Y=%level% to avoid radiation!"
    ),
    WARNING_FIVE_MINUTES(
        "§c[Fallout] §fRadiations dans 5 minutes ! Préparez vos abris !",
        "§c[Fallout] §fRadiation in 5 minutes! Prepare your shelters!"
    ),
    WARNING_ONE_MINUTE(
        "§c[Fallout] §fRadiations dans 1 minute ! Descendez sous Y=%level% !",
        "§c[Fallout] §fRadiation in 1 minute! Get below Y=%level%!"
    ),
    WARNING_TEN_SECONDS(
        "§c[Fallout] §fRadiations dans 10 secondes !",
        "§c[Fallout] §fRadiation in 10 seconds!"
    ),
    SEVERE_RADIATION(
        "§c[Fallout] §fRadiation SÉVÈRE ! Descendez immédiatement !",
        "§c[Fallout] §fSEVERE radiation! Get underground immediately!"
    ),
    MODERATE_RADIATION(
        "§c[Fallout] §fRadiation modérée ! Trouvez un abri !",
        "§c[Fallout] §fModerate radiation! Find shelter!"
    ),
    EXPOSED(
        "§c[Fallout] §fVous êtes exposé aux radiations !",
        "§c[Fallout] §fYou are exposed to radiation!"
    ),
    ;

    private final Map<String, String> translations;

    FalloutLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_EN", en);
    }

    @Override
    public String getKey() { return "fallout." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}
