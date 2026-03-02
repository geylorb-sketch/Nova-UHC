package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.lang.LangManager;

public class NoFood extends Scenario {
    @Override
    public String getName() {
        return "NoFood";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.NO_FOOD, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.GOLDEN_CARROT);
    }

    @Override
    public void noFood(FoodLevelChangeEvent event) {
        event.setCancelled(true);
        event.setFoodLevel(20);

    }
}

