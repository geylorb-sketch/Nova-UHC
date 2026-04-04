package net.novaproject.novauhc.lang.special;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;


public enum MysteryTeamLang implements Lang {


    FIND_TEAMMATES(
            "§6⚔ §eTrouvez vos coéquipiers ! Taille: §6%team_size%",
            "§6⚔ §eFind your teammates! Size: §6%team_size%"
    );


    private final Map<String, String> translations;

    MysteryTeamLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_EN", en);
    }

    @Override
    public String getKey() { return "mysteryteam." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}
