package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.lang.LangManager;

import java.util.HashSet;
import java.util.Set;

public class FastFurnace extends Scenario {

    private final Set<Location> activeFurnaces = new HashSet<>();

    @Override
    public String getName() {
        return "FastFurnace";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.FAST_FURNACE, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.FURNACE);
    }

    @Override
    public void onFurnaceBurn(FurnaceBurnEvent event) {
        if (!isActive()) return;
        Location loc = event.getBlock().getLocation();
        if (activeFurnaces.contains(loc)) return;
        activeFurnaces.add(loc);
        startUpdate((Furnace) event.getBlock().getState(), loc);
    }

    private void startUpdate(final Furnace block, final Location loc) {
        new BukkitRunnable() {
            public void run() {
                if (!isActive() || (block.getCookTime() <= 0 && block.getBurnTime() <= 0)) {
                    activeFurnaces.remove(loc);
                    cancel();
                    return;
                }
                block.setCookTime((short) (block.getCookTime() + 4));
                block.update();
            }
        }.runTaskTimer(Main.get(), 1L, 1L);
    }
}
