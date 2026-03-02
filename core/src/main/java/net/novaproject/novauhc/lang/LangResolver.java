package net.novaproject.novauhc.lang;

import org.bukkit.entity.Player;

/**
 * Résout une référence Lang (Class + nom de constante enum) en texte traduit.
 *
 * Usage dans les UIs :
 *   String name = LangResolver.resolve(annotation.lang(), annotation.nameKey(), player);
 *   String desc = LangResolver.resolve(annotation.lang(), annotation.descKey(), player);
 */
public final class LangResolver {

    private LangResolver() {}

    /**
     * Résout une constante enum Lang par sa classe et son nom,
     * traduite dans la langue du joueur.
     *
     * @param langClass  la classe enum (ex: SoulBrotherLang.class)
     * @param enumName   le nom de la constante (ex: "VAR_REUNION_TIME_NAME")
     * @param player     le joueur (pour la locale)
     * @return le texte traduit, ou enumName en fallback si introuvable
     */
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

    /**
     * Variante sans joueur (locale serveur).
     */
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