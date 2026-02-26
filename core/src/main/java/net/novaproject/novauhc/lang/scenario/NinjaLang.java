package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

/**
 * Messages du scénario Ninja.
 * Remplace l'ancienne classe scenario/lang/lang/NinjaLang.java
 */
public enum NinjaLang implements Lang {

    KILL_INVISIBILITY(
        "§8[Ninja] §fVous devenez invisible pendant 10 secondes !",
        "§8[Ninja] §fYou become invisible for 10 seconds!"
    ),
    INVISIBILITY_EXPIRED(
        "§8[Ninja] §fVotre invisibilité s'estompe...",
        "§8[Ninja] §fYour invisibility fades..."
    )
    ;

    private final Map<String, String> translations;

    NinjaLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_US", en);
    }

    @Override
    public String getKey() { return "ninja." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}
