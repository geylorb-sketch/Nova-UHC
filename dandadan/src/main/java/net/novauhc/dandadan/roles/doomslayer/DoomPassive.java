package net.novauhc.dandadan.roles.doomslayer;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.entity.Player;

public class DoomPassive extends PassiveAbility {
    @Override public String getName() { return "Doom"; }
    @Override public boolean onEnable(Player player) {
        player.setMaxHealth(Math.min(40, player.getMaxHealth() + 10));
        return false;
    }
}
