package net.novaproject.novauhc.lang.ui;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

public enum LimiteStuffUiLang implements Lang {

    DIAMOND_NAME("§8┃ §f%main_color%Limite de pièce en Diamant", "§8┃ §f%main_color%Diamond Armor Limit"),
    DIAMOND_CURRENT(" §8» §fActuel : §3§l%value%", " §8» §fCurrent: §3§l%value%"),
    PROT_NAME("§8┃ §f%main_color%Limite de Protection en Diamant", "§8┃ §f%main_color%Diamond Protection Limit"),
    PROT_CURRENT(" §8» §fActuel : §3§l%value%", " §8» §fCurrent: §3§l%value%"),
    ENCHANT_CURRENT(" §8» §fActuel : §3§l%value%", " §8» §fCurrent: §3§l%value%"),
    PLUS_ONE("§8» §a§l+1", "§8» §a§l+1"),
    MINUS_ONE("§8» §c§l-1", "§8» §c§l-1"),
    DISABLED_VALUE("§cDésactivé", "§cDisabled"),
    DIAMOND_LIMIT_LABEL("§bDiamond Limit: %value%", "§bDiamond Limit: %value%"),
    ;

    private final Map<String, String> translations;
    LimiteStuffUiLang(String fr, String en) { this.translations = Map.of("fr_FR", fr, "en_US", en); }
    @Override public String getKey() { return "ui.limitestuff." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
