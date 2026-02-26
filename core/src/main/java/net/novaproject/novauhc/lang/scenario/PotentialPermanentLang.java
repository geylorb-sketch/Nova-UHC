package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

/**
 * Messages du scénario PotentialPermanent.
 * Remplace l'ancienne classe scenario/lang/lang/PotentialPermanentLang.java
 */
public enum PotentialPermanentLang implements Lang {

    HEALTH_STATUS(
        "§e[PotentialPermanent] §fVie permanente : %permanent_hearts% cœurs | Absorption : %absorption_hearts% cœurs",
        "§e[PotentialPermanent] §fPermanent health: %permanent_hearts% hearts | Absorption: %absorption_hearts% hearts"
    ),
    KILL_CONVERSION(
        "§e[PotentialPermanent] §fKill ! %converted_hearts% cœur(s) d'absorption convertis en vie permanente !",
        "§e[PotentialPermanent] §fKill! %converted_hearts% absorption heart(s) converted to permanent health!"
    ),
    KILL_ANNOUNCEMENT(
        "§e[PotentialPermanent] §f%player% a maintenant %permanent_hearts% cœurs permanents !",
        "§e[PotentialPermanent] §f%player% now has %permanent_hearts% permanent hearts!"
    ),
    STARTING_HEALTH(
        "§e[PotentialPermanent] §fVous commencez avec %permanent_hearts% cœurs permanents + %absorption_hearts% cœurs d'absorption !",
        "§e[PotentialPermanent] §fYou start with %permanent_hearts% permanent hearts + %absorption_hearts% absorption hearts!"
    ),
    CONVERSION_INFO(
        "§e[PotentialPermanent] §fTuez des joueurs pour convertir l'absorption en vie permanente !",
        "§e[PotentialPermanent] §fKill players to convert absorption into permanent health!"
    )
    ;

    private final Map<String, String> translations;

    PotentialPermanentLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_US", en);
    }

    @Override
    public String getKey() { return "potentialpermanent." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}
