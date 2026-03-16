package net.novauhc.dandadan.lang;

import net.novaproject.novauhc.lang.Lang;

import java.util.Map;

public enum DanDaDanLang implements Lang {

    // ── Général ──────────────────────────────────────────────
    YOKAI_OBTAINED(
            "§5§l★ ✦ Malédiction de Yokai ✦ §5§l★\n§fVous avez obtenu la malédiction de §5§l%yokai% §f!",
            "§5§l★ ✦ Yokai Curse ✦ §5§l★\n§fYou obtained the curse of §5§l%yokai%§f!"
    ),
    YOKAI_ALREADY_HAVE(
            "§c✘ Vous possédez déjà une malédiction de yokai !",
            "§c✘ You already have a yokai curse!"
    ),
    YOKAI_ALREADY_CLAIMED(
            "§c✘ Ce yokai a déjà été réclamé par un autre joueur !",
            "§c✘ This yokai has already been claimed by another player!"
    ),
    DEATH_MESSAGE(
            "§7%player% est mort%killer%§7, il était §5§l%yokai%§7.",
            "§7%player% died%killer%§7, they were §5§l%yokai%§7."
    ),
    DEATH_MESSAGE_KILLER(
            " §7tué par §f%killer%",
            " §7killed by §f%killer%"
    ),
    NOT_IN_GAME(
            "§c✘ Vous n'êtes pas en jeu.",
            "§c✘ You are not in game."
    ),
    NO_YOKAI(
            "§c✘ Vous ne possédez pas encore de malédiction de yokai.",
            "§c✘ You don't have a yokai curse yet."
    ),
    CMD_UNKNOWN(
            "§c✘ Commande inconnue ou non disponible pour votre yokai.",
            "§c✘ Unknown command or not available for your yokai."
    ),
    SCENARIO_NOT_ACTIVE(
            "§c✘ Le scénario DanDaDan n'est pas actif.",
            "§c✘ The DanDaDan scenario is not active."
    ),
    CMD_HELP(
            "§5§l【 DanDaDan UHC 】 §7/ddd <commande> [args]",
            "§5§l【 DanDaDan UHC 】 §7/ddd <command> [args]"
    ),
    COOLDOWN_ACTIVE(
            "§c✘ Capacité en cooldown !",
            "§c✘ Ability on cooldown!"
    ),
    MAX_USE_REACHED(
            "§c✘ Vous avez atteint le nombre maximum d'utilisations.",
            "§c✘ You reached the maximum number of uses."
    ),

    // ── Okarun ───────────────────────────────────────────────
    OKARUN_DESC(
            "§7Maîtrisez la §5Malédiction §7pour obtenir vitesse et puissance.\n§7Passif §5Mémé 100km/H §7: speed à l'approche d'un ennemi.",
            "§7Master the §5Curse §7for speed and power.\n§7Passive §5Grandma 100km/H§7: speed near enemies."
    ),
    OKARUN_CURSE_ACTIVATED(
            "§5★ §dMalédiction activée ! §7(+%duration%s de speed)",
            "§5★ §dCurse activated! §7(+%duration%s speed)"
    ),
    OKARUN_ALLOUT_ACTIVATED(
            "§5★ §dAll-Out !",
            "§5★ §dAll-Out!"
    ),
    OKARUN_ALLOUT_HIT(
            "§c✘ Touché par le All-Out d'Okarun !",
            "§c✘ Hit by Okarun's All-Out!"
    ),
    OKARUN_KILL_BONUS(
            "§5+1 minute §dde malédiction pour le kill !",
            "§5+1 minute §dof curse for the kill!"
    ),
    OKARUN_COURSE_WIN(
            "§5★ §dVous avez gagné la course ! §f(+%hp%❤ +%time%s malédiction)",
            "§5★ §dYou won the race! §f(+%hp%❤ +%time%s curse)"
    ),
    OKARUN_COURSE_LOSE(
            "§c✘ Vous avez perdu la course !",
            "§c✘ You lost the race!"
    ),
    OKARUN_COURSE_TARGET_WIN(
            "§a★ Vous avez gagné la course contre §5%player%§a ! §f(+%hp%❤ +speed)",
            "§a★ You won the race against §5%player%§a! §f(+%hp%❤ +speed)"
    ),
    OKARUN_COURSE_USAGE(
            "§c✘ Usage : /ddd course <pseudo>",
            "§c✘ Usage: /ddd course <player>"
    ),

    // ── Momo ─────────────────────────────────────────────────
    MOMO_DESC(
            "§7Enchaînez les coups avec §5Moe Moe §7pour appliquer des effets progressifs.\n§7Passif §5Serveuse §7: 2% d'annuler le KB sur pomme en or.",
            "§7Chain hits with §5Moe Moe §7to apply progressive effects.\n§7Passive §5Waitress§7: 2% cancel KB on golden apple."
    ),
    MOMO_MOEMOE_ACTIVATED(
            "§d✦ §5Moe Moe §dactivé ! (max §f%max%§d coups)",
            "§d✦ §5Moe Moe §dactivated! (max §f%max%§d hits)"
    ),
    MOMO_MOEMOE_COUNTER(
            "§5[MoeMoe] §e%target% §f: §b%hits%§f/§c%max%",
            "§5[MoeMoe] §e%target% §f: §b%hits%§f/§c%max%"
    ),
    MOMO_MOEMOE_SLOW(
            "§8[MoeMoe] §7🟢 Slowness !",
            "§8[MoeMoe] §7🟢 Slowness!"
    ),
    MOMO_MOEMOE_LAVA(
            "§8[MoeMoe] §6🟠 Lave sous vos pieds !",
            "§8[MoeMoe] §6🟠 Lava beneath you!"
    ),
    MOMO_MOEMOE_PROPULSION(
            "§8[MoeMoe] §c🔴 Propulsion !",
            "§8[MoeMoe] §c🔴 Propulsion!"
    ),
    MOMO_MOEMOE_EXHAUSTED(
            "§c✘ Moe Moe épuisé !",
            "§c✘ Moe Moe exhausted!"
    ),

    // ── Bamora ───────────────────────────────────────────────
    BAMORA_DESC(
            "§7Activez §5Kaiju §7pour invoquer votre cœur en bloc d'or et gagner 15% de force.\n§7Devenez invisible avec §5Invisibilité§7.",
            "§7Activate §5Kaiju §7to summon your heart as a gold block and gain 15% strength.\n§7Become invisible with §5Invisibility§7."
    ),
    BAMORA_KAIJU_ACTIVATED(
            "§6★ §eKaiju activé ! Votre cœur est posé, protégez-le !",
            "§6★ §eKaiju activated! Your heart is placed, protect it!"
    ),
    BAMORA_KAIJU_BROKEN(
            "§c✘ Votre cœur a été détruit ! Kaiju désactivé.",
            "§c✘ Your heart was destroyed! Kaiju deactivated."
    ),
    BAMORA_INVIS_ACTIVATED(
            "§8★ §7Invisibilité activée (§f45s§7)",
            "§8★ §7Invisibility activated (§f45s§7)"
    ),
    BAMORA_INVIS_REVEALED_HIT(
            "§c⚠ Coup reçu — visible 2s !",
            "§c⚠ Hit received — visible 2s!"
    ),
    BAMORA_SYSTEME_USAGE(
            "§c✘ Usage : /ddd systeme",
            "§c✘ Usage: /ddd systeme"
    ),
    BAMORA_SYSTEME_TELEPORT(
            "§6★ Téléportation vers votre cœur !",
            "§6★ Teleporting to your heart!"
    ),
    BAMORA_KILL_BONUS(
            "§6+1 minute §ede malédiction Kaiju pour le kill !",
            "§6+1 minute §eof Kaiju curse for the kill!"
    ),

    // ── Seiko ────────────────────────────────────────────────
    SEIKO_DESC(
            "§7Renommez votre épée §5Nessie/Ongle/Harisen §7pour des passifs.\n§7Passif §5Dieu de la région §7: effets selon votre position sur la map.",
            "§7Rename your sword §5Nessie/Claw/Harisen §7for passives.\n§7Passive §5Region God§7: effects based on map position."
    ),
    SEIKO_BARRIER_MYSTIQUE_ACTIVATED(
            "§c★ Barrière mystique posée sur §e%target% §c! (30s)",
            "§c★ Mystical barrier placed on §e%target%§c! (30s)"
    ),
    SEIKO_BARRIER_INTERIEUR_ACTIVATED(
            "§c★ Barrière intérieure activée ! (30s)",
            "§c★ Inner barrier activated! (30s)"
    ),
    SEIKO_BARRIER_BURNED(
            "§c⚠ Vous avez quitté la barrière de §e%seiko% §cet vous brûlez !",
            "§c⚠ You left §e%seiko%§c's barrier and you're burning!"
    ),
    SEIKO_REGION_NORTHWEST(
            "§a[Séiko] §7Zone nord-ouest : Speed 20%",
            "§a[Seiko] §7Northwest zone: Speed 20%"
    ),
    SEIKO_REGION_NORTHEAST(
            "§a[Séiko] §7Zone nord-est : Résistance 20%",
            "§a[Seiko] §7Northeast zone: Resistance 20%"
    ),
    SEIKO_REGION_SOUTHWEST(
            "§a[Séiko] §7Zone sud-ouest : Force 20%",
            "§a[Seiko] §7Southwest zone: Strength 20%"
    ),
    SEIKO_REGION_SOUTHEAST(
            "§a[Séiko] §7Zone sud-est : Fire Resistance",
            "§a[Seiko] §7Southeast zone: Fire Resistance"
    ),

    // ── Kinta ────────────────────────────────────────────────
    KINTA_DESC(
            "§7Transformez-vous avec §5Great Kinta §7pour armure en or renforcée.\n§7§5Rocket Punch §7: lancez votre épée et téléportez-vous sur la cible.",
            "§7Transform with §5Great Kinta §7for enhanced gold armor.\n§7§5Rocket Punch§7: throw your sword and teleport to the target."
    ),
    KINTA_GREAT_KINTA_ACTIVATED(
            "§6★ §eGreat Kinta activé ! Armure en or équipée.",
            "§6★ §eGreat Kinta activated! Gold armor equipped."
    ),
    KINTA_ROCKET_PUNCH_THROWN(
            "§6★ §eRocket Punch ! Épée lancée — reclique pour te téléporter dans la zone.",
            "§6★ §eRocket Punch! Sword thrown — click again to teleport into the zone."
    ),
    KINTA_ROCKET_PUNCH_TELEPORT(
            "§6★ §eTéléportation dans la zone du Rocket Punch !",
            "§6★ §eTeleporting into the Rocket Punch zone!"
    ),
    KINTA_ROCKET_PUNCH_NO_TARGET(
            "§c✘ Aucun joueur dans la zone. Réactivez Rocket Punch.",
            "§c✘ No player in the zone. Reactivate Rocket Punch."
    ),
    KINTA_NANOSKIN_USAGE(
            "§c✘ Usage : /ddd nanoskin",
            "§c✘ Usage: /ddd nanoskin"
    ),
    KINTA_NANOSKIN_CHOOSE(
            "§6[Nanoskin] §eChoisissez un gadget : Jetpack / Épingle / Gantelets",
            "§6[Nanoskin] §eChoose a gadget: Jetpack / Hairpin / Gauntlets"
    ),
    KINTA_NANOSKIN_JETPACK(
            "§6[Nanoskin] §eJetpack activé !",
            "§6[Nanoskin] §eJetpack activated!"
    ),
    KINTA_KILL_BONUS(
            "§6+1 minute §ede Great Kinta pour le kill !",
            "§6+1 minute §eof Great Kinta for the kill!"
    ),

    // ── Kashimoto ────────────────────────────────────────────
    KASHIMOTO_DESC(
            "§7Passif §5Protecteur §7: pièce d'armure cassée remplacée par fer P2 (5s).\n§7§5Flamme de glace §7: enflamme sans dégâts, dégrade armure adverse.",
            "§7Passive §5Protector§7: broken armor replaced by iron Prot2 (5s).\n§7§5Ice Flame§7: ignites without damage, degrades enemy armor."
    ),
    KASHIMOTO_PROTECTEUR_TRIGGERED(
            "§b[Kashimoto] §7Pièce d'armure remplacée temporairement !",
            "§b[Kashimoto] §7Armor piece temporarily replaced!"
    ),
    KASHIMOTO_FLAME_ACTIVATED(
            "§b★ §3Flamme de Glace sur §e%target% §3! (1min)",
            "§b★ §3Ice Flame on §e%target%§3! (1min)"
    ),
    KASHIMOTO_FLAME_RECEIVED(
            "§b⚠ §3Vous êtes ciblé par la Flamme de Glace de §e%kashimoto%§3 !",
            "§b⚠ §3You are targeted by §e%kashimoto%§3's Ice Flame!"
    ),
    KASHIMOTO_ESPRIT_ACTIVATED(
            "§b★ §3Esprit Protecteur invoqué ! (+20% force)",
            "§b★ §3Protective Spirit summoned! (+20% strength)"
    ),
    KASHIMOTO_APHOOM_ACTIVATED(
            "§b★ §3Aphoom-Zhah ! Tous les joueurs proches attirés.",
            "§b★ §3Aphoom-Zhah! All nearby players pulled."
    ),

    // ── Enenra ───────────────────────────────────────────────
    ENENRA_DESC(
            "§7Passif §5Combo §7: bonus progressifs jusqu'au 6ème coup.\n§7§5Setta §7: téléportation surprise + coups critiques 20s.",
            "§7Passive §5Combo§7: progressive bonuses up to 6th hit.\n§7§5Setta§7: surprise teleport + critical hits 20s."
    ),
    ENENRA_COMBO_MARLBO(
            "§8[Combo §72§8] §5Marlbo Ro §7— Speed 15%",
            "§8[Combo §72§8] §5Marlbo Ro §7— Speed 15%"
    ),
    ENENRA_COMBO_MARL_ROUGE(
            "§8[Combo §73§8] §5Marl Rouge §7— Prochain coup annulé",
            "§8[Combo §73§8] §5Marl Rouge §7— Next hit cancelled"
    ),
    ENENRA_COMBO_MARL_MENTH(
            "§8[Combo §74§8] §5Marl Menth §7— Épée Sharp IV temporaire",
            "§8[Combo §74§8] §5Marl Menth §7— Temporary Sharp IV sword"
    ),
    ENENRA_COMBO_MARL_GOLD(
            "§8[Combo §75§8] §5Marl Gold §7— Vie de %target% visible",
            "§8[Combo §75§8] §5Marl Gold §7— %target%'s health visible"
    ),
    ENENRA_COMBO_GOLD_BURST(
            "§8[Combo §76§8] §5Gold Burst §7— Régénération instantanée 3❤",
            "§8[Combo §76§8] §5Gold Burst §7— Instant regen 3❤"
    ),
    ENENRA_SETTA_CHARGE(
            "§8[Setta] §7Restez immobile 3s...",
            "§8[Setta] §7Stay still 3s..."
    ),
    ENENRA_SETTA_ACTIVATED(
            "§8★ §7Setta activée ! Visez un joueur.",
            "§8★ §7Setta activated! Aim at a player."
    ),
    ENENRA_SETTA_TELEPORT(
            "§8★ §7Téléportation Setta sur §e%target%§7 ! (critiques 20s)",
            "§8★ §7Setta teleport on §e%target%§7! (crits 20s)"
    ),
    ENENRA_NINJA_ACTIVATED(
            "§8★ §710 clones invoqués ! (1min)",
            "§8★ §710 clones summoned! (1min)"
    ),

    // ── Payase ───────────────────────────────────────────────
    PAYASE_DESC(
            "§7Passifs jour/nuit : invisibilité conditionnelle.\n§7§5Darkness Form §7: résistance + force selon l'heure.\n§7§5Ombre §7: pièges de particules noires.",
            "§7Day/night passives: conditional invisibility.\n§7§5Darkness Form§7: resistance + strength based on time.\n§7§5Shadow§7: black particle traps."
    ),
    PAYASE_DARKNESS_DAY(
            "§5[Payase] §7Darkness Form jour : +%bonus%% de %effect%",
            "§5[Payase] §7Darkness Form day: +%bonus%% %effect%"
    ),
    PAYASE_DARKNESS_NIGHT(
            "§5[Payase] §7Darkness Form nuit : Résistance 15% + Force 15%",
            "§5[Payase] §7Darkness Form night: Resistance 15% + Strength 15%"
    ),
    PAYASE_PERMUTATION(
            "§5[Payase] §7Ombre activée ! (15s)",
            "§5[Payase] §7Shadow form activated! (15s)"
    ),
    PAYASE_OMBRE_ACTIVATED(
            "§5[Payase] §7Pièges d'ombre posés !",
            "§5[Payase] §7Shadow traps placed!"
    ),
    PAYASE_TRAP_ENEMY(
            "§c⚠ Piège ! Propulsé en hauteur !",
            "§c⚠ Trap! Launched into the air!"
    ),
    PAYASE_TRAP_SELF(
            "§5[Payase] §7Absorption +0.5❤ cumulée !",
            "§5[Payase] §7Absorption +0.5❤ stacked!"
    ),

    // ── Rokuro Serpo ─────────────────────────────────────────
    ROKURO_DESC(
            "§7Deux formes : §5Serupo §7(Speed+) et §5Seijin §7(Force+).\n§7/ddd forme — pour changer de forme.",
            "§7Two forms: §5Serupo §7(Speed+) and §5Seijin §7(Strength+).\n§7/ddd forme — to switch forms."
    ),
    ROKURO_SWITCH_SERUPO(
            "§3[Rokuro] §7Forme §5Serupo §7activée ! Speed 18% + 2❤",
            "§3[Rokuro] §7Form §5Serupo §7activated! Speed 18% + 2❤"
    ),
    ROKURO_SWITCH_SEIJIN(
            "§3[Rokuro] §7Forme §5Seijin §7activée ! Force 20% + Résistance 10%",
            "§3[Rokuro] §7Form §5Seijin §7activated! Strength 20% + Resistance 10%"
    ),
    ROKURO_FORME_USAGE(
            "§c✘ Usage : /ddd forme",
            "§c✘ Usage: /ddd forme"
    ),

    // ── M. Mantis ────────────────────────────────────────────
    MANTIS_DESC(
            "§7Passif §52% §7de conserver une pomme en or.\n§7§5Boxe §7: 1v1 forcé avec no hit delay.\n§7§5Uppercut §7: envoi en l'air + combo aérien.",
            "§7Passive §52% §7chance to keep a golden apple.\n§7§5Boxing§7: forced 1v1 with no hit delay.\n§7§5Uppercut§7: launch + aerial combo."
    ),
    MANTIS_BOXE_ACTIVATED(
            "§e[Mantis] §71v1 engagé avec §f%target%§7 ! (1min30)",
            "§e[Mantis] §71v1 engaged with §f%target%§7! (1min30)"
    ),
    MANTIS_BOXE_NOHITDELAY(
            "§e[Mantis] §7No hit delay actif ! (3s)",
            "§e[Mantis] §7No hit delay active! (3s)"
    ),
    MANTIS_UPPERCUT(
            "§e[Mantis] §7Uppercut ! §f%target% §7propulsé en l'air !",
            "§e[Mantis] §7Uppercut! §f%target% §7launched into the air!"
    ),
    MANTIS_JETWATER(
            "§e[Mantis] §7Seaux d'eau retirés de §f%target%§7 ! (15s sans seau)",
            "§e[Mantis] §7Water buckets removed from §f%target%§7! (15s no bucket)"
    ),
    MANTIS_CRABE_USAGE(
            "§c✘ Usage : /ddd crabe | Coût : 20 pommes en or",
            "§c✘ Usage: /ddd crabe | Cost: 20 golden apples"
    ),
    MANTIS_CRABE_SUCCESS(
            "§e[Mantis] §7Death Rider niveau +1 sur vos bottes !",
            "§e[Mantis] §7Death Rider level +1 on your boots!"
    ),
    MANTIS_CRABE_NO_GAP(
            "§c✘ Pas assez de pommes en or (20 requises).",
            "§c✘ Not enough golden apples (20 required)."
    ),
    MANTIS_KILL_BONUS(
            "§e+1 minute §7de Boxe pour le kill !",
            "§e+1 minute §7of Boxing for the kill!"
    ),

    // ── Jiangshi ─────────────────────────────────────────────
    JIANGSHI_DESC(
            "§7Survivez à un coup mortel toutes les 20min.\n§7Invoquez des §5Jiangshi §7et ressuscitez vos victimes comme PNJs.",
            "§7Survive a lethal hit every 20min.\n§7Summon §5Jiangshi §7and resurrect victims as NPCs."
    ),
    JIANGSHI_SURVIVED(
            "§5[Jiangshi] §7Mort évitée ! Échange de position avec un Jiangshi.",
            "§5[Jiangshi] §7Death avoided! Position swapped with a Jiangshi."
    ),
    JIANGSHI_SUMMON(
            "§5[Jiangshi] §7%zombies% zombies et %skeletons% squelettes invoqués ! (KI: %ki%)",
            "§5[Jiangshi] §7%zombies% zombies and %skeletons% skeletons summoned! (KI: %ki%)"
    ),
    JIANGSHI_NOT_ENOUGH_KI(
            "§c✘ Pas assez de KI !",
            "§c✘ Not enough KI!"
    ),
    JIANGSHI_KILL_BONUS(
            "§5[Jiangshi] §7Kill ! +10 KI. Armée renforcée.",
            "§5[Jiangshi] §7Kill! +10 KI. Army upgraded."
    ),

    // ── Kintama ──────────────────────────────────────────────
    KINTAMA_SPAWNED_1(
            "§d✦ [Kintama] §7Une Kintama est apparue dans un coffre sur la map !",
            "§d✦ [Kintama] §7A Kintama has appeared in a chest on the map!"
    ),
    KINTAMA_SPAWNED_2(
            "§d✦ [Kintama] §7La deuxième Kintama est apparue !",
            "§d✦ [Kintama] §7The second Kintama has appeared!"
    ),
    KINTAMA_COLLECTED_1(
            "§d✦ [Kintama] §7Vous avez obtenu la 1ère Kintama ! +1❤ permanent, -25%% cooldowns.",
            "§d✦ [Kintama] §7You obtained the 1st Kintama! +1❤ permanent, -25% cooldowns."
    ),
    KINTAMA_COLLECTED_2(
            "§d✦ [Kintama] §7Vous avez les 2 Kintamas ! +2❤, -50%% cooldowns, passif Yokai actif !",
            "§d✦ [Kintama] §7You have both Kintamas! +2❤, -50% cooldowns, Yokai passive active!"
    ),
    KINTAMA_DISTANCE(
            "§d[Kintama] §7La 2ème Kintama est à §f%dist% §7blocs — direction §f%dir%",
            "§d[Kintama] §7The 2nd Kintama is §f%dist% §7blocks away — direction §f%dir%"
    ),
    KINTAMA_YOKAI_SURVIVE(
            "§d✦ [Kintama - Passif Yokai] §7Vous avez survécu à la mort ! +3❤ et 2❤ absorption.",
            "§d✦ [Kintama - Yokai Passive] §7You survived death! +3❤ and 2❤ absorption."
    ),
    // ── Espace Vide ───────────────────────────────────────────
    ESPACE_VIDE_ACTIVATED(
            "§b✦ [Espace Vide] §7Vous avez ouvert votre Espace Vide !",
            "§b✦ [Empty Space] §7You opened your Empty Space!"
    ),
    ESPACE_VIDE_TELEPORTED(
            "§c⚠ [Espace Vide] §7Vous avez été téléporté dans un Espace Vide !",
            "§c⚠ [Empty Space] §7You have been teleported into an Empty Space!"
    ),
    // ── Amour ─────────────────────────────────────────────────
    AMOUR_DENJI_TASK(
            "§c❤ [Amour] §7Tâche Denji : donnez une §ffleur blanche §7à §eReze §7en faisant clic-droit.",
            "§c❤ [Love] §7Denji task: give a §fwhite rose §7to §eReze §7by right-clicking."
    ),
    AMOUR_REZE_TASK(
            "§c❤ [Amour] §7Tâche Reze : posez une §fsource d'eau §7sous §eDenji §7et restez proche 30s.",
            "§c❤ [Love] §7Reze task: place §fwater §7under §eDenji §7and stay close for 30s."
    ),
    AMOUR_MOMO_TASK(
            "§c❤ [Amour] §7Tâche Momo : renommez une §fgolden carotte §7en '§fcurry§7' et donnez-la à §eOkarun§7.",
            "§c❤ [Love] §7Momo task: rename a §fgolden carrot §7to '§fcurry§7' and give it to §eOkarun§7."
    ),
    AMOUR_OKARUN_TASK(
            "§c❤ [Amour] §7Tâche Okarun : restez à §fmoins de 5 blocs §7de §eMomo §7pendant §f1 minute§7.",
            "§c❤ [Love] §7Okarun task: stay §fwithin 5 blocks §7of §eMomo §7for §f1 minute§7."
    ),
    AMOUR_TASK_DONE(
            "§a✔ [Amour] §7Tâche complétée !",
            "§a✔ [Love] §7Task completed!"
    ),
    AMOUR_CAFE_START(
            "§c❤ [Amour] §7Épreuve du Café ! Vous avez 5 minutes pour reproduire le patron.",
            "§c❤ [Love] §7Café Challenge! You have 5 minutes to reproduce the pattern."
    ),
    AMOUR_SUCCESS(
            "§c❤ [Amour] §7Amour véritable ! Vous formerez désormais un duo et devez gagner ensemble.",
            "§c❤ [Love] §7True love! You now form a duo and must win together."
    ),
    // ── Typhoon Human ─────────────────────────────────────────
    TYPHOON_HUMAN_START(
            "§b⚡ [Typhoon Human] §7Deux joueurs sont entraînés dans un Typhoon Human !",
            "§b⚡ [Typhoon Human] §7Two players are caught in a Typhoon Human!"
    ),
    TYPHOON_HUMAN_YOU(
            "§b⚡ [Typhoon Human] §7Vous affrontez §f%opponent%§7 ! L'un de vous doit mourir pour sortir.",
            "§b⚡ [Typhoon Human] §7You face §f%opponent%§7! One of you must die to escape."
    ),
    TYPHOON_HUMAN_SURVIVED(
            "§b⚡ [Typhoon Human] §7Votre adversaire est mort. Vous êtes libéré !",
            "§b⚡ [Typhoon Human] §7Your opponent died. You are free!"
    ),
    // ── Radio Morioh-Cho ──────────────────────────────────────
    RADIO_PLAYER_DEATH(
            "§fLe joueur §c%victim% §fa été éliminé par §e%killer%§f.",
            "§fPlayer §c%victim% §fwas eliminated by §e%killer%§f."
    ),
    RADIO_YOKAI_OBTAINED(
            "§f%player% §fa obtenu la malédiction du yokai §5%yokai%§f.",
            "§f%player% §fobtained the curse of yokai §5%yokai%§f."
    ),
    RADIO_STAND_OBTAINED(
            "§f%player% §fa débloqué le Stand §9%stand%§f !",
            "§f%player% §funlocked Stand §9%stand%§f!"
    ),
    RADIO_TYPHOON_HUMAN(
            "§fDeux joueurs sont dans un Typhoon Human : §b%p1% §fet §b%p2%§f.",
            "§fTwo players are in a Typhoon Human: §b%p1% §fand §b%p2%§f."
    ),
    RADIO_TIME_FREEZE(
            "§f%player% §fa activé le Time Freeze !",
            "§f%player% §factivated the Time Freeze!"
    ),
    RADIO_KINTAMA_COLLECTED(
            "§f%player% §fa collecté une Kintama !",
            "§f%player% §fcollected a Kintama!"
    ),
    RADIO_KINTAMA_BOTH(
            "§f%player% §fa les §d2 Kintamas§f !",
            "§f%player% §fhas §dboth Kintamas§f!"
    ),
    RADIO_ESPACE_VIDE(
            "§fLes joueurs §b%players% §fsont dans l'Espace Vide de §b%owner%§f.",
            "§fPlayers §b%players% §fare in §b%owner%§f's Empty Space."
    ),
    RADIO_COORDS(
            "§f%player% §fse trouve aux coordonnées §e(%x%, %z%)§f.",
            "§f%player% §fis at coordinates §e(%x%, %z%)§f."
    ),
    JIANGSHI_ALLOUT_ACTIVATED("§c★ §4Jiangshi All Out ! §7Speed III + Force I (30s)", "§c★ §4Jiangshi All Out! §7Speed III + Strength I (30s)"),
    MANTIS_BUSINESS_MAN_TRIGGERED("§2[Mantis] §7Business Man : absorption volée !", "§2[Mantis] §7Business Man: absorption stolen!"),

    // ── Dimension DanDaDan ────────────────────────────────────
    DANDADAN_ENTERED(
            "§5✦ [DanDaDan] §7Vous êtes entré dans la dimension DanDaDan ! Trouvez un yokai pour en sortir.",
            "§5✦ [DanDaDan] §7You entered the DanDaDan dimension! Find a yokai to escape."
    ),
    DANDADAN_PLAYER_ENTERED(
            "§5[DanDaDan] §f%player% §7est entré dans la dimension !",
            "§5[DanDaDan] §f%player% §7entered the dimension!"
    ),
    DANDADAN_YOKAI_OBTAINED_EXIT(
            "§a✦ [DanDaDan] §7Yokai obtenu ! Retour dans le monde principal...",
            "§a✦ [DanDaDan] §7Yokai obtained! Returning to the main world..."
    ),
    DANDADAN_WORLD_BORDER(
            "§c⚠ [DanDaDan] §7Vous ne pouvez pas quitter la zone (300x300) !",
            "§c⚠ [DanDaDan] §7You cannot leave the zone (300x300)!"
    ),
    DANDADAN_NO_PVP(
            "§c✘ [DanDaDan] §7Le PVP est désactivé dans cette dimension.",
            "§c✘ [DanDaDan] §7PVP is disabled in this dimension."
    ),
    DANDADAN_ALREADY_HAS_ROLE(
            "§c✘ §7Vous possédez déjà un yokai.",
            "§c✘ §7You already have a yokai."
    ),
    ;

    private final Map<String, String> translations;

    DanDaDanLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_US", en);
    }

    @Override public String getKey()                         { return "dandadan." + name(); }
    @Override public Map<String, String> getTranslations()   { return translations; }
}
