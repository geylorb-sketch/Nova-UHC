package net.novauhc.dandadan.lang;

import net.novaproject.novauhc.lang.Lang;

import java.util.Map;

/**
 * DanDaDanVarLang — Clés pour @ScenarioVariable, @RoleVariable, @AbilityVariable.
 * Affichées dans les menus de configuration du framework.
 */
public enum DanDaDanVarLang implements Lang {

    // ══════════════════════════════════════════════════════
    //  @ScenarioVariable (DanDaDan.java)
    // ══════════════════════════════════════════════════════
    PORTAL_COUNT_NAME("§fNombre de portails", "§fPortal count"),
    PORTAL_COUNT_DESC("§7Nombre de portails générés autour du GameWorld", "§7Number of portals generated around GameWorld"),
    PORTAL_RADIUS_NAME("§fRayon des portails", "§fPortal radius"),
    PORTAL_RADIUS_DESC("§7Distance des portails par rapport au centre", "§7Portal distance from center"),
    ZONE_DETECT_RADIUS_NAME("§fRayon de détection zone", "§fZone detect radius"),
    ZONE_DETECT_RADIUS_DESC("§7Rayon de détection des zones d'invocation Yokai", "§7Yokai invocation zone detection radius"),
    INVOCATION_TIME_NAME("§fTemps d'invocation", "§fInvocation time"),
    INVOCATION_TIME_DESC("§7Durée de l'invocation avant TP vers l'épreuve (secondes)", "§7Duration of invocation before TP to trial (seconds)"),

    // ══════════════════════════════════════════════════════
    //  OKARUN — @RoleVariable
    // ══════════════════════════════════════════════════════
    OKARUN_CURSE_MAX_TIME_NAME("§fDurée max malédiction", "§fMax curse duration"),
    OKARUN_CURSE_MAX_TIME_DESC("§7Durée maximale de la malédiction en secondes", "§7Maximum curse duration in seconds"),
    OKARUN_KILL_BONUS_NAME("§fBonus kill malédiction", "§fKill curse bonus"),
    OKARUN_KILL_BONUS_DESC("§7Secondes ajoutées à la malédiction par kill", "§7Seconds added to curse per kill"),

    // Ability names (type = ABILITY)
    OKARUN_ABILITY_MALEDICTION_NAME("§fMalédiction", "§fCurse"),
    OKARUN_ABILITY_ALLOUT_NAME("§fAll-Out", "§fAll-Out"),
    OKARUN_ABILITY_RYTHME_NAME("§fRythme", "§fRhythm"),
    OKARUN_ABILITY_COURSE_NAME("§fCourse", "§fRace"),
    OKARUN_ABILITY_TUNNEL_NAME("§fTunnel", "§fTunnel"),

    // ══════════════════════════════════════════════════════
    //  OKARUN — @AbilityVariable
    // ══════════════════════════════════════════════════════
    OKARUN_CURSE_DURATION_NAME("§fDurée effet speed", "§fSpeed effect duration"),
    OKARUN_CURSE_DURATION_DESC("§7Durée de l'effet speed en secondes", "§7Speed effect duration in seconds"),
    ALLOUT_DASH_DIST_NAME("§fDistance du dash", "§fDash distance"),
    ALLOUT_DASH_DIST_DESC("§7Distance du dash en blocs", "§7Dash distance in blocks"),
    ALLOUT_COLLISION_DMG_NAME("§fDégâts collision", "§fCollision damage"),
    ALLOUT_COLLISION_DMG_DESC("§7Dégâts infligés à la collision (en HP)", "§7Damage dealt on collision (in HP)"),
    ALLOUT_SLOW_DURATION_NAME("§fDurée doigt coupé", "§fFinger cut duration"),
    ALLOUT_SLOW_DURATION_DESC("§7Durée de l'effet doigt coupé en secondes", "§7Finger cut effect duration in seconds"),
    MEME_TRIGGER_RANGE_NAME("§fPortée détection", "§fDetection range"),
    MEME_TRIGGER_RANGE_DESC("§7Rayon pour détecter la présence d'un joueur", "§7Radius to detect a player's presence"),
    MEME_SPEED_RANGE_NAME("§fPortée speed", "§fSpeed range"),
    MEME_SPEED_RANGE_DESC("§7Rayon pour activer le speed", "§7Radius to activate speed"),
    TOUBILLION_HP_THRESHOLD_NAME("§fSeuil HP", "§fHP threshold"),
    TOUBILLION_HP_THRESHOLD_DESC("§7Seuil de vie pour activer (en HP)", "§7Health threshold to activate (in HP)"),
    TOUBILLION_SPEC_DURATION_NAME("§fDurée spectateur", "§fSpectator duration"),
    TOUBILLION_SPEC_DURATION_DESC("§7Durée du mode spectateur en secondes", "§7Spectator mode duration in seconds"),
    TOUBILLION_REGEN_NAME("§fRégénération", "§fRegeneration"),
    TOUBILLION_REGEN_DESC("§7Quantité de HP régénérés", "§7Amount of HP regenerated"),
    RYTHME_HITS_NAME("§fCoups requis", "§fRequired hits"),
    RYTHME_HITS_DESC("§7Nombre de coups pour déclencher le no-hit delay", "§7Number of hits to trigger no-hit delay"),
    RYTHME_BUFF_DURATION_NAME("§fDurée no-hit delay", "§fNo-hit delay duration"),
    RYTHME_BUFF_DURATION_DESC("§7Durée du no-hit delay en secondes", "§7No-hit delay duration in seconds"),
    COURSE_WIN_HP_NAME("§fBonus HP victoire", "§fWin HP bonus"),
    COURSE_WIN_HP_DESC("§7HP bonus pour le gagnant", "§7HP bonus for the winner"),
    COURSE_CURSE_BONUS_NAME("§fBonus malédiction", "§fCurse bonus"),
    COURSE_CURSE_BONUS_DESC("§7Secondes de malédiction bonus pour Okarun", "§7Curse bonus seconds for Okarun"),
    TUNNEL_RADIUS_NAME("§fRayon du tunnel", "§fTunnel radius"),
    TUNNEL_RADIUS_DESC("§7Rayon de téléportation en blocs", "§7Teleportation radius in blocks"),
    TUNNEL_DURATION_NAME("§fDurée du tunnel", "§fTunnel duration"),
    TUNNEL_DURATION_DESC("§7Durée du tunnel en secondes", "§7Tunnel duration in seconds"),

    // ══════════════════════════════════════════════════════
    //  MOMO — @RoleVariable / @AbilityVariable
    // ══════════════════════════════════════════════════════
    MOMO_ABILITY_MOEMOE_NAME("§fMoe Moe", "§fMoe Moe"),
    MOMO_ABILITY_MEMOIRE_NAME("§fMémoire", "§fMemory"),
    MOMO_ABILITY_PSYCHO_NAME("§fPsychokinésie", "§fPsychokinesis"),
    MOEMOE_MAX_HITS_NAME("§fMax coups Moe Moe", "§fMax Moe Moe hits"),
    MOEMOE_MAX_HITS_DESC("§7Nombre maximum de coups avant arrêt", "§7Maximum hit count before stopping"),

    // ══════════════════════════════════════════════════════
    //  BAMORA — @RoleVariable / @AbilityVariable
    // ══════════════════════════════════════════════════════
    BAMORA_ABILITY_KAIJU_NAME("§fKaiju", "§fKaiju"),
    BAMORA_ABILITY_INVIS_NAME("§fInvisibilité", "§fInvisibility"),
    BAMORA_ABILITY_SYSTEME_NAME("§fSystème", "§fSystem"),
    BAMORA_ABILITY_VILLE_NAME("§fVille", "§fCity"),

    // ══════════════════════════════════════════════════════
    //  SEIKO — @RoleVariable
    // ══════════════════════════════════════════════════════
    SEIKO_ABILITY_BARRIERE_M_NAME("§fBarrière mystique", "§fMystic Barrier"),
    SEIKO_ABILITY_BARRIERE_I_NAME("§fBarrière intérieure", "§fInner Barrier"),

    // ══════════════════════════════════════════════════════
    //  KINTA — @RoleVariable
    // ══════════════════════════════════════════════════════
    KINTA_ABILITY_GREAT_NAME("§fGreat Kinta", "§fGreat Kinta"),
    KINTA_ABILITY_DEDALE_NAME("§fDédale Acha", "§fDedale Acha"),
    KINTA_ABILITY_NANOSKIN_NAME("§fNanoskin", "§fNanoskin"),

    // ══════════════════════════════════════════════════════
    //  KASHIMOTO — @RoleVariable
    // ══════════════════════════════════════════════════════
    KASHIMOTO_ABILITY_FLAMME_NAME("§fFlamme de glace", "§fIce Flame"),
    KASHIMOTO_ABILITY_ESPRIT_NAME("§fEsprit Protecteur", "§fProtective Spirit"),
    KASHIMOTO_ABILITY_APHOOM_NAME("§fAphoom-Zhah", "§fAphoom-Zhah"),

    // ══════════════════════════════════════════════════════
    //  AUTO-GENERATED — Remaining roles
    // ══════════════════════════════════════════════════════
    ACROBATIQUE_ABILITY_CHEVEUX_NAME("§fCheveux", "§fCheveux"),
    ACROBATIQUE_ABILITY_TRANSFOA_NAME("§fTransformation", "§fTransformation"),
    CAESAR_ABILITY_SAVONCUTTER_NAME("§fSavon Cutter", "§fSavon Cutter"),
    CAESAR_ABILITY_SAVONLAUNCHER_NAME("§fSavon Launcher", "§fSavon Launcher"),
    CAESAR_ABILITY_SAVONLENSES_NAME("§fSavon Lenses", "§fSavon Lenses"),
    CSG_ABILITY_CARTE_NAME("§fCarte", "§fCarte"),
    CSG_ABILITY_COUTEAU_NAME("§fCouteau", "§fCouteau"),
    CSG_ABILITY_FIOLE_NAME("§fFiole", "§fFiole"),
    DENJI_ABILITY_CHAINE_NAME("§fChaine", "§fChaine"),
    DENJI_ABILITY_CHAINSAWMAN_NAME("§fChainsaw Man", "§fChainsaw Man"),
    DENJI_ABILITY_MALEDENJI_NAME("§fMaledictionenji", "§fMaledictionenji"),
    DEVILMAN_ABILITY_CHALEUR_NAME("§fChaleur", "§fChaleur"),
    DEVILMAN_ABILITY_CROC_NAME("§fCroc", "§fCroc"),
    DEVILMAN_ABILITY_MALED_NAME("§fMalediction", "§fMalediction"),
    DIO_ABILITY_ARRETTEMPSD_NAME("§fArret du Temps", "§fArret du Temps"),
    DIO_ABILITY_COUTEAUDIO_NAME("§fCouteau", "§fCouteau"),
    DIO_ABILITY_ROADROLLER_NAME("§fRoad Roller", "§fRoad Roller"),
    DIO_ABILITY_THEWORLD_NAME("§fThe World", "§fThe World"),
    DIO_ABILITY_TIMESKIP_NAME("§fTime Skip", "§fTime Skip"),
    DOOMSLAYER_ABILITY_CRUCIBLE_NAME("§fCrucible", "§fCrucible"),
    ENENRA_ABILITY_FUMEE_NAME("§fFumee", "§fFumee"),
    ENENRA_ABILITY_NINJA_NAME("§fNinja", "§fNinja"),
    ENENRA_ABILITY_SETTA_NAME("§fSetta", "§fSetta"),
    FLATWOODS_ABILITY_FUMEEF_NAME("§fFumee", "§fFumee"),
    JETBOOSTER_ABILITY_AVIATE_NAME("§fAviate", "§fAviate"),
    JETBOOSTER_ABILITY_BOOST_NAME("§fBoost", "§fBoost"),
    JETBOOSTER_ABILITY_SUPERCHARGE_NAME("§fSupercharge", "§fSupercharge"),
    JIANGSHI_ABILITY_ALLOUTJ_NAME("§fAll-Out", "§fAll-Out"),
    JIANGSHI_ABILITY_INVOCATION_NAME("§fInvocation", "§fInvocation"),
    JIJI_ABILITY_SPIRALES_NAME("§fSpirales", "§fSpirales"),
    JOSEPH_ABILITY_CLACKER_NAME("§fClacker", "§fClacker"),
    JOSEPH_ABILITY_HAMONOVERDRIVE_NAME("§fHamon Overdrive", "§fHamon Overdrive"),
    JOSEPH_ABILITY_REBUFF_NAME("§fRebuff", "§fRebuff"),
    JOTARO_ABILITY_ARRETTEMPSJ_NAME("§fArret du Temps", "§fArret du Temps"),
    JOTARO_ABILITY_ORAORA_NAME("§fOraora", "§fOraora"),
    JOTARO_ABILITY_STARFINGER_NAME("§fStarfinger", "§fStarfinger"),
    JOTARO_ABILITY_STARPLATINUM_NAME("§fStarplatinum", "§fStarplatinum"),
    KIRA_ABILITY_BITESDUST_NAME("§fBites the Dust", "§fBites the Dust"),
    KIRA_ABILITY_KILLERQUEEN_NAME("§fKiller Queen", "§fKiller Queen"),
    KIRA_ABILITY_SHEERHEART_NAME("§fSheer Heart Attack", "§fSheer Heart Attack"),
    KIRA_ABILITY_STRAYCAT_NAME("§fStray Cat", "§fStray Cat"),
    MANTIS_ABILITY_BOXE_NAME("§fBoxe", "§fBoxe"),
    MANTIS_ABILITY_CRABE_NAME("§fCrabe", "§fCrabe"),
    MANTIS_ABILITY_JETWATER_NAME("§fJet Water", "§fJet Water"),
    MANTIS_ABILITY_UPPERCUT_NAME("§fUppercut", "§fUppercut"),
    MINOTAURE_ABILITY_DURABILITE_NAME("§fDurabilite", "§fDurabilite"),
    MINOTAURE_ABILITY_KUNGFU_NAME("§fKungfu", "§fKungfu"),
    NESSIE_ABILITY_DELUGE_NAME("§fDeluge", "§fDeluge"),
    NESSIE_ABILITY_JETEAU_NAME("§fJet d'Eau", "§fJet d'Eau"),
    OEILMALEFIQUE_ABILITY_BALLE_NAME("§fBalle", "§fBalle"),
    OEILMALEFIQUE_ABILITY_MALEDICTIONO_NAME("§fMaledictionictiono", "§fMaledictionictiono"),
    PAYASE_ABILITY_DARKNESS_NAME("§fDarkness", "§fDarkness"),
    PAYASE_ABILITY_OMBRE_NAME("§fOmbre", "§fOmbre"),
    PAYASE_ABILITY_PERMUTATION_NAME("§fPermutation", "§fPermutation"),
    POLNAREFF_ABILITY_HORARUSH_NAME("§fHora Rush", "§fHora Rush"),
    POLNAREFF_ABILITY_IMAGEREMAN_NAME("§fImage Remanente", "§fImage Remanente"),
    POLNAREFF_ABILITY_SILVERCHARIOT_NAME("§fSilver Chariot", "§fSilver Chariot"),
    POLNAREFF_ABILITY_SWORDLAUNCH_NAME("§fSword Launch", "§fSword Launch"),
    REIKO_ABILITY_EMPRISONNEMENT_NAME("§fEmprisonnement", "§fEmprisonnement"),
    REIKO_ABILITY_MIROIRACTIF_NAME("§fMiroir Actif", "§fMiroir Actif"),
    REIKO_ABILITY_THORNS_NAME("§fThorns", "§fThorns"),
    REZE_ABILITY_BOUM_NAME("§fBoum", "§fBoum"),
    REZE_ABILITY_MALEREZE_NAME("§fMalediction", "§fMalediction"),
    REZE_ABILITY_TETECHERC_NAME("§fTete Chercheuse", "§fTete Chercheuse"),
    REZE_ABILITY_TORPILLE_NAME("§fTorpille", "§fTorpille"),
    ROHAN_ABILITY_HEAVENSDOOR_NAME("§fHeaven's Door", "§fHeaven's Door"),
    ROHAN_ABILITY_LIVRE_NAME("§fLivre", "§fLivre"),
    ROKURO_ABILITY_FORME_NAME("§fForme", "§fForme"),
    ROKURO_ABILITY_ORGUE_NAME("§fOrgue", "§fOrgue"),
    ROKURO_ABILITY_ZONEINCROYABLE_NAME("§fZone Incroyable", "§fZone Incroyable"),
    ROKURO_ABILITY_ZONEINTOUCHABLE_NAME("§fZone Intouchable", "§fZone Intouchable"),
    TSUCHINOKO_ABILITY_ONDES_NAME("§fOndes", "§fOndes"),
    TSUCHINOKO_ABILITY_SUICIDE_NAME("§fSuicide", "§fSuicide"),
    TSUCHINOKO_ABILITY_VENIN_NAME("§fVenin", "§fVenin"),
    UMBRELLA_ABILITY_AIRSTRIKE_NAME("§fAir Strike", "§fAir Strike"),
    UMBRELLA_ABILITY_CURSEU_NAME("§fCurse", "§fCurse"),
    UMBRELLA_ABILITY_PARASOL_NAME("§fParasol", "§fParasol"),

    // ══════════════════════════════════════════════════════
    //  Passive abilities converted from getAbilities().add()
    // ══════════════════════════════════════════════════════
    ACROBATIQUE_ABILITY_ACROBATE_NAME("§fAcrobate", "§fAcrobat"),
    BAMORA_ABILITY_PROJECTILE_NAME("§fProjectile", "§fProjectile"),
    CAESAR_ABILITY_BANDANA_NAME("§fBandana", "§fBandana"),
    CAESAR_ABILITY_HAMON_NAME("§fHamon", "§fHamon"),
    DENJI_ABILITY_SANG_NAME("§fPassif Sang", "§fBlood Passive"),
    DEVILMAN_ABILITY_FLAMME_NAME("§fFlamme", "§fFlame"),
    DIO_ABILITY_VAMPIRE_NAME("§fVampire", "§fVampire"),
    DOOMSLAYER_ABILITY_DOOM_NAME("§fDoom", "§fDoom"),
    ENENRA_ABILITY_COMBO_NAME("§fCombo", "§fCombo"),
    FLATWOODS_ABILITY_FLAT_NAME("§fFlatwoods", "§fFlatwoods"),
    JETBOOSTER_ABILITY_TETE_NAME("§fTête Haute", "§fHead High"),
    JIANGSHI_ABILITY_REVIVE_NAME("§fRévive", "§fRevive"),
    JIANGSHI_ABILITY_KIREGEN_NAME("§fRégén. Ki", "§fKi Regen"),
    JIJI_ABILITY_ADAPT_NAME("§fAdaptation", "§fAdaptation"),
    JOSEPH_ABILITY_PREDICTION_NAME("§fPrédiction", "§fPrediction"),
    JOSEPH_ABILITY_HERMIT_NAME("§fHermit Purple", "§fHermit Purple"),
    JOTARO_ABILITY_REACTION_NAME("§fRéaction", "§fReaction"),
    JOTARO_ABILITY_CASQUETTE_NAME("§fCasquette", "§fCap"),
    KASHIMOTO_ABILITY_PROTECTEUR_NAME("§fEsprit Protecteur Passif", "§fProtective Spirit Passive"),
    KINTA_ABILITY_LUNETTE_NAME("§fLunette", "§fGoggles"),
    KIRA_ABILITY_MAIN_NAME("§fMain Explosive", "§fExplosive Hand"),
    MANTIS_ABILITY_BUSINESS_NAME("§fBusiness", "§fBusiness"),
    MINOTAURE_ABILITY_OXYDATION_NAME("§fOxydation", "§fOxidation"),
    MOMO_ABILITY_SERVEUSE_NAME("§fServeuse", "§fWaitress"),
    NESSIE_ABILITY_POISSON_NAME("§fPoisson", "§fFish"),
    OEILMALEFIQUE_ABILITY_ENVIE_NAME("§fEnvie", "§fEnvy"),
    OKARUN_ABILITY_MEME_NAME("§fMémé 100km/H", "§fGranny 100km/H"),
    OKARUN_ABILITY_TOUBILLION_NAME("§fToubillion", "§fTourbillon"),
    PAYASE_ABILITY_PAYASE_NAME("§fPayase Passif", "§fClown Passive"),
    POLNAREFF_ABILITY_FRANCAIS_NAME("§fFrançais", "§fFrench"),
    REIKO_ABILITY_MIROIR_NAME("§fMiroir", "§fMirror"),
    REZE_ABILITY_BOMBE_NAME("§fBombe", "§fBomb"),
    ROHAN_ABILITY_ECRIVAIN_NAME("§fÉcrivain", "§fWriter"),
    ROKURO_ABILITY_SERUPO_NAME("§fSerupo", "§fSerupo"),
    SEIKO_ABILITY_MOTS_NAME("§fPouvoir des Mots", "§fPower of Words"),
    SEIKO_ABILITY_DIEU_NAME("§fDieu de la Région", "§fGod of the Region"),
    TSUCHINOKO_ABILITY_VERMORT_NAME("§fVer Mort", "§fDead Worm"),
    UMBRELLA_ABILITY_UMBRELLA_NAME("§fParapluie", "§fUmbrella"),
    ;

    private final Map<String, String> translations;

    DanDaDanVarLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_US", en);
    }

    @Override public String getKey() { return "dandadan.var." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
