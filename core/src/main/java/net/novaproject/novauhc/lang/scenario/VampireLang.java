package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

/**
 * Messages du scénario Vampire.
 * Remplace l'ancienne classe scenario/lang/lang/VampireLang.java
 */
public enum VampireLang implements Lang {

    KILL_HEAL(
        "§c[Vampire] §fVous avez récupéré %heal_hearts% cœur(s) en tuant %victim% !",
        "§c[Vampire] §fYou recovered %heal_hearts% heart(s) by killing %victim%!"
    ),
    SUN_DAMAGE(
        "§c[Vampire] §fVous brûlez au soleil ! Équipez un casque ou trouvez de l'ombre !",
        "§c[Vampire] §fYou are burning in the sun! Wear a helmet or find shade!"
    ),
    SUN_DAMAGE_SEVERE(
        "§c[Vampire] §fVous gelez ! Trouvez de la chaleur rapidement !",
        "§c[Vampire] §fYou are freezing! Find warmth quickly!"
    )
    ;

    private final Map<String, String> translations;

    VampireLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_US", en);
    }

    @Override
    public String getKey() { return "vampire." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}
