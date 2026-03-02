package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.lang.LangManager;

public class NoCleanUp extends Scenario {

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "NOCLEANUP_VAR_HEALTH_RESTORE_NAME", descKey = "NOCLEANUP_VAR_HEALTH_RESTORE_DESC", type = VariableType.DOUBLE)
    private final double healthRestore = 8.0;

    @Override
    public String getName() {
        return "NoCleanUp";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.NO_CLEAN_UP, player)
                .replace("%hearts%", String.valueOf(healthRestore / 2));
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.GOLD_NUGGET);
    }

    @Override
    public void onDeath(UHCPlayer uhcPlayer, UHCPlayer killer, PlayerDeathEvent event) {
        if (killer != null) {
            double nextHealth = killer.getPlayer().getHealth() + healthRestore;
            killer.getPlayer().setHealth(Math.min(nextHealth, killer.getPlayer().getMaxHealth()));
        }
    }
}
