package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.Common;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.scenario.LongShootLang;
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

public class RewardLongShot extends Scenario {

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "REWARDLONGSHOT_VAR_MIN_DISTANCE_NAME", descKey = "REWARDLONGSHOT_VAR_MIN_DISTANCE_DESC", type = VariableType.INTEGER)
    private int min_distance = 75;
    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "REWARDLONGSHOT_VAR_DAMAGE_MULTIPLIER_NAME", descKey = "REWARDLONGSHOT_VAR_DAMAGE_MULTIPLIER_DESC", type = VariableType.DOUBLE)
    private double damage_multiplier = 1.5;
    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "REWARDLONGSHOT_VAR_HEAL_AMOUNT_NAME", descKey = "REWARDLONGSHOT_VAR_HEAL_AMOUNT_DESC", type = VariableType.DOUBLE)
    private double heal_amount = 2.0;

    @Override public String getName() { return "Rewarding LongShot"; }
    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.REWARD_LONG_SHOT, player);
    }
    @Override public ItemCreator getItem() { return new ItemCreator(Material.BOW); }

    @Override
    public void onHit(Entity entity, Entity damager, EntityDamageByEntityEvent event) {
        if (!(damager instanceof Projectile projectile)) return;
        if (projectile.getType() != EntityType.ARROW) return;
        if (!(projectile.getShooter() instanceof Player shooter)) return;
        if (shooter.getLocation().distance(entity.getLocation()) < min_distance) return;
        event.setDamage(event.getDamage() * damage_multiplier);
        shooter.setHealth(Math.min(shooter.getMaxHealth(), shooter.getHealth() + heal_amount));
        shooter.sendMessage(LangManager.get().get(LongShootLang.LONG_SHOT_REWARD, shooter, Map.of("%servertag%", Common.get().getServertag())));
    }
}
