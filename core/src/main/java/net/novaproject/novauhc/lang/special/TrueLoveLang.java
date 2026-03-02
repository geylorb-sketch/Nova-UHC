package net.novaproject.novauhc.lang.special;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

public enum TrueLoveLang implements Lang {

    TRUE_LOVE_NAME("True Love", "True Love"),
    TRUE_LOVE_DESC(
            "Les équipes se forment automatiquement entre les premiers joueurs qui se voient.",
            "Teams form automatically between the first players who see each other."
    ),

    NO_TEAM_AVAILABLE(
            "§cAucune équipe disponible !",
            "§cNo team available!"
    ),
    PLAYERS_TOO_FAR(
            "§cLes joueurs ne sont plus disponibles ou trop éloignés !",
            "§cPlayers are no longer available or too far away!"
    ),
    TEAM_JOINED(
            "§aVous avez rejoint l'équipe §f%team% §aavec §f%count% §aautre(s) joueur(s) !",
            "§aYou joined team §f%team% §awith §f%count% §aother player(s)!"
    ),

    VAR_SIGHT_RADIUS_NAME("Rayon de détection", "Detection Radius"),
    VAR_SIGHT_RADIUS_DESC("Distance en blocs pour considérer deux joueurs comme se voyant.", "Distance in blocks to consider two players as seeing each other.");

    private final String fr;
    private final String en;

    TrueLoveLang(String fr, String en) {
        this.fr = fr;
        this.en = en;
    }

    @Override
    public String getKey() {
        return "truelove." + name();
    }

    @Override
    public Map<String, String> getTranslations() {
        return Map.of("fr_FR", fr, "en_EN", en);
    }
}