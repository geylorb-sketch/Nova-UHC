package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;


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
    ),

    
    VOTE_IN_FIVE_MINUTES(
        "§9[Democracy] §fVote démocratique dans 5 minutes !",
        "§9[Democracy] §fDemocratic vote in 5 minutes!"
    ),
    VOTE_IN_ONE_MINUTE(
        "§9[Democracy] §fVote démocratique dans 1 minute !",
        "§9[Democracy] §fDemocratic vote in 1 minute!"
    ),
    ONE_MINUTE_LEFT(
        "§9[Democracy] §fPlus qu'1 minute pour voter !",
        "§9[Democracy] §fOnly 1 minute left to vote!"
    ),
    TEN_SECONDS_LEFT(
        "§9[Democracy] §fPlus que 10 secondes pour voter !",
        "§9[Democracy] §fOnly 10 seconds left to vote!"
    ),
    VOTE_STARTS(
        "§9§l[Democracy] §fLE VOTE DÉMOCRATIQUE COMMENCE !",
        "§9§l[Democracy] §fTHE DEMOCRATIC VOTE BEGINS!"
    ),
    USE_VOTE_COMMAND(
        "§9[Democracy] §fUtilisez /vote <joueur> pour voter !",
        "§9[Democracy] §fUse /vote <player> to vote!"
    ),
    PLAYER_ENTRY(
        "§9[Democracy] §f- %player%",
        "§9[Democracy] §f- %player%"
    ),
    YOU_ELIMINATED(
        "§9[Democracy] §cVous avez été éliminé par le vote du peuple !",
        "§9[Democracy] §cYou have been eliminated by the people's vote!"
    ),
    NOT_ENOUGH_VOTES(
        "§9[Democracy] §fAucun joueur n'a reçu assez de votes pour être éliminé !",
        "§9[Democracy] §fNo player received enough votes to be eliminated!"
    ),
    NO_VOTE_ACTIVE(
        "§9[Democracy] §cAucun vote en cours !",
        "§9[Democracy] §cNo vote in progress!"
    ),
    ALREADY_VOTED(
        "§9[Democracy] §cVous avez déjà voté !",
        "§9[Democracy] §cYou have already voted!"
    ),
    PLAYER_NOT_FOUND(
        "§9[Democracy] §cJoueur introuvable !",
        "§9[Democracy] §cPlayer not found!"
    ),
    NOT_PARTICIPATING(
        "§9[Democracy] §cCe joueur ne participe pas !",
        "§9[Democracy] §cThis player is not participating!"
    ),
    CANNOT_VOTE_SELF(
        "§9[Democracy] §cVous ne pouvez pas voter pour vous-même !",
        "§9[Democracy] §cYou cannot vote for yourself!"
    ),
    VOTE_BROADCAST(
        "§9[Democracy] §f%voter% a voté ! (%current%/%total%)",
        "§9[Democracy] §f%voter% has voted! (%current%/%total%)"
    ),
    STATUS_NO_VOTE(
        "§9[Democracy] §fAucun vote en cours.",
        "§9[Democracy] §fNo vote in progress."
    ),
    STATUS_HEADER(
        "§9[Democracy] §fVotes actuels :",
        "§9[Democracy] §fCurrent votes:"
    ),
    STATUS_ENTRY(
        "§9[Democracy] §f%player%: %votes% vote(s)",
        "§9[Democracy] §f%player%: %votes% vote(s)"
    ),
    ;

    private final Map<String, String> translations;

    DemocracyLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_EN", en);
    }

    @Override
    public String getKey() { return "democracy." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}
