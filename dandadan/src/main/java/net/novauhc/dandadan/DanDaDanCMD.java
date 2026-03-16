package net.novauhc.dandadan;

import net.novaproject.novauhc.ability.CommandAbility;
import net.novaproject.novauhc.command.Command;
import net.novaproject.novauhc.command.CommandArguments;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class DanDaDanCMD extends Command {

    @Override
    public void execute(CommandArguments args) {
        CommandSender sender = args.getSender();
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Joueurs uniquement."); return;
        }

        String[] arguments = args.getArguments();
        if (arguments.length == 0) {
            LangManager.get().send(DanDaDanLang.CMD_HELP, player); return;
        }

        UHCPlayer uhcPlayer = UHCPlayerManager.get().getPlayer(player);
        if (uhcPlayer == null || !uhcPlayer.isPlaying()) {
            LangManager.get().send(DanDaDanLang.NOT_IN_GAME, player); return;
        }
        if (DanDaDan.get() == null) {
            LangManager.get().send(DanDaDanLang.SCENARIO_NOT_ACTIVE, player); return;
        }

        DanDaDanRole role = DanDaDan.get().getRoleByUHCPlayer(uhcPlayer);
        if (role == null) {
            LangManager.get().send(DanDaDanLang.NO_YOKAI, player); return;
        }

        String subCommand = arguments[0].toLowerCase();

        boolean found = role.getAbilities().stream()
                .filter(a -> a instanceof CommandAbility ca
                        && ca.getCommandKey().equalsIgnoreCase(subCommand))
                .map(a -> (CommandAbility) a)
                .findFirst()
                .map(ca -> { ca.tryCommandUse(player, arguments); return true; })
                .orElse(false);

        if (!found) LangManager.get().send(DanDaDanLang.CMD_UNKNOWN, player);
    }

    @Override
    public List<String> tabComplete(CommandArguments args) {
        CommandSender sender = args.getSender();
        if (!(sender instanceof Player player)) return List.of();

        UHCPlayer uhcPlayer = UHCPlayerManager.get().getPlayer(player);
        if (uhcPlayer == null || DanDaDan.get() == null) return List.of();

        DanDaDanRole role = DanDaDan.get().getRoleByUHCPlayer(uhcPlayer);
        if (role == null) return List.of();

        String[] a = args.getArguments();
        if (a.length <= 1) {
            String prefix = a.length == 1 ? a[0].toLowerCase() : "";
            return role.getAbilities().stream()
                    .filter(ab -> ab instanceof CommandAbility)
                    .map(ab -> ((CommandAbility) ab).getCommandKey())
                    .filter(k -> k.startsWith(prefix))
                    .toList();
        }
        return getOnlinePlayers(args);
    }
}
