package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.lang.lang.CommonLang;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;

public class TimeBombe extends Scenario {

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "TIMEBOMBE_VAR_EXPLOSION_DELAY_NAME", descKey = "TIMEBOMBE_VAR_EXPLOSION_DELAY_DESC", type = VariableType.INTEGER)
    private final int explosion_delay = 30;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "TIMEBOMBE_VAR_EXPLOSION_POWER_NAME", descKey = "TIMEBOMBE_VAR_EXPLOSION_POWER_DESC", type = VariableType.INTEGER)
    private final int explosion_power = 2;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "TIMEBOMBE_VAR_GIVE_GOLDEN_APPLE_NAME", descKey = "TIMEBOMBE_VAR_GIVE_GOLDEN_APPLE_DESC", type = VariableType.BOOLEAN)
    private final boolean give_golden_apple = true;

    @Override
    public String getName() {
        return "TimeBombe";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.TIME_BOMBE, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.TNT);
    }

    @Override
    public void onDeath(UHCPlayer uhcPlayer, UHCPlayer killer, PlayerDeathEvent event) {

        if (UHCManager.get().getTimer() <= UHCManager.get().getTimerpvp()) return;

        event.getDrops().clear();

        Location loc = uhcPlayer.getPlayer().getLocation().clone();
        Block block = loc.getBlock().getRelative(BlockFace.DOWN);

        block.setType(Material.CHEST);
        Chest chest = (Chest) block.getState();

        block.getRelative(BlockFace.NORTH).setType(Material.CHEST);

        if (give_golden_apple) {
            chest.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
        }

        for (ItemStack item : uhcPlayer.getPlayer().getInventory().getContents()) {
            if (item != null && item.getType() != Material.AIR) {
                chest.getInventory().addItem(item);
            }
        }

        for (ItemStack item : uhcPlayer.getPlayer().getInventory().getArmorContents()) {
            if (item != null && item.getType() != Material.AIR) {
                chest.getInventory().addItem(item);
            }
        }

        final ArmorStand stand = loc.getWorld().spawn(
                chest.getLocation().clone().add(0.5, 1, 0),
                ArmorStand.class
        );

        stand.setCustomNameVisible(true);
        stand.setSmall(true);
        stand.setGravity(false);
        stand.setVisible(false);
        stand.setMarker(true);

        new BukkitRunnable() {
            private int time = explosion_delay + 1;

            @Override
            public void run() {
                time--;

                if (time <= 0) {
                    Bukkit.broadcastMessage(
                            LangManager.get().get(CommonLang.valueOf(
                                    LangManager.get().get(CommonLang.TIMEBOMB_EXPLOSION)), uhcPlayer.getPlayer()
                            )
                    );

                    loc.getBlock().setType(Material.AIR);
                    loc.getWorld().createExplosion(
                            loc.getX() + 0.5,
                            loc.getY() + 0.5,
                            loc.getZ() + 0.5,
                            explosion_power,
                            false,
                            true
                    );

                    loc.getWorld().strikeLightning(loc);
                    stand.remove();
                    cancel();
                    return;
                }

                if (time == 1) stand.setCustomName("§4" + time + "s");
                else if (time == 2) stand.setCustomName("§c" + time + "s");
                else if (time == 3) stand.setCustomName("§6" + time + "s");
                else if (time <= 15) stand.setCustomName("§e" + time + "s");
                else stand.setCustomName("§a" + time + "s");
            }
        }.runTaskTimer(Main.get(), 0, 20);
    }
}
