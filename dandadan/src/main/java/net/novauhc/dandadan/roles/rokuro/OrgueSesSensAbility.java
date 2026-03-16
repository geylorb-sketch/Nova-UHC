package net.novauhc.dandadan.roles.rokuro;

import net.novaproject.novauhc.ability.Ability;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;

public class OrgueSesSensAbility extends Ability {
    @Override public String getName()       { return "Orgue des six sens"; }
    @Override public Material getMaterial() { return Material.BONE; }

    @Override
    public void onClick(org.bukkit.event.player.PlayerInteractEvent event, org.bukkit.inventory.ItemStack item) {
        if (event.getAction().name().contains("LEFT") && item != null && item.getType() == Material.BONE) {
            tryUse(event.getPlayer());
        }
    }

    @Override
    public boolean onEnable(Player player) {
        // Envoie 3 arcs de particules
        Vector dir = player.getLocation().getDirection().normalize();
        for (int arc = -1; arc <= 1; arc++) {
            double angle = arc * Math.PI / 6;
            Vector arcDir = rotateY(dir, angle);
            for (int i = 1; i <= 15; i++) {
                new ParticleBuilder(ParticleEffect.REDSTONE)
                        .setColor(Color.CYAN)
                        .setLocation(player.getLocation().clone().add(arcDir.clone().multiply(i)))
                        .setAmount(2).display();
            }
            // Vérifie collision avec joueurs
            player.getWorld().getPlayers().stream()
                    .filter(p -> !p.equals(player))
                    .forEach(target -> {
                        for (int i = 1; i <= 15; i++) {
                            if (target.getLocation().distance(player.getLocation().clone().add(arcDir.clone().multiply(i))) < 1.5) {
                                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999, 10, false, false));
                                target.sendMessage("§3⚠ Bloqué par l'Orgue des six sens !");
                                // La prochaine pomme ne donnera pas d'absorption (flag)
                                OrgueDebuff.mark(target.getUniqueId());
                                break;
                            }
                        }
                    });
        }
        setCooldown(420); return true;
    }

    private Vector rotateY(Vector v, double angle) {
        double cos = Math.cos(angle), sin = Math.sin(angle);
        return new Vector(v.getX() * cos + v.getZ() * sin, v.getY(), -v.getX() * sin + v.getZ() * cos);
    }

    public static class OrgueDebuff {
        private static final java.util.Set<java.util.UUID> marked = new java.util.HashSet<>();
        public static void mark(java.util.UUID uuid) { marked.add(uuid); }
        public static boolean isMarked(java.util.UUID uuid) { return marked.remove(uuid); }
    }
}

// ── Zone Incroyable ──────────────────────────────────────────────
