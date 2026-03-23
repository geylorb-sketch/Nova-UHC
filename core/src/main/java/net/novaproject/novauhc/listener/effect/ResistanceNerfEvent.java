package net.novaproject.novauhc.listener.effect;

import net.novaproject.novauhc.scenario.ScenarioManager;
import net.novaproject.novauhc.scenario.role.Role;
import net.novaproject.novauhc.scenario.role.ScenarioRole;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.potion.PotionEffectType;

public class ResistanceNerfEvent implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onNerfResistance(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        UHCPlayer uhcPlayer = UHCPlayerManager.get().getPlayer(player);
        if (!uhcPlayer.isPlaying()) return;
        if (!player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) return;

        double resistancePercent = uhcPlayer.getResistancePercent();

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

            double roleResistancePercent = role.getResistancePercent();
            if (roleResistancePercent == 0) return;

            resistancePercent = roleResistancePercent;
        }

        event.setDamage(DamageModifier.RESISTANCE, event.getDamage(DamageModifier.RESISTANCE) * resistancePercent);
    }
}