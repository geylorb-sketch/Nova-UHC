package net.novauhc.dandadan.roles.joseph;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ThomsonPassive extends Ability {
    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "THOMSON_MAX_USES_NAME", descKey = "THOMSON_MAX_USES_DESC", type = VariableType.INTEGER)
    private int maxUses = 3;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "THOMSON_HP_THRESHOLD_NAME", descKey = "THOMSON_HP_THRESHOLD_DESC", type = VariableType.DOUBLE)
    private double hpThreshold = 4.0; // 2❤

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "THOMSON_ARROW_COUNT_NAME", descKey = "THOMSON_ARROW_COUNT_DESC", type = VariableType.INTEGER)
    private int arrowCount = 3;

    private int uses = maxUses;
    @Override public String getName()       { return "Thomson"; }
    @Override public Material getMaterial() { return null; }

    @Override public boolean onEnable(Player player) { return true; }

    @Override public void onSec(Player player) {
        if (uses <= 0 || player.getHealth() > hpThreshold) return;
        uses--;
        for (int i = 0; i < arrowCount; i++) {
            double angle = (2*Math.PI/3)*i;
            Vector dir = new Vector(Math.cos(angle), 0.2, Math.sin(angle)).normalize();
            player.getWorld().spawnArrow(player.getLocation().add(0,1,0), dir, 1.5f, 1f);
        }
    }
}