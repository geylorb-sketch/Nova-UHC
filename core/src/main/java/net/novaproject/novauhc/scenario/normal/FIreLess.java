package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.lang.LangManager;

public class FIreLess extends Scenario {
    @Override
    public String getName() {
        return "FireLess";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.FIRE_LESS, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.MAGMA_CREAM);
    }
    @Override
    public void onPlayerTakeDamage(Entity entity, EntityDamageEvent event) {

        if (event.getCause() == EntityDamageEvent.DamageCause.FIRE
                || event.getCause() == EntityDamageEvent.DamageCause.LAVA
                || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
            event.setCancelled(true);
        }
    }
}
