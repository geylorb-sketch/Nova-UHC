package net.novaproject.ultimate.gonefish;

import net.novaproject.novauhc.lang.LangManager;

import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.scenario.Scenario;

import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class GoneFish extends Scenario {

    @ScenarioVariable(lang = ScenarioVarLang.class,
            nameKey = "GONEFISH_VAR_LUCK_LEVEL_NAME",
            descKey = "GONEFISH_VAR_LUCK_LEVEL_DESC",
            type = VariableType.INTEGER)
    private int luckLevel = 250;

    @ScenarioVariable(lang = ScenarioVarLang.class,
            nameKey = "GONEFISH_VAR_LURE_LEVEL_NAME",
            descKey = "GONEFISH_VAR_LURE_LEVEL_DESC",
            type = VariableType.INTEGER)
    private int lureLevel = 250;

    @ScenarioVariable(lang = ScenarioVarLang.class,
            nameKey = "GONEFISH_VAR_ANVIL_AMOUNT_NAME",
            descKey = "GONEFISH_VAR_ANVIL_AMOUNT_DESC",
            type = VariableType.INTEGER)
    private int anvilAmount = 20;

    @Override
    public String getName() {
        return "GoneFish";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.GONE_FISH, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.FISHING_ROD);
    }

    @Override
    public void onStart(Player player) {
        player.getInventory().addItem(
                new ItemCreator(Material.ANVIL)
                        .setAmount(anvilAmount)
                        .getItemstack()
        );
        player.getInventory().addItem(
                new ItemCreator(Material.FISHING_ROD)
                        .setUnbreakable(true)
                        .addEnchantment(Enchantment.LUCK, luckLevel)
                        .addEnchantment(Enchantment.LURE, lureLevel)
                        .getItemstack()
        );
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}