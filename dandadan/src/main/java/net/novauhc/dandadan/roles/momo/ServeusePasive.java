package net.novauhc.dandadan.roles.momo;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Serveuse — Passif
 * 2% de chance d'annuler le KB si la victime est en train de manger une pomme en or.
 * Le tracking est fait par MomoRole.onConsume() via markEating/unmarkEating.
 */
public class ServeusePasive extends Ability {

    @AbilityVariable(lang = DanDaDanVarLang.class,
            nameKey = "MOMO_SERVEUSE_CHANCE_NAME",
            descKey  = "MOMO_SERVEUSE_CHANCE_DESC",
            type = VariableType.PERCENTAGE)
    private double cancelChance = 0.02;

    private final Set<UUID> eatingPlayers = new HashSet<>();

    @Override public String getName()       { return "Serveuse"; }
    @Override public Material getMaterial() { return null; }
    @Override public boolean onEnable(Player player) { return true; }

    @Override
    public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        Player victim = victimP.getPlayer();
        if (victim == null) return;
        if (eatingPlayers.contains(victim.getUniqueId())
                && ThreadLocalRandom.current().nextDouble() < cancelChance) {
            victim.setVelocity(victim.getVelocity().multiply(0));
        }
    }

    public void markEating(UUID uuid)   { eatingPlayers.add(uuid); }
    public void unmarkEating(UUID uuid) { eatingPlayers.remove(uuid); }
}
