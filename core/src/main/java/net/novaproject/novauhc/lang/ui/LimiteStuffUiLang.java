package net.novaproject.novauhc.lang.ui;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

public enum LimiteStuffUiLang implements Lang {

    DIAMOND_NAME("§8┃ §f%main_color%Armure en Diamant", "§8┃ §f%main_color%Diamond Armor"),
    DIAMOND_CURRENT(" §8» §fActuel §8: §3%value%§7/§34", " §8» §fCurrent§8: §3%value%§7/§34"),
    PROT_NAME("§8┃ §f%main_color%Protection", "§8┃ §f%main_color%Protection"),
    PROT_CURRENT(" §8» §fActuel §8: §3%value%§7/§34", " §8» §fCurrent§8: §3%value%§7/§34"),
    ENCHANT_CURRENT(" §8» §fActuel §8: §3%value%", " §8» §fCurrent§8: §3%value%"),
    PLUS_ONE("§a+1", "§a+1"),
    MINUS_ONE("§c-1", "§c-1"),
    DISABLED_VALUE("§cDésactivé", "§cDisabled"),
    DIAMOND_LIMIT_LABEL("§8┃ §f%main_color%Limite §eDiamants §7(%value%)", "§8┃ §f%main_color%Diamond §eLimit §7(%value%)"),
    FORCE_ITEM_NAME("§8┃ §fForce §7( §e%value%%§7)", "§8┃ §fStrength §7( §e%value%%§7)"),
    FORCE_ITEM_DESC("  §8┃ §fMultiplicateur de §6force§f appliqué", "  §8┃ §6Strength§f multiplier applied"),
    RESI_ITEM_NAME("§8┃ §fRésistance §7( §e%value%%§7)", "§8┃ §fResistance §7( §e%value%%§7)"),
    RESI_ITEM_DESC("  §8┃ §fMultiplicateur de §brésistance§f appliqué", "  §8┃ §bResistance§f multiplier applied"),
    CRIT_ITEM_NAME("§8┃ §fCritiques §7( §e%value%%§7)", "§8┃ §fCriticals §7( §e%value%%§7)"),
    CRIT_ITEM_DESC("  §8┃ §fMultiplicateur des §ccritiques§f appliqué", "  §8┃ §Ccritical§f multiplier applied"),
    ;

    private final Map<String, String> translations;
    LimiteStuffUiLang(String fr, String en) { this.translations = Map.of("fr_FR", fr, "en_EN", en); }
    @Override public String getKey() { return "ui.limitestuff." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
