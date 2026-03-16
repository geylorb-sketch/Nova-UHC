package net.novauhc.dandadan.roles.bamora;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;

/**
 * Projectile électrique — Passif
 * Tous les 15 coups d'épée, trait de particules vertes + rangée d'éclairs (1.5❤ aux touchés).
 */
public class BamoraProjectilePassive extends Ability {

    @AbilityVariable(lang = DanDaDanVarLang.class,
            nameKey = "BAMORA_KAIJU_STRENGTH_PCT_NAME",
            descKey  = "BAMORA_KAIJU_STRENGTH_PCT_DESC",
            type = VariableType.INTEGER)
    private int hitTrigger = 15;

    @AbilityVariable(lang = DanDaDanVarLang.class,
            nameKey = "OKARUN_ALLOUT_DAMAGE_NAME",
            descKey  = "OKARUN_ALLOUT_DAMAGE_DESC",
            type = VariableType.DOUBLE)
    private double lightningDamage = 3.0; // 1.5❤

    private int hitCount = 0;

    @Override public String getName()       { return "Projectile électrique"; }
    @Override public Material getMaterial() { return null; }
    @Override public boolean onEnable(Player player) { return true; }

    @Override
    public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker)) return;
        hitCount++;
        if (hitCount < hitTrigger) return;
        hitCount = 0;

        Player victim = victimP.getPlayer();
        if (victim == null) return;

        // Trait de particules vert entre attaquant et victime
        Location start = attacker.getLocation();
        Location end   = victim.getLocation();
        int steps = (int) start.distance(end) * 2;
        for (int i = 0; i <= steps; i++) {
            double t = (double) i / steps;
            Location point = start.clone().add(
                    (end.getX() - start.getX()) * t,
                    (end.getY() - start.getY()) * t,
                    (end.getZ() - start.getZ()) * t
            );
            new ParticleBuilder(ParticleEffect.REDSTONE)
                    .setColor(Color.GREEN)
                    .setLocation(point).setAmount(1).display();
        }

        // Éclairs + dégâts aux joueurs proches
        victim.getWorld().getNearbyEntities(victim.getLocation(), 3, 3, 3)
                .stream().filter(e -> e instanceof Player && !e.equals(attacker))
                .forEach(e -> {
                    victim.getWorld().strikeLightningEffect(e.getLocation());
                    ((Player) e).damage(lightningDamage, attacker);
                });
    }
}
