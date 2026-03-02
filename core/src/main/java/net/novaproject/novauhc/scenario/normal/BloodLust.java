package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.lang.LangManager;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novaproject.novauhc.lang.scenario.BloodLustLang;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;

public class BloodLust extends Scenario {

    private final Map<UUID, BukkitRunnable> activeEffects = new HashMap<>();

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "BLOODLUST_VAR_SPEED_DURATION_NAME", descKey = "BLOODLUST_VAR_SPEED_DURATION_DESC", type = VariableType.TIME)
    private final int speedDuration = 30;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "BLOODLUST_VAR_STRENGTH_DURATION_NAME", descKey = "BLOODLUST_VAR_STRENGTH_DURATION_DESC", type = VariableType.TIME)
    private final int strengthDuration = 30;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "BLOODLUST_VAR_SPEED_LEVEL_NAME", descKey = "BLOODLUST_VAR_SPEED_LEVEL_DESC", type = VariableType.INTEGER)
    private final int speedLevel = 1;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "BLOODLUST_VAR_STRENGTH_LEVEL_NAME", descKey = "BLOODLUST_VAR_STRENGTH_LEVEL_DESC", type = VariableType.INTEGER)
    private final int strengthLevel = 0;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "BLOODLUST_VAR_COUNTDOWN10SEC_NAME", descKey = "BLOODLUST_VAR_COUNTDOWN10SEC_DESC", type = VariableType.BOOLEAN)
    private final boolean countdown10Sec = true;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "BLOODLUST_VAR_COUNTDOWN5SEC_NAME", descKey = "BLOODLUST_VAR_COUNTDOWN5SEC_DESC", type = VariableType.BOOLEAN)
    private final boolean countdown5Sec = true;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "BLOODLUST_VAR_COUNTDOWN_END_NAME", descKey = "BLOODLUST_VAR_COUNTDOWN_END_DESC", type = VariableType.BOOLEAN)
    private final boolean countdownEnd = true;

    @Override
    public String getName() {
        return "BloodLust";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.BLOOD_LUST, player)
                .replace("%speed_level%", String.valueOf(speedLevel + 1))
                .replace("%strength_level%", String.valueOf(strengthLevel + 1))
                .replace("%duration%", String.valueOf(speedDuration));
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.BLAZE_POWDER);
    }



    @Override
    public void onDeath(UHCPlayer uhcPlayer, UHCPlayer killer, PlayerDeathEvent event) {
        if (!isActive() || killer == null) return;

        Player killerPlayer = killer.getPlayer();
        cancelBloodLustEffect(killerPlayer.getUniqueId());
        applyBloodLustEffect(killerPlayer);

        LangManager.get().send(BloodLustLang.KILL_BOOST, killerPlayer);
        LangManager.get().sendAll(BloodLustLang.BLOODLUST_ACTIVATED, Map.of("%player%", killerPlayer.getName()));
    }

    private void applyBloodLustEffect(Player player) {
        UUID playerUuid = player.getUniqueId();

        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, speedDuration * 20, speedLevel));
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, strengthDuration * 20, strengthLevel));

        BukkitRunnable countdownTask = new BukkitRunnable() {
            private int timeLeft = speedDuration;

            @Override
            public void run() {
                if (!player.isOnline() || !isActive()) {
                    cancel();
                    activeEffects.remove(playerUuid);
                    return;
                }

                timeLeft--;

                if (countdown10Sec && timeLeft == 10) {
                    LangManager.get().send(BloodLustLang.ENDING_TEN_SECONDS, player);
                } else if (countdown5Sec && timeLeft == 5) {
                    LangManager.get().send(BloodLustLang.ENDING_FIVE_SECONDS, player);
                } else if (countdownEnd && timeLeft <= 0) {
                    LangManager.get().send(BloodLustLang.BLOODLUST_ENDED, player);
                    player.removePotionEffect(PotionEffectType.SPEED);
                    player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                    activeEffects.remove(playerUuid);
                    cancel();
                }
            }
        };

        activeEffects.put(playerUuid, countdownTask);
        countdownTask.runTaskTimer(Main.get(), 0, 20);
    }

    private void cancelBloodLustEffect(UUID playerUuid) {
        BukkitRunnable existingTask = activeEffects.get(playerUuid);
        if (existingTask != null) {
            existingTask.cancel();
            activeEffects.remove(playerUuid);

            Player player = Bukkit.getPlayer(playerUuid);
            if (player != null && player.isOnline()) {
                player.removePotionEffect(PotionEffectType.SPEED);
                player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
            }
        }
    }

    @Override
    public void toggleActive() {
        super.toggleActive();
        if (!isActive()) {
            for (UUID playerUuid : activeEffects.keySet()) {
                cancelBloodLustEffect(playerUuid);
            }
            activeEffects.clear();
        }
    }
}
