package net.novaproject.novauhc.lang;

import org.bukkit.entity.Player;


public final class LangResolver {

    private LangResolver() {}


    @SuppressWarnings("unchecked")
    public static String resolve(Class<? extends Lang> langClass, String enumName, Player player) {
        if (langClass == null || enumName == null || enumName.isEmpty()) {
            return "";
        }

        try {
            
            Lang lang = (Lang) Enum.valueOf((Class<? extends Enum>) langClass, enumName);
            return LangManager.get().get(lang, player);
        } catch (IllegalArgumentException e) {
            
            return enumName;
        }
    }


    @SuppressWarnings("unchecked")
    public static String resolve(Class<? extends Lang> langClass, String enumName) {
        if (langClass == null || enumName == null || enumName.isEmpty()) {
            return "";
        }

        try {
            Lang lang = (Lang) Enum.valueOf((Class<? extends Enum>) langClass, enumName);
            return LangManager.get().get(lang);
        } catch (IllegalArgumentException e) {
            return enumName;
        }
    }
}