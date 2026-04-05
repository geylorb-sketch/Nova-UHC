package net.novaproject.novauhc.lang.ui;

import net.novaproject.novauhc.lang.Lang;

import java.util.Map;

public enum DefaultUiLang implements Lang {

    
    BORDER_ITEM_NAME("§8┃ §fGestion de la %main_color%bordure", "§8┃ §fBorder %main_color%management"),
    BORDER_ITEM_DESC("  §8┃ §fPermet de modifier la §ataille§f et la §bvitesse§f de la %main_color%bordure§f.", "  §8┃ §fModify the §asize§f and §bspeed§f of the %main_color%border§f."),
    SCENARIOS_ITEM_NAME("§8┃ §fGestion des %main_color%scénarios", "§8┃ §f%main_color%Scenario§f management"),
    SCENARIOS_ITEM_DESC("  §8┃ §fPermet d'§aajouter§f des scénarios qui dynamiseront la %main_color%partie§f.", "  §8┃ §fAdd scenarios to spice up the %main_color%game§f."),
    SPECIAL_ITEM_NAME("§8┃ §fMode de %main_color%jeu", "§8┃ §fGame %main_color%mode"),
    SPECIAL_ITEM_DESC("  §8┃ §fPermet de modifier les options %main_color%liées§f au mode de jeu §aactif§f.", "  §8┃ §fModify options %main_color%linked§f to the active game mode§f."),
    TEAM_ITEM_NAME("§8┃ §fGestion des %main_color%équipes", "§8┃ §f%main_color%Team§f management"),
    TEAM_ITEM_DESC("  §8┃ §fPermet de gérer les %main_color%options§f des équipes.", "  §8┃ §fManage %main_color%team§f options."),
    STOP_ITEM_NAME("§8┃ §c§lStopper §fle serveur", "§8┃ §c§lStop §fthe server"),
    STOP_ITEM_DESC("  §8┃ §fPermet de stopper le %main_color%serveur", "  §8┃ §fStop the %main_color%server"),
    SPEC_ITEM_NAME("§8┃ §fSpectateurs", "§8┃ §fSpectators"),
    SPEC_ITEM_STATUS(" §8» §fStatut §f: ", " §8» §fStatus §f: "),
    SPEC_ENABLED("§aActivé", "§aEnabled"),
    SPEC_DISABLED("§cDésactivé", "§cDisabled"),
    SPEC_ITEM_DESC("  §8┃ §fPermet d'§aaccepter§f ou §cnon§f les spectateurs dans la §cpartie§f.", "  §8┃ §fAllow or §cdisallow§f spectators in the §cgame§f."),
    OPTION_ITEM_NAME("§8┃ §fOptions de la %main_color%partie", "§8┃ §fGame %main_color%options"),
    OPTION_ITEM_DESC("  §8┃ §fPermet d'accéder aux %main_color%options§f/%main_color%règles§f de la partie", "  §8┃ §fAccess game %main_color%options§f/%main_color%rules§f"),
    PRECONF_ITEM_NAME("§8┃ §fPré-Config §f(%main_color%§lUHC§f)", "§8┃ §fPre-Config §f(%main_color%§lUHC§f)"),
    PRECONF_ITEM_DESC("  §8┃ §fPermet d'accéder à vos %main_color%configurations§f.", "  §8┃ §fAccess your %main_color%configurations§f."),
    WHITE_ITEM_NAME("§8┃ §fAccessibilité de la %main_color%partie", "§8┃ §fGame %main_color%accessibility"),
    WHITE_ITEM_DESC("  §8┃ §fPermet de %main_color%modifier§f l'accessibilité à la partie.", "  §8┃ §f%main_color%Modify§f game accessibility."),
    WORLD_ITEM_NAME("§8┃ §fMonde", "§8┃ §fWorld"),
    WORLD_ITEM_DESC("  §8┃ §fPermet de configurer les paramètres de la §2map§f.", "  §8┃ §fConfigure §2map§f parameters."),
    RULES_LOBBY_NAME("§8┃ §fTéléportation au §alobby", "§8┃ §fTeleport to §alobby"),
    RULES_RULES_NAME("§8┃ §fTéléportation à la §asalle des règles", "§8┃ §fTeleport to §arules room"),
    RULES_LOBBY_DESC("  §8┃ §fPermet de téléporter les §ajoueurs§f au point d'apparition§f.", "  §8┃ §fTeleport §aplayers§f to spawn§f."),
    RULES_RULES_DESC("  §8┃ §fPermet de téléporter les §ajoueurs§f dans la salle des règles§f.", "  §8┃ §fTeleport §aplayers§f to rules room§f."),
    SLOT_ITEM_NAME("§8┃ §fSlots", "§8┃ §fSlots"),
    SLOT_ITEM_DESC("  §8┃ §fVous permet de %main_color%modifier§fle nombre de %main_color%joueurs§f autorisés.", "  §8┃ §f%main_color%Modify§fthe number of %main_color%players§f allowed."),

    
    HEALTH_ITEM_NAME("§8┃ §fVie au-dessus des têtes", "§8┃ §fHealth above heads"),
    HEALTH_ITEM_STATUS(" §8» §fStatut §f: ", " §8» §fStatus §f: "),
    HEALTH_ITEM_DESC("  §8┃ §fAffiche la §cvie§f de chaque joueur en §e%§f au-dessus de sa tête.", "  §8┃ §fDisplays each player's §chealth§f as a §e%§f above their head."),
    KILLBOARD_ITEM_NAME("§8┃ §fClassement §6kills", "§8┃ §f§6Kill§f leaderboard"),
    KILLBOARD_ITEM_STATUS(" §8» §fStatut §f: ", " §8» §fStatus §f: "),
    KILLBOARD_ITEM_DESC("  §8┃ §fAlterne le scoreboard avec le §6classement§f des kills en temps réel.", "  §8┃ §fAlternates the scoreboard with the §6kill§f ranking in real time."),
    STOP_CONFIRM("Etes-vous sûr de vouloir stopper le serveur ?", "Are you sure you want to stop the server?"),
    SLOT_ITEM_DESC2("  §8┃ §fà se §aconnecter§f à la %main_color%partie§f.", "  §8┃ §fto §aconnect§f to the %main_color%game§f."),
    START_READY_NAME("§aLancer la partie", "§aStart the game"),
    START_READY_LORE1("§8➤ §fTout est §aprêt §f?", "§8➤ §fAll §aready§f?"),
    START_READY_LORE2("§7Permet de lancer la %main_color%partie§7.", "§7Start the %main_color%game§7."),
    START_CANCEL_NAME("§cAnnuler le lancement", "§cCancel launch"),
    START_CANCEL_LORE1("§8➤ §fPas sûr ? §cArrête§f !", "§8➤ §fNot sure? §cStop§f!"),
    START_CANCEL_LORE2("§7Permet d'arrêter la %main_color%partie§7.", "§7Stop the %main_color%game§7."),
    START_ACCESS("§8» §fAccès : §eHost", "§8» §fAccess: §eHost"),
    START_CLICK("§8» §fCliquez pour §aactiver§f.", "§8» §fClick to §aactivate§f."),
    ;

    private final Map<String, String> translations;

    DefaultUiLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_EN", en);
    }

    @Override public String getKey() { return "ui.default." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
