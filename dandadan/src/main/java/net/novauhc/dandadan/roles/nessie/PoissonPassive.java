package net.novauhc.dandadan.roles.nessie;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PoissonPassive extends PassiveAbility {
    @Override public String getName() { return "Poisson"; }
    @Override public boolean onEnable(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 40, 0, false, false));
        return false;
    }
}
