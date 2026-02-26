package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

/**
 * Messages du scénario DeathNote.
 * Remplace l'ancienne classe scenario/lang/lang/DeathNoteLang.java
 */
public enum DeathNoteLang implements Lang {

    DEATH_NOTE_RECEIVED(
        "§c§l[DeathNote] §fVous avez reçu un Death Note !",
        "§c§l[DeathNote] §fYou received a Death Note!"
    ),
    DEATH_NOTE_USED(
        "§c[DeathNote] §fVous avez utilisé le Death Note sur %target% !",
        "§c[DeathNote] §fYou used the Death Note on %target%!"
    ),
    DEATH_NOTE_COOLDOWN(
        "§c[DeathNote] §fCooldown : %cooldown% minute(s)",
        "§c[DeathNote] §fCooldown: %cooldown% minute(s)"
    ),
    DEATH_NOTE_READY(
        "§a[DeathNote] §fLe Death Note est prêt !",
        "§a[DeathNote] §fThe Death Note is ready!"
    ),
    TARGET_INVALID(
        "§c[DeathNote] §fCible invalide.",
        "§c[DeathNote] §fInvalid target."
    ),
    TARGET_NOT_FOUND(
        "§c[DeathNote] §fJoueur introuvable.",
        "§c[DeathNote] §fPlayer not found."
    ),
    TARGET_TEAMMATE(
        "§c[DeathNote] §fVous ne pouvez pas cibler un coéquipier !",
        "§c[DeathNote] §fYou cannot target a teammate!"
    ),
    TARGET_ALREADY_DEAD(
        "§c[DeathNote] §fCe joueur est déjà mort.",
        "§c[DeathNote] §fThis player is already dead."
    ),
    TARGET_SELF(
        "§c[DeathNote] §fVous ne pouvez pas vous cibler vous-même !",
        "§c[DeathNote] §fYou cannot target yourself!"
    ),
    DEATH_COUNTDOWN_START(
        "§c§l[DeathNote] §f%target% a été condamné ! Il mourra dans %death_timer% secondes !",
        "§c§l[DeathNote] §f%target% has been condemned! They will die in %death_timer% seconds!"
    ),
    DEATH_COUNTDOWN_WARNING_30(
        "§c[DeathNote] §f%target% mourra dans 30 secondes !",
        "§c[DeathNote] §f%target% will die in 30 seconds!"
    ),
    DEATH_COUNTDOWN_WARNING_10(
        "§c[DeathNote] §f%target% mourra dans 10 secondes !",
        "§c[DeathNote] §f%target% will die in 10 seconds!"
    ),
    DEATH_COUNTDOWN_WARNING_5(
        "§c[DeathNote] §f%target% mourra dans 5 secondes !",
        "§c[DeathNote] §f%target% will die in 5 seconds!"
    ),
    DEATH_COUNTDOWN_FINAL(
        "§c§l[DeathNote] §f%target% est mort !",
        "§c§l[DeathNote] §f%target% is dead!"
    ),
    DEATH_BY_DEATH_NOTE(
        "§c[DeathNote] §fVous avez été tué par le Death Note de §f%killer% §c!",
        "§c[DeathNote] §fYou were killed by §f%killer%§c's Death Note!"
    ),
    DEATH_ANNOUNCEMENT(
        "§c§l☠ §f%target% §ca été exécuté par le Death Note !",
        "§c§l☠ §f%target% §cwas executed by the Death Note!"
    ),
    DEATH_CANCELLED(
        "§a[DeathNote] §fLa mort de %target% a été annulée.",
        "§a[DeathNote] §f%target%'s death was cancelled."
    ),
    EPISODE_START(
        "§6§l[DeathNote] §fÉpisode %episode% commence !",
        "§6§l[DeathNote] §fEpisode %episode% begins!"
    ),
    EPISODE_REGEN(
        "§a[DeathNote] §fFinal heal ! Tous les joueurs sont soignés.",
        "§a[DeathNote] §fFinal heal! All players healed."
    ),
    EPISODE_TIME_LEFT(
        "§6[DeathNote] §fTemps restant dans l'épisode : %time_left%",
        "§6[DeathNote] §fTime left in episode: %time_left%"
    ),
    KIRA_CHAT_FORMAT(
        "§4§l[Kira] §r§c%player% §8» §f%message%",
        "§4§l[Kira] §r§c%player% §8» §f%message%"
    ),
    KIRA_TEAM_FORMED(
        "§4[DeathNote] §fL'équipe Kira est formée !",
        "§4[DeathNote] §fThe Kira team is formed!"
    ),
    KIRA_MEMBER_JOINED(
        "§4[DeathNote] §f%player% a rejoint l'équipe Kira !",
        "§4[DeathNote] §f%player% joined the Kira team!"
    ),
    KIRA_MEMBER_LEFT(
        "§4[DeathNote] §f%player% a quitté l'équipe Kira.",
        "§4[DeathNote] §f%player% left the Kira team."
    ),
    KIRA_MEMBER_DIED(
        "§4[DeathNote] §fUn membre Kira est mort : %player%",
        "§4[DeathNote] §fA Kira member died: %player%"
    ),
    WIN_GENTILS(
        "§a§l✦ VICTOIRE DES GENTILS ! §fKira a été vaincu !",
        "§a§l✦ GOOD GUYS WIN! §fKira has been defeated!"
    ),
    WIN_KIRA(
        "§4§l✦ VICTOIRE DE KIRA ! §fLes gentils ont été éliminés !",
        "§4§l✦ KIRA WINS! §fThe good guys have been eliminated!"
    ),
    WIN_REASON_ALL_KIRA_DEAD(
        "§fTous les membres Kira ont été éliminés.",
        "§fAll Kira members have been eliminated."
    ),
    WIN_REASON_ALL_GENTILS_DEAD(
        "§fTous les gentils ont été éliminés.",
        "§fAll good guys have been eliminated."
    ),
    WIN_REASON_KIRA_MAJORITY(
        "§fKira est majoritaire.",
        "§fKira has the majority."
    ),
    ERROR_NO_PERMISSION(
        "§c[DeathNote] §fVous n'avez pas la permission.",
        "§c[DeathNote] §fYou don't have permission."
    ),
    ERROR_SCENARIO_DISABLED(
        "§c[DeathNote] §fLe scénario est désactivé.",
        "§c[DeathNote] §fThe scenario is disabled."
    ),
    ERROR_INVALID_INPUT(
        "§c[DeathNote] §fEntrée invalide.",
        "§c[DeathNote] §fInvalid input."
    ),
    ERROR_BOOK_NOT_FOUND(
        "§c[DeathNote] §fDeath Note introuvable.",
        "§c[DeathNote] §fDeath Note not found."
    )
    ;

    private final Map<String, String> translations;

    DeathNoteLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_US", en);
    }

    @Override
    public String getKey() { return "deathnote." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}
