// TODO: renommer en <RoleName><Type>Ability selon le type (Passive, Use, Melee, Bow, Command)
// Package : net.novaproject.myscenario.roles.<camp>.<rolename>
package net.novaproject.myscenario.roles.campX.roleName;

import net.novaproject.novauhc.ability.template.PassiveAbility; // TODO: choisir le bon template
// Autres templates disponibles dans net.novaproject.novauhc.ability.template :
//   UseAbility, MeleeAbility, BowAbility, CommandAbility
// Ou étendre Ability directement pour un listener d'événement unique

import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novaproject.myscenario.lang.MyScenarioVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;

// TODO: renommer la classe
public class RoleNamePassiveAbility extends PassiveAbility {

    // ── Paramètres configurables ─────────────────────────────────────
    // OBLIGATOIRE : @AbilityVariable sur chaque champ de configuration
    // Champs NON-final pour que le framework puisse les modifier via l'UI
    // Ne PAS re-annoter cooldown, maxUse, active (déjà dans Ability base)
    @AbilityVariable(
        lang    = MyScenarioVarLang.class,
        nameKey = "ROLENAME_PARAM_NAME",  // TODO: clé dans MyScenarioVarLang
        descKey = "ROLENAME_PARAM_DESC",
        type    = VariableType.INTEGER    // TODO: TIME / INTEGER / DOUBLE / PERCENTAGE / BOOLEAN
    )
    private int myParam = 5; // TODO: valeur par défaut

    // ── Constructeur ─────────────────────────────────────────────────
    // OBLIGATOIRE : constructeur vide (aucun paramètre)
    public RoleNamePassiveAbility() {
        // setCooldown(600);  // en ticks (600 = 30s) — optionnel si applicable
        // setMaxUse(3);      // limite d'utilisations — optionnel
    }

    // ── Identité ─────────────────────────────────────────────────────
    @Override
    public String getName() {
        return "TODO: NomAbility"; // TODO
    }

    @Override
    public Material getMaterial() {
        return Material.BLAZE_POWDER; // TODO: item représentatif (affiché dans l'inventaire)
    }

    // ── Logique principale ────────────────────────────────────────────
    // Pour PassiveAbility : onEnable() est appelé chaque seconde (via AbilityManager)
    // Pour UseAbility     : onEnable() est appelé au clic droit sur getMaterial()
    // Pour MeleeAbility   : onEnable() est appelé au hit PvP
    // Pour BowAbility     : onEnable() est appelé au tir à l'arc
    @Override
    public boolean onEnable(Player player) {
        // Toujours récupérer le owner depuis getOwner() — NE PAS utiliser le param player directement
        if (getOwner() == null) return false;
        Player owner = getOwner().getPlayer();
        if (owner == null) return false;

        // TODO: logique de l'ability
        // ex: owner.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 0));

        return true; // true = succès (cooldown déclenché), false = échec (cooldown ignoré)
    }

    // ── Hooks additionnels (optionnel selon le type) ──────────────────
    // Surcharger selon les besoins :
    //   onKill(UHCPlayer victim)
    //   onDeath(UHCPlayer killer)
    //   onGive(UHCPlayer uhcPlayer)
    //   onSec(Player player) → uniquement pour PassiveAbility si logique complexe

    // @Override
    // public void onKill(UHCPlayer victim) {
    //     Player owner = getOwner().getPlayer();
    //     if (owner == null) return;
    //     // bonus on kill
    // }
}
