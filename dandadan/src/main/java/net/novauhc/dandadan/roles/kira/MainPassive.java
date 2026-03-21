package net.novauhc.dandadan.roles.kira;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.entity.Player;

public class MainPassive extends PassiveAbility {
    @Override public String getName() { return "Main"; }

    @Override
    public boolean onEnable(Player player) {
        return false;
    }

    private int hitCount = 0;
    public void onHit() { hitCount++; }
    public int getHitCount() { return hitCount; }
}
