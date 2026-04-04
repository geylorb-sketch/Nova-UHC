package net.novaproject.novauhc.lang.special;

import net.novaproject.novauhc.lang.Lang;

import java.util.Map;


public enum KingLang implements Lang {

    YOU_ARE_KING(
        "§6§l♚ King §r§aVous êtes le Roi de votre équipe ! Vos coéquipiers doivent vous protéger.",
        "§6§l♚ King §r§aYou are the King of your team! Your teammates must protect you."
    ),
    TEAM_KING_ANNOUNCE(
        "§6§l♚ King §r§e%king% §fest le Roi de votre équipe. Protégez-le !",
        "§6§l♚ King §r§e%king% §fis your team's King. Protect them!"
    ),
    KING_DIED(
        "§6§l♚ King §r§c☠ Votre Roi est mort ! Poison infligé à toute l'équipe.",
        "§6§l♚ King §r§c☠ Your King has fallen! Poison inflicted on the whole team."
    ),

    ;

    private final Map<String, String> translations;

    KingLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_EN", en);
    }

    @Override
    public String getKey() { return "king." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}