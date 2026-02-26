package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;

import java.util.Map;

public enum FireForceLang implements Lang {

    DEATH_REVIVE(
            "§cVous êtes mort mais vous pouvez toujours être ressuscité.",
            "§cYou are dead but can still be revived."
    ),

    SOMEONE_DIED(
            "§8§m─────────────────────────§r\n" +
                    "§bQuelqu'un est mort : §6%player%\n" +
                    "§8§m─────────────────────────§r",
            "§8§m─────────────────────────§r\n" +
                    "§bSomeone has died: §6%player%\n" +
                    "§8§m─────────────────────────§r"
    ),

    RITUAL_START(
            "§6Les fragments ont été réunis, le rituel commence...",
            "§6The fragments have been gathered, the ritual begins..."
    );

    private final Map<String, String> translations;

    FireForceLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_US", en);
    }

    @Override
    public String getKey() {
        return "fireforce." + name();
    }

    @Override
    public Map<String, String> getTranslations() {
        return translations;
    }
}