package net.novaproject.novauhc.scenario.role;

import net.novaproject.novauhc.lang.Lang;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.HoverUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * DSL fluent pour construire la description d'un rôle dans {@link Role#sendDescription(Player)}.
 *
 * <pre>
 * RoleDescription.of(p)
 *     .separator(MonsterDescLang.SEPARATOR)
 *     .line(MonsterDescLang.ROLE_PREFIX, MonsterDescLang.MY_ROLE_NAME)
 *     .hover(MonsterDescLang.MY_ABILITY_TEXT, MonsterDescLang.MY_ABILITY_HOVER)
 *     .separator(MonsterDescLang.SEPARATOR)
 *     .send();
 * </pre>
 */
public class RoleDescription {

    private final Player player;
    private final List<Runnable> lines = new ArrayList<>();

    private RoleDescription(Player player) {
        this.player = player;
    }

    public static RoleDescription of(Player player) {
        return new RoleDescription(player);
    }

    /** Envoie une ligne de séparateur (clé lang). */
    public RoleDescription separator(Lang key) {
        lines.add(() -> player.sendMessage(LangManager.get().get(key, player)));
        return this;
    }

    /** Envoie un message vide. */
    public RoleDescription space() {
        lines.add(() -> player.sendMessage(" "));
        return this;
    }

    /** Envoie une ligne de texte simple. */
    public RoleDescription line(Lang key) {
        lines.add(() -> player.sendMessage(LangManager.get().get(key, player)));
        return this;
    }

    /** Envoie deux clés concaténées sur la même ligne. */
    public RoleDescription line(Lang prefix, Lang suffix) {
        lines.add(() -> player.sendMessage(
                LangManager.get().get(prefix, player) + LangManager.get().get(suffix, player)));
        return this;
    }

    /** Envoie une ligne avec un texte sur lequel on peut hover pour voir plus d'infos. */
    public RoleDescription hover(Lang text, Lang hover) {
        lines.add(() -> HoverUtils.sendHoverLine(
                player,
                LangManager.get().get(text, player),
                LangManager.get().get(hover, player)));
        return this;
    }

    /** Envoie un message brut (sans LangKey). */
    public RoleDescription raw(String message) {
        lines.add(() -> player.sendMessage(message));
        return this;
    }

    /** Exécute toutes les lignes et les envoie au joueur. */
    public void send() {
        lines.forEach(Runnable::run);
    }
}
