package net.novaproject.novauhc.lang;

import java.util.Map;


public interface Lang {

    
    String name();

    
    String getKey();

    
    Map<String, String> getTranslations();

    
    default String getFallback() {
        Map<String, String> t = getTranslations();
        if (t.containsKey("fr_FR")) return t.get("fr_FR");
        if (!t.isEmpty()) return t.values().iterator().next();
        return "[" + getKey() + "]";
    }
}
