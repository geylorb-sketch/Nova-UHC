package net.novauhc.dandadan.roles.oeilmalefique;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EnviePassive extends PassiveAbility {
    @Override public String getName() { return "Envie"; }
    @Override public boolean onEnable(Player player) {
        for (Entity e : player.getNearbyEntities(30,30,30)) {
            if (e instanceof Player t) t.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 40, 0, false, false));
        }
        return false;
    }
}
