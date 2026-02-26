package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

/**
 * Messages du scénario Democracy.
 * Remplace l'ancienne classe scenario/lang/lang/DemocracyLang.java
 */
public enum DemocracyLang implements Lang {

    VOTE_STARTED(
        "§9§l[Democracy] §fLE VOTE DÉMOCRATIQUE COMMENCE !",
        "§9§l[Democracy] §fTHE DEMOCRATIC VOTE BEGINS!"
    ),
    VOTE_INSTRUCTION(
        "§9[Democracy] §fUtilisez /vote <joueur> pour voter !",
        "§9[Democracy] §fUse /vote <player> to vote!"
    ),
    VOTE_DURATION(
        "§9[Democracy] §fVous avez %duration% minutes pour voter !",
        "§9[Democracy] §fYou have %duration% minutes to vote!"
    ),
    VOTE_WARNING(
        "§9[Democracy] §fVote démocratique dans %time% !",
        "§9[Democracy] §fDemocratic vote in %time%!"
    ),
    VOTE_COUNTDOWN_1MIN(
        "§9[Democracy] §fPlus qu'1 minute pour voter !",
        "§9[Democracy] §fOnly 1 minute left to vote!"
    ),
    VOTE_COUNTDOWN_10SEC(
        "§9[Democracy] §fPlus que 10 secondes pour voter !",
        "§9[Democracy] §fOnly 10 seconds left to vote!"
    ),
    VOTE_RESULTS(
        "§9§l[Democracy] §fRÉSULTATS DU VOTE :",
        "§9§l[Democracy] §fVOTE RESULTS:"
    ),
    VOTE_RESULT_LINE(
        "§9[Democracy] §f%player%: %votes% vote(s)",
        "§9[Democracy] §f%player%: %votes% vote(s)"
    ),
    PLAYER_ELIMINATED(
        "§9§l[Democracy] §f%player% §fa été éliminé par vote démocratique !",
        "§9§l[Democracy] §f%player% §fwas eliminated by democratic vote!"
    ),
    NO_ELIMINATION(
        "§9[Democracy] §fAucun joueur n'a reçu assez de votes pour être éliminé !",
        "§9[Democracy] §fNo player received enough votes to be eliminated!"
    ),
    NO_VOTES(
        "§9[Democracy] §fAucun vote ! Personne n'est éliminé.",
        "§9[Democracy] §fNo votes! Nobody is eliminated."
    ),
    TIE_RANDOM(
        "§9[Democracy] §fÉgalité ! Sélection aléatoire...",
        "§9[Democracy] §fTie! Random selection..."
    ),
    NOT_ENOUGH_PLAYERS(
        "§9[Democracy] §fPas assez de joueurs pour un vote !",
        "§9[Democracy] §fNot enough players to vote!"
    ),
    VOTE_CAST(
        "§9[Democracy] §f%voter% a voté ! (%current%/%total% votes reçus)",
        "§9[Democracy] §f%voter% voted! (%current%/%total% votes received)"
    ),
    AVAILABLE_PLAYERS(
        "§9[Democracy] §fJoueurs disponibles :",
        "§9[Democracy] §fAvailable players:"
    ),
    AVAILABLE_PLAYER_LINE(
        "§9[Democracy] §f- %player%",
        "§9[Democracy] §f- %player%"
    )
    ;

    private final Map<String, String> translations;

    DemocracyLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_US", en);
    }

    @Override
    public String getKey() { return "democracy." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}
