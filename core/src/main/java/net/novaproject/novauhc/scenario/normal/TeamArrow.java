package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.uhcteam.UHCTeam;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.Titles;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.lang.LangManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeamArrow extends Scenario {

    private final Map<UUID, BukkitRunnable> playerTasks = new HashMap<>();

    @Override
    public String getName() {
        return "TeamArrow";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.TEAM_ARROW, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.ARROW);
    }

    @Override
    public void onStart(Player player) {
        if (!isActive()) return;
        UHCPlayer uhcPlayer = UHCPlayerManager.get().getPlayer(player);
        if (!uhcPlayer.getTeam().isPresent()) return;

        UUID uuid = player.getUniqueId();
        BukkitRunnable existing = playerTasks.get(uuid);
        if (existing != null) existing.cancel();

        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                if (!isActive() || !uhcPlayer.getTeam().isPresent()) {
                    playerTasks.remove(uuid);
                    cancel();
                    return;
                }
                UHCTeam team = uhcPlayer.getTeam().get();
                if (team.getPlayers().isEmpty()) {
                    playerTasks.remove(uuid);
                    cancel();
                    return;
                }
                for (UHCPlayer p : team.getPlayers()) {
                    if (p == uhcPlayer) continue;
                    new Titles().sendActionText(uhcPlayer.getPlayer(),
                            " " + p.getPlayer().getName() + " : " +
                            getArrowDirection(uhcPlayer.getPlayer().getLocation(),
                                    p.getPlayer().getLocation(),
                                    uhcPlayer.getPlayer().getLocation().getYaw()));
                }
            }
        };
        playerTasks.put(uuid, task);
        task.runTaskTimer(Main.get(), 0, 20);
    }

    @Override
    public void onStop() {
        for (BukkitRunnable task : playerTasks.values()) {
            try { task.cancel(); } catch (Exception ignored) {}
        }
        playerTasks.clear();
    }

    public String getArrowDirection(Location from, Location to, float playerYaw) {
        double dx = to.getX() - from.getX();
        double dz = to.getZ() - from.getZ();

        double angle = Math.toDegrees(Math.atan2(-dx, dz));
        angle = (angle + 360) % 360;

        float normalizedYaw = (playerYaw + 360) % 360;

        double relativeAngle = (angle - normalizedYaw + 360) % 360;

        if (relativeAngle >= 337.5 || relativeAngle < 22.5) return "↑";
        if (relativeAngle >= 22.5 && relativeAngle < 67.5) return "↗";
        if (relativeAngle >= 67.5 && relativeAngle < 112.5) return "→";
        if (relativeAngle >= 112.5 && relativeAngle < 157.5) return "↘";
        if (relativeAngle >= 157.5 && relativeAngle < 202.5) return "↓";
        if (relativeAngle >= 202.5 && relativeAngle < 247.5) return "↙";
        if (relativeAngle >= 247.5 && relativeAngle < 292.5) return "←";
        if (relativeAngle >= 292.5 && relativeAngle < 337.5) return "↖";

        return "?";
    }
}
