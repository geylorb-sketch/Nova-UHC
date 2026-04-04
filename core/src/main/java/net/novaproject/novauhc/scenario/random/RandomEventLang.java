package net.novaproject.novauhc.scenario.random;

import net.novaproject.novauhc.lang.Lang;

import java.util.Map;

public enum RandomEventLang implements Lang {

    ENABLED_NAME    ("Activé",                                          "Enabled"),
    ENABLED_DESC    ("Active ou désactive cet événement",              "Enable or disable this event"),
    CHANCE_NAME     ("Chance",                                          "Chance"),
    CHANCE_DESC     ("Probabilité que l'événement se déclenche",       "Probability that the event fires"),
    MIN_TIME_NAME   ("Temps minimum",                                   "Minimum time"),
    MIN_TIME_DESC   ("Temps min (en secondes) depuis le début du jeu", "Min time (in seconds) since game start"),
    MAX_TIME_NAME   ("Temps maximum",                                   "Maximum time"),
    MAX_TIME_DESC   ("Temps max (en secondes) depuis le début du jeu", "Max time (in seconds) since game start"),
    REPEATING_NAME  ("Répétable",                                       "Repeating"),
    REPEATING_DESC  ("Si l'événement se répète après déclenchement",   "Whether the event repeats after firing"),
    ;

    private final Map<String, String> translations;

    RandomEventLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_EN", en);
    }

    @Override public String getKey() { return "random_event." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
