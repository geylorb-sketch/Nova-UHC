// TODO: renommer en <NomScénario>Lang et ajuster le package
package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;

/**
 * Enum de messages pour le scénario MyScenario.
 * Français en premier, anglais en second.
 * Inscrire dans LangManager via LangManager.get().register(MyScenarioLang.values())
 * — dans Scenario.onGameStart() ou dans Main.onEnable() selon l'emplacement du scénario.
 */
public enum MyScenarioLang implements Lang {

    // ── Messages gameplay ────────────────────────────────────────────
    // TODO: ajouter une entrée par message envoyé aux joueurs via sendMessage / LangManager.send
    // Placeholders : %player%, %value%, etc.
    // Pour afficher un % après un placeholder → "%value%%" (2x %)

    MY_ACTION_MESSAGE(
        "§a[MyScenario] §7Action effectuée par §f%player%§7 !",
        "§a[MyScenario] §7Action done by §f%player%§7!"
    ),
    MY_COOLDOWN_MESSAGE(
        "§c[MyScenario] §7Attends §f%time%s§7 avant de réutiliser.",
        "§c[MyScenario] §7Wait §f%time%s§7 before using again."
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
