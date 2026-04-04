package net.novaproject.novauhc.lang.ui;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

public enum OrePopulatorUiLang implements Lang {

    BACK_BUTTON("§7Retour", "§7Back"),
    LORE_LEFT("§e► Clic gauche pour §a+ 10%", "§e► Left click for §a+10%"),
    LORE_RIGHT("§e► Clic droit pour §c- 10%", "§e► Right click for §c-10%"),
    CURRENT_BOOST("§7Boost actuel : %value%", "§7Current boost: %value%"),
    DIAMOND_LABEL("§3Boost : Diamond", "§3Boost: Diamond"),
    GOLD_LABEL("§eBoost : Gold", "§eBoost: Gold"),
    IRON_LABEL("§fBoost : Iron", "§fBoost: Iron"),
    LAPIS_LABEL("§bBoost : Lapis", "§bBoost: Lapis"),
    ;

    private final Map<String, String> translations;
    OrePopulatorUiLang(String fr, String en) { this.translations = Map.of("fr_FR", fr, "en_EN", en); }
    @Override public String getKey() { return "ui.orepop." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
