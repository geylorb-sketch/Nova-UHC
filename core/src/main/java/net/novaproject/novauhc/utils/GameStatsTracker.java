package net.novaproject.novauhc.utils;

import net.novaproject.novauhc.api.ApiManager;
import java.util.*;
import java.util.stream.Collectors;

public class GameStatsTracker {

    private final Map<UUID, PlayerGameStats> stats = new HashMap<>();
    private long gameStartTime = 0;

    public static class PlayerGameStats {
        public final UUID uuid;
        public String name;
        public int kills = 0;
        public int deaths = 0;
        public boolean isAlive = true;
        public String camp = null; 
        public long joinTime = System.currentTimeMillis();

        public PlayerGameStats(UUID uuid, String name) {
            this.uuid = uuid;
            this.name = name;
        }

        public int getPlaytime() {
            return (int) ((System.currentTimeMillis() - joinTime) / 1000);
        }
    }


    public void startGame() {
        stats.clear();
        gameStartTime = System.currentTimeMillis();
    }


    public void addPlayer(UUID uuid, String name) {
        stats.put(uuid, new PlayerGameStats(uuid, name));
    }


    public void addKill(UUID killer) {
        PlayerGameStats stat = stats.get(killer);
        if (stat != null) {
            stat.kills++;
        }
    }


    public void addDeath(UUID victim) {
        PlayerGameStats stat = stats.get(victim);
        if (stat != null) {
            stat.deaths++;
            stat.isAlive = false;
        }
    }


    public void setCamp(UUID uuid, String camp) {
        PlayerGameStats stat = stats.get(uuid);
        if (stat != null) {
            stat.camp = camp;
        }
    }


    public PlayerGameStats getStats(UUID uuid) {
        return stats.get(uuid);
    }


    public int getGameDuration() {
        if (gameStartTime == 0) return 0;
        return (int) ((System.currentTimeMillis() - gameStartTime) / 1000);
    }


    public List<ApiManager.PlayerStats> getPlayerStats() {
        List<ApiManager.PlayerStats> list = new ArrayList<>();

        
        List<PlayerGameStats> sorted = new ArrayList<>(stats.values());
        sorted.sort((a, b) -> {
            if (a.isAlive != b.isAlive) return b.isAlive ? 1 : -1;
            return Integer.compare(b.kills, a.kills);
        });

        
        int placement = 1;
        for (PlayerGameStats stat : sorted) {
            list.add(new ApiManager.PlayerStats(
                    stat.uuid.toString(),
                    stat.name,
                    stat.kills,
                    stat.deaths,
                    placement++,
                    stat.camp
            ));
        }

        return list;
    }


    public List<PlayerGameStats> getAlivePlayers() {
        return stats.values().stream()
                .filter(s -> s.isAlive)
                .collect(Collectors.toList());
    }


    public List<ApiManager.WinnerInfo> getWinners(List<UUID> winnerUuids) {
        List<ApiManager.WinnerInfo> winners = new ArrayList<>();
        for (UUID uuid : winnerUuids) {
            PlayerGameStats stat = stats.get(uuid);
            if (stat != null) {
                winners.add(new ApiManager.WinnerInfo(
                        "player",
                        stat.uuid.toString(),
                        stat.name,
                        stat.kills,
                        stat.camp
                ));
            }
        }
        return winners;
    }


    public List<ApiManager.WinnerInfo> getWinnersByCamp(String winnerCamp) {
        return stats.values().stream()
                .filter(s -> s.isAlive && winnerCamp.equals(s.camp))
                .map(s -> new ApiManager.WinnerInfo("player", s.uuid.toString(), s.name, s.kills, s.camp))
                .collect(Collectors.toList());
    }


    public void reset() {
        stats.clear();
        gameStartTime = 0;
    }
}