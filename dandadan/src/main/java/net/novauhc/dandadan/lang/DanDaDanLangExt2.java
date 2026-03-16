package net.novauhc.dandadan.lang;

import net.novaproject.novauhc.lang.Lang;

import java.util.Map;

public enum DanDaDanLangExt2 implements Lang {

    // ── Acrobatique Soyeuse ──────────────────────────────────
    ACRO_DESC("§7Passif §5Acrobate §7: immunité chute + double saut ttes 15s.\n§7§5Transformation §7: améliorations cheveux/corps/jambes/pieds.", "§7Passive §5Acrobat§7: fall immunity + double jump every 15s.\n§7§5Transformation§7: hair/body/legs/feet upgrades."),
    ACRO_CHEVEUX_ACTIVATED("§d★ §5Cheveux activés ! Particules rouge/verte autour de vous.", "§d★ §5Hair activated! Red/green particles around you."),
    ACRO_TRANSFORM_TITLE("§d[Acrobatique] §7Choisissez une amélioration :", "§d[Acrobatic] §7Choose an upgrade:"),
    ACRO_TRANSFORM_CHEVEUX("§c[Cheveux] §7Pirouette Noble Drill — fosse 30 blocs [1x/3.5min]", "§c[Hair] §7Noble Pirouette Drill — 30 block pit [1x/3.5min]"),
    ACRO_TRANSFORM_CORPS("§e[Corps] §7Compteur 10 coups → frappe garantie 2❤ à distance", "§e[Body] §7Counter 10 hits → guaranteed 2❤ hit at range"),
    ACRO_TRANSFORM_JAMBES("§a[Jambes] §7Passe à travers la résistance + copie résistance adverse", "§a[Legs] §7Bypass resistance + copy enemy resistance"),
    ACRO_TRANSFORM_PIED("§b[Pied] §7+2% speed/coup, -1% si reçoit coup (max 40%)", "§b[Feet] §7+2% speed/hit, -1% on hit (max 40%)"),
    ACRO_HEAT_USAGE("§c✘ Usage : /ddd heat <pseudo>", "§c✘ Usage: /ddd heat <player>"),
    ACRO_HEAT_ACTIVATED("§d[Acrobatique] §f%target% §7mis en spectateur 15s !", "§d[Acrobatic] §f%target% §7put in spectator 15s!"),
    ACRO_DOUBLE_JUMP("§d↑ §5Double saut !", "§d↑ §5Double jump!"),

    // ── Minotaure ────────────────────────────────────────────
    MINO_DESC("§7Passif §580% §7réduction feu.\n§7§5Durabilité immense §7: armure fer P3 + résistance cumulative.\n§7§5Kung-Fu §7: zone feu + vole résistance.", "§7Passive §580% §7fire reduction.\n§7§5Immense Durability§7: iron P3 + stacking resistance.\n§7§5Kung-Fu§7: fire zone + steal resistance."),
    MINO_DURABILITE_ACTIVATED("§c★ §4Durabilité immense ! Armure équipée, résistance cumulative.", "§c★ §4Immense Durability! Armor equipped, stacking resistance."),
    MINO_RESISTANCE_STACKED("§c[Mino] §7Résistance cumulée : §f%pct%%", "§c[Mino] §7Stacked resistance: §f%pct%%"),
    MINO_KUNGFU_ACTIVATED("§c★ §4Kung-Fu ! Zone de feu 10x10 (20s)", "§c★ §4Kung-Fu! 10x10 fire zone (20s)"),
    MINO_FER_USAGE("§c✘ Usage : /ddd fer", "§c✘ Usage: /ddd fer"),
    MINO_FER_ACTIVATED("§c[Mino] §7Durabilité des pièces en fer restaurée !", "§c[Mino] §7Iron armor durability restored!"),
    MINO_KILL_BONUS("§c+1min §4de Durabilité pour le kill !", "§c+1min §4of Durability for the kill!"),

    // ── Umbrella Boy ─────────────────────────────────────────
    UMBRA_DESC("§7Passif §520% §7absorption volée sur pomme.\n§7§5Malédiction §7: 2 épées flottantes + 25% résistance.\n§7§5Parasol §7: épée lancée → lave/impossibilité de frapper.", "§7Passive §520% §7absorption steal on apple.\n§7§5Curse§7: 2 floating swords + 25% resistance.\n§7§5Parasol§7: sword thrown → lava/can't hit."),
    UMBRA_CURSE_ACTIVATED("§7★ §fMalédiction Umbrella ! 2 épées flottantes + 25% résistance.", "§7★ §fUmbrella Curse! 2 floating swords + 25% resistance."),
    UMBRA_PARASOL_THROWN("§7★ §fParasol lancé sur §e%target%§f !", "§7★ §fParasol thrown at §e%target%§f!"),
    UMBRA_PARASOL_HIT_PLAYER("§7⚠ §fTouché par le Parasol ! Lave + impossibilité de frapper 7s.", "§7⚠ §fHit by Parasol! Lava + can't attack 7s."),
    UMBRA_PARASOL_HIT_BLOCK("§7★ §fParasol frappe un bloc → explosion de lave 3x3 !", "§7★ §fParasol hits block → 3x3 lava explosion!"),
    UMBRA_AIR_ACTIVATED("§7★ §fSaut en l'air ! Choisissez 3 positions pour vos parasols (10s).", "§7★ §fJumped! Choose 3 parasol positions (10s)."),
    UMBRA_PROTECTION_ACTIVATED("§7★ §fBouclier de protection ! (30s — projectiles renvoyés)", "§7★ §fProtection shield! (30s — projectiles reflected)"),
    UMBRA_KILL_BONUS("§7+1min §fde malédiction Umbrella pour le kill !", "§7+1min §fof Umbrella curse for the kill!"),

    // ── Devilman ─────────────────────────────────────────────
    DEVIL_DESC("§7Passif §52% §7feu sur coup reçu.\n§7§5Malédiction §7: 20% force + résistance + fire res.\n§7§5Crybaby §7: ailes de feu, élimination auto sous 2❤.", "§7Passive §52% §7ignite on hit received.\n§7§5Curse§7: 20% strength + resistance + fire res.\n§7§5Crybaby§7: fire wings, auto-kill under 2❤."),
    DEVIL_CURSE_ACTIVATED("§4★ §cMalédiction Devilman ! (force 20% + résistance 20% + fire res)", "§4★ §cDevilman Curse! (strength 20% + resistance 20% + fire res)"),
    DEVIL_CROC_ACTIVATED("§4★ §cCroc ! §f%target% §cest dévoré (1min)", "§4★ §cFang! §f%target% §cis devoured (1min)"),
    DEVIL_CROC_EXPIRED("§c[Devilman] §72 slots de hotbar échangés !", "§c[Devilman] §72 hotbar slots swapped!"),
    DEVIL_CHALEUR_ACTIVATED("§4★ §cChaleur ! Cercle de feu autour de vous (30s)", "§4★ §cHeat! Fire circle around you (30s)"),
    DEVIL_CRYBABY_ACTIVATED("§4§lDEVILMAN CRYBABY", "§4§lDEVILMAN CRYBABY"),
    DEVIL_CRYBABY_MSG("§4§lDEVILMAN §c§lCRYBABY", "§4§lDEVILMAN §c§lCRYBABY"),
    DEVIL_KILL_BONUS("§4+1min §cde malédiction Devilman pour le kill !", "§4+1min §cof Devilman curse for the kill!"),
    ;

    private final Map<String, String> translations;
    DanDaDanLangExt2(String fr, String en) { this.translations = Map.of("fr_FR", fr, "en_US", en); }
    @Override public String getKey()                       { return "dandadan." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
