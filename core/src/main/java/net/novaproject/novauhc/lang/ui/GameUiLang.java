package net.novaproject.novauhc.lang.ui;

import net.novaproject.novauhc.lang.Lang;

import java.util.Map;

public enum GameUiLang implements Lang {

    POTION_ITEM_NAME("§8┃ §fLimite de §9§lpotions", "§8┃ §fPotion §9§llimit"),
    POTION_ITEM_DESC("  §8┃ §fVous permet de limiter la fabrication de certaines potions", "  §8┃ §fLimit the brewing of certain potions"),
    PVP_ITEM_NAME("§8┃ §fPvP (%main_color%%time%§f)", "§8┃ §fPvP (%main_color%%time%§f)"),
    PVP_ITEM_DESC("  §8┃ §fModifier le temps avant l'§aactivation§f du §ePvP§f.", "  §8┃ §fModify the time before §ePvP§f §aactivation§f."),
    BORDER_ITEM_NAME("§8┃ §fBordure (%main_color%%time%§f)", "§8┃ §fBorder (%main_color%%time%§f)"),
    BORDER_ITEM_DESC("  §8┃ §fModifier le temps avant l'§aactivation§f de la réduction de §ebordure§f.", "  §8┃ §fModify the time before §eborder§f §areduction§f."),
    DIAMS_ITEM_NAME("§8┃ §fLimite de §eDiamant§r (%main_color%%value%§f)", "§8┃ §fDiamond §elimit§r (%main_color%%value%§f)"),
    DIAMS_ITEM_DESC("  §8┃ §fVous permet de limiter le nombre de diamants minables.", "  §8┃ §fLimit the number of mineable diamonds."),
    ENCHANT_ITEM_NAME("§8┃ §fLimite d'§b§lenchantements", "§8┃ §fEnchantment §b§llimit"),
    ENCHANT_ITEM_DESC("  §8┃ §fDéfinir la limite de tous les enchantements.", "  §8┃ §fDefine the limit of all enchantments."),
    VERIF_ITEM_NAME("§8┃ §fVérifier les inventaires par défaut", "§8┃ §fCheck default inventories"),
    VERIF_ITEM_DESC("  §8┃ §fVérifier l'inventaire par %main_color%défaut§f donné en §6§ldébut §fde partie ou à la §8§lmort§f.", "  §8┃ §fCheck the %main_color%default§f inventory given at §6§lgame start§f or on §8§ldeath§f."),
    START_INV_NAME("§8┃ §fInventaire de%main_color% départ", "§8┃ §fStart%main_color% inventory"),
    START_INV_DESC("  §8┃ §fDéfinir l'inventaire par défaut donné en début de partie.", "  §8┃ §fDefine the default inventory given at game start."),
    DEATH_INV_NAME("§8┃ §fInventaire de %main_color%mort", "§8┃ §f%main_color%Death§f inventory"),
    DEATH_INV_DESC("  §8┃ §fDéfinir l'inventaire de %main_color%mort§f donné lors d'une %main_color%mort§f.", "  §8┃ §fDefine the %main_color%death§f inventory given on %main_color%death§f."),
    DROP_ITEM_NAME("§8┃ §fTaux de §7§ldrop", "§8┃ §7§lDrop§f rate"),
    DROP_ITEM_DESC("  §8┃ §fModifier les taux de drop de certains objets.", "  §8┃ §fModify drop rates of certain items."),
    VERIF_START_DESC("  §8┃ §fVous permet de §5vérifier§f l'inventaire par défaut donné en début de partie.", "  §8┃ §f§5Verify§f the default inventory given at game start."),
    VERIF_DEATH_DESC("  §8┃ §fVous permet de §5vérifier§f l'inventaire de %main_color%mort§f donné lors d'une %main_color%mort§f.", "  §8┃ §f§5Verify§f the %main_color%death§f inventory given on %main_color%death§f."),
    ;

    private final Map<String, String> translations;

    GameUiLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_US", en);
    }

    @Override public String getKey() { return "ui.game." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
