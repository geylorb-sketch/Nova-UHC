package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.lang.LangManager;

public class HeathCharity extends Scenario {

    @Override
    public String getName() {
        return "Heath Charity";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.HEATH_CHARITY, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.RED_ROSE);
    }

    @Override
    public void onGameStart() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!isActive()) return;

                Player lowestHealthPlayer = null;
                double lowestHealth = Double.MAX_VALUE;

                for (UHCPlayer uhcPlayer : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
                    double health = uhcPlayer.getPlayer().getHealth();
                    if (health < lowestHealth) {
                        lowestHealth = health;
                        lowestHealthPlayer = uhcPlayer.getPlayer();
                    }
                }
                if (lowestHealthPlayer != null) {
                    lowestHealthPlayer.setHealth(lowestHealthPlayer.getMaxHealth());
                }
            }
        }.runTaskTimer(Main.get(), 0, 20 * 300 * 2);
    }
}
