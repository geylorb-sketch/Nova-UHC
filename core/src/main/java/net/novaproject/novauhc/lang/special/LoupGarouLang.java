package net.novaproject.novauhc.lang.special;

import net.novaproject.novauhc.lang.Lang;

import java.util.Map;

public enum LoupGarouLang implements Lang {

    DEATH_REVIVE(
            "§cVous êtes mort mais vous pouvez toujours être ressuscité.",
            "§cYou are dead but can still be revived."
    ),

    VILLAGE_DEATH(
            "§8§m─────────────────────────§r\n" +
                    "§bLe village a perdu un de ses membres §6%player%\n" +
                    "§8§m─────────────────────────§r",
            "§8§m─────────────────────────§r\n" +
                    "§bThe village has lost a member §6%player%\n" +
                    "§8§m─────────────────────────§r"
    ),

    LOUP_ROLE_NAME("§4Loup-Garou", "§4Werewolf"),

    LOUP_ROLE_MSG(
            "§8§m─────§cLoup-Garou§8§m─────§r\n" +
                    "§fObjectif : §cGagnez avec les Loups-Garous\n" +
                    "§fPouvoir : §bForce I la nuit\n" +
                    "§fDescription : §5Vous connaissez la liste de vos coéquipiers.\n" +
                    "§8§m──────────────────────§r",
            "§8§m─────§cWerewolf§8§m─────§r\n" +
                    "§fGoal: §cWin with the Werewolves\n" +
                    "§fPower: §bStrength I at night\n" +
                    "§fDescription: §5You know the list of your teammates.\n" +
                    "§8§m──────────────────────§r"
    ),

    VILLAGEOIS_ROLE_NAME("§aVillageois", "§aVillager"),

    VILLAGEOIS_ROLE_MSG(
            "§8§m─────§aVillageois§8§m─────§r\n" +
                    "§fObjectif : §aGagnez avec le Village\n" +
                    "§fPouvoir : §bAucun\n" +
                    "§fDescription : §5Vous n'avez aucun pouvoir. Bonne chance !\n" +
                    "§8§m──────────────────────§r",
            "§8§m─────§aVillager§8§m─────§r\n" +
                    "§fGoal: §aWin with the Village\n" +
                    "§fPower: §bNone\n" +
                    "§fDescription: §5You have no power. Good luck!\n" +
                    "§8§m──────────────────────§r"
    ),

    RENARD_ROLE_NAME("§aRenard", "§aFox"),

    RENARD_ROLE_MSG(
            "§8§m─────§aRenard§8§m─────§r\n" +
                    "§fObjectif : §aGagnez avec le Village\n" +
                    "§fPouvoir : §bFlair + Speed I la nuit\n" +
                    "§fDescription : §5Votre flair révèle si un joueur est §cLoup-Garou§5.\n" +
                    "§8§m──────────────────────§r",
            "§8§m─────§aFox§8§m─────§r\n" +
                    "§fGoal: §aWin with the Village\n" +
                    "§fPower: §bSniff + Speed I at night\n" +
                    "§fDescription: §5Your sniff reveals if a player is a §cWerewolf§5.\n" +
                    "§8§m──────────────────────§r"
    ),

    ALLIES_LIST(
            "§cVos alliés : %list%",
            "§cYour allies: %list%"
    );

    private final Map<String, String> translations;

    LoupGarouLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_EN", en);
    }

    @Override
    public String getKey() {
        return "loupgarou." + name();
    }

    @Override
    public Map<String, String> getTranslations() {
        return translations;
    }
}