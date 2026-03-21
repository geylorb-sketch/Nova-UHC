package net.novauhc.dandadan.roles.rohan;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EcrivainPassive extends PassiveAbility {
    @Override public String getName() { return "Ecrivain"; }
    @Override public boolean onEnable(Player player) {
        for (Entity e : player.getNearbyEntities(20,20,20)) {
            if (e instanceof Player t) t.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 40, 0, false, false));
        }
        return false;
    }
}
