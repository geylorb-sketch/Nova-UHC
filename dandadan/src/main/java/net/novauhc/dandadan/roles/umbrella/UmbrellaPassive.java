package net.novauhc.dandadan.roles.umbrella;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class UmbrellaPassive extends PassiveAbility {
    @Override public String getName() { return "Umbrella"; }

    @Override
    public boolean onEnable(Player player) {
        return false;
    }

    public void onDamage(Player player) {
        for (Entity e : player.getNearbyEntities(3,3,3)) {
            if (e instanceof Player t) {
                Vector push = t.getLocation().toVector().subtract(player.getLocation().toVector()).normalize().multiply(1.2);
                t.setVelocity(push);
            }
        }
    }
}
