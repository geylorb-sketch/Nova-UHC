package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;


public enum LuckyOreLang implements Lang {

    LUCKY_FIND(
        "§6§l[LuckyOre] §f%player% §fa trouvé %reward% §fen minant du %ore% !",
        "§6§l[LuckyOre] §f%player% §ffound %reward% §fwhile mining %ore%!"
    ),
    LUCKY_PERSONAL(
        "§6§l[LuckyOre] §fVOUS AVEZ EU DE LA CHANCE !",
        "§6§l[LuckyOre] §fYOU GOT LUCKY!"
    ),
    INVENTORY_FULL(
        "§6[LuckyOre] §fInventaire plein ! L'objet a été jeté au sol.",
        "§6[LuckyOre] §fInventory full! The item was dropped on the ground."
    ),

    
    LUCKY_BROADCAST(
        "§6§l[LuckyOre] §f%player% a trouvé un minerai chanceux !",
        "§6§l[LuckyOre] §f%player% found a lucky ore!"
    ),
    ;

    private final Map<String, String> translations;

    LuckyOreLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_US", en);
    }

    @Override
    public String getKey() { return "luckyore." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}
