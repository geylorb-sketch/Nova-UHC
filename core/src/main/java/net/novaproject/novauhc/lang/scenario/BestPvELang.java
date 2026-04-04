package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;


public enum BestPvELang implements Lang {

    LIST_QUIT(
        "Vous avez quitté la liste BestPvE. Vous la rejoindrez à nouveau dans %best_timer%",
        "You left the BestPvE list. You'll rejoin in %best_timer%"
    ),
    LIST_JOIN(
        "Vous avez rejoint la liste BestPvE. Attention à ne plus prendre de dégâts !",
        "You joined the BestPvE list. Don't take any damage!"
    ),
    GAIN_MESSAGE(
        "Vous avez gagné §a%heart_gain% §fcoeur(s) !",
        "You gained §a%heart_gain% §fheart(s)!"
    )
    ;

    private final Map<String, String> translations;

    BestPvELang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_EN", en);
    }

    @Override
    public String getKey() { return "bestpve." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}
