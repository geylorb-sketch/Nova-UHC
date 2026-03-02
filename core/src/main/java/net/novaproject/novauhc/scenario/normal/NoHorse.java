package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.lang.CommonLang;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;

public class NoHorse extends Scenario {
    @Override
    public String getName() {
        return "HorseLess";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.NO_HORSE, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.SADDLE);
    }

    @Override
    public void onPlayerInteractonEntity(Player player, PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() == EntityType.HORSE) {
            event.setCancelled(true);
            LangManager.get().send(CommonLang.DISABLE_ACTION, player);
        }
    }

}

