// TODO: renommer en <NomScénario>VarLang et ajuster le package
package net.novaproject.myscenario.lang;

import net.novaproject.novauhc.lang.Lang;

/**
 * Labels affichés dans l'UI de configuration (menus in-game).
 *
 * Règle : une paire NAME + DESC par :
 *   - chaque @AbilityVariable dans chaque Ability
 *   - chaque @RoleVariable dans chaque Role (paramètre inline ou déclaration d'Ability)
 *   - chaque @ScenarioVariable dans MyScenarioUHC
 *
 * Convention de nommage : <ROLENAME>_<PARAMNAME>_NAME / _DESC
 * Ex: STEVE_HEALTH_RANGE_NAME, STEVE_HEALTH_RANGE_DESC
 */
public enum MyScenarioVarLang implements Lang {

    // ── Variables du scénario (@ScenarioVariable) ─────────────────────
    MY_SCENARIO_PARAM_NAME("TODO: Nom du paramètre scénario", "TODO: Scenario param name"),
    MY_SCENARIO_PARAM_DESC("§7Description du paramètre scénario.", "§7Scenario param description."),

    // ── Variables par rôle / ability ─────────────────────────────────
    // TODO: dupliquer le bloc suivant pour chaque @AbilityVariable et @RoleVariable

    // RoleName → ability déclarée via @RoleVariable
    ROLENAME_ABILITY_NAME("TODO: Nom Ability", "TODO: Ability name"),
    // (Pas de DESC obligatoire pour @RoleVariable de type ABILITY)

    // RoleName → paramètre dans l'ability
    ROLENAME_PARAM_NAME("TODO: Nom du paramètre", "TODO: Param name"),
    ROLENAME_PARAM_DESC("§7TODO: description du paramètre.", "§7TODO: param description.");
    // TODO: compléter pour chaque ability/rôle

    // ── Boilerplate (ne pas modifier) ────────────────────────────────
    private final String fr;
    private final String en;

    MyScenarioVarLang(String fr, String en) {
        this.fr = fr;
        this.en = en;
    }

    @Override public String getFr() { return fr; }
    @Override public String getEn() { return en; }
}
