// TODO: renommer en <RoleName>Role et ajuster le package
// Package : net.novaproject.myscenario.roles.<camp>.<rolename>
package net.novaproject.myscenario.roles.campX.roleName;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novaproject.myscenario.MyCamps;
import net.novaproject.myscenario.MyRole;
import net.novaproject.myscenario.lang.MyScenarioDescLang;
import net.novaproject.myscenario.lang.MyScenarioVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;

// TODO: renommer la classe
public class RoleNameRole extends MyRole {

    // ── Abilities ────────────────────────────────────────────────────
    // OBLIGATOIRE : déclarer chaque ability via @RoleVariable (jamais getAbilities().add())
    @RoleVariable(
        lang    = MyScenarioVarLang.class,
        nameKey = "ROLENAME_ABILITY_NAME",  // TODO: clé dans MyScenarioVarLang
        type    = VariableType.ABILITY
    )
    private Ability myAbility = new RoleNamePassiveAbility(); // TODO

    // ── Constructeur ─────────────────────────────────────────────────
    public RoleNameRole() {
        setCamp(MyCamps.CAMP_A); // TODO: choisir le bon camp
    }

    // ── Identité ─────────────────────────────────────────────────────
    @Override
    public int getId() {
        return 1; // TODO: ID unique dans le module (1, 2, 3, ...)
    }

    @Override
    public String getName() {
        return "TODO: NomRôle"; // TODO
    }

    @Override
    public Material getIconMaterial() {
        return Material.SKULL_ITEM; // TODO: material représentatif du rôle
    }

    // ── Description (sendDescription) ────────────────────────────────
    // Imports nécessaires en haut du fichier :
    //   import net.novaproject.novauhc.scenario.role.RoleDescription;
    @Override
    public void sendDescription(Player p) {
        // STRUCTURE OBLIGATOIRE :
        //   separator → space → INFO → (role + camp + objectif) → space
        //   → PASSIFS → (hover par passif) → space
        //   → ACTIFS (si applicable) → (hover par actif) → space
        //   → separator

        net.novaproject.novauhc.scenario.role.RoleDescription.of(p)
            .separator(MyScenarioDescLang.SEPARATOR)
            .space()
            .line(MyScenarioDescLang.SECTION_INFO)
            .line(MyScenarioDescLang.ROLE_PREFIX, MyScenarioDescLang.ROLENAME_NAME)
            .line(MyScenarioDescLang.CAMP_A)         // TODO: bon camp
            .line(MyScenarioDescLang.OBJECTIVE)
            .space()
            .line(MyScenarioDescLang.SECTION_PASSIFS)
            .hover(MyScenarioDescLang.ROLENAME_PASSIVE_TEXT, MyScenarioDescLang.ROLENAME_PASSIVE_HOVER)
            // .space()
            // .line(MyScenarioDescLang.SECTION_ACTIFS)  // décommenter si actifs
            // .hover(MyScenarioDescLang.ROLENAME_ACTIVE_TEXT, MyScenarioDescLang.ROLENAME_ACTIVE_HOVER)
            .space()
            .separator(MyScenarioDescLang.SEPARATOR)
            .send();
    }

    // ── Hooks inline (optionnel) ──────────────────────────────────────
    // Si la logique est simple, implémenter directement ici au lieu d'une Ability :
    //   onHit, onDeath, onKill, onSec (→ TOUJOURS avec getOwner() guard), onGive

    // @Override
    // public void onGive(UHCPlayer uhcPlayer) {
    //     // Donner des items au joueur au début de la partie
    //     uhcPlayer.getPlayer().getInventory().addItem(...);
    // }
}
