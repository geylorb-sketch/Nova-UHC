package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;


public enum OreSwapLang implements Lang {

    SWAP_ANNOUNCEMENT(
        "§6[OreSwap] §fLes minerais ont été mélangés !",
        "§6[OreSwap] §fOres have been swapped!"
    ),
    SWAP_WARNING(
        "§6[OreSwap] §fMélange des minerais dans %time% !",
        "§6[OreSwap] §fOre swap in %time%!"
    ),
    SWAP_FORCED(
        "§6[OreSwap] §fMélange forcé des minerais par un administrateur !",
        "§6[OreSwap] §fOre swap forced by an administrator!"
    ),
    ORE_SWAPPED(
        "§6[OreSwap] §f%original_ore% → %swapped_ore% !",
        "§6[OreSwap] §f%original_ore% → %swapped_ore%!"
    ),
    NEW_MAPPING(
        "§6[OreSwap] §fNouveau mapping des minerais :",
        "§6[OreSwap] §fNew ore mapping:"
    ),
    MAPPING_LINE(
        "§6[OreSwap] §f%original% §7→ §f%swapped%",
        "§6[OreSwap] §f%original% §7→ §f%swapped%"
    ),
    MAPPING_UNCHANGED(
        "§6[OreSwap] §f%original% §7→ §f%swapped% §7(inchangé)",
        "§6[OreSwap] §f%original% §7→ §f%swapped% §7(unchanged)"
    )
    ;

    private final Map<String, String> translations;

    OreSwapLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_EN", en);
    }

    @Override
    public String getKey() { return "oreswap." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}
