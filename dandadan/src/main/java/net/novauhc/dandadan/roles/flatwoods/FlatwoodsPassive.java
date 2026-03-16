package net.novauhc.dandadan.roles.flatwoods;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.concurrent.ThreadLocalRandom;

public class FlatwoodsPassive extends Ability {
    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "FLATWOODS_HP_THRESHOLD_NAME", descKey = "FLATWOODS_HP_THRESHOLD_DESC", type = VariableType.DOUBLE)
    private double hpThreshold = 4.0; // 2❤

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "FLATWOODS_KB_CHANCE_NAME", descKey = "FLATWOODS_KB_CHANCE_DESC", type = VariableType.PERCENTAGE)
    private int kbChancePct = 15;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "FLATWOODS_KB_STRENGTH_NAME", descKey = "FLATWOODS_KB_STRENGTH_DESC", type = VariableType.DOUBLE)
    private double kbStrength = 1.3;

    @Override public String getName()       { return "Flatwoods"; }
    @Override public Material getMaterial() { return null; }

    @Override public boolean onEnable(Player player) { return true; }

    @Override
    public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker)) return;
        if (attacker.getHealth() > hpThreshold) return; // > 2❤ → pas de bonus
        if (ThreadLocalRandom.current().nextDouble() >= kbChancePct / 100.0) return;
        Player victim = victimP.getPlayer();
        if (victim == null) return;
        org.bukkit.util.Vector kb = victim.getLocation().subtract(attacker.getLocation()).toVector().normalize().multiply(kbStrength).setY(0.4);
        victim.setVelocity(kb);
    }
}