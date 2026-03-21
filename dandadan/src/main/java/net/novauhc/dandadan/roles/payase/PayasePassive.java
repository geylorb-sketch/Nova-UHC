package net.novauhc.dandadan.roles.payase;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class PayasePassive extends PassiveAbility {
    @Override public String getName() { return "Payase Passif"; }

    private final Random random = new Random();

    @Override
    public boolean onEnable(Player player) {
        // Checked per-tick, passive only sets context
        return false;
    }

    public void onDamage(Player player, EntityDamageByEntityEvent event) {
        long time = player.getWorld().getTime();
        boolean night = time > 13000 && time < 23000;
        if (night && player.getHealth() > 10.0 && random.nextDouble() < 0.05) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 60, 0, false, false));
        } else if (!night && player.getHealth() < 6.0 && random.nextDouble() < 0.05) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20, 0, false, false));
        }
    }

}
