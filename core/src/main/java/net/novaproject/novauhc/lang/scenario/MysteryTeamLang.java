package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

/**
 * Messages du scénario MysteryTeam.
 * Remplace l'ancienne classe scenario/lang/lang/MysteryTeamLang.java
 */
public enum MysteryTeamLang implements Lang {

    TEAMS_ASSIGNED(
        "§5§l[MysteryTeam] §fLes équipes mystères ont été assignées !",
        "§5§l[MysteryTeam] §fMystery teams have been assigned!"
    ),
    FIND_TEAMMATES(
        "§5[MysteryTeam] §fTrouvez vos coéquipiers en comparant vos bannières !",
        "§5[MysteryTeam] §fFind your teammates by comparing banners!"
    ),
    TEAMMATE_FOUND(
        "§5[MysteryTeam] §f%teammate% §fest votre coéquipier ! (Équipe %team_name%)",
        "§5[MysteryTeam] §f%teammate% §fis your teammate! (Team %team_name%)"
    ),
    TEAMS_REVEALED(
        "§5§l[MysteryTeam] §fTOUTES LES ÉQUIPES SONT RÉVÉLÉES !",
        "§5§l[MysteryTeam] §fALL TEAMS ARE REVEALED!"
    ),
    TEAM_ANNOUNCEMENT(
        "§5[MysteryTeam] §fÉquipe %team_name%: %members%",
        "§5[MysteryTeam] §fTeam %team_name%: %members%"
    ),
    REVEAL_WARNING(
        "§5[MysteryTeam] §fRévélation des équipes dans %time% !",
        "§5[MysteryTeam] §fTeams revealed in %time%!"
    ),
    REVEAL_FORCED(
        "§5[MysteryTeam] §fÉquipes révélées par un administrateur !",
        "§5[MysteryTeam] §fTeams revealed by an administrator!"
    )
    ;

    private final Map<String, String> translations;

    MysteryTeamLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_US", en);
    }

    @Override
    public String getKey() { return "mysteryteam." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}
