package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.lang.LangManager;

public class InfiniteEnchanter extends Scenario {
    @Override
    public String getName() {
        return "InfiniteEnchanter";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.INFINITE_ENCHANTER, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.ENCHANTMENT_TABLE);
    }

    @Override
    public void onStart(Player player) {
        player.setLevel(10000000);
    }
}
