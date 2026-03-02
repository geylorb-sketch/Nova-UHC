package net.novaproject.ultimate.legend.roles.abilities;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;


public class DragonFirePassive extends Ability {

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "DRAGON_FIRE_CHANCE_NAME", descKey = "DRAGON_FIRE_CHANCE_DESC", type = VariableType.PERCENTAGE)
    private double fireChance = 0.30;

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "DRAGON_FIRE_DURATION_NAME", descKey = "DRAGON_FIRE_DURATION_DESC", type = VariableType.INTEGER)
    private int fireDuration = 5;


    private Player pendingVictim;

    public DragonFirePassive() { setCooldown(0); }

    @Override public String getName() { return "Souffle du Dragon"; }
    @Override public Material getMaterial() { return null; }


    @Override
    public void onSec(Player player) {
        player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 80, 0, false, false));
        super.onSec(player);
    }


    @Override
    public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker)) return;
        Player victim = victimP.getPlayer();
        if (victim == null) return;
        pendingVictim = victim;
        tryUse(attacker);
    }


    @Override
    public boolean onEnable(Player player) {
        if (pendingVictim == null) return false;
        boolean ignited = false;
        if (ThreadLocalRandom.current().nextDouble() < fireChance) {
            pendingVictim.setFireTicks(20 * fireDuration);
            ignited = true;
        }
        pendingVictim = null;
        return ignited;
    }
}
