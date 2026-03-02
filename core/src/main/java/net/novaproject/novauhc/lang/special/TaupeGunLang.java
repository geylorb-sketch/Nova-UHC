package net.novaproject.novauhc.lang.special;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

public enum TaupeGunLang implements Lang {

    TAUPE_GUN_NAME("TaupeGun", "TaupeGun"),
    TAUPE_GUN_DESC(
            "Certains joueurs deviennent des taupes secrètes avec des kits spéciaux.",
            "Some players become secret moles with special kits."
    ),

    HELP_MESSAGE(
            "§5Utilisation de la commande :\n" +
                    "/t tc : Envoie vos coordonnées à votre équipe normale.\n" +
                    "/t ti : Ouvrir l'inventaire de l'équipe normale.\n" +
                    "/t kit : Récupérer votre kit.\n" +
                    "/t reveal : Vous révéler en tant que Taupe.\n" +
                    "/tc : Envoyer vos coordonnées à votre équipe taupe.\n" +
                    "/ti : Accéder au TI de votre équipe taupe.",
            "§5Command usage:\n" +
                    "/t tc: Send your coordinates to your normal team.\n" +
                    "/t ti: Open your normal team inventory.\n" +
                    "/t kit: Retrieve your kit.\n" +
                    "/t reveal: Reveal yourself as a Mole.\n" +
                    "/tc: Send coordinates to your mole team.\n" +
                    "/ti: Access your mole team inventory."
    ),
    NOT_TAUPE_COMMAND_ERROR(
            "§cVous devez être une taupe pour utiliser cette commande.",
            "§cYou must be a mole to use this command."
    ),
    NOT_TAUPE_ERROR(
            "§cVous n'êtes pas une taupe.",
            "§cYou are not a mole."
    ),
    UNKNOWN_COMMAND(
            "§cCommande inconnue. Utilisez /t pour voir l'aide.",
            "§cUnknown command. Use /t to see help."
    ),
    KIT_ALREADY_CLAIMED(
            "§cTu as déjà récupéré ton kit !",
            "§cYou already claimed your kit!"
    ),
    KIT_RECEIVED(
            "§aVotre kit a bien été reçu !",
            "§aYour kit has been received!"
    ),

    REVEAL_SUCCESS(
            "§c%player% §7s'est révélé en tant que Taupe !",
            "§c%player% §7has revealed themselves as a Mole!"
    ),
    REVEAL_NOT_TAUPE(
            "§cVous n'êtes pas une taupe, vous ne pouvez pas vous révéler.",
            "§cYou are not a mole, you cannot reveal yourself."
    ),

    TAUPE_ASSIGNED_TITLE("§c§lTAUPE", "§c§lMOLE"),
    TAUPE_ASSIGNED_SUBTITLE(
            "§7Vous êtes une taupe secrète !",
            "§7You are a secret mole!"
    ),

    TAUPE_COORDS_FORMAT(
            "§8[§cTaupe§8] §f%player% §7— %co%",
            "§8[§cMole§8] §f%player% §7— %co%"
    ),
    TEAM_COORDS_FORMAT(
            "§8[§bÉquipe§8] §f%player% §7— %co%",
            "§8[§bTeam§8] §f%player% §7— %co%"
    ),
    TAUPE_CHAT_FORMAT(
            "§8[§cTaupe§8] §c%player% §8» §f%message%",
            "§8[§cMole§8] §c%player% §8» §f%message%"
    ),
    TEAM_CHAT_FORMAT(
            "§8[§bÉquipe§8] §b%player% §8» §f%message%",
            "§8[§bTeam§8] §b%player% §8» §f%message%"
    ),

    KIT_DESCRIPTION_0(
            "§6Kit Archer §7: Punch 1, Power 3, 64 flèches, 3 fils.",
            "§6Archer Kit §7: Punch 1, Power 3, 64 arrows, 3 strings."
    ),
    KIT_DESCRIPTION_1(
            "§6Kit Mobilité §7: 4 perles de l'Ender, Chute Plume 4.",
            "§6Mobility Kit §7: 4 Ender Pearls, Feather Falling 4."
    ),
    KIT_DESCRIPTION_2(
            "§6Kit Potions §7: Speed 1, Résistance au Feu 1, Poison 1.",
            "§6Potions Kit §7: Speed 1, Fire Resistance 1, Poison 1."
    ),
    KIT_DESCRIPTION_3(
            "§6Kit Combat §7: Protection 3, Tranchant 3, Puissance 3.",
            "§6Combat Kit §7: Protection 3, Sharpness 3, Power 3."
    ),
    KIT_DESCRIPTION_4(
            "§6Kit Ressources §7: 14 obsidienne, 10 diamants, 32 or, 64 fer.",
            "§6Resources Kit §7: 14 obsidian, 10 diamonds, 32 gold, 64 iron."
    ),
    KIT_DESCRIPTION_5(
            "§6Kit Feu §7: Aspect du Feu 3, Flèches Enflammées 1.",
            "§6Fire Kit §7: Fire Aspect 3, Flame 1."
    ),
    KIT_DESCRIPTION_6(
            "§6Kit Discret §7: Invisibilité 2 (1min), Force 1 (8min).",
            "§6Stealth Kit §7: Invisibility 2 (1min), Strength 1 (8min)."
    ),

    VAR_MOLE_COUNT_NAME("Nombre de Taupes par Équipe", "Moles per Team"),
    VAR_MOLE_COUNT_DESC("Nombre de joueurs désignés taupes par équipe normale.", "Number of players designated as moles per normal team."),

    VAR_MOLE_TEAM_SIZE_NAME("Taille des Équipes Taupe", "Mole Team Size"),
    VAR_MOLE_TEAM_SIZE_DESC("Nombre de joueurs maximum par équipe taupe.", "Maximum number of players per mole team."),

    UI_TITLE("§5TaupeGun", "§5TaupeGun"),
    UI_MOLE_COUNT_NAME("§6Nombre de Taupes par Équipe", "§6Moles per Team"),
    UI_MOLE_COUNT_LORE(
            "\n §7► Clic gauche pour §aaugmenter\n §7► Clic droit pour §cdiminuer\n §7Nombre : §b%value%\n",
            "\n §7► Left click to §aincrease\n §7► Right click to §cdecrease\n §7Count: §b%value%\n"
    ),
    UI_MOLE_TEAM_SIZE_NAME("§6Taille des Équipes Taupe", "§6Mole Team Size"),
    UI_MOLE_TEAM_SIZE_LORE(
            "\n §7► Clic gauche pour §aaugmenter\n §7► Clic droit pour §cdiminuer\n §7Taille : §b%value%\n",
            "\n §7► Left click to §aincrease\n §7► Right click to §cdecrease\n §7Size: §b%value%\n"
    );

    private final String fr;
    private final String en;

    TaupeGunLang(String fr, String en) {
        this.fr = fr;
        this.en = en;
    }

    @Override
    public String getKey() {
        return "taupegun." + name();
    }

    @Override
    public Map<String, String> getTranslations() {
        return Map.of("fr_FR", fr, "en_EN", en);
    }
}