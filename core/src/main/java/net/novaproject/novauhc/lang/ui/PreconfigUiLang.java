package net.novaproject.novauhc.lang.ui;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

public enum PreconfigUiLang implements Lang {
    LOADING_NAME("§e§l⏳ Chargement...", "§e§l⏳ Loading..."),
    LOADING_DESC("§7Récupération de vos configurations... Veuillez patienter...", "§7Fetching your configurations... Please wait..."),
    EMPTY_NAME("Aucune configuration", "No configuration"),
    EMPTY_DESC("§7Vous n'avez pas encore de configuration.", "§7You don't have any configurations yet."),
    EMPTY_HINT("§7Cliquez sur le papier en bas pour en créer une !", "§7Click the paper below to create one!"),
    CONFIG_LOAD("Charger", "Load"),
    CONFIG_DELETE("Supprimer", "Delete"),
    CONFIRM_LOAD("Êtes-vous sûr de vouloir charger la configuration %name% ?", "Are you sure you want to load configuration %name%?"),
    CONFIRM_DELETE("Êtes-vous sûr de vouloir supprimer la configuration %name% ?", "Are you sure you want to delete configuration %name%?"),
    CLICK_LOAD("§e► Clic gauche §8» §a§lCharger", "§e► Left click §8» §a§lLoad"),
    CLICK_DELETE("§e► Clic droit §8» §c§lSupprimer", "§e► Right click §8» §c§lDelete"),
    ADD_CONFIG_NAME("§8┃ §fAjoutez une §e§lconfiguration", "§8┃ §fAdd a §e§lconfiguration"),
    ADD_CONFIG_DESC("  §8┃ §fPermet d'ajouter une §e§lConfiguration", "  §8┃ §fAdd a §e§lConfiguration"),
    ADD_CONFIG_ANVIL_HINT("§fNom de la §e§lConfiguration", "§fConfiguration §e§lName"),
    ;

    private final Map<String, String> translations;
    PreconfigUiLang(String fr, String en) { this.translations = Map.of("fr_FR", fr, "en_EN", en); }
    @Override public String getKey() { return "ui.preconfig." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
