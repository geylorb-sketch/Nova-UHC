package net.novaproject.novauhc.command.cmd;


import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.lang.lang.CommonLang;
import net.novaproject.novauhc.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MsgCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;

        if (args.length < 2) {
            LangManager.get().send(CommonLang.MSG_USAGE, player);
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);

        if (target == null || !target.isOnline()) {
            LangManager.get().send(CommonLang.MSG_PLAYER_OFFLINE, player);
            return true;
        }

        if (target == player) {
            LangManager.get().send(CommonLang.MSG_CANNOT_MESSAGE_SELF, player);
            return true;
        }

        if (UHCManager.get().isChatdisbale()) {
            LangManager.get().send(CommonLang.CHAT_DISABLED, player);
            return true;
        }

        String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        // Envoyer le message au sender
        Map<String, Object> senderPlaceholders = new HashMap<>();
        senderPlaceholders.put("%target%", target.getName());
        senderPlaceholders.put("%message%", message);
        LangManager.get().send(CommonLang.MSG_SENT_FORMAT, player, senderPlaceholders);

        // Envoyer le message au destinataire
        Map<String, Object> targetPlaceholders = new HashMap<>();
        targetPlaceholders.put("%sender%", player.getName());
        targetPlaceholders.put("%message%", message);
        LangManager.get().send(CommonLang.MSG_RECEIVED_FORMAT, target, targetPlaceholders);

        MessageManager.setLastMessage(player.getUniqueId(), target.getUniqueId());
        return true;
    }
}
