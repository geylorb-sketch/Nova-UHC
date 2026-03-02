package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcteam.UHCTeam;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;

import net.novaproject.novauhc.lang.LangManager;

public class Compensation extends Scenario {

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "COMPENSATION_VAR_HEARTS_PER_DEATH_NAME", descKey = "COMPENSATION_VAR_HEARTS_PER_DEATH_DESC", type = VariableType.DOUBLE)
    private final double heartsPerDeath = 2.0;

    @Override
    public String getName() {
        return "Compensation";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.COMPENSATION, player)                .replace("%hearts%", String.valueOf(heartsPerDeath));
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.DIAMOND);
    }

    @Override
    public void onDeath(UHCPlayer uhcPlayer, UHCPlayer killer, PlayerDeathEvent event) {
        if (uhcPlayer.getTeam().isPresent()) {
            UHCTeam team = uhcPlayer.getTeam().get();
            double healthToAdd = heartsPerDeath * 2;
            team.getPlayers().forEach(uhcP -> uhcP.getPlayer().setMaxHealth(uhcP.getPlayer().getMaxHealth() + healthToAdd));
        }
    }
}
