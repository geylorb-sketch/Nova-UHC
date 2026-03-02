package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.lang.LangManager;

public class NoNameTag extends Scenario {

    @Override
    public String getName() {
        return "NoNameTag";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.NO_NAME_TAG, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.NAME_TAG);
    }

    @Override
    public void onStart(Player player) {
        player.setCustomNameVisible(false);
    }
}
