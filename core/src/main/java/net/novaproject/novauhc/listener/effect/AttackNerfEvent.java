package net.novaproject.novauhc.listener.effect;

import net.novaproject.novauhc.scenario.ScenarioManager;
import net.novaproject.novauhc.scenario.role.Role;
import net.novaproject.novauhc.scenario.role.ScenarioRole;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.utils.ItemUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

public class AttackNerfEvent implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onNerfStrength(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player damager)) return;
        if (!(event.getEntity() instanceof LivingEntity))    return;
        if (event.getDamage() <= 0.0)                        return;

        UHCPlayer uhcPlayer = UHCPlayerManager.get().getPlayer(damager);
        if (!uhcPlayer.isPlaying()) return;

        double strengthPercent = uhcPlayer.getForcePercent();
        double criticPercent   = uhcPlayer.getForceCriticPercent();

        ScenarioRole<?> activeScenarioRole = ScenarioManager.get()
                .getActiveSpecialScenarios()
                .stream()
                .filter(s -> s instanceof ScenarioRole)
                .map(s -> (ScenarioRole<?>) s)
                .findFirst()
                .orElse(null);

        if (activeScenarioRole != null) {
            Role role = activeScenarioRole.getRoleByUHCPlayer(uhcPlayer);
            if (role == null) return;

            double roleStrengthPercent = role.getStrengthPercent();
            double roleCriticPercent   = role.getStrengthCriticPercent();
            if (roleStrengthPercent == 0 && roleCriticPercent == 0) return;

            strengthPercent = roleStrengthPercent;
            criticPercent   = roleCriticPercent;
        }

        boolean hasStrength = damager.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE);
        boolean isCritical  = ItemUtil.isCriticalDamage(damager);

        if (!hasStrength && !isCritical) return;

        double itemDamage    = ItemUtil.getAttackDamageItem(damager.getItemInHand());
        double enchantDamage = ItemUtil.getEnchantDamageItem(damager.getItemInHand());
        double strengthLevel = ItemUtil.getEnchantEffect(damager);

        double strengthMultiplier = hasStrength ? 1.0 + 1.3 * strengthLevel * strengthPercent : 1.0;
        double criticMultiplier   = isCritical  ? 1.0 + 0.5 * criticPercent                  : 1.0;

        double total;

        if (isCritical && isPureCritical(itemDamage, strengthLevel, event.getDamage())) {
            total = itemDamage * strengthMultiplier * 0.5 * criticPercent;
        } else {
            total = itemDamage * strengthMultiplier * criticMultiplier + enchantDamage;
        }

        applyNerfedDamage(event, total);
    }

    private boolean isPureCritical(double itemDamage, double strengthLevel, double rawDamage) {
        double vanillaCriticBonus = itemDamage * (1.0 + 1.3 * strengthLevel) * 0.5;
        return Math.round(vanillaCriticBonus / rawDamage) == 1;
    }

    private void applyNerfedDamage(EntityDamageByEntityEvent event, double total) {
        event.setDamage(total);
    }
}