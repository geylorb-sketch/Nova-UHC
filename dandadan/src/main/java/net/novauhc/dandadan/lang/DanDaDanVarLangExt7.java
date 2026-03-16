package net.novauhc.dandadan.lang;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

/**
 * Clés VarLang supplémentaires — abilities ajoutées (EspaceVide, Toubillion, etc.)
 * 53 clés.
 */
public enum DanDaDanVarLangExt7 implements Lang {

    ACROBATIQUE_ABILITY_ESPACE_VIDE_NAME("§fAcrobatique Ability Espace Vide", "§fAcrobatique Ability Espace Vide"),
    BAMORA_ABILITY_SLAUGHTER_NAME("§fBamora Ability Slaughter", "§fBamora Ability Slaughter"),
    BAMORA_ABILITY_VILLE_NAME("§fBamora Ability Ville", "§fBamora Ability Ville"),
    DEVILMAN_ABILITY_ESPACE_VIDE_NAME("§fDevilman Ability Espace Vide", "§fDevilman Ability Espace Vide"),
    ENENRA_ABILITY_ESPACE_VIDE_NAME("§fEnenra Ability Espace Vide", "§fEnenra Ability Espace Vide"),
    FLATWOODS_ABILITY_ESPACE_VIDE_NAME("§fFlatwoods Ability Espace Vide", "§fFlatwoods Ability Espace Vide"),
    JETBOOSTER_ABILITY_ESPACE_VIDE_NAME("§fJetbooster Ability Espace Vide", "§fJetbooster Ability Espace Vide"),
    JIANGSHI_ABILITY_ESPACE_VIDE_NAME("§fJiangshi Ability Espace Vide", "§fJiangshi Ability Espace Vide"),
    JIJI_ABILITY_ESPACE_VIDE_NAME("§fJiji Ability Espace Vide", "§fJiji Ability Espace Vide"),
    KASHIMOTO_ABILITY_ESPACE_VIDE_NAME("§fKashimoto Ability Espace Vide", "§fKashimoto Ability Espace Vide"),
    KINTA_ABILITY_DEDALE_NAME("§fKinta Ability Dedale", "§fKinta Ability Dedale"),
    KINTA_ABILITY_ESPACE_VIDE_NAME("§fKinta Ability Espace Vide", "§fKinta Ability Espace Vide"),
    MANTIS_ABILITY_ESPACE_VIDE_NAME("§fMantis Ability Espace Vide", "§fMantis Ability Espace Vide"),
    MINOTAURE_ABILITY_ESPACE_VIDE_NAME("§fMinotaure Ability Espace Vide", "§fMinotaure Ability Espace Vide"),
    MOMO_ABILITY_MEMOIRE_NAME("§fMomo Ability Memoire", "§fMomo Ability Memoire"),
    MOMO_ABILITY_PSYCHO_NAME("§fMomo Ability Psycho", "§fMomo Ability Psycho"),
    NESSIE_ABILITY_ESPACE_VIDE_NAME("§fNessie Ability Espace Vide", "§fNessie Ability Espace Vide"),
    OEIL_ABILITY_ESPACE_VIDE_NAME("§fOeil Ability Espace Vide", "§fOeil Ability Espace Vide"),
    OKARUN_ABILITY_RYTHME_NAME("§fOkarun Ability Rythme", "§fOkarun Ability Rythme"),
    OKARUN_ABILITY_TOUBILLION_NAME("§fOkarun Ability Toubillion", "§fOkarun Ability Toubillion"),
    OKARUN_ABILITY_TUNNEL_NAME("§fOkarun Ability Tunnel", "§fOkarun Ability Tunnel"),
    PAYASE_ABILITY_ESPACE_VIDE_NAME("§fPayase Ability Espace Vide", "§fPayase Ability Espace Vide"),
    PSYCHO_GRAB_DURATION_DESC("§7Réglage pour Psycho Grab Duration", "§7Réglage pour Psycho Grab Duration"),
    PSYCHO_GRAB_DURATION_NAME("§fPsycho Grab Duration", "§fPsycho Grab Duration"),
    PSYCHO_STONE_COUNT_DESC("§7Réglage pour Psycho Stone Count", "§7Réglage pour Psycho Stone Count"),
    PSYCHO_STONE_COUNT_NAME("§fPsycho Stone Count", "§fPsycho Stone Count"),
    REIKO_ABILITY_ESPACE_VIDE_NAME("§fReiko Ability Espace Vide", "§fReiko Ability Espace Vide"),
    ROKURO_ABILITY_ESPACE_VIDE_NAME("§fRokuro Ability Espace Vide", "§fRokuro Ability Espace Vide"),
    RYTHME_BUFF_DURATION_DESC("§7Réglage pour Rythme Buff Duration", "§7Réglage pour Rythme Buff Duration"),
    RYTHME_BUFF_DURATION_NAME("§fRythme Buff Duration", "§fRythme Buff Duration"),
    RYTHME_REQUIRED_HITS_DESC("§7Réglage pour Rythme Required Hits", "§7Réglage pour Rythme Required Hits"),
    RYTHME_REQUIRED_HITS_NAME("§fRythme Required Hits", "§fRythme Required Hits"),
    SEIKO_ABILITY_ESPACE_VIDE_NAME("§fSeiko Ability Espace Vide", "§fSeiko Ability Espace Vide"),
    SLAUGHTER_DURATION_DESC("§7Réglage pour Slaughter Duration", "§7Réglage pour Slaughter Duration"),
    SLAUGHTER_DURATION_NAME("§fSlaughter Duration", "§fSlaughter Duration"),
    SLAUGHTER_HP_DESC("§7Réglage pour Slaughter Hp", "§7Réglage pour Slaughter Hp"),
    SLAUGHTER_HP_NAME("§fSlaughter Hp", "§fSlaughter Hp"),
    TOUBILLION_HP_THRESHOLD_DESC("§7Réglage pour Toubillion Hp Threshold", "§7Réglage pour Toubillion Hp Threshold"),
    TOUBILLION_HP_THRESHOLD_NAME("§fToubillion Hp Threshold", "§fToubillion Hp Threshold"),
    TOUBILLION_REGEN_AMOUNT_DESC("§7Réglage pour Toubillion Regen Amount", "§7Réglage pour Toubillion Regen Amount"),
    TOUBILLION_REGEN_AMOUNT_NAME("§fToubillion Regen Amount", "§fToubillion Regen Amount"),
    TOUBILLION_SPEC_DURATION_DESC("§7Réglage pour Toubillion Spec Duration", "§7Réglage pour Toubillion Spec Duration"),
    TOUBILLION_SPEC_DURATION_NAME("§fToubillion Spec Duration", "§fToubillion Spec Duration"),
    TSUCHINOKO_ABILITY_ESPACE_VIDE_NAME("§fTsuchinoko Ability Espace Vide", "§fTsuchinoko Ability Espace Vide"),
    TUNNEL_DURATION_DESC("§7Réglage pour Tunnel Duration", "§7Réglage pour Tunnel Duration"),
    TUNNEL_DURATION_NAME("§fTunnel Duration", "§fTunnel Duration"),
    TUNNEL_RADIUS_DESC("§7Réglage pour Tunnel Radius", "§7Réglage pour Tunnel Radius"),
    TUNNEL_RADIUS_NAME("§fTunnel Radius", "§fTunnel Radius"),
    UMBRELLA_ABILITY_ESPACE_VIDE_NAME("§fUmbrella Ability Espace Vide", "§fUmbrella Ability Espace Vide"),
    VILLE_DURATION_DESC("§7Réglage pour Ville Duration", "§7Réglage pour Ville Duration"),
    VILLE_DURATION_NAME("§fVille Duration", "§fVille Duration"),
    VILLE_RADIUS_DESC("§7Réglage pour Ville Radius", "§7Réglage pour Ville Radius"),
    VILLE_RADIUS_NAME("§fVille Radius", "§fVille Radius");

    private final Map<String, String> translations;

    DanDaDanVarLangExt7(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_US", en);
    }

    @Override public String getKey() { return "dandadan.var." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
