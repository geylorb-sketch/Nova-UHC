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


public class ZeusLightningPassive extends Ability {

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "ZEUS_LIGHTNING_CHANCE_NAME", descKey = "ZEUS_LIGHTNING_CHANCE_DESC", type = VariableType.PERCENTAGE)
    private double lightningChance = 0.10;

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "ZEUS_SPEED_CHANCE_NAME", descKey = "ZEUS_SPEED_CHANCE_DESC", type = VariableType.PERCENTAGE)
    private double speedChance = 0.20;


    private Player pendingAttacker;
    private Player pendingVictim;

    public ZeusLightningPassive() { setCooldown(0); }

    @Override public String getName() { return "Foudre de Zeus"; }
    @Override public Material getMaterial() { return null; }

    @Override
    public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker)) return;
        if (getOwner() == null || !attacker.equals(getOwner().getPlayer())) return;
        Player victim = victimP.getPlayer();
        if (victim == null) return;
        pendingAttacker = attacker;
        pendingVictim   = victim;
        tryUse(attacker);
    }

    @Override
    public boolean onEnable(Player player) {
        if (pendingAttacker == null || pendingVictim == null) return false;

        boolean triggered = false;

        if (ThreadLocalRandom.current().nextDouble() < lightningChance) {
            pendingVictim.getWorld().strikeLightningEffect(pendingVictim.getLocation());
            pendingVictim.damage(3.0, pendingAttacker);
            triggered = true;
        }

        if (ThreadLocalRandom.current().nextDouble() < speedChance) {
            pendingAttacker.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 0));
            triggered = true;
        }

        pendingAttacker = null;
        pendingVictim   = null;
        return triggered;
    }
}
