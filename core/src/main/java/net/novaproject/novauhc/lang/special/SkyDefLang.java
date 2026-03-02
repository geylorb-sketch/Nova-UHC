package net.novaproject.novauhc.lang.special;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

public enum SkyDefLang implements Lang {

    SKYDEF_NAME("SkyDef", "SkyDef"),
    SKYDEF_DESC(
            "Défendez ou détruisez la bannière pour remporter la partie.",
            "Defend or destroy the banner to win the game."
    ),
    BANNER_ZONE_PROTECTED(
            "§cCette zone autour de la bannière est protégée !",
            "§cThis area around the banner is protected!"
    ),
    DEFENDERS_NOT_DEAD(
            "§cLa team des défenseurs n'est pas encore morte.",
            "§cThe defenders team is not dead yet."
    ),
    BANNER_BROKEN_BROADCAST(
            "§eBien joué à l'équipe %team% composée de : %players%",
            "§eWell played to team %team% composed of: %players%"
    ),
    BANNER_PLACE_FORBIDDEN(
            "§cTu ne peux rien poser dans une sphère de %radius% blocs autour de la bannière !",
            "§cYou cannot place anything within a sphere of %radius% blocks around the banner!"
    ),


    SKYDEF_UI_TITLE("§b§l SkyDef", "§b§l SkyDef"),

    SKYDEF_UI_DEF_TEAM_SIZE_LORE(
            "\n  §8┃ §fVous permet de %main_color%modifier\n  §8┃ §fle nombre de %main_color%joueurs§f\n  §8┃ §fdans l'equipe de §bDéfenseur§f\n",
            "\n  §8┃ §fAllows you to %main_color%modify\n  §8┃ §fthe number of %main_color%players§f\n  §8┃ §fin the §bDefender§f team\n"
    ),
    VAR_COOLDOWN_TIME_NAME("Temps de Cooldown TP", "TP Cooldown Time"),
    VAR_COOLDOWN_TIME_DESC("Durée du cooldown en secondes entre deux téléportations.", "Cooldown duration in seconds between two teleportations."),

    VAR_TEAM_SIZE_NAME("Taille de l'équipe Défenseur", "Defender Team Size"),
    VAR_TEAM_SIZE_DESC("Nombre de joueurs dans l'équipe des défenseurs.", "Number of players in the defender team."),

    VAR_TP_RADIUS_NAME("Rayon de Téléportation", "Teleportation Radius"),
    VAR_TP_RADIUS_DESC("Rayon en blocs autour du point de TP pour déclencher la téléportation.", "Radius in blocks around the TP point to trigger teleportation."),

    VAR_BANNER_PLACE_RADIUS_NAME("Rayon de Protection Bannière", "Banner Protection Radius"),
    VAR_BANNER_PLACE_RADIUS_DESC("Rayon en blocs autour de la bannière où le placement de blocs est interdit.", "Radius in blocks around the banner where block placement is forbidden."),

    VAR_ARMOR_ENCHANT_LEVEL_NAME("Niveau d'Enchantement Armure", "Armor Enchantment Level"),
    VAR_ARMOR_ENCHANT_LEVEL_DESC("Niveau de l'enchantement Protection sur l'armure des défenseurs.", "Protection enchantment level on defenders' armor."),

    VAR_GOLDEN_CARROT_AMOUNT_NAME("Quantité de Carottes Dorées", "Golden Carrot Amount"),
    VAR_GOLDEN_CARROT_AMOUNT_DESC("Nombre de carottes dorées données aux défenseurs au démarrage.", "Amount of golden carrots given to defenders on start."),

    VAR_BOOK_AMOUNT_NAME("Quantité de Livres", "Book Amount"),
    VAR_BOOK_AMOUNT_DESC("Nombre de livres donnés aux défenseurs au démarrage.", "Amount of books given to defenders on start.");

    private final String fr;
    private final String en;

    SkyDefLang(String fr, String en) {
        this.fr = fr;
        this.en = en;
    }

    @Override
    public String getKey() {
        return "skydef." + name();
    }

    @Override
    public Map<String, String> getTranslations() {
        return Map.of("fr_FR", fr, "en_EN", en);
    }
}