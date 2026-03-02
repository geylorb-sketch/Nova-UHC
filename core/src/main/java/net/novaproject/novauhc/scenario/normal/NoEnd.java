package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.scenario.NoEndLang;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerPortalEvent;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;

public class NoEnd extends Scenario {
    @Override public String getName() { return "EndLess"; }
    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.NO_END, player);
    }
    @Override public ItemCreator getItem() { return new ItemCreator(Material.ENDER_STONE); }

    @Override
    public void onPortal(PlayerPortalEvent event) {
        if (event.getCause() == PlayerPortalEvent.TeleportCause.END_PORTAL) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(LangManager.get().get(NoEndLang.BLOCKED, event.getPlayer()));
        }
    }
}
