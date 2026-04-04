package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;


public enum LootCrateLang implements Lang {

    CRATES_SPAWNED(
        "§d§l[LootCrate] §f%count% coffres de loot sont apparus !",
        "§d§l[LootCrate] §f%count% loot crates have appeared!"
    ),
    CRATES_ANNOUNCEMENT(
        "§d[LootCrate] §fCourrez les récupérer avant les autres !",
        "§d[LootCrate] §fRush to collect them before others!"
    ),
    CRATE_NEARBY(
        "§d[LootCrate] §fUn coffre de loot est apparu près de vous ! (%distance% blocs)",
        "§d[LootCrate] §fA loot crate appeared near you! (%distance% blocks)"
    ),
    CRATES_WARNING_1MIN(
        "§d[LootCrate] §fNouveaux coffres dans 1 minute !",
        "§d[LootCrate] §fNew crates in 1 minute!"
    ),
    CRATES_WARNING_10SEC(
        "§d[LootCrate] §fNouveaux coffres dans 10 secondes !",
        "§d[LootCrate] §fNew crates in 10 seconds!"
    ),
    CRATES_FORCED(
        "§d[LootCrate] §fCoffres forcés par un administrateur !",
        "§d[LootCrate] §fCrates forced by an administrator!"
    ),

    
    CRATE_SPAWNED(
        "§d[LootCrate] §fUn coffre de loot est apparu près de vous !",
        "§d[LootCrate] §fA loot crate has spawned near you!"
    ),
    ;

    private final Map<String, String> translations;

    LootCrateLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_EN", en);
    }

    @Override
    public String getKey() { return "lootcrate." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}
