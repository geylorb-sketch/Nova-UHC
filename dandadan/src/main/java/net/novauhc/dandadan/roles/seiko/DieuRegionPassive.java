package net.novauhc.dandadan.roles.seiko;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Dieu de la region — Passif Seiko
 * NO: 20% speed | NE: 20% resist | SO: 20% force | SE: fire resist
 */
public class DieuRegionPassive extends PassiveAbility {

    @Override public String getName() { return "Dieu de la region"; }

    @Override
    public boolean onEnable(Player player) {
        Location loc = player.getLocation();
        boolean north = loc.getZ() < 0;
        boolean west = loc.getX() < 0;

        player.removePotionEffect(PotionEffectType.SPEED);
        player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
        player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);

        if (north && west) { // NW
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 0, false, false));
        } else if (north) { // NE
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40, 0, false, false));
        } else if (west) { // SW
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 40, 0, false, false));
        } else { // SE
            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 40, 0, false, false));
        }
        return false;
    }
}
