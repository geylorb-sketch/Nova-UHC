package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;


public enum BlizzardLang implements Lang {

    BLIZZARD_START(
        "§b[Blizzard] §fTempête de neige ! Visibilité réduite et ralentissement !",
        "§b[Blizzard] §fSnowstorm! Reduced visibility and slowness!"
    ),
    BLIZZARD_END(
        "§b[Blizzard] §fLa tempête de neige se calme.",
        "§b[Blizzard] §fThe snowstorm calms down."
    ),
    BLIZZARD_WARNING(
        "§b[Blizzard] §fTempête de neige dans %time% secondes !",
        "§b[Blizzard] §fSnowstorm in %time% seconds!"
    ),
    IN_BLIZZARD(
        "§b[Blizzard] §fVous êtes dans la tempête !",
        "§b[Blizzard] §fYou are in the storm!"
    ),

    
    WARNING_ONE_MINUTE(
        "§b[Blizzard] §fTempête de neige dans 1 minute !",
        "§b[Blizzard] §fBlizzard in 1 minute!"
    ),
    WARNING_TEN_SECONDS(
        "§b[Blizzard] §fTempête de neige dans 10 secondes !",
        "§b[Blizzard] §fBlizzard in 10 seconds!"
    ),
    ENDING_SOON(
        "§b[Blizzard] §fLa tempête se calme dans 10 secondes !",
        "§b[Blizzard] §fThe storm calms down in 10 seconds!"
    ),
    STORM_START(
        "§b§l[Blizzard] §fUne tempête de neige commence ! Trouvez de la chaleur !",
        "§b§l[Blizzard] §fA blizzard begins! Find warmth!"
    ),
    STORM_END(
        "§b[Blizzard] §fLa tempête de neige s'est calmée !",
        "§b[Blizzard] §fThe blizzard has calmed down!"
    ),
    FREEZING(
        "§b[Blizzard] §fVous gelez ! Trouvez de la chaleur !",
        "§b[Blizzard] §fYou are freezing! Find warmth!"
    ),
    VERY_COLD(
        "§b[Blizzard] §fVous avez très froid !",
        "§b[Blizzard] §fYou are very cold!"
    ),
    GETTING_COLD(
        "§b[Blizzard] §fVous commencez à avoir froid...",
        "§b[Blizzard] §fYou are starting to get cold..."
    ),
    ;

    private final Map<String, String> translations;

    BlizzardLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_US", en);
    }

    @Override
    public String getKey() { return "blizzard." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}
