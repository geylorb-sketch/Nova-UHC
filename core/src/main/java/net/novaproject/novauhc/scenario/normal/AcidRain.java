package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.lang.LangManager;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.lang.scenario.AcidRainLang;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;


public class AcidRain extends Scenario {

    private final Random random = new Random();
    private BukkitRunnable acidRainTask;
    private boolean isRaining = false;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "ACIDRAIN_VAR_NEXT_RAIN_IN_BASE_NAME", descKey = "ACIDRAIN_VAR_NEXT_RAIN_IN_BASE_DESC", type = VariableType.TIME)
    private int nextRainInBase = 300;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "ACIDRAIN_VAR_RAIN_DURATION_BASE_NAME", descKey = "ACIDRAIN_VAR_RAIN_DURATION_BASE_DESC", type = VariableType.TIME)
    private int rainDurationBase = 60;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "ACIDRAIN_VAR_RAIN_DAMAGE_NAME", descKey = "ACIDRAIN_VAR_RAIN_DAMAGE_DESC", type = VariableType.DOUBLE)
    private double rainDamage = 1.0;

    @Override
    public String getName() {
        return "AcidRain";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.ACID_RAIN,player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.WATER_BUCKET);
    }




    @Override
    public void toggleActive() {
        super.toggleActive();
        if (!isActive()) {
            stopAcidRainCycle();
        }
    }


    @Override
    public void onGameStart() {
        startAcidRainCycle();
    }

    private void startAcidRainCycle() {
        if (acidRainTask != null) {
            acidRainTask.cancel();
        }

        acidRainTask = new BukkitRunnable() {
            private int cycleTimer = 0;
            private int rainDuration = 0;
            private int nextRainIn = nextRainInBase + random.nextInt(nextRainInBase);

            @Override
            public void run() {
                if (!isActive()) {
                    cancel();
                    return;
                }

                cycleTimer++;

                if (!isRaining) {
                    if (cycleTimer >= nextRainIn) {
                        startAcidRain();
                        cycleTimer = 0;
                        rainDuration = rainDurationBase + random.nextInt(rainDurationBase * 2);
                    } else {
                        int timeLeft = nextRainIn - cycleTimer;
                        if (timeLeft == 60) {
                            LangManager.get().sendAll(AcidRainLang.WARNING_ONE_MINUTE);
                        } else if (timeLeft == 10) {
                            LangManager.get().sendAll(AcidRainLang.WARNING_TEN_SECONDS);
                        }
                    }
                } else {
                    damageExposedPlayers();
                    rainDuration--;

                    if (rainDuration <= 0) {
                        stopAcidRain();
                        cycleTimer = 0;
                        nextRainIn = nextRainInBase + random.nextInt(nextRainInBase);
                    } else {
                        if (rainDuration == 10) {
                            LangManager.get().sendAll(AcidRainLang.ENDING_SOON);
                        }
                    }
                }
            }
        };

        acidRainTask.runTaskTimer(Main.get(), 0, 20);
    }

    private void stopAcidRainCycle() {
        if (acidRainTask != null) {
            acidRainTask.cancel();
            acidRainTask = null;
        }

        if (isRaining) {
            stopAcidRain();
        }
    }

    private void startAcidRain() {
        isRaining = true;

        for (World world : Bukkit.getWorlds()) {
            world.setStorm(true);
            world.setWeatherDuration(Integer.MAX_VALUE);
        }

        LangManager.get().sendAll(AcidRainLang.ACID_RAIN_START);

        for (UHCPlayer uhcPlayer : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
            Player player = uhcPlayer.getPlayer();
            player.getWorld().playSound(player.getLocation(),
                    org.bukkit.Sound.AMBIENCE_THUNDER, 1.0f, 1.0f);
        }
    }

    private void stopAcidRain() {
        isRaining = false;

        for (World world : Bukkit.getWorlds()) {
            world.setStorm(false);
            world.setWeatherDuration(0);
        }

        LangManager.get().sendAll(AcidRainLang.RAIN_STOPPED);
    }

    private void damageExposedPlayers() {
        for (UHCPlayer uhcPlayer : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
            Player player = uhcPlayer.getPlayer();

            if (isPlayerExposedToRain(player)) {
                player.damage(rainDamage);
                LangManager.get().send(AcidRainLang.BURNING, player);

                player.setFireTicks(20);
            }
        }
    }

    private boolean isPlayerExposedToRain(Player player) {
        Location playerLoc = player.getLocation();
        World world = playerLoc.getWorld();

        
        if (!world.hasStorm()) {
            return false;
        }

        
        if (playerLoc.getY() < 60) {
            return false;
        }

        
        Location checkLoc = playerLoc.clone();

        
        for (int y = 1; y <= 10; y++) {
            checkLoc.add(0, 1, 0);
            Material blockType = checkLoc.getBlock().getType();

            
            if (blockType != Material.AIR &&
                    blockType != Material.WATER &&
                    blockType != Material.LAVA &&
                    !blockType.name().contains("LEAVES")) {
                return false;
            }
        }

        
        return true;
    }

}
