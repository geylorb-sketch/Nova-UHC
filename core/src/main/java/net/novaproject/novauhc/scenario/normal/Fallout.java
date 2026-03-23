package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.lang.scenario.FalloutLang;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;

public class Fallout extends Scenario {

    private final Map<UUID, Integer> playerWarnings = new HashMap<>();
    private BukkitRunnable falloutTask;
    private boolean falloutStarted = false;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "FALLOUT_VAR_FALLOUT_START_TIME_NAME", descKey = "FALLOUT_VAR_FALLOUT_START_TIME_DESC", type = VariableType.TIME)
    private int falloutStartTime = 45 * 60;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "FALLOUT_VAR_SAFE_YLEVEL_NAME", descKey = "FALLOUT_VAR_SAFE_YLEVEL_DESC", type = VariableType.INTEGER)
    private int safeYLevel = 60;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "FALLOUT_VAR_BASE_DAMAGE_NAME", descKey = "FALLOUT_VAR_BASE_DAMAGE_DESC", type = VariableType.DOUBLE)
    private double baseDamage = 0.5;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "FALLOUT_VAR_MODERATE_THRESHOLD_NAME", descKey = "FALLOUT_VAR_MODERATE_THRESHOLD_DESC", type = VariableType.INTEGER)
    private int moderateThreshold = 3;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "FALLOUT_VAR_SEVERE_THRESHOLD_NAME", descKey = "FALLOUT_VAR_SEVERE_THRESHOLD_DESC", type = VariableType.INTEGER)
    private int severeThreshold = 5;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "FALLOUT_VAR_MAX_DAMAGE_NAME", descKey = "FALLOUT_VAR_MAX_DAMAGE_DESC", type = VariableType.DOUBLE)
    private double maxDamage = 2.0;

    @Override
    public String getName() {
        return "Fallout";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.FALLOUT, player)
                .replace("%level%", String.valueOf(safeYLevel));
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.SKULL_ITEM);
    }

    @Override
    public void onGameStart() {
        startFalloutTask();
    }

    @Override
    public void onSec(Player p) {
        if (!isActive()) return;

        int currentTime = UHCManager.get().getTimer();

        if (!falloutStarted) {
            sendFalloutWarnings(currentTime);
        }
    }

    private void startFalloutTask() {
        if (falloutTask != null) falloutTask.cancel();

        falloutTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (!isActive()) {
                    cancel();
                    return;
                }

                int currentTime = UHCManager.get().getTimer();

                if (!falloutStarted && currentTime >= falloutStartTime) startFallout();
                if (falloutStarted) applyRadiationToExposedPlayers();
            }
        };

        falloutTask.runTaskTimer(Main.get(), 0, 200); 
    }

    private void startFallout() {
        falloutStarted = true;

        LangManager.get().sendAll(FalloutLang.RADIATION_START);
        LangManager.get().sendAll(FalloutLang.GO_UNDERGROUND, Map.of("%level%", String.valueOf(safeYLevel)));

        for (UHCPlayer uhcPlayer : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
            Player player = uhcPlayer.getPlayer();
            player.getWorld().playSound(player.getLocation(),
                    org.bukkit.Sound.WITHER_SPAWN, 1.0f, 0.5f);
        }
    }

    private void sendFalloutWarnings(int currentTime) {
        int timeUntilFallout = falloutStartTime - currentTime;
        if (timeUntilFallout == 300)
            LangManager.get().sendAll(FalloutLang.WARNING_FIVE_MINUTES);
        else if (timeUntilFallout == 60)
            LangManager.get().sendAll(FalloutLang.WARNING_ONE_MINUTE, Map.of("%level%", String.valueOf(safeYLevel)));
        else if (timeUntilFallout == 10)
            LangManager.get().sendAll(FalloutLang.WARNING_TEN_SECONDS);
    }

    private void checkPlayerRadiation(Player player) {
        if (!falloutStarted) return;

        Location loc = player.getLocation();
        UUID uuid = player.getUniqueId();

        if (loc.getY() > safeYLevel) {
            int warnings = playerWarnings.getOrDefault(uuid, 0);
            playerWarnings.put(uuid, warnings + 1);
            applyRadiationEffects(player, warnings);
        } else {
            playerWarnings.put(uuid, 0);
            removeRadiationEffects(player);
        }
    }

    private void applyRadiationToExposedPlayers() {
        for (UHCPlayer uhcPlayer : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
            checkPlayerRadiation(uhcPlayer.getPlayer());
        }
    }

    private void applyRadiationEffects(Player player, int exposureLevel) {
        double damage = Math.min(maxDamage, baseDamage + exposureLevel * 0.1);
        player.damage(damage);

        if (exposureLevel >= severeThreshold) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100, 1));
            LangManager.get().send(FalloutLang.SEVERE_RADIATION, player);
        } else if (exposureLevel >= moderateThreshold) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 60, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 60, 0));
            LangManager.get().send(FalloutLang.MODERATE_RADIATION, player);
        } else {
            player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 40, 0));
            LangManager.get().send(FalloutLang.EXPOSED, player);
        }
    }

    private void removeRadiationEffects(Player player) {
        player.removePotionEffect(PotionEffectType.POISON);
        player.removePotionEffect(PotionEffectType.WEAKNESS);
        player.removePotionEffect(PotionEffectType.HUNGER);
    }
}

