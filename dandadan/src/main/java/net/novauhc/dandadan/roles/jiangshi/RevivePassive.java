package net.novauhc.dandadan.roles.jiangshi;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.entity.Player;

public class RevivePassive extends PassiveAbility {
    @Override public String getName() { return "Revive"; }

    @Override
    public boolean onEnable(Player player) {
        return false;
    }

    private boolean revived = false;
    public boolean canRevive() { return !revived; }
    public void setRevived() { revived = true; }
}
