package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

public enum InventorsLang implements Lang {

    FIRST_CRAFT("§e§l[Inventors] §f%player% §fest le premier à crafter §e%item% §f!", "§e§l[Inventors] §f%player% §fis the first to craft §e%item%§f!"),
    MILESTONE_5("§e§l[Inventors] §f%player% §fa inventé 5 objets ! Récompense spéciale !", "§e§l[Inventors] §f%player% §fhas crafted 5 items! Special reward!"),
    MILESTONE_10("§e§l[Inventors] §f%player% §fa inventé 10 objets ! Maître inventeur !", "§e§l[Inventors] §f%player% §fhas crafted 10 items! Master inventor!"),
    INVENTORY_FULL("§e[Inventors] §fInventaire plein ! Bonus jeté au sol.", "§e[Inventors] §fInventory full! Bonus dropped."),
    BONUS_REWARD("§e[Inventors] §fBonus d'invention : §e%bonus% §f!", "§e[Inventors] §fCraft bonus: §e%bonus%§f!"),
    RESET_BROADCAST("§e[Inventors] §fToutes les inventions ont été réinitialisées !", "§e[Inventors] §fAll inventions have been reset!"),
    ;

    private final Map<String, String> translations;
    InventorsLang(String fr, String en) { this.translations = Map.of("fr_FR", fr, "en_US", en); }
    @Override public String getKey() { return "inventors." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
