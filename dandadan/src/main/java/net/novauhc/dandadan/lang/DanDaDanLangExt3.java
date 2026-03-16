package net.novauhc.dandadan.lang;

import net.novaproject.novauhc.lang.Lang;

import java.util.Map;

public enum DanDaDanLangExt3 implements Lang {

    // ── Compte de Saint-Germain ──────────────────────────────
    CSG_DESC("§7Attribué aléatoirement. §5Couteau §7: vole les capacités d'un joueur mourant 7min.\n§7§5Fiole §7: 3 effets différents (double saut, éclair, absorption eau).", "§7Randomly assigned. §5Knife§7: steal dying player's abilities 7min.\n§7§5Vial§7: 3 effects (double jump, lightning, water absorption)."),
    CSG_COUTEAU_ACTIVATED("§6[CSG] §7Couteau activé sur §f%target%§7 ! Ses capacités seront volées à sa mort.", "§6[CSG] §7Knife activated on §f%target%§7! Abilities stolen on death."),
    CSG_POWERS_STOLEN("§6[CSG] §7Vous avez obtenu les capacités de §f%victim%§7 ! (7min)", "§6[CSG] §7You obtained §f%victim%§7's abilities! (7min)"),
    CSG_COUTEAU_USAGE("§c✘ Usage : /ddd couteau <pseudo>", "§c✘ Usage: /ddd couteau <player>"),
    CSG_FIOLE_JUMP("§6★ §eFiole Jump Crone ! Double saut activé (5min)", "§6★ §eJump Crone vial! Double jump (5min)"),
    CSG_FIOLE_COUPE("§6★ §eFiole Coupe Cheveux ! Éclair + aveugle + 1❤ (5min)", "§6★ §eHaircut vial! Lightning + blind + 1❤ (5min)"),
    CSG_FIOLE_EAU("§6★ §eFiole Tigre d'Eau ! +1❤ absorption/pomme en eau (5min)", "§6★ §eWater Tiger vial! +1❤ absorption/apple in water (5min)"),
    CSG_CARD_VISAGE("§6[Carte] §7Visage — lave sous vos pieds ou Wither I !", "§6[Card] §7Face — lava or Wither I!"),
    CSG_CARD_BOUCHE("§6[Carte] §7Bouche — casque remplacé par or !", "§6[Card] §7Mouth — helmet replaced by gold!"),
    CSG_CARD_OEIL("§6[Carte] §7Œil — Blindness I !", "§6[Card] §7Eye — Blindness I!"),
    CSG_CARD_OREILLE("§6[Carte] §7Oreille — Slowness I + no slot change 10s !", "§6[Card] §7Ear — Slowness I + no slot change 10s!"),

    // ── Caesar ───────────────────────────────────────────────
    CAESAR_DESC("§7Duo avec §5Joseph §7— gagnez ensemble !\n§7§5Savon Launcher §7: bouclier 45% + renvoi flèches.\n§7§5Savon Lenses §7: particules volent objets.", "§7Duo with §5Joseph§7 — win together!\n§7§5Soap Launcher§7: 45% shield + arrow reflect.\n§7§5Soap Lenses§7: particles steal items."),
    CAESAR_JOSEPH_NAME("§b[Caesar] §7Joseph est : §f%name%", "§b[Caesar] §7Joseph is: §f%name%"),
    CAESAR_SAVON_LAUNCHER("§b★ §3Savon Launcher actif ! (45% blocage + renvoi flèches)", "§b★ §3Soap Launcher active! (45% block + arrow reflect)"),
    CAESAR_SAVON_LENSES("§b★ §3Savon Lenses lancé ! (20% force + particules)", "§b★ §3Soap Lenses launched! (20% strength + particles)"),
    CAESAR_SAVON_CUTTER("§b★ §3Savon Cutter ! Zone anti-lave posée.", "§b★ §3Soap Cutter! Anti-lava zone placed."),

    // ── Joseph ───────────────────────────────────────────────
    JOSEPH_DESC("§7Duo avec §5Caesar §7— gagnez ensemble !\n§7§5Hamon Overdrive §7: 2❤ + KB + immobilisation.\n§7§5NIGERUNDAYO §7: speed dégressif.", "§7Duo with §5Caesar§7 — win together!\n§7§5Hamon Overdrive§7: 2❤ + KB + freeze.\n§7§5NIGERUNDAYO§7: decreasing speed."),
    JOSEPH_RUN_ACTIVATED("§e★ §6NIGERUNDAYO ! Speed 3→2→1 (mais -75% dégâts)", "§e★ §6NIGERUNDAYO! Speed 3→2→1 (but -75% damage)"),
    JOSEPH_HAMON_OVERDRIVE("§e★ §6Hamon Overdrive sur §f%target%§6 !", "§e★ §6Hamon Overdrive on §f%target%§6!"),
    JOSEPH_REBUFF("§e★ §6Rebuff Overdrive activé ! (8 coups → feu 30s)", "§e★ §6Rebuff Overdrive activated! (8 hits → fire 30s)"),
    JOSEPH_CLACKER("§e★ §6Clacker Boomerang ! §f%target% §6ne peut pas s'éloigner > 5 blocs.", "§e★ §6Clacker Boomerang! §f%target% §6can't go > 5 blocks away."),
    JOSEPH_RIPPLE("§e★ §6Ripple Hair Attack ! +20% résistance + 10% pour Caesar.", "§e★ §6Ripple Hair Attack! +20% resistance + 10% for Caesar."),
    JOSEPH_RUN_USAGE("§c✘ Usage : /ddd run", "§c✘ Usage: /ddd run"),

    // ── Doomslayer ───────────────────────────────────────────
    DOOM_DESC("§7Obtenu en survivant l'arène Doom (1min de jeu).\n§7§5Armure verte §7: 5💚 bloquent les dégâts.\n§7§5Armes §7: Shotgun, BFG, Baliste, Lance-Flammes.", "§7Obtained by surviving the Doom arena (1min game).\n§7§5Green armor§7: 5💚 block damage.\n§7§5Weapons§7: Shotgun, BFG, Ballista, Flamethrower."),
    DOOM_ARENA_START("§4§l【 DOOM ETERNAL 】 §cTous téléportés dans l'arène ! Dernier survivant = Doomslayer.", "§4§l【 DOOM ETERNAL 】 §cAll teleported to the arena! Last survivor = Doomslayer."),
    DOOM_ARENA_WIN("§4★ §cVous avez survécu ! Vous êtes le §4§lDoomslayer§c !", "§4★ §cYou survived! You are the §4§lDoomslayer§c!"),
    DOOM_ARMOR_LOST("§c[Doom] §7Dernière 💚 perdue ! Armure enchantée Prot+1 (5s)", "§c[Doom] §7Last 💚 lost! Armor enchanted Prot+1 (5s)"),
    DOOM_BLADE_BERZERKER("§4[Doom] §cMode Berzerker ! Sharp IV + Speed 10% (no regen)", "§4[Doom] §cBerzerker Mode! Sharp IV + Speed 10% (no regen)"),
    DOOM_BLADE_CREUSET("§4[Doom] §cMode Creuset ! Sharp IV + 25% vol de vie", "§4[Doom] §cCreuset Mode! Sharp IV + 25% lifesteal"),
    DOOM_WEAPON_USAGE("§c✘ Usage : /ddd weapon", "§c✘ Usage: /ddd weapon"),
    DOOM_YOKAI_DETECTED("§c[Doom] §f%player% §ca obtenu la malédiction §5%yokai%§c !", "§c[Doom] §f%player% §cjust got the §5%yokai%§c curse!"),

    // ── Denji ────────────────────────────────────────────────
    DENJI_DESC("§7Passif §5Sang §7: 100 unités, régénérées passif.\n§7§5Malédiction §7: casque spécial + résistance/force/speed.\n§7§5Chainsaw Man §7: compteur 0-10 avec malus progressifs.", "§7Passive §5Blood§7: 100 units, passive regen.\n§7§5Curse§7: special helmet + resist/strength/speed.\n§7§5Chainsaw Man§7: 0-10 counter with progressive debuffs."),
    DENJI_BLOOD_DISPLAY("§c🩸 §f%blood%§c/100", "§c🩸 §f%blood%§c/100"),
    DENJI_CURSE_ACTIVATED("§c★ §4Malédiction Denji ! (casque spécial + résistance/force/speed)", "§c★ §4Denji Curse! (special helmet + resistance/strength/speed)"),
    DENJI_CHAINSAW_ACTIVATED("§c★ §4Chainsaw Man ! Compteur 0-10 avec malus progressifs.", "§c★ §4Chainsaw Man! Counter 0-10 with progressive debuffs."),
    DENJI_CHAIN_ACTIVATED("§c★ §4Chaine lancée !", "§c★ §4Chain launched!"),
    DENJI_NOT_ENOUGH_BLOOD("§c✘ Pas assez de sang !", "§c✘ Not enough blood!"),

    // ── Reze ─────────────────────────────────────────────────
    REZE_DESC("§7Passif §5Bombe §7: immunité explosion/feu.\n§7§5Malédiction §7: speed 18% + force 16%.\n§7§5Boum §7: tous les 4 coups, fait exploser l'item adverse.", "§7Passive §5Bomb§7: explosion/fire immunity.\n§7§5Curse§7: speed 18% + strength 16%.\n§7§5Boom§7: every 4 hits, explodes enemy's item."),
    REZE_CURSE_ACTIVATED("§6★ §eMalédiction Reze ! (speed 18% + force 16%)", "§6★ §eReze Curse! (speed 18% + strength 16%)"),
    REZE_BOUM_ACTIVATED("§6★ §eBoum ! Tous les 4 coups → explosion item (30s)", "§6★ §eBoom! Every 4 hits → item explosion (30s)"),
    REZE_TETE_ACTIVATED("§6★ §eTête chercheuse lancée vers §f%target%§e !", "§6★ §eHoming head launched at §f%target%§e!"),
    REZE_TORPILLE_ACTIVATED("§6★ §eTorpille ! 3 flammes actives. Explosions à chaque coup reçu.", "§6★ §eTorpedo! 3 flames active. Explosions on each hit received."),
    REZE_KILL_BONUS("§6+1min §ede malédiction Reze pour le kill !", "§6+1min §eof Reze curse for the kill!"),

    // ── Jotaro ───────────────────────────────────────────────
    JOTARO_DESC("§7Passif §5Réaction §7: annule les projectiles regardés.\n§7§5Star Platinum §7(Stand) §7: 10% résistance + 15% speed.\n§7§5Ora Ora §7: CPS duel 5s.", "§7Passive §5Reaction§7: cancels looked-at projectiles.\n§7§5Star Platinum §7(Stand)§7: 10% resistance + 15% speed.\n§7§5Ora Ora§7: CPS duel 5s."),
    JOTARO_STAR_FINGER("§1★ §9Star Finger ! Portée +5 blocs + Blindness 3s + 3 coups perce résistance.", "§1★ §9Star Finger! +5 block range + Blindness 3s + 3 pierce hits."),
    JOTARO_STAND_ACTIVATED("§1★ §9Star Platinum activé ! (résistance 10% + speed 15%)", "§1★ §9Star Platinum activated! (resistance 10% + speed 15%)"),
    JOTARO_TIME_STOP("§1★ §9Arrêt du Temps ! (%seconds%s)", "§1★ §9Time Stop! (%seconds%s)"),
    JOTARO_ORA_ORA("§1★ §9Ora Ora ! Duel de CPS 5s avec §f%target%§9 !", "§1★ §9Ora Ora! CPS duel 5s with §f%target%§9!"),
    JOTARO_ORA_WIN("§1★ §9Vous avez gagné le Ora Ora ! §f%target% §9: Weakness 5min.", "§1★ §9You won Ora Ora! §f%target%§9: Weakness 5min."),
    JOTARO_ORA_LOSE("§c✘ Vous avez perdu le Ora Ora ! -2❤ permanents.", "§c✘ You lost Ora Ora! -2❤ permanent."),

    // ── Dio ──────────────────────────────────────────────────
    DIO_DESC("§7Passif §5Vampire §7: force de nuit + regen 3❤ par kill.\n§7§5The World §7(Stand)§7: 15% force + 10% résistance.\n§7§5Time Skip §7: TP 3x à 15 blocs.", "§7Passive §5Vampire§7: night strength + 3❤ regen per kill.\n§7§5The World §7(Stand)§7: 15% strength + 10% resistance.\n§7§5Time Skip§7: TP 3x 15 blocks."),
    DIO_STAND_ACTIVATED("§e★ §6The World activé ! (force 15% + résistance 10%)", "§e★ §6The World activated! (strength 15% + resistance 10%)"),
    DIO_TIME_STOP("§e★ §6ZA WARUDO ! Arrêt du Temps (%seconds%s)", "§e★ §6ZA WARUDO! Time Stop (%seconds%s)"),
    DIO_KNIFE("§e★ §65 couteaux lancés ! Saignement sur touche.", "§e★ §65 knives thrown! Bleeding on hit."),
    DIO_ROAD_ROLLER("§e★ §6Road Roller sur §f%target%§6 !", "§e★ §6Road Roller on §f%target%§6!"),
    DIO_TIME_SKIP("§e★ §6Time Skip TP !", "§e★ §6Time Skip TP!"),
    DIO_JOESTAR_ALERT("§6[Dio] §7Un joueur entre dans votre rayon 100 blocs : §f%player%", "§6[Dio] §7A player entered your 100-block radius: §f%player%"),

    // ── Kira ─────────────────────────────────────────────────
    KIRA_DESC("§7Passif §5Main §7: vole enchant épée en tuant.\n§7§5Killer Queen §7(Stand)§7: 17% résistance + statut explosé.\n§7§5Bites the Dust §7: rewind tous les 'explosés' + explosion.", "§7Passive §5Hand§7: steals sword enchant on kill.\n§7§5Killer Queen §7(Stand)§7: 17% resistance + exploded status.\n§7§5Bites the Dust§7: rewind all 'exploded' + explosion."),
    KIRA_STAND_ACTIVATED("§5★ §dKiller Queen activé ! (résistance 17%)", "§5★ §dKiller Queen activated! (resistance 17%)"),
    KIRA_BOMBE_ACTIVATED("§5★ §dBombe ! Prochain coup → statut 'explosé' sur §f%target%§d.", "§5★ §dBomb! Next hit → 'exploded' status on §f%target%§d."),
    KIRA_EXPLODED_STATUS("§5⚠ §dVous êtes 'explosé' ! Méfiez-vous de Kira.", "§5⚠ §dYou are 'exploded'! Beware of Kira."),
    KIRA_SHEER_ACTIVATED("§5★ §dSheer Heart Attack lancé !", "§5★ §dSheer Heart Attack launched!"),
    KIRA_BTD_ACTIVATED("§5★ §dBites the Dust ! Tous les 'explosés' remontent dans le temps.", "§5★ §dBites the Dust! All 'exploded' rewound in time."),
    KIRA_STRAY_CAT("§5★ §dStray Cat ! Dôme de protection (regen 2, 30s)", "§5★ §dStray Cat! Protection dome (regen 2, 30s)"),
    KIRA_MAIN_PASSIVE("§5[Kira] §7Épée enchantée ! (+1 niveau, 25 coups)", "§5[Kira] §7Sword enchanted! (+1 level, 25 hits)"),

    // ── Polnareff ────────────────────────────────────────────
    POLNA_DESC("§7Passif §5Précision §7: 25% flèches auto-aim.\n§7§5Silver Chariot §7(Stand)§7: 10% force.\n§7§5Hora Rush §7: épée +1 enchant + -speed/coup.", "§7Passive §5Precision§7: 25% auto-aim arrows.\n§7§5Silver Chariot §7(Stand)§7: 10% strength.\n§7§5Hora Rush§7: sword +1 enchant + -speed/hit."),
    POLNA_STAND_ACTIVATED("§f★ §7Silver Chariot activé ! (force 10%)", "§f★ §7Silver Chariot activated! (strength 10%)"),
    POLNA_SWORD_LAUNCH("§f★ §7Sword Launch ! -1 enchant épée → speed + flèches auto-aim (30s)", "§f★ §7Sword Launch! -1 enchant sword → speed + auto-aim arrows (30s)"),
    POLNA_HORA_RUSH("§f★ §7Hora Rush ! Épée +1 enchant + -speed/coup (1min)", "§f★ §7Hora Rush! Sword +1 enchant + -speed/hit (1min)"),
    POLNA_IMAGE_REMANENTE("§f★ §76 PNJ invoqués ! (Weakness 3, 30❤, attaquent votre dernière cible)", "§f★ §76 NPCs summoned! (Weakness 3, 30❤, attack your last target)"),

    // ── Rohan ────────────────────────────────────────────────
    ROHAN_DESC("§7§5Heaven's Door §7(Stand)§7: 10% speed.\n§7§5Livre §7: transforme un joueur en livre lisible.", "§7§5Heaven's Door §7(Stand)§7: 10% speed.\n§7§5Livre§7: transforms a player into a readable book."),
    ROHAN_STAND_ACTIVATED("§2★ §aHeaven's Door activé ! (speed 10%)", "§2★ §aHeaven's Door activated! (speed 10%)"),
    ROHAN_LIVRE("§2★ §aLivre ! §f%target% §2transformé en livre pendant 5s.", "§2★ §aLivre! §f%target% §2turned into a book for 5s."),

    // ── Time Freeze ─────────────────────────────────────────
    TIME_FREEZE_START("§b§l⏱ ARRÊT DU TEMPS ! §r§bTous les joueurs sont immobilisés !", "§b§l⏱ TIME FREEZE! §r§bAll players are frozen!"),
    TIME_FREEZE_END("§a§l⏱ LE TEMPS REPREND ! §r§aLes dégâts accumulés sont infligés.", "§a§l⏱ TIME RESUMES! §r§aAccumulated damage is dealt."),
    ;

    private final Map<String, String> translations;
    DanDaDanLangExt3(String fr, String en) { this.translations = Map.of("fr_FR", fr, "en_US", en); }
    @Override public String getKey()                       { return "dandadan." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
