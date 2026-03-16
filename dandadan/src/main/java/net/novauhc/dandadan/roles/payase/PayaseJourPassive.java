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

/** Payase Jour — Passif : 5% invis 1s si HP < 3❤ en recevant un coup. */
public class PayaseJourPassive extends Ability {

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "PAYASE_JOUR_CHANCE_NAME", descKey = "PAYASE_JOUR_CHANCE_DESC", type = VariableType.PERCENTAGE)
    private int invisChancePct = 5; // 5%

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "PAYASE_JOUR_HP_THRESHOLD_NAME", descKey = "PAYASE_JOUR_HP_THRESHOLD_DESC", type = VariableType.DOUBLE)
    private double hpThreshold = 6.0; // 3❤

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "PAYASE_JOUR_INVIS_DURATION_NAME", descKey = "PAYASE_JOUR_INVIS_DURATION_DESC", type = VariableType.TIME)
    private int invisDurationTicks = 20; // 1s

    @Override public String getName()       { return "Payase Jour"; }
    @Override public Material getMaterial() { return null; }
    @Override public boolean onEnable(Player player) { return true; }

    @Override
    public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        Player victim = victimP.getPlayer();
        if (victim == null) return;
        if (!isDay(victim)) return;
        if (victim.getHealth() > hpThreshold) return;
        if (ThreadLocalRandom.current().nextDouble() >= invisChancePct / 100.0) return;

        victim.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, invisDurationTicks, 0, false, false));
    }

    private boolean isDay(Player p) {
        long time = p.getWorld().getTime();
        return time < 12300 || time > 23850;
    }
}
