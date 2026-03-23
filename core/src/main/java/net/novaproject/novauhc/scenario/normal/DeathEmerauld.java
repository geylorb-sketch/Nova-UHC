package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;
import java.util.Random;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.lang.LangManager;

public class DeathEmerauld extends Scenario {

    private final Random random = new Random();

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "DEATHEMERAULD_VAR_DAMAGE_AMOUNT_NAME", descKey = "DEATHEMERAULD_VAR_DAMAGE_AMOUNT_DESC", type = VariableType.DOUBLE)
    private double damageAmount = 4.0;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "DEATHEMERAULD_VAR_TARGET_BLOCK_NAME", descKey = "DEATHEMERAULD_VAR_TARGET_BLOCK_DESC", type = VariableType.STRING)
    private String targetBlock = "EMERALD_ORE";

    @Override
    public String getName() {
        return "DeathEmerauld";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.DEATH_EMERAULD, player)
                .replace("%block%", String.valueOf(targetBlock))
                .replace("%damage%", String.valueOf(damageAmount));
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.EMERALD);
    }

    @Override
    public void onBreak(Player player, Block block, BlockBreakEvent event) {
        Material target = Material.getMaterial(targetBlock);
        if (target == null || block.getType() != target) return;

        List<UHCPlayer> players = UHCPlayerManager.get().getPlayingOnlineUHCPlayers();
        if (players.isEmpty()) return;

        UHCPlayer chosen = players.get(random.nextInt(players.size()));
        chosen.getPlayer().damage(damageAmount);
    }
}
