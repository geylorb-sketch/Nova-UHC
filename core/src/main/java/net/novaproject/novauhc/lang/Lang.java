package net.novaproject.novauhc.lang;

import java.util.Map;

/**
 * Interface unifiée pour toutes les clés de traduction du plugin.
 *
 * Chaque enum de messages implémente cette interface.
 * Les traductions sont stockées dans plugins/NovaUHC/lang/<locale>.yml
 * et gérées par {@link LangManager}.
 *
 * Usage :
 *   LangManager.get().send(CommonLang.PVP_START, player);
 *   LangManager.get().send(AcidRainLang.ACID_RAIN_WARNING, player, Map.of("%time%", 5));
 *   LangManager.get().sendAll(CommonLang.MEETUP_START);
 */
public interface Lang {

    /** Fourni automatiquement par l'enum. */
    String name();

    /**
     * Clé YAML unique utilisée dans les fichiers de traduction.
     * Convention : "prefix.ENUM_NAME"
     * ex : "acidrain.ACID_RAIN_START", "common.PVP_START", "ui.default.BORDER_ITEM_NAME"
     */
    String getKey();

    /**
     * Messages par défaut pour chaque locale supportée par cet enum.
     * Au minimum "fr_FR" doit être présent.
     * Ces valeurs sont écrites dans les YAML si la clé est absente
     * (les customisations admin sont préservées).
     */
    Map<String, String> getTranslations();

    /**
     * Message de secours si la locale demandée n'est pas définie.
     * Par défaut : fr_FR, ou la première valeur disponible.
     */
    default String getFallback() {
        Map<String, String> t = getTranslations();
        if (t.containsKey("fr_FR")) return t.get("fr_FR");
        if (!t.isEmpty()) return t.values().iterator().next();
        return "[" + getKey() + "]";
    }
}
