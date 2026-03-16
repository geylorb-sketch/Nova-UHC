package net.novauhc.dandadan.lang;

import net.novaproject.novauhc.lang.Lang;

import java.util.Map;

/**
 * Clés de variables configurables (ScenarioVarLang custom pour DanDaDan).
 * Utilisées dans @RoleVariable et @AbilityVariable pour nommer/décrire les champs.
 */
public enum DanDaDanVarLang implements Lang {

    // ── Okarun ───────────────────────────────────────────────
    OKARUN_CURSE_MAX_TIME_NAME("Temps max malédiction", "Max curse time"),
    OKARUN_CURSE_MAX_TIME_DESC("Durée maximale de la malédiction en secondes", "Maximum curse duration in seconds"),
    OKARUN_CURSE_KILL_BONUS_NAME("Bonus kill malédiction", "Curse kill bonus"),
    OKARUN_CURSE_KILL_BONUS_DESC("Secondes ajoutées par kill sur la malédiction", "Seconds added per kill to the curse"),
    OKARUN_ABILITY_MALEDICTION_NAME("Malédiction", "Curse"),
    OKARUN_ABILITY_ALLOUT_NAME("All-Out", "All-Out"),
    OKARUN_ABILITY_COURSE_NAME("Course", "Race"),
    OKARUN_MEME_TRIGGER_RANGE_NAME("Portée détection", "Detection range"),
    OKARUN_MEME_TRIGGER_RANGE_DESC("Distance à laquelle un ennemi déclenche le passif (blocs)", "Distance at which an enemy triggers the passive (blocks)"),
    OKARUN_MEME_SPEED_RANGE_NAME("Portée speed", "Speed range"),
    OKARUN_MEME_SPEED_RANGE_DESC("Distance pour obtenir le bonus de speed (blocs)", "Distance for speed bonus (blocks)"),
    OKARUN_CURSE_DURATION_NAME("Durée malédiction", "Curse duration"),
    OKARUN_CURSE_DURATION_DESC("Durée d'activation de la malédiction en secondes", "Curse activation duration in seconds"),
    OKARUN_ALLOUT_DASH_NAME("Distance dash", "Dash distance"),
    OKARUN_ALLOUT_DASH_DESC("Distance du dash en blocs", "Dash distance in blocks"),
    OKARUN_ALLOUT_DAMAGE_NAME("Dégâts collision", "Collision damage"),
    OKARUN_ALLOUT_DAMAGE_DESC("Dégâts infligés lors d'une collision All-Out", "Damage dealt on All-Out collision"),
    OKARUN_ALLOUT_SLOW_NAME("Durée slow collision", "Collision slow duration"),
    OKARUN_ALLOUT_SLOW_DESC("Durée du Slowness appliqué lors d'une collision (secondes)", "Slowness duration on collision (seconds)"),
    OKARUN_COURSE_WIN_HP_NAME("HP bonus course", "Race HP bonus"),
    OKARUN_COURSE_WIN_HP_DESC("Demi-cœurs gagnés en remportant la course", "Half-hearts gained on winning the race"),
    OKARUN_COURSE_WIN_CURSE_BONUS_NAME("Bonus temps course", "Race time bonus"),
    OKARUN_COURSE_WIN_CURSE_BONUS_DESC("Secondes de malédiction gagnées en remportant la course", "Curse seconds gained on winning the race"),
    OKARUN_COURSE_LOSE_SPEED_NAME("Durée speed adverse", "Opponent speed duration"),
    OKARUN_COURSE_LOSE_SPEED_DESC("Durée du speed pour la cible si elle remporte la course (secondes)", "Speed duration for target if they win the race (seconds)"),

    // ── Momo ─────────────────────────────────────────────────
    MOMO_ABILITY_MOEMOE_NAME("Moe Moe", "Moe Moe"),
    MOMO_SERVEUSE_CHANCE_NAME("Chance annulation KB", "KB cancel chance"),
    MOMO_SERVEUSE_CHANCE_DESC("Chance d'annuler le KB si la cible mange une pomme en or (0.0-1.0)", "Chance to cancel KB if target eats a golden apple (0.0-1.0)"),
    MOMO_MOEMOE_MAX_HITS_NAME("Coups max", "Max hits"),
    MOMO_MOEMOE_MAX_HITS_DESC("Nombre de coups avant l'épuisement de Moe Moe", "Number of hits before Moe Moe exhaustion"),
    MOMO_MOEMOE_SLOW_DURATION_NAME("Durée slowness stade 1", "Stage 1 slowness duration"),
    MOMO_MOEMOE_SLOW_DURATION_DESC("Durée du Slowness appliqué au stade 1 (secondes)", "Slowness duration at stage 1 (seconds)"),
    MOMO_MOEMOE_KNOCKBACK_PCT_NAME("% HP stade 3", "Stage 3 HP %"),
    MOMO_MOEMOE_KNOCKBACK_PCT_DESC("Pourcentage de la vie actuelle perdu au stade 3 (0.0-1.0)", "Percentage of current HP lost at stage 3 (0.0-1.0)"),

    // ── Bamora ───────────────────────────────────────────────
    BAMORA_ABILITY_KAIJU_NAME("Kaiju", "Kaiju"),
    BAMORA_ABILITY_INVIS_NAME("Invisibilité", "Invisibility"),
    BAMORA_ABILITY_SYSTEME_NAME("Système", "System"),
    BAMORA_KAIJU_MAX_TIME_NAME("Temps max Kaiju", "Max Kaiju time"),
    BAMORA_KAIJU_MAX_TIME_DESC("Durée maximale de Kaiju en secondes", "Maximum Kaiju duration in seconds"),
    BAMORA_KAIJU_KILL_BONUS_NAME("Bonus kill Kaiju", "Kaiju kill bonus"),
    BAMORA_KAIJU_KILL_BONUS_DESC("Secondes ajoutées par kill sur Kaiju", "Seconds added per kill to Kaiju"),
    BAMORA_KAIJU_STRENGTH_PCT_NAME("Force Kaiju", "Kaiju strength"),
    BAMORA_KAIJU_STRENGTH_PCT_DESC("Pourcentage de force lors de Kaiju (0.0-1.0)", "Strength percentage during Kaiju (0.0-1.0)"),
    BAMORA_INVIS_DURATION_NAME("Durée invisibilité", "Invisibility duration"),
    BAMORA_INVIS_DURATION_DESC("Durée de l'invisibilité en secondes", "Invisibility duration in seconds"),
    BAMORA_INVIS_MAX_TIME_NAME("Temps max invisibilité", "Max invisibility time"),
    BAMORA_INVIS_MAX_TIME_DESC("Durée maximale de l'invisibilité (secondes)", "Maximum invisibility time (seconds)"),
    BAMORA_INVIS_KILL_PENALTY_NAME("Pénalité kill invisibilité", "Invisibility kill penalty"),
    BAMORA_INVIS_KILL_PENALTY_DESC("Secondes retirées par kill sur l'invisibilité", "Seconds removed per kill from invisibility"),

    // ── Seiko ────────────────────────────────────────────────
    SEIKO_ABILITY_BARRIER_MYSTIQUE_NAME("Barrière mystique", "Mystical barrier"),
    SEIKO_ABILITY_BARRIER_INTERIEUR_NAME("Barrière intérieure", "Inner barrier"),
    SEIKO_BARRIER_DURATION_NAME("Durée barrière", "Barrier duration"),
    SEIKO_BARRIER_DURATION_DESC("Durée de la barrière en secondes", "Barrier duration in seconds"),
    SEIKO_BARRIER_RADIUS_NAME("Rayon barrière", "Barrier radius"),
    SEIKO_BARRIER_RADIUS_DESC("Rayon de la zone de barrière (blocs)", "Barrier zone radius (blocks)"),

    // ── Kinta ────────────────────────────────────────────────
    KINTA_ABILITY_GREAT_KINTA_NAME("Great Kinta", "Great Kinta"),
    KINTA_ABILITY_ROCKET_PUNCH_NAME("Rocket Punch", "Rocket Punch"),
    KINTA_ABILITY_NANOSKIN_NAME("Nanoskin", "Nanoskin"),
    TSUCHINOKO_REGEN("Régénération passive", "Passive Regen"),
    KINTA_GREAT_KINTA_MAX_TIME_NAME("Temps max Great Kinta", "Max Great Kinta time"),
    KINTA_GREAT_KINTA_MAX_TIME_DESC("Durée maximale de Great Kinta en secondes", "Maximum Great Kinta duration in seconds"),
    KINTA_ROCKET_PUNCH_DAMAGE_NAME("Dégâts Rocket Punch", "Rocket Punch damage"),
    KINTA_ROCKET_PUNCH_DAMAGE_DESC("Dégâts infligés par le Rocket Punch", "Damage dealt by Rocket Punch"),
    KINTA_ROCKET_PUNCH_ZONE_NAME("Rayon zone Rocket Punch", "Rocket Punch zone radius"),
    KINTA_ROCKET_PUNCH_ZONE_DESC("Rayon de la zone de particules (blocs)", "Particle zone radius (blocks)"),

    // ── Kashimoto ────────────────────────────────────────────
    KASHIMOTO_ABILITY_FLAME_NAME("Flamme de Glace", "Ice Flame"),
    KASHIMOTO_ABILITY_ESPRIT_NAME("Esprit Protecteur", "Protective Spirit"),
    KASHIMOTO_ABILITY_APHOOM_NAME("Aphoom-Zhah", "Aphoom-Zhah"),
    KASHIMOTO_FLAME_DURATION_NAME("Durée flamme de glace", "Ice flame duration"),
    KASHIMOTO_FLAME_DURATION_DESC("Durée de la flamme de glace en secondes", "Ice flame duration in seconds"),
    KASHIMOTO_ESPRIT_STRENGTH_NAME("Force Esprit Protecteur", "Protective Spirit strength"),
    KASHIMOTO_ESPRIT_STRENGTH_DESC("Pourcentage de force de l'Esprit Protecteur (0.0-1.0)", "Protective Spirit strength percentage (0.0-1.0)"),

    // ── Enenra ───────────────────────────────────────────────
    ENENRA_ABILITY_SETTA_NAME("Setta", "Setta"),
    ENENRA_ABILITY_NINJA_NAME("Ninja", "Ninja"),
    ENENRA_SETTA_CRIT_DURATION_NAME("Durée critiques Setta", "Setta crit duration"),
    ENENRA_SETTA_CRIT_DURATION_DESC("Durée des coups critiques après Setta (secondes)", "Critical hit duration after Setta (seconds)"),
    ENENRA_NINJA_CLONE_COUNT_NAME("Nombre de clones Ninja", "Ninja clone count"),
    ENENRA_NINJA_CLONE_COUNT_DESC("Nombre de clones invoqués par Ninja", "Number of clones summoned by Ninja"),
    ENENRA_NINJA_DURATION_NAME("Durée Ninja", "Ninja duration"),
    ENENRA_NINJA_DURATION_DESC("Durée des clones en secondes", "Clone duration in seconds"),

    // ── Payase ───────────────────────────────────────────────
    PAYASE_ABILITY_DARKNESS_NAME("Darkness Form", "Darkness Form"),
    PAYASE_ABILITY_PERMUTATION_NAME("Permutation", "Permutation"),
    PAYASE_ABILITY_OMBRE_NAME("Ombre", "Shadow"),
    PAYASE_OMBRE_TRAP_COUNT_NAME("Nombre de pièges", "Trap count"),
    PAYASE_OMBRE_TRAP_COUNT_DESC("Nombre de particules-pièges posées", "Number of trap particles placed"),

    // ── Rokuro Serpo ─────────────────────────────────────────
    ROKURO_ABILITY_FORME_SERPO_NAME("Forme serpoienne", "Serpoan Form"),
    ROKURO_ABILITY_ORGUE_NAME("Orgue des six sens", "Organ of Six Senses"),
    ROKURO_ABILITY_ZONE_INCROYABLE_NAME("Zone incroyable", "Incredible Zone"),
    ROKURO_ABILITY_ZONE_INTOUCHABLE_NAME("Zone intouchable", "Untouchable Zone"),

    // ── M. Mantis ────────────────────────────────────────────
    MANTIS_ABILITY_BOXE_NAME("Boxe", "Boxing"),
    MANTIS_ABILITY_UPPERCUT_NAME("Salaire Uppercut", "Wage Uppercut"),
    MANTIS_ABILITY_JETWATER_NAME("Jet Water", "Jet Water"),
    MANTIS_ABILITY_CRABE_NAME("Crabe", "Crab"),
    MANTIS_BOXE_MAX_TIME_NAME("Temps max Boxe", "Max Boxing time"),
    MANTIS_BOXE_MAX_TIME_DESC("Durée maximale du 1v1 en secondes", "Maximum 1v1 duration in seconds"),
    MANTIS_BOXE_RADIUS_NAME("Rayon repousse", "Repel radius"),
    MANTIS_BOXE_RADIUS_DESC("Rayon de repousse des joueurs tiers (blocs)", "Radius to repel third-party players (blocks)"),

    // ── Jiangshi ─────────────────────────────────────────────
    JIANGSHI_ABILITY_SUMMON_NAME("Jiangshi", "Jiangshi"),
    JIANGSHI_ABILITY_ALLOUT_NAME("All-Out Jiangshi", "Jiangshi All-Out"),
    JIANGSHI_REVIVE_COOLDOWN_NAME("Cooldown survie", "Survival cooldown"),
    JIANGSHI_REVIVE_COOLDOWN_DESC("Temps entre deux survivances à un coup mortel (secondes)", "Time between two lethal survivals (seconds)"),
    JIANGSHI_BASE_ZOMBIES_NAME("Zombies de base", "Base zombies"),
    JIANGSHI_BASE_ZOMBIES_DESC("Nombre de zombies invoqués de base", "Base number of zombies summoned"),
    JIANGSHI_BASE_SKELETONS_NAME("Squelettes de base", "Base skeletons"),
    JIANGSHI_BASE_SKELETONS_DESC("Nombre de squelettes invoqués de base", "Base number of skeletons summoned"),
    OKARUN_PASSIVE_MEME_NAME("Mémé 100km/h (passif)", "Mémé 100km/h (passive)"),
    BAMORA_PASSIVE_PROJECTILE_NAME("Projectile (passif)", "Projectile (passive)"),
    SEIKO_PASSIVE_DIEU_REGION_NAME("Dieu de la région (passif)", "God of Region (passive)"),
    KINTA_PASSIVE_LUNETTE_NAME("Lunette (passif)", "Scope (passive)"),
    KASHIMOTO_PASSIVE_PROTECTEUR_NAME("Protecteur (passif)", "Protector (passive)"),
    ENENRA_PASSIVE_COMBO_NAME("Combo (passif)", "Combo (passive)"),
    ENENRA_PASSIVE_CORPS_FUMEE_NAME("Corps de Fumée (passif)", "Smoke Body (passive)"),
    PAYASE_PASSIVE_JOUR_NAME("Payase Jour (passif)", "Payase Day (passive)"),
    PAYASE_PASSIVE_NUIT_NAME("Payase Nuit (passif)", "Payase Night (passive)"),
    ROKURO_PASSIVE_SERUPO_NAME("Serupo (passif)", "Serupo (passive)"),
    ROKURO_PASSIVE_SEIJIN_NAME("Seijin (passif)", "Seijin (passive)"),
    ROKURO_CMD_FORME_NAME("Forme (commande)", "Forme (command)"),
    MANTIS_PASSIVE_BUSINESS_NAME("Business Man (passif)", "Business Man (passive)"),
    JIANGSHI_PASSIVE_KI_REGEN_NAME("KI Régénération (passif)", "KI Regen (passive)"),
    TSUCHINOKO_PASSIVE_VER_MORT_NAME("Ver de la Mort (passif)", "Death Worm (passive)"),
    OEIL_PASSIVE_ENVIE_MEURTR_NAME("Envie de meurtre (passif)", "Murder Urge (passive)"),
    OEIL_PASSIVE_JIJI_DETECT_NAME("Détection Jiji (passif)", "Jiji Detection (passive)"),
    JIJI_PASSIVE_ADAPTATION_NAME("Adaptation (passif)", "Adaptation (passive)"),
    FLATWOODS_PASSIVE_NAME("Flatwoods (passif)", "Flatwoods (passive)"),
    REIKO_PASSIVE_MIROIR_NAME("Miroir (passif)", "Mirror (passive)"),
    REIKO_PASSIVE_BOYFRIEND_NAME("Boyfriend (passif)", "Boyfriend (passive)"),
    JETBOOSTER_PASSIVE_TETE_HAUTE_NAME("Tête haute (passif)", "Head High (passive)"),
    JETBOOSTER_PASSIVE_ESQUIVE_NAME("Esquive (passif)", "Dodge (passive)"),
    ACROBATIQUE_PASSIVE_ACROBATE_NAME("Acrobate (passif)", "Acrobat (passive)"),
    NESSIE_PASSIVE_POISSON_NAME("Poisson (passif)", "Fish (passive)"),
    NESSIE_PASSIVE_COU_LARGE_NAME("Cou Large (passif)", "Wide Neck (passive)"),
    MINOTAURE_PASSIVE_OXYDATION_NAME("Oxydation (passif)", "Oxidation (passive)"),
    UMBRELLA_PASSIVE_NAME("Umbrella (passif)", "Umbrella (passive)"),
    DEVILMAN_PASSIVE_FLAMME_NAME("Flamme (passif)", "Flame (passive)"),
    CAESAR_PASSIVE_HAMON_NAME("Hamon (passif)", "Hamon (passive)"),
    CAESAR_PASSIVE_BANDANA_NAME("Bandana (passif)", "Bandana (passive)"),
    JOSEPH_PASSIVE_THOMSON_NAME("Thomson (passif)", "Thomson (passive)"),
    JOSEPH_PASSIVE_PREDICTION_NAME("Prédiction (passif)", "Prediction (passive)"),
    JOSEPH_PASSIVE_HERMIT_PURPLE_NAME("Hermit Purple (passif)", "Hermit Purple (passive)"),
    DOOMSLAYER_PASSIVE_DOOM_NAME("Doom (passif)", "Doom (passive)"),
    DOOMSLAYER_PASSIVE_CRUCIBLE_NAME("Crucible Blade (passif)", "Crucible Blade (passive)"),
    DENJI_PASSIVE_SANG_NAME("Sang (passif)", "Blood (passive)"),
    DENJI_PASSIVE_OUBLIE_NAME("Oublie (passif)", "Forget (passive)"),
    REZE_PASSIVE_BOMBE_NAME("Bombe (passif)", "Bomb (passive)"),
    REZE_PASSIVE_EXPLOSION_DJ_NAME("Explosion Double Jump (passif)", "Explosion Double Jump (passive)"),
    JOTARO_PASSIVE_REACTION_NAME("Réaction (passif)", "Reaction (passive)"),
    JOTARO_PASSIVE_DETECTIVE_NAME("Détective (passif)", "Detective (passive)"),
    DIO_PASSIVE_VAMPIRE_NAME("Vampire (passif)", "Vampire (passive)"),
    DIO_PASSIVE_ETOILE_JOESTAR_NAME("Étoile Joestar (passif)", "Joestar Star (passive)"),
    KIRA_PASSIVE_MAIN_NAME("Main (passif)", "Hand (passive)"),
    KIRA_PASSIVE_EXPLOSION_IMMUNE_NAME("Immunité Explosion (passif)", "Explosion Immunity (passive)"),
    KIRA_PASSIVE_NAME_NAME("My Name Is (passif)", "My Name Is (passive)"),
    POLNAREFF_PASSIVE_FRANCAIS_NAME("Français (passif)", "French (passive)"),
    POLNAREFF_PASSIVE_PRECISION_NAME("Précision (passif)", "Precision (passive)"),
    ROHAN_PASSIVE_ECRIVAIN_NAME("Écrivain (passif)", "Writer (passive)"),

    // ── ScenarioVariables DanDaDan ────────────────────────────
    SCENARIO_ENABLE_DOOM_ARENA_NAME("Activer Arène Doom", "Enable Doom Arena"),
    SCENARIO_ENABLE_DOOM_ARENA_DESC("Active l'arène Doom au début de la partie", "Enables the Doom arena at game start"),
    SCENARIO_ENABLE_AURA_NAME("Activer Auras", "Enable Auras"),
    SCENARIO_ENABLE_AURA_DESC("Affiche les pastilles de couleur (aura) des yokais", "Shows yokai color aura badges"),
    SCENARIO_ENABLE_KINTAMA_NAME("Activer Kintamas", "Enable Kintamas"),
    SCENARIO_ENABLE_KINTAMA_DESC("Active l'apparition des Kintamas sur la map", "Enables Kintama spawning on the map"),
    SCENARIO_ENABLE_ESPACE_VIDE_NAME("Activer Espace Vide", "Enable Empty Space"),
    SCENARIO_ENABLE_ESPACE_VIDE_DESC("Active la dimension Espace Vide", "Enables the Empty Space dimension"),
    SCENARIO_ENABLE_AMOUR_NAME("Activer Système Amour", "Enable Love System"),
    SCENARIO_ENABLE_AMOUR_DESC("Active le système romantique (Denji/Reze, Okarun/Momo)", "Enables the love system (Denji/Reze, Okarun/Momo)"),
    SCENARIO_ENABLE_MORIOH_RADIO_NAME("Activer Radio Morioh", "Enable Morioh Radio"),
    SCENARIO_ENABLE_MORIOH_RADIO_DESC("Active les broadcasts de la Radio Morioh-Cho", "Enables Morioh-Cho Radio broadcasts"),
    SCENARIO_ENABLE_TYPHOON_HUMAN_NAME("Activer Typhoon Human", "Enable Typhoon Human"),
    SCENARIO_ENABLE_TYPHOON_HUMAN_DESC("Active l'événement Typhoon Human", "Enables the Typhoon Human event"),
    SCENARIO_ENABLE_WORLD_DANDADAN_NAME("Activer Monde DanDaDan", "Enable DanDaDan World"),
    SCENARIO_ENABLE_WORLD_DANDADAN_DESC("Active la dimension et les portails DanDaDan", "Enables the DanDaDan dimension and portals"),
    SCENARIO_DOOM_DELAY_NAME("Délai Arène Doom", "Doom Arena Delay"),
    SCENARIO_DOOM_DELAY_DESC("Délai en secondes avant le lancement de l'arène Doom", "Delay in seconds before the Doom arena launches"),
    SCENARIO_PORTAL_RADIUS_NAME("Rayon portails", "Portal radius"),
    SCENARIO_PORTAL_RADIUS_DESC("Distance en blocs des portails par rapport au spawn", "Distance in blocks of portals from spawn"),
    ;

    private final Map<String, String> translations;

    DanDaDanVarLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_US", en);
    }

    @Override public String getKey()                        { return "dandadanvar." + name(); }
    @Override public Map<String, String> getTranslations()  { return translations; }
}
