package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

/**
 * Messages du scénario Genie.
 * Remplace l'ancienne classe scenario/lang/lang/GenieLang.java
 */
public enum GenieLang implements Lang {

    WISHES_RECEIVED(
        "§6[Genie] §fVous avez 3 souhaits ! Utilisez /wish pour voir vos options.",
        "§6[Genie] §fYou have 3 wishes! Use /wish to see your options."
    ),
    WISH_GRANTED(
        "§6[Genie] §fSouhait exaucé ! Il vous reste %remaining% souhait(s).",
        "§6[Genie] §fWish granted! You have %remaining% wish(es) left."
    ),
    WISH_ANNOUNCED(
        "§6[Genie] §f%player% a utilisé un souhait !",
        "§6[Genie] §f%player% used a wish!"
    ),
    NO_WISHES_LEFT(
        "§6[Genie] §cVous n'avez plus de souhaits !",
        "§6[Genie] §cYou have no wishes left!"
    ),
    NOT_ENOUGH_KILLS(
        "§6[Genie] §cVous n'avez pas assez de kills pour ce souhait !",
        "§6[Genie] §cYou don't have enough kills for this wish!"
    ),
    WISH_OPTIONS_IMPROVED(
        "§6[Genie] §fVos options de souhaits se sont améliorées avec ce kill !",
        "§6[Genie] §fYour wish options improved with this kill!"
    ),
    HEAL_GRANTED(
        "§6[Genie] §fVous avez été soigné !",
        "§6[Genie] §fYou have been healed!"
    ),
    FOOD_GRANTED(
        "§6[Genie] §fVotre faim a été restaurée !",
        "§6[Genie] §fYour hunger has been restored!"
    ),
    SPEED_GRANTED(
        "§6[Genie] §fVous avez reçu Speed II pendant %duration% minutes !",
        "§6[Genie] §fYou received Speed II for %duration% minutes!"
    ),
    STRENGTH_GRANTED(
        "§6[Genie] §fVous avez reçu Strength I pendant %duration% minutes !",
        "§6[Genie] §fYou received Strength I for %duration% minutes!"
    ),
    RESISTANCE_GRANTED(
        "§6[Genie] §fVous avez reçu Resistance I pendant %duration% minutes !",
        "§6[Genie] §fYou received Resistance I for %duration% minutes!"
    ),
    INVISIBILITY_GRANTED(
        "§6[Genie] §fVous êtes invisible pendant %duration% minutes !",
        "§6[Genie] §fYou are invisible for %duration% minutes!"
    ),
    TELEPORT_GRANTED(
        "§6[Genie] §fVous avez été téléporté !",
        "§6[Genie] §fYou have been teleported!"
    ),
    FLIGHT_GRANTED(
        "§6[Genie] §fVous pouvez voler pendant %duration% secondes !",
        "§6[Genie] §fYou can fly for %duration% seconds!"
    )
    ;

    private final Map<String, String> translations;

    GenieLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_US", en);
    }

    @Override
    public String getKey() { return "genie." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}
