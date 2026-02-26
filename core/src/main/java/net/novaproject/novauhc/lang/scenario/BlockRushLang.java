package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

public enum BlockRushLang implements Lang {

    FIRST_MINE("§6§l[BlockRush] §f%player% §fest le premier à miner §6%block% §f!", "§6§l[BlockRush] §f%player% §fis the first to mine §6%block%§f!"),
    FIRST_BONUS("§6[BlockRush] §fPremière découverte ! Bonus : §6%bonus%", "§6[BlockRush] §fFirst discovery! Bonus: §6%bonus%"),
    NEW_BLOCK("§6[BlockRush] §fNouveau bloc miné : §6%block% §f! +%amount% Lingot(s) d'Or", "§6[BlockRush] §fNew block mined: §6%block%§f! +%amount% Gold Ingot(s)"),
    INVENTORY_FULL("§6[BlockRush] §fInventaire plein !", "§6[BlockRush] §fInventory full!"),
    STEP_REWARD("§6[BlockRush] §fRécompense étape : §6%amount%x %item%", "§6[BlockRush] §fStep reward: §6%amount%x %item%"),
    MILESTONE("§6[BlockRush] §f%player% §fa miné %count% types de blocs différents !", "§6[BlockRush] §f%player% §fhas mined %count% different block types!"),
    ;

    private final Map<String, String> translations;
    BlockRushLang(String fr, String en) { this.translations = Map.of("fr_FR", fr, "en_US", en); }
    @Override public String getKey() { return "blockrush." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
