package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.lang.LangManager;

public class BloodDiamonds extends Scenario {
    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "BLOODDIAMONDS_VAR_DAMAGE_AMOUNT_NAME", descKey = "BLOODDIAMONDS_VAR_DAMAGE_AMOUNT_DESC", type = VariableType.DOUBLE)
    private final int damageAmount = 1;

    @Override
    public String getName() {
        return "BloodDiamonds";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.BLOOD_DIAMONDS, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.DIAMOND_ORE);
    }

    @Override
    public void onBreak(Player player, Block block, BlockBreakEvent event) {
        if (!isActive() || block.getType() != Material.DIAMOND_ORE) return;

        player.damage(damageAmount);
    }
}
