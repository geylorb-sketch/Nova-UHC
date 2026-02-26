package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

/**
 * Messages du scénario WeakestLink.
 * Remplace l'ancienne classe scenario/lang/lang/WeakestLinkLang.java
 */
public enum WeakestLinkLang implements Lang {

    WEAKEST_PLAYER(
        "§c[WeakestLink] §f%player% est maintenant le maillon faible ! (Dégâts x%multiplier%)",
        "§c[WeakestLink] §f%player% is now the weakest link! (Damage x%multiplier%)"
    ),
    DAMAGE_TAKEN(
        "§c[WeakestLink] §fVous êtes le maillon faible ! Vous prenez %multiplier%x plus de dégâts !",
        "§c[WeakestLink] §fYou are the weakest link! You take %multiplier%x more damage!"
    ),
    NO_LONGER_WEAKEST(
        "§c[WeakestLink] §fVous n'êtes plus le maillon faible !",
        "§c[WeakestLink] §fYou are no longer the weakest link!"
    )
    ;

    private final Map<String, String> translations;

    WeakestLinkLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_US", en);
    }

    @Override
    public String getKey() { return "weakestlink." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}
