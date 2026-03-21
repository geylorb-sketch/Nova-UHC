package net.novauhc.dandadan.roles.caesar;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.entity.Player;

public class HamonPassive extends PassiveAbility {
    @Override public String getName() { return "Hamon"; }

    @Override
    public boolean onEnable(Player player) {
        return false;
    }

    public void onDamage(Player p) {
        if (p.getFireTicks() > 0) { p.setFireTicks(0); }
    }
}
