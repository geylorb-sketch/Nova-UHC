package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

/**
 * Messages du scénario Gladiator.
 * Remplace l'ancienne classe scenario/lang/lang/GladiatorLang.java
 */
public enum GladiatorLang implements Lang {

    ARENA_CREATED(
        "§4[Gladiator] §fArène créée ! Combat entre %player1% et %player2% !",
        "§4[Gladiator] §fArena created! Fight between %player1% and %player2%!"
    ),
    TELEPORTING(
        "§4[Gladiator] §fTéléportation dans l'arène dans %seconds% secondes...",
        "§4[Gladiator] §fTeleporting to the arena in %seconds% seconds..."
    ),
    COMBAT_STARTED(
        "§4[Gladiator] §fQue le combat commence ! Bonne chance !",
        "§4[Gladiator] §fLet the fight begin! Good luck!"
    ),
    COMBAT_TIMEOUT(
        "§4[Gladiator] §fTemps écoulé ! Les deux combattants sont téléportés.",
        "§4[Gladiator] §fTime's up! Both fighters are teleported."
    ),
    WINNER_ANNOUNCED(
        "§4[Gladiator] §f%winner% remporte le duel !",
        "§4[Gladiator] §f%winner% wins the duel!"
    ),
    ARENA_CLEANUP(
        "§4[Gladiator] §fArène nettoyée.",
        "§4[Gladiator] §fArena cleaned up."
    )
    ;

    private final Map<String, String> translations;

    GladiatorLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_US", en);
    }

    @Override
    public String getKey() { return "gladiator." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}
