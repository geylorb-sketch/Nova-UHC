package net.novaproject.novauhc.command.cmd;

import net.novaproject.novauhc.command.Command;
import net.novaproject.novauhc.command.CommandArguments;
import net.novaproject.novauhc.ui.player.ColorPickerUi;
import net.novaproject.novauhc.ui.player.ColorPlayerListUi;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class ColorCMD extends Command {

    @Override
    public void execute(CommandArguments args) {
        if (!(args.getSender() instanceof Player)) return;
        Player player = (Player) args.getSender();

        if (args.getArguments().length == 0) {
            new ColorPlayerListUi(player).open();
            return;
        }

        String targetName = args.getArgument(0);
        Player target = Bukkit.getPlayer(targetName);
        if (target == null || !target.isOnline()) {
            player.sendMessage("§cJoueur introuvable : §f" + targetName);
            return;
        }
        if (target.equals(player)) {
            player.sendMessage("§cTu ne peux pas choisir ta propre couleur.");
            return;
        }

        new ColorPickerUi(player, target).open();
    }

    @Override
    public List<String> tabComplete(CommandArguments args) {
        if (args.getArguments().length == 1) {
            String partial = args.getArgument(0).toLowerCase();
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(n -> n.toLowerCase().startsWith(partial))
                    .collect(Collectors.toList());
        }
        return List.of();
    }
}
