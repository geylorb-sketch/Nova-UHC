package net.novauhc.dandadan.roles.jiji;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.entity.Player;

public class AdaptPassive extends PassiveAbility {
    @Override public String getName() { return "Adapt"; }

    @Override
    public boolean onEnable(Player player) {
        return false;
    }

    private int hitsReceived = 0;
    public void onDamage() { hitsReceived++; }
    public double getResistMult() { return Math.max(0.5, 1.0 - hitsReceived * 0.02); }
}
