package net.novaproject.novauhc.lang.ui;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

public enum DeathNoteConfigUiLang implements Lang {

    
    HEADER_NAME("§c§lConfiguration Death Note UHC", "§c§lDeath Note UHC Configuration"),
    HEADER_LORE1("§7Configurez tous les paramètres du mode", "§7Configure all mode parameters"),
    HEADER_LORE2("§7Clic gauche : Augmenter", "§7Left click: Increase"),
    HEADER_LORE3("§7Clic droit : Diminuer", "§7Right click: Decrease"),

    
    DEATH_TIMER_NAME("§c§lTimer de Mort", "§c§lDeath Timer"),
    DEATH_TIMER_LORE1("§7Temps avant la mort par Death Note", "§7Time before death by Death Note"),
    DEATH_TIMER_LORE2("§7Valeur actuelle : §e%value% secondes", "§7Current value: §e%value% seconds"),
    DEATH_TIMER_LORE3("§7Clic gauche : +5 secondes", "§7Left click: +5 seconds"),
    DEATH_TIMER_LORE4("§7Clic droit : -5 secondes", "§7Right click: -5 seconds"),
    DEATH_TIMER_LORE5("§7Range : 10-120 secondes", "§7Range: 10-120 seconds"),

    
    COOLDOWN_NAME("§6§lCooldown Death Note", "§6§lDeath Note Cooldown"),
    COOLDOWN_LORE1("§7Temps d'attente entre utilisations", "§7Wait time between uses"),
    COOLDOWN_LORE2("§7Valeur actuelle : §e%value% minutes", "§7Current value: §e%value% minutes"),
    COOLDOWN_LORE3("§7Clic gauche : +1 minute", "§7Left click: +1 minute"),
    COOLDOWN_LORE4("§7Clic droit : -1 minute", "§7Right click: -1 minute"),
    COOLDOWN_LORE5("§7Range : 1-30 minutes", "§7Range: 1-30 minutes"),

    
    EP2_DAMAGE_NAME("§4§lDégâts Épisode 2", "§4§lEpisode 2 Damage"),
    EP2_DAMAGE_LORE1("§7Dégâts infligés à l'épisode 2", "§7Damage dealt at episode 2"),
    EP2_DAMAGE_LORE2("§7Valeur actuelle : §c%value% cœurs", "§7Current value: §c%value% hearts"),
    EP2_DAMAGE_LORE3("§7Clic gauche : +1 cœur", "§7Left click: +1 heart"),
    EP2_DAMAGE_LORE4("§7Clic droit : -1 cœur", "§7Right click: -1 heart"),
    EP2_DAMAGE_LORE5("§7Range : 1-10 cœurs", "§7Range: 1-10 hearts"),

    
    EP_DURATION_NAME("§3§lDurée des Épisodes", "§3§lEpisode Duration"),
    EP_DURATION_LORE1("§7Durée de chaque épisode", "§7Duration of each episode"),
    EP_DURATION_LORE2("§7Valeur actuelle : §e%value% minutes", "§7Current value: §e%value% minutes"),
    EP_DURATION_LORE3("§7Clic gauche : +5 minutes", "§7Left click: +5 minutes"),
    EP_DURATION_LORE4("§7Clic droit : -5 minutes", "§7Right click: -5 minutes"),
    EP_DURATION_LORE5("§7Range : 10-60 minutes", "§7Range: 10-60 minutes"),

    
    HEART_REGEN_NAME("§a§lRégénération de Vie", "§a§lLife Regeneration"),
    HEART_REGEN_LORE1("§7Cœurs régénérés par épisode", "§7Hearts regenerated per episode"),
    HEART_REGEN_LORE2("§7Valeur actuelle : §c%value% cœur(s)", "§7Current value: §c%value% heart(s)"),
    HEART_REGEN_LORE3("§7Clic gauche : +1 cœur", "§7Left click: +1 heart"),
    HEART_REGEN_LORE4("§7Clic droit : -1 cœur", "§7Right click: -1 heart"),
    HEART_REGEN_LORE5("§7Range : 0-5 cœurs", "§7Range: 0-5 hearts"),

    
    KIRA_COUNT_NAME("§c§lNombre de Kira", "§c§lNumber of Kira"),
    KIRA_COUNT_LORE1("§7Nombre de Kira par partie", "§7Number of Kira per game"),
    KIRA_COUNT_LORE2("§7Valeur actuelle : §e%value%", "§7Current value: §e%value%"),
    KIRA_COUNT_LORE3("§7Clic gauche : +1", "§7Left click: +1"),
    KIRA_COUNT_LORE4("§7Clic droit : -1", "§7Right click: -1"),
    KIRA_COUNT_LORE5("§7Range : 1-5", "§7Range: 1-5"),

    
    ENQUETEUR_COUNT_NAME("§a§lNombre d'Enquêteurs", "§a§lNumber of Investigators"),
    ENQUETEUR_COUNT_LORE1("§7Nombre d'Enquêteurs par partie", "§7Number of investigators per game"),
    ENQUETEUR_COUNT_LORE2("§7Valeur actuelle : §e%value%", "§7Current value: §e%value%"),
    ENQUETEUR_COUNT_LORE3("§7Clic gauche : +1", "§7Left click: +1"),
    ENQUETEUR_COUNT_LORE4("§7Clic droit : -1", "§7Right click: -1"),
    ENQUETEUR_COUNT_LORE5("§7Range : 0-3", "§7Range: 0-3"),

    
    AUTO_SCALING_NAME("§e§lAttribution Automatique", "§e§lAutomatic Assignment"),
    AUTO_SCALING_LORE1("§7Ajuste automatiquement les rôles", "§7Automatically adjusts roles"),
    AUTO_SCALING_LORE2("§7selon le nombre de joueurs", "§7based on player count"),
    AUTO_SCALING_ENABLED("§7État : §aActivé", "§7State: §aEnabled"),
    AUTO_SCALING_DISABLED("§7État : §cDésactivé", "§7State: §cDisabled"),

    
    KIRA_CHAT_NAME("§c§lChat Kira", "§c§lKira Chat"),
    KIRA_CHAT_LORE1("§7Active le chat privé entre Kira", "§7Enables private chat between Kira"),
    KIRA_CHAT_LORE2("§7Commande : /k <message>", "§7Command: /k <message>"),
    KIRA_CHAT_ENABLED("§7État : §aActivé", "§7State: §aEnabled"),
    KIRA_CHAT_DISABLED("§7État : §cDésactivé", "§7State: §cDisabled"),

    
    DEBUG_NAME("§7§lMode Debug", "§7§lDebug Mode"),
    DEBUG_LORE1("§7Active les messages de debug", "§7Enables debug messages"),
    DEBUG_LORE2("§7pour les développeurs", "§7for developers"),
    DEBUG_ENABLED("§7État : §aActivé", "§7State: §aEnabled"),
    DEBUG_DISABLED("§7État : §cDésactivé", "§7State: §cDisabled"),
    ;

    private final Map<String, String> translations;
    DeathNoteConfigUiLang(String fr, String en) { this.translations = Map.of("fr_FR", fr, "en_EN", en); }
    @Override public String getKey() { return "ui.deathnote." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
