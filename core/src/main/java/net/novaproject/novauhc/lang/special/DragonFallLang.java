package net.novaproject.novauhc.lang.special;

import net.novaproject.novauhc.lang.Lang;

import java.util.Map;

public enum DragonFallLang implements Lang {

    FIRE_BLIGHT_START(
        "§c🔥 Vous êtes en proie aux flammes !",
        "§c🔥 You are engulfed in flames!"
    ),
    FIRE_BLIGHT_END(
        "§7🔥 Le feu s'est éteint.",
        "§7🔥 The fire has gone out."
    )

    ;

    private final Map<String, String> translations;

    DragonFallLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_EN", en);
    }

    @Override
    public String getKey() { return "dragonfall." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}
