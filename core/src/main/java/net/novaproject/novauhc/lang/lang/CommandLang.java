package net.novaproject.novauhc.lang.lang;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

public enum CommandLang implements Lang {

    PLAYERS_ONLY("Cette commande est réservée aux joueurs !", "This command is for players only!"),
    NO_PERMISSION("§cVous n'avez pas la permission d'utiliser cette commande.", "§cYou don't have permission to use this command."),
    FORCE_PVP("%infotag%Le PvP vient d'être forcé.", "%infotag%PvP has been forced."),
    FORCE_MEETUP("%infotag%Le meetup vient d'être forcé.", "%infotag%Meetup has been forced."),
    WHITELIST_USAGE("/h whitelist add/remove <target>", "/h whitelist add/remove <target>"),
    WHITELIST_LIST_USAGE("/h whitelist list/clear/on/off", "/h whitelist list/clear/on/off"),
    UNKNOWN_CMD("§cCommande inconnue. Essayez /h pour plus d'informations.", "§cUnknown command. Try /h for more information."),
    INVALID_PLAYER("§cNom du joueur invalide", "§cInvalid player name"),
    FORCETEAM_USAGE("§cUsage: /h forceteam <joueur> <team>", "§cUsage: /h forceteam <player> <team>"),
    PLAYER_NOT_FOUND("§cLe joueur spécifié est introuvable !", "§cSpecified player not found!"),
    PLAYER_NOT_PLAYING("§cLe joueur spécifié n'est pas dans la partie !", "§cSpecified player is not in the game!"),
    TEAM_NOT_FOUND("§cCette équipe n'existe pas !", "§cThis team does not exist!"),
    FORCETEAM_SUCCESS("§aLe joueur %player% a été ajouté à l'équipe %team% !", "§aPlayer %player% has been added to team %team%!"),
    STUFF_DELETED("§cLe stuff a été supprimé.", "§cInventory has been deleted."),
    REP_USAGE("§cUsage: /r <message>", "§cUsage: /r <message>"),
    REP_NO_TARGET("§cPersonne à qui répondre.", "§cNo one to reply to."),
    REP_OFFLINE("§cCe joueur est hors ligne.", "§cThis player is offline."),
    REP_SENT("§8│ §7§lMoi §7→ §7§l%target% §f%message%", "§8│ §7§lMe §7→ §7§l%target% §f%message%"),
    REP_RECEIVED("§8│ §7§l%sender% §7→ Moi §f%message%", "§8│ §7§l%sender% §7→ Me §f%message%"),
    TEAMCORD_NO_TEAM("§cVous n'avez pas d'équipe ou la partie n'a pas commencé !", "§cYou don't have a team or the game hasn't started!"),
    TEAMCORD_FORMAT("§7[§6%team%§7] §f%player% §8» §f%coords%", "§7[§6%team%§7] §f%player% §8» §f%coords%"),
    ;

    private final Map<String, String> translations;
    CommandLang(String fr, String en) { this.translations = Map.of("fr_FR", fr, "en_EN", en); }
    @Override public String getKey() { return "cmd." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
