package net.novauhc.dandadan.roles.devilman;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.concurrent.ThreadLocalRandom;

public class FlammePassive extends Ability {
    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "FLAMME_CHANCE_NAME", descKey = "FLAMME_CHANCE_DESC", type = VariableType.PERCENTAGE)
    private int counterFireChancePct = 2;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "FLAMME_FIRE_TICKS_NAME", descKey = "FLAMME_FIRE_TICKS_DESC", type = VariableType.TIME)
    private int fireTicks = 40;

    @Override public String getName()       { return "Flamme"; }
    @Override public Material getMaterial() { return null; }

    @Override public boolean onEnable(Player player) { return true; }

    @Override public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player devilman)) return; // Devilman est frappé
        if (ThreadLocalRandom.current().nextDouble() < counterFireChancePct / 100.0) {
            if (event.getDamager() instanceof Player attacker) attacker.setFireTicks(fireTicks);
        }
    }
}