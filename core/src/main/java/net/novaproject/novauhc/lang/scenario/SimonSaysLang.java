package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

public enum SimonSaysLang implements Lang {

    INCOMING_ORDER("§6[SimonSays] §fSimon va bientôt donner un ordre...", "§6[SimonSays] §fSimon will soon give an order..."),
    FIVE_SECONDS("§6[SimonSays] §c5 secondes restantes !", "§6[SimonSays] §c5 seconds remaining!"),
    NEW_ORDER("§6§l[SimonSays] §fSimon dit : §e%order%", "§6§l[SimonSays] §fSimon says: §e%order%"),
    ORDER_DURATION("§6[SimonSays] §fVous avez %seconds% secondes !", "§6[SimonSays] §fYou have %seconds% seconds!"),
    FAILED_COUNT("§6[SimonSays] §c%count% joueur(s) ont échoué !", "§6[SimonSays] §c%count% player(s) failed!"),
    ALL_OBEYED("§6[SimonSays] §aTous les joueurs ont obéi à Simon !", "§6[SimonSays] §aAll players obeyed Simon!"),
    DAMAGE_MSG("§6[SimonSays] §cVous prenez 1 cœur de dégâts !", "§6[SimonSays] §cYou take 1 heart of damage!"),
    HUNGER_MSG("§6[SimonSays] §cVous perdez de la faim !", "§6[SimonSays] §cYou lose hunger!"),
    ;

    private final Map<String, String> translations;
    SimonSaysLang(String fr, String en) { this.translations = Map.of("fr_FR", fr, "en_US", en); }
    @Override public String getKey() { return "simonsays." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
