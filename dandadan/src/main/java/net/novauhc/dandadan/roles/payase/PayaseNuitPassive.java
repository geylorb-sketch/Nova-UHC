package net.novauhc.dandadan.roles.payase;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

public class PayaseNuitPassive extends Ability {
    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "PAYASE_NUIT_CHANCE_NAME", descKey = "PAYASE_NUIT_CHANCE_DESC", type = VariableType.PERCENTAGE)
    private int invisChancePct = 5;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "PAYASE_NUIT_HP_THRESHOLD_NAME", descKey = "PAYASE_NUIT_HP_THRESHOLD_DESC", type = VariableType.DOUBLE)
    private double hpThreshold = 10.0; // 5❤

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "PAYASE_NUIT_INVIS_DURATION_NAME", descKey = "PAYASE_NUIT_INVIS_DURATION_DESC", type = VariableType.TIME)
    private int invisDurationTicks = 60; // 3s

    @Override public String getName()       { return "Payase Nuit"; }
    @Override public Material getMaterial() { return null; }

    @Override public boolean onEnable(Player player) { return true; }

    @Override
    public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        Player victim = victimP.getPlayer();
        if (victim == null) return;
        long time = victim.getWorld().getTime();
        if (time < 12300 || time > 23850) return; // jour
        if (victim.getHealth() <= hpThreshold) return;
        if (ThreadLocalRandom.current().nextDouble() >= invisChancePct / 100.0) return;
        victim.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, invisDurationTicks, 0, false, false));
    }
}

// ─────────────────────────────────────────────────────────────

/** Darkness Form — Clic-Droit (nuit : Résistance15%+Force15%, jour : +10% d'un des deux aléatoire). */
