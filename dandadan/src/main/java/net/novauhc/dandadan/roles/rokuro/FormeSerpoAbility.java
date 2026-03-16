package net.novauhc.dandadan.roles.rokuro;

import net.novaproject.novauhc.ability.UseAbiliy;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class FormeSerpoAbility extends UseAbiliy {
    @Override public String getName()       { return "Forme serpoienne"; }
    @Override public Material getMaterial() { return Material.COMPASS; }
    @Override public boolean onEnable(Player player) {
        // Repousse tous les joueurs devant Rokuro + les bloque 3s
        player.getWorld().getNearbyEntities(player.getLocation(), 10, 10, 10)
                .stream().filter(e -> e instanceof Player && !e.equals(player))
                .forEach(e -> {
                    Vector dir = e.getLocation().subtract(player.getLocation()).toVector().normalize();
                    e.setVelocity(dir.multiply(2.5).setY(0.5));
                    Player target = (Player) e;
                    target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 10, false, false));
                });
        setCooldown(180); return true;
    }
}

// ── Orgue des six sens ───────────────────────────────────────────
