package net.novaproject.novauhc.command.cmd;

import net.novaproject.novauhc.command.Command;
import net.novaproject.novauhc.command.CommandArguments;
import net.novaproject.novauhc.lang.lang.CommandLang;
import net.novaproject.novauhc.lang.LangManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RepCMD extends Command {

    private static final Map<UUID, UUID> lastMessaged = new HashMap<>();

    public static void setLastMessaged(UUID from, UUID to) { lastMessaged.put(from, to); }

    @Override
    public void execute(CommandArguments args) {
        Player player = (Player) args.getSender();

        LangManager lm = LangManager.get();
        if (args.getArguments().length < 1) { player.sendMessage(lm.get(CommandLang.REP_USAGE, player)); return; }
        UUID targetId = lastMessaged.get(player.getUniqueId());
        if (targetId == null) { player.sendMessage(lm.get(CommandLang.REP_NO_TARGET, player)); return; }
        Player target = Bukkit.getPlayer(targetId);
        if (target == null) { player.sendMessage(lm.get(CommandLang.REP_OFFLINE, player)); return; }
        String message = String.join(" ", args.getArguments());
        player.sendMessage(lm.get(CommandLang.REP_SENT, player, Map.of("%target%", target.getName(), "%message%", message)));
        target.sendMessage(lm.get(CommandLang.REP_RECEIVED, target, Map.of("%sender%", player.getName(), "%message%", message)));
        lastMessaged.put(target.getUniqueId(), player.getUniqueId());
    }
}
