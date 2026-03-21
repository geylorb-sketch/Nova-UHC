package net.novauhc.dandadan.lang;

import net.novaproject.novauhc.lang.Lang;

import java.util.Map;

/**
 * DanDaDanLang — Messages classiques (portails, zones, épreuves, mort, abilities).
 */
public enum DanDaDanLang implements Lang {

    // ── Portails ──
    PORTAL_ENTER("§5§l✦ §dVous entrez dans le DanDaDan ! Explorez et trouvez votre Yokai.", "§5§l✦ §dYou enter the DanDaDan! Explore and find your Yokai."),

    // ── Zones Yokai ──
    YOKAI_ZONE_ENTERED("§5§l✦ §dZone Yokai détectée : §f%yokai% §d— Invocation en cours...", "§5§l✦ §dYokai zone detected: §f%yokai% §d— Invoking..."),
    YOKAI_TRIAL_START("§5§l✦ §dÉpreuve de §f%yokai% §d! Accepte le pacte (§f/ddd accept§d) ou saute dans le vide.", "§5§l✦ §dTrial of §f%yokai% §d! Accept the pact (§f/ddd accept§d) or jump into the void."),
    YOKAI_PACT_ACCEPTED("§a§l✦ §aPacte de §f%yokai% §aaccepté ! Bienvenue dans le combat.", "§a§l✦ §aPact of §f%yokai% §aaccepted! Welcome to the fight."),
    YOKAI_PACT_REFUSED("§c§l✦ §cPacte refusé. Retour dans le DanDaDan...", "§c§l✦ §cPact refused. Returning to DanDaDan..."),
    YOKAI_ALREADY_CLAIMED("§c✘ Ce Yokai a déjà été réclamé par un autre joueur !", "§c✘ This Yokai has already been claimed!"),
    YOKAI_ALREADY_HAVE("§c✘ Vous avez déjà un Yokai !", "§c✘ You already have a Yokai!"),

    // ── Broadcast ──
    YOKAI_CLAIMED_BROADCAST("§5§l✦ §f%player% §da obtenu la malédiction de §f%yokai% §d!", "§5§l✦ §f%player% §dobtained the curse of §f%yokai% §d!"),

    // ── Mort ──
    DEATH_MESSAGE("§5§l✦ §f%player% §7est mort. Il était §5%yokai%§7.", "§5§l✦ §f%player% §7died. They were §5%yokai%§7."),

    // ── Okarun — Messages abilities ──
    OKARUN_MALEDICTION_ON("§5§l✦ Malédiction activée ! §r§d30%% speed pendant %duration%s.", "§5§l✦ Curse activated! §r§d30%% speed for %duration%s."),
    OKARUN_ALLOUT_DASH("§5§l✦ All-Out ! §r§dDash de %distance% blocs !", "§5§l✦ All-Out! §r§d%distance% blocks dash!"),
    OKARUN_ALLOUT_HIT("§c§l✦ All-Out ! §r§cDoigt coupé pendant %duration%s !", "§c§l✦ All-Out! §r§cFinger cut for %duration%s!"),
    OKARUN_TOUBILLION_START("§b§l✦ Toubillion ! §r§bSpectateur pendant %duration%s...", "§b§l✦ Toubillion! §r§bSpectator for %duration%s..."),
    OKARUN_TOUBILLION_END("§a§l✦ Toubillion terminé ! §r§a+%hearts%❤ régénérés.", "§a§l✦ Toubillion over! §r§a+%hearts%❤ regenerated."),
    OKARUN_RYTHME_ON("§e§l♪ Rythme activé ! §r§eMets %hits% coups pour le no-hit delay !", "§e§l♪ Rhythm activated! §r§eHit %hits% times for no-hit delay!"),
    OKARUN_RYTHME_COUNT("§e♪ Rythme : %count%/%max%", "§e♪ Rhythm: %count%/%max%"),
    OKARUN_RYTHME_TRIGGERED("§a§l♪ Rythme déclenché ! §r§aNo-hit delay 60%% pendant %duration%s !", "§a§l♪ Rhythm triggered! §r§aNo-hit delay 60%% for %duration%s!"),
    OKARUN_COURSE_CHALLENGE("§e§l♪ Course ! §r§eDéfi lancé contre §f%target%§e !", "§e§l♪ Race! §r§eChallenge sent to §f%target%§e!"),
    OKARUN_COURSE_CHALLENGED("§e§l♪ Course ! §r§e%player% §ete défie en course !", "§e§l♪ Race! §r§e%player% §echallenges you!"),
    OKARUN_TUNNEL_ON("§5§l✦ Tunnel activé ! §r§5Malédiction gratuite %duration%s !", "§5§l✦ Tunnel activated! §r§5Free curse %duration%s!"),
    OKARUN_TUNNEL_ENTER("§5§l✦ Tunnel ! §r§5Aspiré dans l'espace vide d'Okarun !", "§5§l✦ Tunnel! §r§5Sucked into Okarun's void space!"),
    OKARUN_TUNNEL_END("§a✦ Le Tunnel s'est refermé !", "§a✦ The Tunnel has closed!"),
    OKARUN_KILL_BONUS("§5§l✦ §d+1min de malédiction ! §7(%time%min restantes)", "§5§l✦ §d+1min curse! §7(%time%min remaining)"),
    OKARUN_NO_PLAYERS("§c✘ Aucun joueur à proximité !", "§c✘ No players nearby!"),
    OKARUN_COURSE_USAGE("§c✘ Usage : /ddd course <pseudo>", "§c✘ Usage: /ddd course <player>"),
    OKARUN_COURSE_NOT_FOUND("§c✘ Joueur introuvable : %name%", "§c✘ Player not found: %name%"),
    OKARUN_COURSE_SELF("§c✘ Tu ne peux pas te défier toi-même !", "§c✘ You can't challenge yourself!"),

    // ── Momo — Messages abilities ──
    MOMO_MOEMOE_ON("§5§l✦ Moe Moe activé ! §r§dCompteur de coups actif.", "§5§l✦ Moe Moe activated! §r§dHit counter active."),
    MOMO_MOEMOE_END("§7§l✦ Moe Moe terminé ! §r§7Max coups atteint.", "§7§l✦ Moe Moe ended! §r§7Max hits reached."),
    MOMO_MOEMOE_GREEN("§a§l🟢 §r§aStade vert : §fSlowness 2 (10s)", "§a§l🟢 §r§aGreen stage: §fSlowness 2 (10s)"),
    MOMO_MOEMOE_ORANGE("§6§l🟠 §r§6Stade orange : §fLave sous les pieds !", "§6§l🟠 §r§6Orange stage: §fLava underfoot!"),
    MOMO_MOEMOE_RED("§c§l🔴 §r§cStade rouge : §fPropulsé + -30%% vie !", "§c§l🔴 §r§cRed stage: §fLaunched + -30%% HP!"),
    MOMO_MEMOIRE_FOUND("§d§l✦ Mémoire ! §r§dJoueur le plus proche : %coords% §7(%direction%§7)", "§d§l✦ Memory! §r§dNearest player: %coords% §7(%direction%§7)"),
    MOMO_MEMOIRE_NONE("§c✘ Aucun joueur détecté.", "§c✘ No player detected."),
    MOMO_PSYCHO_RIGHT("§b§l✦ Psychokinésie ! §r§bJoueur capturé dans la main !", "§b§l✦ Psychokinesis! §r§bPlayer captured in hand!"),
    MOMO_PSYCHO_LEFT("§b§l✦ Psychokinésie ! §r§b3 blocs de pierre lancés !", "§b§l✦ Psychokinesis! §r§b3 stone blocks launched!"),
    MOMO_PSYCHO_NO_TARGET("§c✘ Aucun joueur visé !", "§c✘ No player targeted!"),
    MOMO_SERVEUSE_TRIGGER("§d§l✦ Serveuse ! §r§dKB annulé !", "§d§l✦ Waitress! §r§dKB cancelled!"),

    // ── CMD / Config UI ──
    CMD_PLAYERS_ONLY("§c✘ Joueurs uniquement.", "§c✘ Players only."),
    CMD_NOT_IN_TRIAL("§c✘ Tu n'es pas en épreuve Yokai.", "§c✘ You are not in a Yokai trial."),
    CMD_NO_PERMISSION("§c✘ Permission insuffisante.", "§c✘ Insufficient permission."),
    CMD_TOGGLE_USAGE("§c✘ Usage : /ddd toggle <yokai>", "§c✘ Usage: /ddd toggle <yokai>"),
    CMD_TOGGLE_RESULT("§5[DanDaDan] §f%yokai% : %status%", "§5[DanDaDan] §f%yokai%: %status%"),
    CMD_TOGGLE_UNKNOWN("§c✘ Yokai inconnu : %name%", "§c✘ Unknown Yokai: %name%"),
    CMD_RELOAD_DONE("§a✔ dandadan.yml rechargé ! Zones et positions mises à jour.", "§a✔ dandadan.yml reloaded! Zones and positions updated."),
    CMD_HELP_HEADER("§5§m──────────§5 DanDaDan §5§m──────────", "§5§m──────────§5 DanDaDan §5§m──────────"),
    CMD_HELP_ACCEPT("  §d/ddd accept §7— Accepter le pacte Yokai", "  §d/ddd accept §7— Accept the Yokai pact"),
    CMD_HELP_YOKAI("  §d/ddd yokai §7— Liste des Yokai", "  §d/ddd yokai §7— List Yokai"),
    CMD_HELP_CONFIG("  §d/ddd config §7— Menu de configuration", "  §d/ddd config §7— Configuration menu"),
    CMD_HELP_TOGGLE("  §d/ddd toggle <yokai> §7— Activer/désactiver un Yokai", "  §d/ddd toggle <yokai> §7— Toggle a Yokai"),
    CMD_HELP_RELOAD("  §d/ddd reload §7— Recharger dandadan.yml", "  §d/ddd reload §7— Reload dandadan.yml"),
    CMD_YOKAI_HEADER("§5§m──────────§5 Yokai §5§m──────────", "§5§m──────────§5 Yokai §5§m──────────"),
    CMD_YOKAI_LINE("  %type% §f%name% %status%", "  %type% §f%name% %status%"),
    CMD_YOKAI_ENABLED("§aActivé", "§aEnabled"),
    CMD_YOKAI_DISABLED("§cDésactivé", "§cDisabled"),
    CMD_YOKAI_CLAIMED("§6Réclamé", "§6Claimed"),
    CONFIG_ALL_ENABLED("§a✔ Tous les Yokai activés.", "§a✔ All Yokai enabled."),
    CONFIG_SPECIAL_DISABLED("§c✔ Tous les Yokai spéciaux désactivés.", "§c✔ All special Yokai disabled."),
    CONFIG_TYPE_ENABLED("§a✔ Tous les %type% activés.", "§a✔ All %type% enabled."),
    CONFIG_TYPE_DISABLED("§c✔ Tous les %type% désactivés.", "§c✔ All %type% disabled."),

    // ── Bamora — Messages abilities ──
    BAMORA_PROJECTILE_TRIGGER("§e§l⚡ Projectile électrique ! §r§eRangée d'éclairs !", "§e§l⚡ Electric projectile! §r§eLightning row!"),
    BAMORA_KAIJU_ON("§6§l✦ Kaiju activé ! §r§6Bloc d'or (coeur) placé. 15%% force.", "§6§l✦ Kaiju activated! §r§6Gold block (heart) placed. 15%% strength."),
    BAMORA_KAIJU_BROKEN("§c§l✦ Kaiju désactivé ! §r§cLe coeur a été cassé !", "§c§l✦ Kaiju deactivated! §r§cThe heart has been broken!"),
    BAMORA_INVIS_ON("§7§l✦ Invisibilité activée ! §r§745s.", "§7§l✦ Invisibility activated! §r§745s."),
    BAMORA_SYSTEME_TP("§a§l✦ Système ! §r§aTP au bloc d'or.", "§a§l✦ System! §r§aTP to gold block."),
    BAMORA_SYSTEME_INACTIVE("§c✘ Kaiju n'est pas actif.", "§c✘ Kaiju is not active."),
    BAMORA_SLAUGHTER_ON("§6§l✦ Slaughter Mode ! §r§6Golem activé : 20%% force + 20%% speed.", "§6§l✦ Slaughter Mode! §r§6Golem activated: 20%% strength + 20%% speed."),
    BAMORA_VILLE_ENTER("§5§l✦ Ville ! §r§5Aspiré dans l'espace vide de Bamora !", "§5§l✦ City! §r§5Sucked into Bamora's void space!"),
    BAMORA_VILLE_END("§a✦ La Ville s'est refermée !", "§a✦ The City has closed!"),
    BAMORA_VILLE_NO_PLAYERS("§c✘ Aucun joueur à proximité !", "§c✘ No players nearby!"),

    // ── Seiko — Messages abilities ──
    SEIKO_MOTS_NESSIE("§b§l✦ Nessie ! §r§bCoup critique x1.5 !", "§b§l✦ Nessie! §r§bCritical hit x1.5!"),
    SEIKO_MOTS_ONGLE("§e§l✦ Ongle ! §r§eFleche bonus + armure cassée 1s !", "§e§l✦ Nail! §r§eBonus arrow + armor break 1s!"),
    SEIKO_MOTS_HARISEN("§d§l✦ Harisen ! §r§dPouvoirs adverses bloqués 1min !", "§d§l✦ Harisen! §r§dEnemy powers blocked 1min!"),
    SEIKO_BARRIERE_M_ON("§c§l✦ Barrière mystique activée !", "§c§l✦ Mystic Barrier activated!"),
    SEIKO_BARRIERE_I_ON("§6§l✦ Barrière intérieure activée !", "§6§l✦ Inner Barrier activated!"),
    SEIKO_BARRIERE_NO_TARGET("§c✘ Aucun joueur visé !", "§c✘ No player targeted!"),

    // ── Kinta — Messages abilities ──
    KINTA_GREAT_ON("§6§l✦ Great Kinta ! §r§6Armure en or équipée.", "§6§l✦ Great Kinta! §r§6Gold armor equipped."),
    KINTA_DEDALE_ON("§e§l✦ Dédale Acha ! §r§eSpeed cumulable activée.", "§e§l✦ Dedale Acha! §r§eStackable speed activated."),
    KINTA_NANOSKIN_MENU("§d/ddd nanoskin <jetpack|epingle|gantelets>", "§d/ddd nanoskin <jetpack|pin|gauntlets>"),
    KINTA_NANOSKIN_JETPACK("§b§l✦ Nanoskin Jetpack ! §r§bEnvol < 2 coeurs.", "§b§l✦ Nanoskin Jetpack! §r§bFly < 2 hearts."),
    KINTA_NANOSKIN_EPINGLE("§e§l✦ Nanoskin Épingle ! §r§eIgnore anti-frappe.", "§e§l✦ Nanoskin Pin! §r§eIgnore anti-hit."),
    KINTA_NANOSKIN_GANTELETS("§6§l✦ Nanoskin Gantelets ! §r§6Ignore force/résist.", "§6§l✦ Nanoskin Gauntlets! §r§6Ignore strength/resist."),

    // ── Kashimoto — Messages abilities ──
    KASHIMOTO_PROTECTEUR_TRIGGER("§9§l✦ Protecteur ! §r§9Armure fer Prot II temporaire.", "§9§l✦ Protector! §r§9Temporary iron Prot II armor."),
    KASHIMOTO_FLAMME_ON("§b§l✦ Flamme de glace ! §r§bJoueur enflammé + enchant réduit.", "§b§l✦ Ice Flame! §r§bPlayer ignited + enchant reduced."),
    KASHIMOTO_ESPRIT_ON("§e§l✦ Esprit Protecteur ! §r§e20%% force + PNJ.", "§e§l✦ Protective Spirit! §r§e20%% strength + NPC."),
    KASHIMOTO_APHOOM_ON("§9§l✦ Aphoom-Zhah ! §r§9Bloc de glace ! Joueurs attirés.", "§9§l✦ Aphoom-Zhah! §r§9Ice block! Players pulled."),

    // ── Config UI (CustomInventory) ──
    CONFIG_UI_TITLE("§5§lDanDaDan §8— Configuration", "§5§lDanDaDan §8— Configuration"),
    CONFIG_NORMAL_TITLE("§5§lYokai §7— Normaux", "§5§lYokai §7— Normal"),
    CONFIG_SPECIAL_TITLE("§6§lYokai §7— Spéciaux", "§6§lYokai §7— Special"),
    CONFIG_ENABLED_COUNT("activés", "enabled"),
    CONFIG_CLICK_TO_CONFIGURE("§eCliquez pour configurer", "§eClick to configure"),
    CONFIG_CLICK_TO_TOGGLE("§eCliquez pour activer/désactiver", "§eClick to toggle"),
    CONFIG_QUICK_ACTIONS("§a§lActions rapides", "§a§lQuick actions"),
    CONFIG_LEFT_ENABLE_ALL("§7Clic gauche : §aActiver tous", "§7Left click: §aEnable all"),
    CONFIG_RIGHT_DISABLE_SPECIAL("§7Clic droit : §cDésactiver spéciaux", "§7Right click: §cDisable special"),
    CONFIG_ENABLE_ALL_TYPE("§a§lActiver tous", "§a§lEnable all"),
    CONFIG_DISABLE_ALL_TYPE("§c§lDésactiver tous", "§c§lDisable all"),
    CONFIG_YOKAI_CLAIMED("§6✦ Déjà réclamé", "§6✦ Already claimed"),
    PACT_MENU_TITLE_SUFFIX("Pacte", "Pact"),

    GENERIC_ABILITY_ON("§5§l✦ %name% activé !", "§5§l✦ %name% activated!"),

    // ── Enenra ──
    ENENRA_SETTA_ON("§8§l✦ Setta ! §r§83s immobile puis TP...", "§8§l✦ Setta! §r§83s still then TP..."),
    ENENRA_FUMEE_ON("§7§l✦ Corps de fumée ! §r§775%% réduction 2min.", "§7§l✦ Smoke Body! §r§775%% reduction 2min."),
    ENENRA_NINJA_ON("§5§l✦ Ninja ! §r§510 clones invoqués !", "§5§l✦ Ninja! §r§510 clones summoned!"),

    // ── Payase ──
    PAYASE_DARKNESS_ON("§8§l✦ Darkness Form activée !", "§8§l✦ Darkness Form activated!"),
    PAYASE_PERMUTATION_ON("§5§l✦ Permutation ! §r§5Ombre 15s.", "§5§l✦ Swap! §r§5Shadow 15s."),
    PAYASE_OMBRE_ON("§0§l✦ Ombre activée ! §r§0Particules noires.", "§0§l✦ Shadow activated! §r§0Black particles."),


    // ── Menu de pacte ──
    PACT_MENU_DESC_LINE("§7Acceptez la malédiction de ce Yokai", "§7Accept the curse of this Yokai"),
    PACT_MENU_YOKAI_TYPE("§7Type : §f%type%", "§7Type: §f%type%"),
    PACT_MENU_ACCEPT("§a§lAccepter le pacte", "§a§lAccept the pact"),
    PACT_MENU_ACCEPT_LORE("§7Vous obtiendrez les pouvoirs de ce Yokai.", "§7You will obtain this Yokai's powers."),
    PACT_MENU_REFUSE("§c§lRefuser le pacte", "§c§lRefuse the pact"),
    PACT_MENU_REFUSE_LORE("§7Retour dans le DanDaDan.", "§7Return to DanDaDan."),
    PACT_MENU_INFO_CAMP("§e§lCamp", "§e§lCamp"),
    PACT_MENU_INFO_OBJECTIVE("§e§lObjectif", "§e§lObjective"),
    PACT_MENU_INFO_POWERS("§d§lPouvoirs", "§d§lPowers"),
    PACT_MENU_INFO_POWERS_LORE("§7Découvrez vos pouvoirs après le pacte.", "§7Discover your powers after the pact."),
    PACT_ACCEPT_TITLE("§a§l✦ Pacte accepté !", "§a§l✦ Pact accepted!"),

    // ── Titles épreuve ──
    TRIAL_TITLE_SUBTITLE("§dAccepte le pacte via le PNJ", "§dAccept the pact via the NPC"),

    // ── Course ──
    COURSE_ALREADY_ACTIVE("§c✘ Une course est déjà en cours !", "§c✘ A race is already in progress!"),
    COURSE_NOT_CONFIGURED("§c✘ La course n'est pas configurée dans dandadan.yml.", "§c✘ The course is not configured in dandadan.yml."),
    COURSE_GO_TITLE("§a§lGO !", "§a§lGO!"),
    COURSE_WIN("§a§l✦ Victoire ! §r§a+1❤ ! §7(%loser% a perdu)", "§a§l✦ Victory! §r§a+1❤! §7(%loser% lost)"),
    COURSE_LOSE("§c§l✦ Défaite... §r§c%winner% a gagné la course.", "§c§l✦ Defeat... §r§c%winner% won the race."),
    COURSE_TIMEOUT("§7§l✦ Course terminée ! §r§7Aucun gagnant (timeout).", "§7§l✦ Race ended! §r§7No winner (timeout)."),

    // ── Items (noms affichés dans l'inventaire) ──
    ITEM_MALEDICTION("§5Malédiction / All-Out", "§5Curse / All-Out"),
    ITEM_RYTHME("§eRythme", "§eRhythm"),
    ITEM_TUNNEL("§5Tunnel", "§5Tunnel"),
    ITEM_MOE_MOE("§5Moe Moe", "§5Moe Moe"),
    ITEM_PSYCHOKINESIE("§bPsychokinésie §7(Droit: Main / Gauche: Pierres)", "§bPsychokinesis §7(Right: Hand / Left: Stones)"),
    ITEM_KAIJU("§6Kaiju", "§6Kaiju"),
    ITEM_INVISIBILITE("§7Invisibilité", "§7Invisibility"),
    ITEM_VILLE("§5Ville", "§5City"),
    ITEM_BARRIERE_M("§cBarrière mystique", "§cMystic Barrier"),
    ITEM_BARRIERE_I("§6Barrière intérieure", "§6Inner Barrier"),
    ITEM_GREAT_KINTA("§6Great Kinta / Dédale Acha", "§6Great Kinta / Dedale Acha"),
    ITEM_FLAMME_GLACE("§bFlamme de glace", "§bIce Flame"),
    ITEM_ESPRIT("§eEsprit Protecteur", "§eProtective Spirit"),
    ITEM_APHOOM("§9Aphoom-Zhah", "§9Aphoom-Zhah"),

    // ── UI Inventaire (titres et labels) ──
    UI_CONFIG_TITLE("§5§lDanDaDan §8— Configuration", "§5§lDanDaDan §8— Configuration"),
    UI_YOKAI_NORMAL_TITLE("§5§lYokai §7— Normaux", "§5§lYokai §7— Normal"),
    UI_YOKAI_SPECIAL_TITLE("§6§lYokai §7— Spéciaux", "§6§lYokai §7— Special"),
    UI_NORMAL_BUTTON("§5§lYokai Normaux", "§5§lNormal Yokai"),
    UI_SPECIAL_BUTTON("§6§lYokai Spéciaux", "§6§lSpecial Yokai"),
    UI_QUICK_ACTIONS("§a§lActions rapides", "§a§lQuick actions"),
    UI_QUICK_LEFT("§7Clic gauche : §aActiver tous", "§7Left click: §aEnable all"),
    UI_QUICK_RIGHT("§7Clic droit : §cDésactiver tous les spéciaux", "§7Right click: §cDisable all special"),
    UI_ENABLED("§aActivé", "§aEnabled"),
    UI_DISABLED("§cDésactivé", "§cDisabled"),
    UI_CLAIMED("§6✦ Déjà réclamé", "§6✦ Already claimed"),
    UI_CLICK_CONFIGURE("§eCliquez pour configurer", "§eClick to configure"),
    UI_CLICK_TOGGLE("§eCliquez pour %action%", "§eClick to %action%"),
    UI_BACK("§7← Retour", "§7← Back"),
    UI_ENABLE_ALL("§a§lActiver tous", "§a§lEnable all"),
    UI_DISABLE_ALL("§c§lDésactiver tous", "§c§lDisable all"),
    UI_X_OF_Y("§7%enabled%/%total% activés", "§7%enabled%/%total% enabled"),


    // ── Trial NPC + Pact Menu ──
    TRIAL_NPC_SPAWNED("§5§l✦ §dCliquez sur le PNJ §f%yokai% §dpour accepter le pacte.", "§5§l✦ §dClick on the §f%yokai% §dNPC to accept the pact."),
    TRIAL_NPC_NOT_YOURS("§c✘ Ce n'est pas votre PNJ d'épreuve.", "§c✘ This is not your trial NPC."),
    PACT_MENU_REFUSED_MSG("§7Le pacte reste ouvert. Cliquez sur le PNJ pour réessayer.", "§7The pact remains open. Click the NPC to try again."),
    PACT_MENU_YOKAI_LORE_1("§7Acceptez le pacte de ce Yokai", "§7Accept this Yokai's pact"),
    PACT_MENU_YOKAI_LORE_2("§7pour obtenir ses pouvoirs.", "§7to obtain its powers."),
    PACT_MENU_YOKAI_LORE_3("§eCliquez pour ouvrir la description.", "§eClick to open the description."),

    KASHIMOTO_NO_TARGET("§c✘ Aucun joueur visé !", "§c✘ No player targeted!"),

    // ── Trial NPC + Menu Pacte ──
    TRIAL_NPC_HINT("§d§l✦ §dCliquez sur §f%yokai% §dpour accepter le pacte !", "§d§l✦ §dClick on §f%yokai% §dto accept the pact!"),
    PACT_MENU_TITLE("§5§l✦ Pacte Yokai", "§5§l✦ Yokai Pact"),
    PACT_MENU_YOKAI_LORE("§7Acceptez ce pacte pour obtenir ses pouvoirs.", "§7Accept this pact to gain its powers."),
    PACT_MENU_CLICK_HINT("§8Cliquez sur les boutons ci-dessous.", "§8Click the buttons below."),

    PACT_MENU_DETAILS("§e§lDétails du rôle", "§e§lRole details"),
    PACT_MENU_DETAILS_LORE("§7Cliquez pour voir les pouvoirs.", "§7Click to see the abilities."),
    PACT_MENU_REFUSED_HINT("§7Sautez dans le vide pour confirmer le refus.", "§7Jump into the void to confirm refusal."),

    // ── Course ──
    COURSE_CHECKPOINT("§a✓ Checkpoint %num%/%total% !", "§a✓ Checkpoint %num%/%total%!"),


    // ── Items (noms des objets donnés aux joueurs) ──
    ITEM_OKARUN_CURSE("§5Malédiction / All-Out", "§5Curse / All-Out"),
    ITEM_OKARUN_RYTHME("§eRythme", "§eRhythm"),
    ITEM_OKARUN_TUNNEL("§5Tunnel", "§5Tunnel"),
    ITEM_MOMO_MOEMOE("§5Moe Moe", "§5Moe Moe"),
    ITEM_MOMO_PSYCHO("§bPsychokinésie", "§bPsychokinesis"),
    ITEM_BAMORA_KAIJU("§6Kaiju", "§6Kaiju"),
    ITEM_BAMORA_INVIS("§7Invisibilité", "§7Invisibility"),
    ITEM_BAMORA_VILLE("§5Ville", "§5City"),
    ITEM_SEIKO_BARRIERE_M("§cBarrière mystique", "§cMystic Barrier"),
    ITEM_SEIKO_BARRIERE_I("§6Barrière intérieure", "§6Inner Barrier"),
    ITEM_KINTA_GREAT("§6Great Kinta / Dédale Acha", "§6Great Kinta / Dedale Acha"),
    ITEM_KASHIMOTO_FLAMME("§bFlamme de glace", "§bIce Flame"),
    ITEM_KASHIMOTO_ESPRIT("§eEsprit Protecteur", "§eProtective Spirit"),
    ITEM_KASHIMOTO_APHOOM("§9Aphoom-Zhah", "§9Aphoom-Zhah"),

    // ── Items générés (noms abilities) ──
    ITEM_ACROBATIQUE_5TRANSFORMATION("§5Transformation", "§5Transformation"),
    ITEM_ACROBATIQUE_DCHEVEUX("§dCheveux", "§dCheveux"),
    ITEM_CAESAR_BSAVON_LAUNCHER("§bSavon Launcher", "§bSavon Launcher"),
    ITEM_CAESAR_CSAVON_CUTTER("§cSavon Cutter", "§cSavon Cutter"),
    ITEM_CAESAR_ESAVON_LENSES("§eSavon Lenses", "§eSavon Lenses"),
    ITEM_CSG_6CARTE("§6Carte", "§6Carte"),
    ITEM_CSG_EFIOLE("§eFiole", "§eFiole"),
    ITEM_CSG_FCOUTEAU("§fCouteau", "§fCouteau"),
    ITEM_DENJI_5MALEDICTION("§5Malediction", "§5Malediction"),
    ITEM_DENJI_7CHAINE("§7Chaine", "§7Chaine"),
    ITEM_DENJI_CCHAINSAW_MAN("§cChainsaw Man", "§cChainsaw Man"),
    ITEM_DEVILMAN_5MALEDICTION("§5Malediction", "§5Malediction"),
    ITEM_DEVILMAN_6CHALEUR("§6Chaleur", "§6Chaleur"),
    ITEM_DEVILMAN_CCROC("§cCroc", "§cCroc"),
    ITEM_DIO_5ARRET_DU_TEMPS("§5Arret du Temps", "§5Arret du Temps"),
    ITEM_DIO_6ROAD_ROLLER("§6Road Roller", "§6Road Roller"),
    ITEM_DIO_7COUTEAU("§7Couteau", "§7Couteau"),
    ITEM_DIO_CTHE_WORLD("§cThe World", "§cThe World"),
    ITEM_DIO_DTIME_SKIP("§dTime Skip", "§dTime Skip"),
    ITEM_DOOMSLAYER_CCRUCIBLE_BLADE("§cCrucible Blade", "§cCrucible Blade"),
    ITEM_ENENRA_5NINJA("§5Ninja", "§5Ninja"),
    ITEM_ENENRA_7CORPS_DE_FUMEE("§7Corps de fumee", "§7Corps de fumee"),
    ITEM_ENENRA_8SETTA("§8Setta", "§8Setta"),
    ITEM_FLATWOODS_8FUMEE("§8Fumee", "§8Fumee"),
    ITEM_JETBOOSTER_BAVIATE("§bAviate", "§bAviate"),
    ITEM_JETBOOSTER_ESUPERCHARGE("§eSupercharge", "§eSupercharge"),
    ITEM_JETBOOSTER_FBOOST("§fBoost", "§fBoost"),
    ITEM_JIANGSHI_7INVOCATION("§7Invocation", "§7Invocation"),
    ITEM_JIANGSHI_CALLOUT("§cAll-Out", "§cAll-Out"),
    ITEM_JIJI_DSPIRALES("§dSpirales", "§dSpirales"),
    ITEM_JOSEPH_6CLACKER("§6Clacker", "§6Clacker"),
    ITEM_JOSEPH_CREBUFF("§cRebuff", "§cRebuff"),
    ITEM_JOSEPH_EHAMON_OVERDRIVE("§eHamon Overdrive", "§eHamon Overdrive"),
    ITEM_JOTARO_5ARRET_DU_TEMPS("§5Arret du Temps", "§5Arret du Temps"),
    ITEM_JOTARO_6ORA_ORA("§6Ora Ora", "§6Ora Ora"),
    ITEM_JOTARO_9STAR_FINGER("§9Star Finger", "§9Star Finger"),
    ITEM_JOTARO_DSTAR_PLATINUM("§dStar Platinum", "§dStar Platinum"),
    ITEM_KIRA_5BITES_THE_DUST("§5Bites the Dust", "§5Bites the Dust"),
    ITEM_KIRA_ASTRAYCAT("§aStrayCat", "§aStrayCat"),
    ITEM_KIRA_CSHEER_HEART_ATTACK("§cSheer Heart Attack", "§cSheer Heart Attack"),
    ITEM_KIRA_DKILLER_QUEEN("§dKiller Queen", "§dKiller Queen"),
    ITEM_MANTIS_6UPPERCUT("§6Uppercut", "§6Uppercut"),
    ITEM_MANTIS_9JET_WATER("§9Jet Water", "§9Jet Water"),
    ITEM_MANTIS_CBOXE("§cBoxe", "§cBoxe"),
    ITEM_MANTIS_ECRABE("§eCrabe", "§eCrabe"),
    ITEM_MINOTAURE_7DURABILITE("§7Durabilite", "§7Durabilite"),
    ITEM_MINOTAURE_CKUNG_FU("§cKung Fu", "§cKung Fu"),
    ITEM_NESSIE_9DELUGE("§9Deluge", "§9Deluge"),
    ITEM_NESSIE_BJET_DEAU("§bJet d'Eau", "§bJet d'Eau"),
    ITEM_OEILMALEFIQUE_5BALLE_DE_RANCUNE("§5Balle de Rancune", "§5Balle de Rancune"),
    ITEM_OEILMALEFIQUE_CMALEDICTION("§cMalediction", "§cMalediction"),
    ITEM_PAYASE_0OMBRE("§0Ombre", "§0Ombre"),
    ITEM_PAYASE_5PERMUTATION("§5Permutation", "§5Permutation"),
    ITEM_PAYASE_8DARKNESS_FORM("§8Darkness Form", "§8Darkness Form"),
    ITEM_POLNAREFF_5IMAGE_REMANENTE("§5Image Remanente", "§5Image Remanente"),
    ITEM_POLNAREFF_7SILVER_CHARIOT("§7Silver Chariot", "§7Silver Chariot"),
    ITEM_POLNAREFF_BSWORD_LAUNCH("§bSword Launch", "§bSword Launch"),
    ITEM_POLNAREFF_EHORA_RUSH("§eHora Rush", "§eHora Rush"),
    ITEM_REIKO_2THORNS("§2Thorns", "§2Thorns"),
    ITEM_REIKO_7EMPRISONNEMENT("§7Emprisonnement", "§7Emprisonnement"),
    ITEM_REIKO_FMIROIR_ACTIF("§fMiroir Actif", "§fMiroir Actif"),
    ITEM_REZE_6BOUM("§6Boum", "§6Boum"),
    ITEM_REZE_CMALEDICTION("§cMalediction", "§cMalediction"),
    ITEM_REZE_CTORPILLE("§cTorpille", "§cTorpille"),
    ITEM_REZE_ETETE_CHERCHEUSE("§eTete Chercheuse", "§eTete Chercheuse"),
    ITEM_ROHAN_BHEAVENS_DOOR("§bHeaven's Door", "§bHeaven's Door"),
    ITEM_ROHAN_ELIVRE("§eLivre", "§eLivre"),
    ITEM_ROKURO_5FORME_SERPIENNE("§5Forme Serpienne", "§5Forme Serpienne"),
    ITEM_ROKURO_AZONE_INCROYABLE("§aZone Incroyable", "§aZone Incroyable"),
    ITEM_ROKURO_BZONE_INTOUCHABLE("§bZone Intouchable", "§bZone Intouchable"),
    ITEM_ROKURO_EORGUE_DES_SENS("§eOrgue des sens", "§eOrgue des sens"),
    ITEM_TSUCHINOKO_2VENIN("§2Venin", "§2Venin"),
    ITEM_TSUCHINOKO_AONDES("§aOndes", "§aOndes"),
    ITEM_TSUCHINOKO_CSUICIDE("§cSuicide", "§cSuicide"),
    ITEM_UMBRELLA_BPARASOL("§bParasol", "§bParasol"),
    ITEM_UMBRELLA_CCURSE("§cCurse", "§cCurse"),
    ITEM_UMBRELLA_EAIR_STRIKE("§eAir Strike", "§eAir Strike"),

    // ── ConfigUi ──
    UI_YOKAI_NORMAL_NAME("§5§lYokai Normaux", "§5§lNormal Yokai"),
    UI_YOKAI_SPECIAL_NAME("§6§lYokai Spéciaux", "§6§lSpecial Yokai"),
    UI_YOKAI_COUNT("§7%enabled%/%total% activés", "§7%enabled%/%total% enabled"),

    UI_ALREADY_CLAIMED("§6✦ Déjà réclamé", "§6✦ Already claimed"),
    UI_CLICK_TOGGLE_ON("§eCliquez pour §cdésactiver", "§eClick to §cdisable"),
    UI_CLICK_TOGGLE_OFF("§eCliquez pour §aactiver", "§eClick to §aenable"),

    ;

    private final Map<String, String> translations;

    DanDaDanLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_US", en);
    }

    @Override public String getKey() { return "dandadan." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
