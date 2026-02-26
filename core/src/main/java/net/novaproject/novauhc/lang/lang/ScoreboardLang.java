package net.novaproject.novauhc.lang.lang;

import net.novaproject.novauhc.lang.Lang;

import java.util.Map;

public enum ScoreboardLang implements Lang {

    // ─── Tab ─────────────────────────────────────────────────────────────────
    TAB_LOBBY_HEADER(
            "§3»§b»§f» %main_color%NOVA §f§lUHC §f«§b«§3«\n\n§8│ §7Joueurs: §f%players% §8| §7Serveur: §fUHC §8│\n",
            "§3»§b»§f» %main_color%NOVA §f§lUHC §f«§b«§3«\n\n§8│ §7Players: §f%players% §8| §7Server: §fUHC §8│\n"),
    TAB_LOBBY_FOOTER(
            "\n§8● §9/§fdiscord §8● §3/§fdoc",
            "\n§8● §9/§fdiscord §8● §3/§fdoc"),

    TAB_GAME_HEADER(
            "§3»§b»§f» %main_color%NOVA §f§lUHC §f«§b«§3«\n\n§8│ §7Joueurs: §f%players% §8| §7Serveur: §fUHC §8│\n",
            "§3»§b»§f» %main_color%NOVA §f§lUHC §f«§b«§3«\n\n§8│ §7Players: §f%players% §8| §7Server: §fUHC §8│\n"),
    TAB_GAME_FOOTER(
            "\n§8● §9/§fdiscord §8● §3/§fdoc\n§cKills: §f%kills%",
            "\n§8● §9/§fdiscord §8● §3/§fdoc\n§cKills: §f%kills%"),

    TAB_END_HEADER(
            "§3»§b»§f» %main_color%NOVA §f§lUHC §f«§b«§3«\n\n§8│ §7Joueurs: §f%players% §8| §7Serveur: §fUHC §8│\n",
            "§3»§b»§f» %main_color%NOVA §f§lUHC §f«§b«§3«\n\n§8│ §7Players: §f%players% §8| §7Server: §fUHC §8│\n"),
    TAB_END_FOOTER(
            "\n§8● §9/§fdiscord §8● §3/§fdoc\n§cKills: §f%kills%\n%main_color%✦ Merci d'avoir participé ! ✦",
            "\n§8● §9/§fdiscord §8● §3/§fdoc\n§cKills: §f%kills%\n%main_color%✦ Thanks for playing! ✦"),

    // ─── Scoreboard LOBBY ────────────────────────────────────────────────────
    SB_LOBBY_TITLE("%main_color%§lNova UHC", "%main_color%§lNova UHC"),
    SB_LOBBY_L1("", ""),
    SB_LOBBY_L2("§f §5│ §fConnecté(s): §3%players%§f/%slot%", "§f §5│ §fConnected: §3%players%§f/%slot%"),
    SB_LOBBY_L3("§f §5│ §fHost: §3%host%", "§f §5│ §fHost: §3%host%"),
    SB_LOBBY_L4("", ""),
    SB_LOBBY_L5("<ip>", "<ip>"),

    // ─── Scoreboard GAME ─────────────────────────────────────────────────────
    SB_GAME_TITLE("§f- %main_color%§lNova UHC §f-", "§f- %main_color%§lNova UHC §f-"),
    SB_GAME_L1("", ""),
    SB_GAME_L2("§f §5│ §fConnecté(s): §3%players%§f/%slot%", "§f §5│ §fConnected: §3%players%§f/%slot%"),
    SB_GAME_L3("§f §5│ §fDurée: §3%timer%", "§f §5│ §fDuration: §3%timer%"),
    SB_GAME_L4("", ""),
    SB_GAME_L5("§f §6│ §fPartie : ", "§f §6│ §fGame: "),
    SB_GAME_L6("§f  §8● §fTeam: %main_color%%team%", "§f  §8● §fTeam: %main_color%%team%"),
    SB_GAME_L7("§f  §8● §fKill : %main_color%%kills%", "§f  §8● §fKills: %main_color%%kills%"),
    SB_GAME_L8("", ""),
    SB_GAME_L9("§f  §8● §fBordure: %main_color%%border%", "§f  §8● §fBorder: %main_color%%border%"),
    SB_GAME_L10("§f  §8● §fCentre: %main_color%%distance% %arrow%", "§f  §8● §fCenter: %main_color%%distance% %arrow%"),
    SB_GAME_L11("", ""),
    SB_GAME_L12("<ip>", "<ip>"),

    // ─── Scoreboard GAME (rôle) ───────────────────────────────────────────────
    SB_GAME_ROLE_TITLE("§f- %main_color%§lNova UHC §f-", "§f- %main_color%§lNova UHC §f-"),
    SB_GAME_ROLE_L1("", ""),
    SB_GAME_ROLE_L2("§f §5│ §fConnecté(s): §3%players%§f/%slot%", "§f §5│ §fConnected: §3%players%§f/%slot%"),
    SB_GAME_ROLE_L3("§f §5│ §fDurée: §3%timer%", "§f §5│ §fDuration: §3%timer%"),
    SB_GAME_ROLE_L4("", ""),
    SB_GAME_ROLE_L5("§f §6│ §fPartie :", "§f §6│ §fGame:"),
    SB_GAME_ROLE_L6("§f  §8● §fRôle: %main_color%%role%", "§f  §8● §fRole: %main_color%%role%"),
    SB_GAME_ROLE_L7("§f  §8● §fKill : %main_color%%kills%", "§f  §8● §fKills: %main_color%%kills%"),
    SB_GAME_ROLE_L8("", ""),
    SB_GAME_ROLE_L9("§f  §8● §fBordure: %main_color%%border%", "§f  §8● §fBorder: %main_color%%border%"),
    SB_GAME_ROLE_L10("§f  §8● §fCentre: %main_color%%distance%", "§f  §8● §fCenter: %main_color%%distance%"),
    SB_GAME_ROLE_L11("§f  §8● §fDirection: %main_color%%arrow%", "§f  §8● §fDirection: %main_color%%arrow%"),
    SB_GAME_ROLE_L12("", ""),
    SB_GAME_ROLE_L13("<ip>", "<ip>"),

    // ─── Scoreboard END ───────────────────────────────────────────────────────
    SB_END_TITLE("%main_color%§lNova UHC", "%main_color%§lNova UHC"),
    SB_END_L1("", ""),
    SB_END_L2("§f §5│ §fConnecté(s): §3%players%§f/%slot%", "§f §5│ §fConnected: §3%players%§f/%slot%"),
    SB_END_L3("§f §5│ §fDurée: §3%timer%", "§f §5│ §fDuration: §3%timer%"),
    SB_END_L4("", ""),
    SB_END_L5("§f §6│ §fPartie : ", "§f §6│ §fGame: "),
    SB_END_L6("§f  §8● §fRôle: %main_color%%role%", "§f  §8● §fRole: %main_color%%role%"),
    SB_END_L7("§f  §8● §fKill : %main_color%%kills%", "§f  §8● §fKills: %main_color%%kills%"),
    SB_END_L8("", ""),
    SB_END_L9("<ip>", "<ip>"),
    ;

    private final Map<String, String> translations;

    ScoreboardLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_US", en);
    }

    @Override public String getKey() { return "scoreboard." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
