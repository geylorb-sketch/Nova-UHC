package net.novauhc.dandadan.lang;

import net.novaproject.novauhc.lang.Lang;

import java.util.Map;

/**
 * Extension de DanDaDanLang pour les yokais 12-18.
 * Fusionne avec DanDaDanLang au moment de l'enregistrement :
 * LangManager.get().register(DanDaDanLangExt.values());
 */
public enum DanDaDanLangExt implements Lang {

    // ── Tsuchinoko ───────────────────────────────────────────
    TSUCHINOKO_DESC(
            "§7Passif §5Ver de la mort §7: 2% d'éclair sur coup.\n§7§5Venin §7: blocs d'éponge Wither/Poison. §5Ondes §7: fuite forcée.",
            "§7Passive §5Death Worm§7: 2% lightning on hit.\n§7§5Venom§7: sponge blocks Wither/Poison. §5Waves§7: forced flee."
    ),
    TSUCHINOKO_VENIN_ACTIVATED("§2★ §aVenin activé ! (10min)", "§2★ §aVenom activated! (10min)"),
    TSUCHINOKO_ONDES_ACTIVATED("§2★ §aOndes psychiques ! Fuyez...", "§2★ §aPsychic Waves! Flee..."),
    TSUCHINOKO_SUICIDE_ACTIVATED("§2★ §aZone Suicide activée !", "§2★ §aSuicide zone activated!"),
    TSUCHINOKO_REGEN_USAGE("§c✘ Usage : /ddd regen", "§c✘ Usage: /ddd regen"),
    TSUCHINOKO_REGEN_ACTIVATED("§2★ §aRégénération : 25% réduction dégâts sur une partie du corps (épisode)", "§2★ §aRegeneration: 25% damage reduction on one body part (episode)"),

    // ── L'Œil maléfique ──────────────────────────────────────
    OEIL_DESC(
            "§7Passif §5Envie de meurtre §7: TP sur un joueur après kill.\n§7§5Malédiction §7: 40% d'effets aléatoires Force/Résistance/Speed.",
            "§7Passive §5Murder Urge§7: TP on a player after kill.\n§7§5Curse§7: 40% random Strength/Resistance/Speed effects."
    ),
    OEIL_CURSE_ACTIVATED("§4★ §cMalédiction activée ! (effets aléatoires)", "§4★ §cCurse activated! (random effects)"),
    OEIL_BALLE_ACTIVATED("§4★ §cBalle de rancune !", "§4★ §cGrudge Ball!"),
    OEIL_KILL_NOTIFY("§c[Œil] §7%killer% vient de tuer §f%victim%§7 — TP possible !", "§c[Eye] §7%killer% just killed §f%victim%§7 — TP possible!"),
    OEIL_JIJI_HIT("§c[Œil] §7Vous venez de frapper §5Jiji §7!", "§c[Eye] §7You just hit §5Jiji§7!"),
    OEIL_TRANSFO_USAGE("§c✘ Usage : /ddd transfo", "§c✘ Usage: /ddd transfo"),
    OEIL_TRANSFO_ACTIVATED("§4★ §cEffets remélangés !", "§4★ §cEffects reshuffled!"),

    // ── Jiji ─────────────────────────────────────────────────
    JIJI_DESC(
            "§7Passif §5Adaptation §7: obtient l'inverse des effets adverses.\n§7§5Spirales §7: pouvoirs selon position hotbar.",
            "§7Passive §5Adaptation§7: gains inverse of enemy effects.\n§7§5Spirals§7: powers based on hotbar position."
    ),
    JIJI_PISTOLET_ACTIVATED("§5★ §dPistolet Maléfique !", "§5★ §dEvil Pistol!"),
    JIJI_RASEN_ACTIVATED("§5★ §dRasen ! (regard aléatoire 30s)", "§5★ §dRasen! (random look 30s)"),
    JIJI_KOTODAMA_ACTIVATED("§5★ §dKotodama ! Inventaire mélangé.", "§5★ §dKotodama! Inventory shuffled."),
    JIJI_ADAPTATION_APPLIED("§5[Jiji] §7Adaptation : §f%effect%", "§5[Jiji] §7Adaptation: §f%effect%"),
    JIJI_KI_USAGE("§c✘ Usage : /ddd ki", "§c✘ Usage: /ddd ki"),

    // ── Monstre de Flatwoods ─────────────────────────────────
    FLATWOODS_DESC(
            "§7Passif §5low HP §7: 15% de KB amplifié sous 2❤.\n§7§5Fumée §7: compteur 0-5, Blindness + saignement.\n§7§5Sumo §7: 1v1 arène.",
            "§7Passive §5low HP§7: 15% amplified KB under 2❤.\n§7§5Smoke§7: counter 0-5, Blindness + bleeding.\n§7§5Sumo§7: 1v1 arena."
    ),
    FLATWOODS_FUMEE_COUNTER("§8[Fumée] §f%target% §7: §b%count%§f/5", "§8[Smoke] §f%target% §7: §b%count%§f/5"),
    FLATWOODS_FUMEE_MAX("§8[Fumée] §cBlindness + saignement sur §f%target%§c !", "§8[Smoke] §cBlindness + bleeding on §f%target%§c!"),
    FLATWOODS_SUMO_START("§7[Flatwoods] §fSumo engagé vs §e%target%§f ! (10 coups à mains nues)", "§7[Flatwoods] §fSumo vs §e%target%§f! (10 bare-handed hits)"),
    FLATWOODS_SUMO_WIN("§7[Flatwoods] §aVous avez gagné le Sumo ! +10% force, -10% KB", "§7[Flatwoods] §aYou won the Sumo! +10% strength, -10% KB"),
    FLATWOODS_SUMO_USAGE("§c✘ Usage : /ddd sumo <pseudo>", "§c✘ Usage: /ddd sumo <player>"),
    FLATWOODS_FORME_ATTAQUANT("§7[Flatwoods] §cForme Attaquante ! (Sharp4 + force 20%)", "§7[Flatwoods] §cAttack Form! (Sharp4 + strength 20%)"),
    FLATWOODS_FORME_DEFENSIF("§7[Flatwoods] §bForme Défensive ! (Full diamant + blocage 20%)", "§7[Flatwoods] §bDefensive Form! (Full diamond + 20% block)"),

    // ── Reiko Kashima ────────────────────────────────────────
    REIKO_DESC(
            "§7Passif §5Miroir §7: 5% retournement à 180°.\n§7§5Thorns §7: renvoie 50% des dégâts reçus pendant 1min.\n§7§5Emprisonnement §7: cage de verre.",
            "§7Passive §5Mirror§7: 5% 180° turn.\n§7§5Thorns§7: reflects 50% damage for 1min.\n§7§5Imprisonment§7: glass cage."
    ),
    REIKO_THORNS_ACTIVATED("§f★ §7Attaque à distance ! (50% renvoi, 1min)", "§f★ §7Ranged Attack! (50% reflect, 1min)"),
    REIKO_MIRROR_COUNTER("§f[Reiko] §e%target% §7: §b%count%§f/6", "§f[Reiko] §e%target% §7: §b%count%§f/6"),
    REIKO_MIRROR_ACTIVATED("§f[Reiko] §7Miroir invoqué pour §e%target%§7 ! (30s)", "§f[Reiko] §7Mirror summoned for §e%target%§7! (30s)"),
    REIKO_EMPRISONNEMENT("§f★ §7Cage de verre posée sur §e%target%§7 !", "§f★ §7Glass cage placed on §e%target%§7!"),

    // ── Jet Booster Kur ──────────────────────────────────────
    KUR_DESC(
            "§7Passif §5Tête haute §7: voit la vie des joueurs.\n§7§5Aviate Exosuit §7: +3 absorption/pomme + speed.\n§7§5Boost §7: projection + 20% force.",
            "§7Passive §5Head Up§7: sees player HP.\n§7§5Aviate Exosuit§7: +3 absorption/apple + speed.\n§7§5Boost§7: projection + 20% strength."
    ),
    KUR_AVIATE_ACTIVATED("§e★ §6Aviate Exosuit activé ! (+3 absorption/pomme, speed 20%)", "§e★ §6Aviate Exosuit activated! (+3 absorption/apple, speed 20%)"),
    KUR_BOOST_ACTIVATED("§e★ §6Boost ! Projection + 20% force (1min)", "§e★ §6Boost! Projection + 20% strength (1min)"),
    KUR_SUPERCHARGE_ACTIVATED("§e★ §63 cercles lancés sur §f%target%§6 !", "§e★ §63 circles launched on §f%target%§6!"),
    KUR_ABAND_USAGE("§c✘ Usage : /ddd aband", "§c✘ Usage: /ddd aband"),
    KUR_ABAND_ACTIVATED("§e[Kur] §7PNJ lancé à votre place — TP aléatoire !", "§e[Kur] §7NPC sent in your place — random TP!"),
    KUR_KILL_BONUS("§e+1min §6de malédiction Kur pour le kill !", "§e+1min §6of Kur curse for the kill!"),

    // ── Nessie ───────────────────────────────────────────────
    NESSIE_DESC(
            "§7Passif §5Poisson §7: Depth Strider 3 permanent.\n§7§5Cou Large §7: double coup tous les 7 hits.\n§7§5Jet d'eau §7: ralentit et réduit les dégâts.",
            "§7Passive §5Fish§7: permanent Depth Strider 3.\n§7§5Wide Neck§7: double hit every 7 hits.\n§7§5Water Jet§7: slows and reduces damage."
    ),
    NESSIE_DELUGE_ACTIVATED("§9★ §bCréation du Déluge ! Hotbar remplie de seaux.", "§9★ §bDeluge! Hotbar filled with buckets."),
    NESSIE_JETEAU_ACTIVATED("§9★ §bJet d'eau ! (zone eau, -20% vitesse attaque)", "§9★ §bWater Jet! (water zone, -20% attack speed)"),
    NESSIE_COU_USAGE("§c✘ Usage : /ddd cou <pseudo>", "§c✘ Usage: /ddd cou <player>"),
    NESSIE_COU_SUCCESS("§9[Nessie] §7Seaux volés à §f%target%§7 !", "§9[Nessie] §7Buckets stolen from §f%target%§7!"),
    NESSIE_DOUBLE_HIT("§9[Nessie] §7Double coup !", "§9[Nessie] §7Double hit!"),
    ;

    private final Map<String, String> translations;

    DanDaDanLangExt(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_US", en);
    }

    @Override public String getKey()                        { return "dandadan." + name(); }
    @Override public Map<String, String> getTranslations()  { return translations; }
}
