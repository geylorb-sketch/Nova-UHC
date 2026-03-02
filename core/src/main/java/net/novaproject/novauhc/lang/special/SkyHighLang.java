package net.novaproject.novauhc.lang.special;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;
import java.util.HashMap;

public enum SkyHighLang implements Lang {

    DAMAGE_FIRST_LAYER("§cVous prenez des dégâts %first_damage%, montez au-dessus de la couche %first_level%!","§cYou take %first_damage% damage, go above layer %first_level%!"),
    DAMAGE_SECOND_LAYER("§cVous prenez des dégâts %second_damage%, montez au-dessus de la couche %second_level%!","§cYou take %second_damage% damage, go above layer %second_level%!"),
    DAMAGE_THIRD_LAYER("§cVous prenez des dégâts %third_damage%, montez au-dessus de la couche %third_level%!","§cYou take %third_damage% damage, go above layer %third_level%!"),
    WARNING_SKY_HIGH("§eAttention, vous devez rester en hauteur !","§eWarning, you must stay high!");

    private final Map<String,String> translations = new HashMap<>();

    SkyHighLang(String fr, String en) {
        translations.put("fr_FR", fr);
        translations.put("en_US", en);
    }

    @Override
    public String getKey() {
        return "skyhigh." + name();
    }

    @Override
    public Map<String, String> getTranslations() {
        return translations;
    }
}