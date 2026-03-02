package net.novaproject.ultimate.legend.roles.abilities;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;


public class ArcherBowPassive extends Ability {

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "ARCHER_BOW_BONUS_NAME", descKey = "ARCHER_BOW_BONUS_DESC", type = VariableType.DOUBLE)
    private double bowBonusDamage = 1.5;

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "ARCHER_SLOW_CHANCE_NAME", descKey = "ARCHER_SLOW_CHANCE_DESC", type = VariableType.PERCENTAGE)
    private double slowChance = 0.25;

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "ARCHER_SLOW_DURATION_NAME", descKey = "ARCHER_SLOW_DURATION_DESC", type = VariableType.TIME)
    private int slowDuration = 5;


    private Player pendingVictim;
    private EntityDamageByEntityEvent pendingEvent;

    @Override public String getName() { return "Maîtrise de l'Arc"; }
    @Override public Material getMaterial() { return null; }

    @Override
    public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {

        if (!(event.getDamager() instanceof Arrow arrow)) return;
        if (!(arrow.getShooter() instanceof Player shooter)) return;

        Player victim = victimP.getPlayer();
        if (victim == null) return;

        pendingVictim = victim;
        pendingEvent  = event;
        tryUse(shooter);
    }

    @Override
    public boolean onEnable(Player player) {
        if (pendingEvent == null || pendingVictim == null) return false;

        pendingEvent.setDamage(pendingEvent.getDamage() + bowBonusDamage);

        if (ThreadLocalRandom.current().nextDouble() < slowChance) {
            pendingVictim.addPotionEffect(
                    new PotionEffect(PotionEffectType.SLOW, 20 * slowDuration, 0));
        }

        pendingVictim = null;
        pendingEvent  = null;
        return true;
    }
}
