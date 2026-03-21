package net.novauhc.dandadan.roles.momo;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Random;

/**
 * Serveuse — Passif Momo
 * 2% de chance d'annuler le KB du joueur frappé quand il mange une pomme en or.
 */
public class ServeusePassive extends PassiveAbility {

    private final Random random = new Random();

    @Override public String getName() { return "Serveuse"; }

    @Override
    public boolean onEnable(Player player) { return false; }

    /**
     * Appelé quand Momo frappe un joueur.
     * Si la victime mange une pomme en or → 2% de chance d'annuler le KB.
     */
    public boolean shouldCancelKB(Player victim) {
        if (victim == null) return false;
        // Vérifier si la victime a une pomme en or dans la main
        if (victim.getItemInHand() == null) return false;
        Material hand = victim.getItemInHand().getType();
        if (hand != Material.GOLDEN_APPLE) return false;
        return random.nextDouble() < 0.02; // 2%
    }
}
