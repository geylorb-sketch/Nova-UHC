package net.novaproject.novauhc.command.cmd;

import net.novaproject.novauhc.command.Command;
import net.novaproject.novauhc.command.CommandArguments;
import net.novaproject.novauhc.lang.lang.CommandLang;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.uhcteam.UHCTeam;

import org.bukkit.entity.Player;

import java.util.Map;

public class TeamCordCMD extends Command {



    @Override
    public void execute(CommandArguments args) {
        LangManager lm = LangManager.get();
        Player player = (Player) args.getSender();
        UHCPlayer uhcPlayer = UHCPlayerManager.get().getPlayer(player);
        if (uhcPlayer == null || uhcPlayer.getTeam().isEmpty()) {
            player.sendMessage(lm.get(CommandLang.TEAMCORD_NO_TEAM, player));
            return;
        }
        UHCTeam team = uhcPlayer.getTeam().get();
        String coordsMessage = player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " + player.getLocation().getBlockZ();
        String teamMessage = lm.get(CommandLang.TEAMCORD_FORMAT, player, Map.of(
                "%team%", team.name(), "%player%", player.getName(), "%coords%", coordsMessage));
        team.getPlayers().stream()
                .filter(UHCPlayer::isOnline)
                .forEach(p -> p.getPlayer().sendMessage(teamMessage));
    }
}
