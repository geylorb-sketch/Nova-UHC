package net.novauhc.dandadan.roles.flatwoods;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class FlatwoodsPassive extends PassiveAbility {
    @Override public String getName() { return "Flatwoods"; }

    @Override
    public boolean onEnable(Player player) {
        return false;
    }

    public void onDamage(Player player) {
        if (player.getHealth() < 8.0) {
            for (Entity e : player.getNearbyEntities(5,5,5)) {
                if (e instanceof Player t) {
                    Vector push = t.getLocation().toVector().subtract(player.getLocation().toVector()).normalize().multiply(1.5);
                    t.setVelocity(push);
                }
            }
        }
    }
}
