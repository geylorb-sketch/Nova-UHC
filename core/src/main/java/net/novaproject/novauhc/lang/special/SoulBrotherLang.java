package net.novaproject.novauhc.lang.special;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

public enum SoulBrotherLang implements Lang {

    SOUL_BROTHER_NAME("SoulBrother", "SoulBrother"),
    SOUL_BROTHER_DESC(
            "Les équipes sont séparées dans 2 mondes identiques, réunies après %minutes% minutes !",
            "Teams are split into 2 identical worlds, reunited after %minutes% minutes!"
    ),
    SOULS_SEPARATED(
            "§6Les âmes sœurs ont été séparées dans deux mondes !",
            "§6Soul brothers have been separated into two worlds!"
    ),
    TEAM_SIZE_MINIMUM(
            "§cSoulBrother nécessite des équipes d'au moins 2 joueurs. Taille forcée à 2.",
            "§cSoulBrother requires teams of at least 2 players. Size forced to 2."
    ),
    WORLDS_NOT_AVAILABLE(
            "§cLes mondes ne sont pas disponibles !",
            "§cThe worlds are not available!"
    ),
    SOUL_BROTHER_LINKED(
            "§bTon âme sœur est §f%brother%§b !",
            "§bYour soul brother is §f%brother%§b!"
    ),
    NO_SOUL_BROTHER(
            "§7Tu n'as pas d'âme sœur pour cette partie.",
            "§7You have no soul brother for this game."
    ),
    ASSIGNED_TO_WORLD(
            "§7Tu as été assigné au monde §f%world%§7.",
            "§7You have been assigned to world §f%world%§7."
    ),
    BROTHER_DIED_BROADCAST(
            "§c%player% §7est mort. Son âme sœur §c%brother% §7subit les conséquences...",
            "§c%player% §7has died. Their soul brother §c%brother% §7suffers the consequences..."
    ),
    BROTHER_DIED(
            "§cTon âme sœur §f%brother% §cest morte ! Tu subis un contrecoup !",
            "§cYour soul brother §f%brother% §chas died! You suffer a backlash!"
    ),
    REUNION_WARNING_MINUTES(
            "§6La réunion des âmes sœurs aura lieu dans §f%minutes% minutes§6 !",
            "§6Soul brothers reunion will happen in §f%minutes% minutes§6!"
    ),
    REUNION_WARNING_ONE_MINUTE(
            "§6La réunion des âmes sœurs aura lieu dans §f1 minute§6 !",
            "§6Soul brothers reunion will happen in §f1 minute§6!"
    ),
    REUNION_WARNING_SECONDS(
            "§cRéunion dans §f%seconds% secondes§c !",
            "§cReunion in §f%seconds% seconds§c!"
    ),
    REUNION_BROADCAST(
            "§6§lLes âmes sœurs sont réunies !",
            "§6§lSoul brothers are reunited!"
    ),
    REUNION_PERSONAL(
            "§bTu as été téléporté pour la réunion !",
            "§bYou have been teleported for the reunion!"
    ),
    REUNION_BROTHER_NAME(
            "§bTon âme sœur §f%brother% §beste avec toi !",
            "§bYour soul brother §f%brother% §bis with you!"
    ),
    REUNION_BONUS(
            "§aBonus de réunion reçu !",
            "§aReunion bonus received!"
    ),
    SOUL_UPDATE(
            "§7Âme sœur §f%brother% §7— Position: §f%x%§7, §f%z% §7— Vie: §f%health%§7❤",
            "§7Soul brother §f%brother% §7— Position: §f%x%§7, §f%z% §7— Health: §f%health%§7❤"
    ),

    VAR_REUNION_TIME_NAME("Temps de réunion", "Reunion Time"),
    VAR_REUNION_TIME_DESC("Temps avant la réunion des âmes sœurs (en secondes).", "Time before soul brothers reunion (in seconds)."),

    VAR_UPDATE_INTERVAL_NAME("Intervalle d'update", "Update Interval"),
    VAR_UPDATE_INTERVAL_DESC("Temps entre chaque update de position envoyé aux joueurs (en secondes).", "Time between each position update sent to players (in seconds)."),

    VAR_DEATH_BACKLASH_NAME("Contrecoup de mort", "Death Backlash"),
    VAR_DEATH_BACKLASH_DESC("Inflige des dégâts et des effets au frère quand son âme sœur meurt.", "Deals damage and effects to the brother when their soul dies."),

    VAR_DEATH_BACKLASH_DAMAGE_NAME("Dégâts de contrecoup", "Backlash Damage"),
    VAR_DEATH_BACKLASH_DAMAGE_DESC("Dégâts infligés au frère survivant lors du contrecoup.", "Damage dealt to the surviving brother on backlash."),

    VAR_DEATH_BACKLASH_DURATION_NAME("Durée des effets de contrecoup", "Backlash Effect Duration"),
    VAR_DEATH_BACKLASH_DURATION_DESC("Durée en ticks des effets (lenteur, cécité) infligés lors du contrecoup.", "Duration in ticks of effects (slowness, blindness) applied on backlash."),

    VAR_REUNION_SCATTER_RADIUS_NAME("Rayon de dispersion réunion", "Reunion Scatter Radius"),
    VAR_REUNION_SCATTER_RADIUS_DESC("Rayon en blocs autour du spawn pour téléporter les joueurs lors de la réunion.", "Radius in blocks around spawn for teleporting players during reunion.");

    private final String fr;
    private final String en;

    SoulBrotherLang(String fr, String en) {
        this.fr = fr;
        this.en = en;
    }

    @Override
    public String getKey() {
        return "soulbrother." + name();
    }

    @Override
    public Map<String, String> getTranslations() {
        return Map.of("fr_FR", fr, "en_EN", en);
    }
}