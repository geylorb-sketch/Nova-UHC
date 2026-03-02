package net.novaproject.ultimate.legend.roles.abilities;

import net.novaproject.novauhc.ability.PassiveAbility;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Passif Princesse : Marqueur pour immunité aux dégâts de chute.
 * Aucun effet appliqué ici — le listener du rôle vérifie isPuppet() via hasAbility().
 * PassiveAbility.onSec() → tryUse() → onEnable() — retourne true pour signaler que
 * l'ability est active (cooldown 0 = re-check chaque seconde sans overhead).
 */
public class PrincesseNoFallPassive extends PassiveAbility {

    public PrincesseNoFallPassive() { setCooldown(0); }

    @Override public String getName() { return "Grâce Royale"; }
    @Override public Material getMaterial() { return null; }

    @Override
    public boolean onEnable(Player player) {
        // Marqueur pur : la logique d'immunité est gérée dans le listener EntityDamageEvent du rôle.
        // On retourne true pour indiquer que l'ability est bien active.
        return true;
    }
}
