package net.novaproject.novauhc.lang.ui;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

public enum TeamConfigUiLang implements Lang {

    UPDATE_TEAMS_NAME("§8┃ §fMettre à jour les équipes", "§8┃ §fUpdate teams"),
    UPDATE_TEAMS_DESC1("  §8┃ §fMet à jour %main_color%toutes", "  §8┃ §fUpdates %main_color%all"),
    UPDATE_TEAMS_DESC2("  §8┃ §fles équipes selon la nouvelle taille.", "  §8┃ §fteams to the new size."),
    TEAM_SIZE_LABEL("§8┃ §fTaille des équipes : §e§l%value%", "§8┃ §fTeam size: §e§l%value%"),
    CUSTOM_NAME("§8┃ §fTaille des équipes §6§lCustom", "§8┃ §fCustom §6§lteam size"),
    CUSTOM_DESC1("  §8┃ §fPermet de modifier le %main_color%nombre", "  §8┃ §fModify the %main_color%number"),
    CUSTOM_DESC2("  §8┃ §fde §6§ljoueurs §fdans les équipes.", "  §8┃ §fof §6§lplayers§f per team."),
    CUSTOM_ANVIL_HINT("Nombre de joueurs", "Number of players"),
    DISABLE_NAME("§8┃ §fDésactiver les équipes", "§8┃ §fDisable teams"),
    DISABLE_DESC("  §8┃ §fPermet de désactiver les %main_color%équipes.", "  §8┃ §fDisable %main_color%teams."),
    TEAM_ITEM_NAME("§8┃ §fJoueurs par équipe : §e§l%value%", "§8┃ §fPlayers per team: §e§l%value%"),
    TEAM_ITEM_DESC1("  §8┃ §fPermet de modifier le %main_color%nombre", "  §8┃ §fModify the %main_color%number"),
    TEAM_ITEM_DESC2("  §8┃ §fde §6§ljoueurs §fdans les équipes.", "  §8┃ §fof §6§lplayers§f per team."),
    TITLE("§6Menu de configuration : Team", "§6Team Configuration Menu"),
    UPDATE_BROADCAST("%main_color%[Info] §eMise à jour des équipes", "%main_color%[Info] §eTeams updated"),
    ;

    private final Map<String, String> translations;
    TeamConfigUiLang(String fr, String en) { this.translations = Map.of("fr_FR", fr, "en_US", en); }
    @Override public String getKey() { return "ui.teamconfig." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
