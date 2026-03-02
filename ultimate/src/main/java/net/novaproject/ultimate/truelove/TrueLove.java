package net.novaproject.ultimate.truelove;

import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.lang.CommonLang;
import net.novaproject.novauhc.lang.special.TrueLoveLang;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.uhcteam.UHCTeam;
import net.novaproject.novauhc.uhcteam.UHCTeamManager;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class TrueLove extends Scenario {

    @ScenarioVariable(lang = TrueLoveLang.class, nameKey = "VAR_SIGHT_RADIUS_NAME", descKey = "VAR_SIGHT_RADIUS_DESC", type = VariableType.INTEGER)
    private int sightRadius = 20;

    private final List<UHCTeam> pickedTeams = new ArrayList<>();

    @Override
    public String getName() {
        return LangManager.get().get(TrueLoveLang.TRUE_LOVE_NAME);
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(TrueLoveLang.TRUE_LOVE_DESC, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.CAKE);
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public void onStart(Player player) {
        pickedTeams.clear();
        for (UHCPlayer uhcPlayer : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
            uhcPlayer.setTeam(Optional.empty());
        }
    }

    @Override
    public void onTeamUpdate() {
        if (UHCManager.get().getTeam_size() == 1) {
            UHCManager.get().setTeam_size(2);
            LangManager.get().sendAll(CommonLang.TEAM_REDFINIED_AUTO);
        }
    }

    @Override
    public void onSec(Player p) {
        UHCPlayer uhcPlayer = UHCPlayerManager.get().getPlayer(p);
        synchronized (uhcPlayer) {
            if (uhcPlayer.getTeam().isPresent()) return;
            Set<UHCPlayer> teamMembers = findValidTeamMembers(uhcPlayer);
            if (!teamMembers.isEmpty()) assignTeam(uhcPlayer, teamMembers);
        }
    }

    private Set<UHCPlayer> findValidTeamMembers(UHCPlayer leader) {
        Set<UHCPlayer> validMembers = new HashSet<>();
        List<UHCPlayer> availablePlayers = getNearbyPlayers(leader.getPlayer(), sightRadius).stream()
                .filter(p -> !p.getTeam().isPresent())
                .collect(Collectors.toList());

        validMembers.add(leader);
        availablePlayers.forEach(candidate -> {
            if (validMembers.stream().allMatch(member -> isPlayerNearby(candidate.getPlayer(), member.getPlayer(), sightRadius))) {
                validMembers.add(candidate);
            }
        });

        return validMembers.size() > 1 ? validMembers : Collections.emptySet();
    }

    private void assignTeam(UHCPlayer leader, Set<UHCPlayer> teamMembers) {
        UHCTeam team = UHCTeamManager.get().getTeams().stream()
                .filter(t -> t.getPlayers().size() < UHCManager.get().getTeam_size())
                .findFirst().orElse(null);

        if (team == null) {
            LangManager.get().send(TrueLoveLang.NO_TEAM_AVAILABLE, leader.getPlayer());
            return;
        }

        synchronized (team) {
            if (!teamMembers.stream().allMatch(p -> !p.getTeam().isPresent()
                    && isPlayerNearby(leader.getPlayer(), p.getPlayer(), sightRadius))) {
                LangManager.get().send(TrueLoveLang.PLAYERS_TOO_FAR, leader.getPlayer());
                return;
            }

            teamMembers.forEach(member -> {
                synchronized (member) {
                    member.setTeam(Optional.of(team));
                    LangManager.get().send(TrueLoveLang.TEAM_JOINED, member.getPlayer(), Map.of(
                            "%team%", team.name(),
                            "%count%", String.valueOf(teamMembers.size() - 1)
                    ));
                }
            });
        }
    }

    private boolean isPlayerNearby(Player p1, Player p2, double radius) {
        return p1.getWorld() == p2.getWorld()
                && p1.getLocation().distanceSquared(p2.getLocation()) <= radius * radius;
    }

    private List<UHCPlayer> getNearbyPlayers(Player player, double radius) {
        return UHCPlayerManager.get().getPlayingOnlineUHCPlayers().stream()
                .filter(p -> !p.equals(player) && isPlayerNearby(player, p.getPlayer(), radius))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isWin() {
        return UHCTeamManager.get().getAliveTeams().size() == 1
                && UHCPlayerManager.get().getPlayingOnlineUHCPlayers().size() == UHCManager.get().getTeam_size();
    }

    @Override
    public void scatter(UHCPlayer uhcPlayer, Location location, HashMap<UHCTeam, Location> teamloc) {
        uhcPlayer.getPlayer().teleport(location);
    }
}