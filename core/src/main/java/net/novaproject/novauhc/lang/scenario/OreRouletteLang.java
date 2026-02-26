package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

public enum OreRouletteLang implements Lang {

    ORE_CHANGED("§d[OreRoulette] §f%from% → %to% !", "§d[OreRoulette] §f%from% → %to%!"),
    JACKPOT_DIAMOND("§d[OreRoulette] §6§lCHANCE INCROYABLE ! Diamant obtenu !", "§d[OreRoulette] §6§lINCREDIBLE LUCK! Diamond obtained!"),
    JACKPOT_EMERALD("§d[OreRoulette] §a§lTRÈS RARE ! Émeraude obtenue !", "§d[OreRoulette] §a§lVERY RARE! Emerald obtained!"),
    ;

    private final Map<String, String> translations;
    OreRouletteLang(String fr, String en) { this.translations = Map.of("fr_FR", fr, "en_US", en); }
    @Override public String getKey() { return "oreroulette." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
