package net.novaproject.novauhc.uhcplayer;

import fr.mrmicky.fastboard.FastBoard;
import net.novaproject.novauhc.Common;
import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.lang.ScoreboardLang;
import net.novaproject.novauhc.scenario.ScenarioManager;
import net.novaproject.novauhc.scenario.role.ScenarioRole;
import net.novaproject.novauhc.utils.ConfigUtils;
import net.novaproject.novauhc.utils.TabListManager;
import net.novaproject.novauhc.utils.PlayerColorManager;
import net.novaproject.novauhc.utils.TeamsTagsManager;
import net.novaproject.novauhc.utils.UHCUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class UHCPlayerManager {

    private boolean tasks = false;
    private int sbCycle = 0;
    private int healthCycle = 0;

    public static UHCPlayerManager get() {
        return UHCManager.get().getUhcPlayerManager();
    }

    private final Map<UUID, UHCPlayer> players = new HashMap<>();
    public final Map<UUID, FastBoard> boards = new HashMap<>();

    public List<UHCPlayer> getOnlineUHCPlayers() {
        return players.values().stream().filter(UHCPlayer::isOnline).collect(Collectors.toList());
    }

    public List<UHCPlayer> getPlayingOnlineUHCPlayers() {
        return players.values().stream()
                .filter(UHCPlayer::isPlaying)
                .filter(UHCPlayer::isOnline)
                .collect(Collectors.toList());
    }

    public UHCPlayer getPlayer(Player player) {
        return players.get(player.getUniqueId());
    }

    public void connect(Player player) {
        UHCPlayer uhcPlayer = new UHCPlayer(player);
        players.putIfAbsent(player.getUniqueId(), uhcPlayer);
        uhcPlayer = getPlayer(player);
        uhcPlayer.connect(player);

        FastBoard board = new FastBoard(player);
        boards.put(player.getUniqueId(), board);

        // Réappliquer les couleurs TAB
        Bukkit.getScheduler().runTaskLater(Main.get(), () -> {
            PlayerColorManager.reapplyColorsForViewer(player);
            PlayerColorManager.reapplyColorsForTarget(player);
        }, 5L);

        if (!tasks) {
            tasks = true;
            player.getServer().getScheduler().runTaskTimerAsynchronously(
                    Main.get(),
                    () -> {
                        sbCycle++;
                        healthCycle++;
                        boards.values().forEach(this::updateBoard);
                        if (healthCycle >= 5) {
                            healthCycle = 0;
                            Bukkit.getScheduler().runTask(Main.get(), () -> {
                                boolean show = UHCManager.get().isShowHealthPercent() && UHCManager.get().isGame();
                                if (TeamsTagsManager.scoreboard == null) return;
                                for (UHCPlayer uhcTarget : getPlayingOnlineUHCPlayers()) {
                                    Player tp = uhcTarget.getPlayer();
                                    if (tp == null) continue;
                                    org.bukkit.scoreboard.Team team = TeamsTagsManager.scoreboard.getPlayerTeam(tp);
                                    if (team == null) continue;
                                    if (show) {
                                        double pct = (tp.getHealth() / tp.getMaxHealth()) * 100;
                                        String color = pct > 75 ? "§a" : pct > 50 ? "§e" : pct > 25 ? "§6" : "§c";
                                        team.setSuffix(" " + color + String.format("%.0f%%", pct));
                                    } else {
                                        team.setSuffix("");
                                    }
                                }
                            });
                        }
                    },
                    0, 1L
            );
        }
    }

    public void disconnect(Player player) {
        UHCPlayer uhcPlayer = getPlayer(player);
        uhcPlayer.disconnect(player);

        PlayerColorManager.removeTeamForTarget(player);
        PlayerColorManager.cleanupViewer(player.getUniqueId());

        if (UHCManager.get().isLobby()) {
            players.remove(player.getUniqueId());
        }
        FastBoard board = boards.remove(player.getUniqueId());
        if (board != null) board.delete();
    }

    private void updateBoardKills(FastBoard board, Player player) {
        LangManager lm = LangManager.get();
        board.updateTitle(lm.get(ScoreboardLang.SB_KILLS_TITLE, player));

        List<UHCPlayer> sorted = players.values().stream()
                .filter(UHCPlayer::isOnline)
                .sorted(Comparator.comparingInt(UHCPlayer::getKill).reversed())
                .limit(10)
                .collect(Collectors.toList());

        List<String> lines = new ArrayList<>();
        lines.add("");
        if (sorted.isEmpty()) {
            lines.add(lm.get(ScoreboardLang.SB_KILLS_EMPTY, player));
        } else {
            String[] medals = {"§6⭐", "§7✦", "§c✦"};
            for (int rank = 0; rank < sorted.size(); rank++) {
                UHCPlayer p = sorted.get(rank);
                String prefix = rank < medals.length ? medals[rank] : "§8" + (rank + 1) + ".";
                lines.add(prefix + " §f" + p.getPlayer().getName() + " §8- §e" + p.getKill() + " kill" + (p.getKill() > 1 ? "s" : ""));
            }
        }
        lines.add("");
        board.updateLines(lines);
    }

    private void updateBoard(FastBoard board) {
        Player player = board.getPlayer();
        UHCManager uhcManager = UHCManager.get();

        UHCPlayer uhcPlayer = UHCPlayerManager.get().getPlayer(player);
        if (uhcPlayer == null) return;

        // Kill scoreboard — fin de game : toujours ; en game : alterner toutes les ~5s
        if (uhcManager.isShowKillScoreboard()) {
            if (!uhcManager.isLobby() && !uhcManager.isGame()) {
                updateBoardKills(board, player);
                return;
            }
            if (uhcManager.isGame() && sbCycle % 200 >= 100) {
                updateBoardKills(board, player);
                return;
            }
        }

        LangManager lm = LangManager.get();
        boolean showTab = ConfigUtils.getGeneralConfig().getBoolean("message.tab.show", true);
        boolean isRole = ScenarioManager.get().getActiveSpecialScenarios().stream()
                .anyMatch(s -> s instanceof ScenarioRole);

        String ip = Common.get().getServerIp();

        if (uhcManager.isLobby()) {
            
            board.updateTitle(lm.get(ScoreboardLang.SB_LOBBY_TITLE, player));
            board.updateLines(lines(lm, player, ip,
                    ScoreboardLang.SB_LOBBY_L1,
                    ScoreboardLang.SB_LOBBY_L2,
                    ScoreboardLang.SB_LOBBY_L3,
                    ScoreboardLang.SB_LOBBY_L4,
                    ScoreboardLang.SB_LOBBY_L5));

            if (showTab) {
                TabListManager.sendTab(player,
                        lm.get(ScoreboardLang.TAB_LOBBY_HEADER, player),
                        lm.get(ScoreboardLang.TAB_LOBBY_FOOTER, player));
            }

        } else if (uhcManager.isGame()) {
            
            if (isRole) {
                board.updateTitle(lm.get(ScoreboardLang.SB_GAME_ROLE_TITLE, player));
                board.updateLines(lines(lm, player, ip,
                        ScoreboardLang.SB_GAME_ROLE_L1,
                        ScoreboardLang.SB_GAME_ROLE_L2,
                        ScoreboardLang.SB_GAME_ROLE_L3,
                        ScoreboardLang.SB_GAME_ROLE_L4,
                        ScoreboardLang.SB_GAME_ROLE_L5,
                        ScoreboardLang.SB_GAME_ROLE_L6,
                        ScoreboardLang.SB_GAME_ROLE_L7,
                        ScoreboardLang.SB_GAME_ROLE_L8,
                        ScoreboardLang.SB_GAME_ROLE_L9,
                        ScoreboardLang.SB_GAME_ROLE_L10,
                        ScoreboardLang.SB_GAME_ROLE_L11,
                        ScoreboardLang.SB_GAME_ROLE_L12,
                        ScoreboardLang.SB_GAME_ROLE_L13));
            } else {
                board.updateTitle(lm.get(ScoreboardLang.SB_GAME_TITLE, player));
                board.updateLines(lines(lm, player, ip,
                        ScoreboardLang.SB_GAME_L1,
                        ScoreboardLang.SB_GAME_L2,
                        ScoreboardLang.SB_GAME_L3,
                        ScoreboardLang.SB_GAME_L4,
                        ScoreboardLang.SB_GAME_L5,
                        ScoreboardLang.SB_GAME_L6,
                        ScoreboardLang.SB_GAME_L7,
                        ScoreboardLang.SB_GAME_L8,
                        ScoreboardLang.SB_GAME_L9,
                        ScoreboardLang.SB_GAME_L10,
                        ScoreboardLang.SB_GAME_L11,
                        ScoreboardLang.SB_GAME_L12));
            }

            if (showTab) {
                TabListManager.sendTab(player,
                        lm.get(ScoreboardLang.TAB_GAME_HEADER, player),
                        lm.get(ScoreboardLang.TAB_GAME_FOOTER, player));
            }

        } else {
            
            board.updateTitle(lm.get(ScoreboardLang.SB_END_TITLE, player));
            board.updateLines(lines(lm, player, ip,
                    ScoreboardLang.SB_END_L1,
                    ScoreboardLang.SB_END_L2,
                    ScoreboardLang.SB_END_L3,
                    ScoreboardLang.SB_END_L4,
                    ScoreboardLang.SB_END_L5,
                    ScoreboardLang.SB_END_L6,
                    ScoreboardLang.SB_END_L7,
                    ScoreboardLang.SB_END_L8,
                    ScoreboardLang.SB_END_L9));

            if (showTab) {
                TabListManager.sendTab(player,
                        lm.get(ScoreboardLang.TAB_END_HEADER, player),
                        lm.get(ScoreboardLang.TAB_END_FOOTER, player));
            }
        }
    }


    private List<String> lines(LangManager lm, Player player, String ip, ScoreboardLang... keys) {
        return Arrays.stream(keys)
                .map(key -> lm.get(key, player).replace("<ip>", ip))
                .collect(Collectors.toList());
    }
}
