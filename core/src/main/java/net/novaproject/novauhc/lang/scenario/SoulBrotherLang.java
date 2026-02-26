package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

/**
 * Messages du scénario SoulBrother.
 * Remplace l'ancienne classe scenario/lang/lang/SoulBrotherLang.java
 */
public enum SoulBrotherLang implements Lang {

    SOULS_SEPARATED(
        "§d§l[SoulBrother] §fLes âmes sœurs sont séparées dans des mondes parallèles !",
        "§d§l[SoulBrother] §fSoul brothers are separated in parallel worlds!"
    ),
    WORLD_ASSIGNMENT(
        "§d[SoulBrother] §fVous êtes dans le monde %world_name% !",
        "§d[SoulBrother] §fYou are in world %world_name%!"
    ),
    REUNION_STARTED(
        "§d§l[SoulBrother] §fLES ÂMES SŒURS SE RETROUVENT !",
        "§d§l[SoulBrother] §fSOUL BROTHERS REUNITE!"
    ),
    REUNION_MESSAGE(
        "§d[SoulBrother] §fVous avez été réuni avec votre âme sœur !",
        "§d[SoulBrother] §fYou have been reunited with your soul brother!"
    ),
    SOUL_BROTHER_INFO(
        "§d[SoulBrother] §fVotre âme sœur est §d%brother_name% §f!",
        "§d[SoulBrother] §fYour soul brother is §d%brother_name%§f!"
    ),
    REUNION_BONUS(
        "§d[SoulBrother] §fBonus de réunion reçu !",
        "§d[SoulBrother] §fReunion bonus received!"
    ),
    REUNION_WARNING(
        "§d[SoulBrother] §fRéunion des âmes sœurs dans %time% !",
        "§d[SoulBrother] §fSoul brother reunion in %time%!"
    ),
    REUNION_FORCED(
        "§d[SoulBrother] §fRéunion forcée par un administrateur !",
        "§d[SoulBrother] §fReunion forced by an administrator!"
    ),
    SOUL_BROTHER_UPDATE(
        "§d[SoulBrother] §fVotre âme sœur %brother_name% est en %x%, %z% (Vie: %health%/20)",
        "§d[SoulBrother] §fYour soul brother %brother_name% is at %x%, %z% (Health: %health%/20)"
    )
    ;

    private final Map<String, String> translations;

    SoulBrotherLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_US", en);
    }

    @Override
    public String getKey() { return "soulbrother." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}
