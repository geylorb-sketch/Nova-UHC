package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;


public enum MagnetLang implements Lang {

    MAGNET_ACTIVATED(
        "§6[Magnet] §fMinerais attirés dans un rayon de %radius% blocs !",
        "§6[Magnet] §fOres attracted within %radius% blocks!"
    ),

    
    ORES_ATTRACTED(
        "§b[Magnet] §f%count% minerai(s) attiré(s) !",
        "§b[Magnet] §f%count% ore(s) attracted!"
    ),
    ;

    private final Map<String, String> translations;

    MagnetLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_EN", en);
    }

    @Override
    public String getKey() { return "magnet." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}
