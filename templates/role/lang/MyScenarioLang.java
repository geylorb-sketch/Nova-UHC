// TODO: renommer en <NomScénario>Lang et ajuster le package
package net.novaproject.myscenario.lang;

import net.novaproject.novauhc.lang.Lang;

/**
 * Messages gameplay envoyés aux joueurs.
 * Usage : LangManager.get().send(MyScenarioLang.MY_KEY, player, Map.of("%placeholder%", value))
 *         LangManager.get().get(MyScenarioLang.MY_KEY, player)
 *
 * Règle % : pour afficher un % après un placeholder → "%value%%" (2x %)
 * Ex: "§f%value%%" → after replacement of %value% by "50" → "50%"
 */
public enum MyScenarioLang implements Lang {

    // ── Messages généraux ────────────────────────────────────────────
    ROLE_OBTAINED(
        "§a✦ [TODO] §7Rôle obtenu : §f%role%§7 !",
        "§a✦ [TODO] §7Role obtained: §f%role%§7!"
    ),
    ROLES_DISTRIBUTED(
        "§a✦ [TODO] §7Les rôles ont été distribués !",
        "§a✦ [TODO] §7Roles have been distributed!"
    ),

    // ── Messages spécifiques aux abilities ───────────────────────────
    // TODO: ajouter un message par feedback aux joueurs (activation, cooldown, erreur, etc.)
    MY_ABILITY_ACTIVATED(
        "§a[TODO] §fAbility activée !",
        "§a[TODO] §fAbility activated!"
    ),
    MY_ABILITY_COOLDOWN(
        "§c[TODO] §fCooldown : §e%time%s§f restant(s).",
        "§c[TODO] §fCooldown: §e%time%s§f remaining."
    );
    // TODO: compléter

    // ── Boilerplate (ne pas modifier) ────────────────────────────────
    private final String fr;
    private final String en;

    MyScenarioLang(String fr, String en) {
        this.fr = fr;
        this.en = en;
    }

    @Override public String getFr() { return fr; }
    @Override public String getEn() { return en; }
}
