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

public class LongShoot extends Scenario {

    @ScenarioVariable(name="Distance minimale", description="Distance minimale pour le bonus", type=VariableType.INTEGER)
    private int minDistance = 75;
    @ScenarioVariable(name="Multiplicateur de dégâts", description="Multiplicateur pour les tirs longue distance", type=VariableType.DOUBLE)
    private double damageMultiplier = 1.5;

    @Override public String getName() { return "LongShot"; }
    @Override public String getDescription() { return "Les tirs à l'arc de plus de " + minDistance + " blocs infligent x" + damageMultiplier + " de dégâts."; }
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
