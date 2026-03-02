package net.novaproject.ultimate.legend.roles.abilities;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;


public class SuccubeLifestealPassive extends Ability {

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "SUCCUBE_LIFESTEAL_NAME", descKey = "SUCCUBE_LIFESTEAL_DESC", type = VariableType.DOUBLE)
    private double lifestealAmount = 1.0;


    private Player pendingAttacker;

    public SuccubeLifestealPassive() { setCooldown(0); }

    @Override public String getName() { return "Vol de Vie"; }
    @Override public Material getMaterial() { return null; }

    @Override
    public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker)) return;
        pendingAttacker = attacker;
        tryUse(attacker);
    }

    @Override
    public boolean onEnable(Player player) {
        if (pendingAttacker == null) return false;
        pendingAttacker.setHealth(
                Math.min(pendingAttacker.getHealth() + lifestealAmount, pendingAttacker.getMaxHealth()));
        pendingAttacker = null;
        return true;
    }
}
