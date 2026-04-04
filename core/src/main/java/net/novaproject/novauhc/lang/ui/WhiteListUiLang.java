package net.novaproject.novauhc.lang.ui;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

public enum WhiteListUiLang implements Lang {
    DISABLE_BUTTON("§cDésactiver la whitelist", "§cDisable whitelist"),
    ENABLE_BUTTON("§aActiver la whitelist", "§aEnable whitelist"),
    ADD_PLAYER_BUTTON("§2Ajouter un joueur", "§2Add a player"),
    ADD_PLAYER_ANVIL_HINT("Nom du joueur", "Player name"),
    CONFIRM_REMOVE("§cÊtes-vous sûr de vouloir retirer %player% de la Whitelist ?", "§cAre you sure you want to remove %player% from the Whitelist?"),
    CLOUD_ITEM_NAME("§8┃ §fModifier l'affichage %main_color%Lobby", "§8┃ §fModify %main_color%Lobby§f display"),
    CLOUD_ITEM_DESC("  §8┃ §fPermet de modifier l'%main_color%affichage§f de la partie dans les §6§lLobby§f.", "  §8┃ §fModify the game's %main_color%display§f in §6§lLobbies§f."),
    ;

    private final Map<String, String> translations;
    WhiteListUiLang(String fr, String en) { this.translations = Map.of("fr_FR", fr, "en_EN", en); }
    @Override public String getKey() { return "ui.whitelist." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
