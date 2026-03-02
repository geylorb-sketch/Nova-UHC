package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.scenario.NoNetherLang;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioManager;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerPortalEvent;

import java.util.Optional;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;

public class NoNether extends Scenario {
    @Override public String getName() { return "NetherLess"; }
    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.NO_NETHER, player);
    }
    @Override public ItemCreator getItem() { return new ItemCreator(Material.NETHERRACK); }

    @Override
    public void onPortal(PlayerPortalEvent event) {
        Optional<Scenario> netheriBus = ScenarioManager.get().getScenarioByName("NetheriBus");
        if (netheriBus.isPresent() && ScenarioManager.get().getActiveScenarios().contains(netheriBus.get())) return;
        if (event.getCause() == PlayerPortalEvent.TeleportCause.NETHER_PORTAL) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(LangManager.get().get(NoNetherLang.BLOCKED, event.getPlayer()));
        }
    }
}
