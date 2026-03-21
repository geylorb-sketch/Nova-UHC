package net.novauhc.dandadan.roles.denji;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.entity.Player;

public class SangPassive extends PassiveAbility {
    @Override public String getName() { return "Sang"; }

    @Override
    public boolean onEnable(Player player) {
        addSang(10);
        return true;
    }

    private int sang = 100;
    public int getSang() { return sang; }
    public void addSang(int amount) { sang = Math.min(100, sang + amount); }
    public boolean useSang(int amount) { if (sang < amount) return false; sang -= amount; return true; }
}
