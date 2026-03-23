package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.scenario.LongShootLang;
import net.novaproject.novauhc.Common;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Map;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;

public class LongShoot extends Scenario {

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "LONGSHOOT_VAR_MIN_DISTANCE_NAME", descKey = "LONGSHOOT_VAR_MIN_DISTANCE_DESC", type = VariableType.INTEGER)
    private int minDistance = 75;
    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "LONGSHOOT_VAR_DAMAGE_MULTIPLIER_NAME", descKey = "LONGSHOOT_VAR_DAMAGE_MULTIPLIER_DESC", type = VariableType.DOUBLE)
    private double damageMultiplier = 1.5;

    @Override public String getName() { return "LongShot"; }
    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.LONG_SHOOT, player)
                .replace("%distance%", String.valueOf(minDistance))
                .replace("%multiplier%", String.valueOf(damageMultiplier));
    }
    @Override public ItemCreator getItem() { return new ItemCreator(Material.BOW); }

    @Override
    public void onHit(Entity entity, Entity damager, EntityDamageByEntityEvent event) {
        if (!(damager instanceof Projectile projectile)) return;
        if (!projectile.getType().equals(EntityType.ARROW)) return;
        if (!(projectile.getShooter() instanceof Player shooter)) return;
        if (shooter.getLocation().distance(entity.getLocation()) < minDistance) return;
        event.setDamage(event.getDamage() * damageMultiplier);
        shooter.sendMessage(LangManager.get().get(LongShootLang.LONG_SHOT, shooter, Map.of("%servertag%", Common.get().getServertag())));
    }
}
