// TODO: renommer en <NomScénario>DescLang et ajuster le package
package net.novaproject.myscenario.lang;

import net.novaproject.novauhc.lang.Lang;

/**
 * Descriptions affichées dans le menu "sendDescription()" de chaque rôle.
 * Structure OBLIGATOIRE :
 *   SEPARATOR → espace → sections (INFO, PASSIFS, ACTIFS) → espace → SEPARATOR final
 *
 * Clés partagées : SEPARATOR, SECTION_INFO, SECTION_PASSIFS, SECTION_ACTIFS, ROLE_PREFIX
 * Clés par rôle  : ROLENAME_NAME, ROLENAME_CAMP, ROLENAME_ABILITY_TEXT, ROLENAME_ABILITY_HOVER
 */
public enum MyScenarioDescLang implements Lang {

    // ── Clés partagées (obligatoires, identiques dans tous les modules) ──
    SEPARATOR(
        "§7▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
        "§7▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"
    ),
    SECTION_INFO  ("§e● §fInformations", "§e● §fInformation"),
    SECTION_PASSIFS("§e● §fPassifs",     "§e● §fPassives"),
    SECTION_ACTIFS ("§e● §fActifs",      "§e● §fActives"),
    ROLE_PREFIX    ("§f§lRôle §8» ",     "§f§lRole §8» "),

    // Description courte du scénario (utilisée dans getDescription())
    DESCRIPTION("§7TODO: description du scénario.", "§7TODO: scenario description."),

    // ── Clés par camp ─────────────────────────────────────────────────
    // TODO: une entrée par camp défini dans MyCamps
    CAMP_A ("§7Camp : §aCamp A",  "§7Camp: §aCamp A"),
    CAMP_B ("§7Camp : §cCamp B",  "§7Camp: §cCamp B"),
    // Objectif générique (peut être remplacé par un objectif spécifique par rôle)
    OBJECTIVE("§7Objectif : §fTODO", "§7Objective: §fTODO"),

    // ── Clés par rôle ─────────────────────────────────────────────────
    // TODO: dupliquer le bloc suivant pour chaque rôle
    // Nommage : <ROLENAME>_NAME, <ROLENAME>_<ABILITY>_TEXT, <ROLENAME>_<ABILITY>_HOVER

    ROLENAME_NAME("TODO: NomRôle", "TODO: RoleName"),

    // Passif
    ROLENAME_PASSIVE_TEXT(
        "§7• §dMon Passif §7— Passif",
        "§7• §dMy Passive §7— Passive"
    ),
    ROLENAME_PASSIVE_HOVER(
        "§fDescription complète du passif affiché au survol.",
        "§fFull passive description shown on hover."
    ),

    // Actif (supprimer si pas d'actif)
    ROLENAME_ACTIVE_TEXT(
        "§7• §bMon Actif §7— Actif",
        "§7• §bMy Active §7— Active"
    ),
    ROLENAME_ACTIVE_HOVER(
        "§fDescription complète de l'actif affiché au survol.",
        "§fFull active description shown on hover."
    );
    // TODO: continuer pour chaque rôle

    // ── Boilerplate (ne pas modifier) ────────────────────────────────
    private final String fr;
    private final String en;

    MyScenarioDescLang(String fr, String en) {
        this.fr = fr;
        this.en = en;
    }

    @Override public String getFr() { return fr; }
    @Override public String getEn() { return en; }
}
