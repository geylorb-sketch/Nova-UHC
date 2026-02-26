package net.novaproject.novauhc.command.cmd;

import net.novaproject.novauhc.command.Command;
import net.novaproject.novauhc.command.CommandArguments;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.lang.CommonLang;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class LangCMD extends Command {
    @Override
    public void execute(CommandArguments args) {
        if (args.getArguments().length < 1) {
            return;
        }
        Player player = (Player) args.getSender();
        UHCPlayer uhcPlayer = UHCPlayerManager.get().getPlayer(player);
        String lang = args.getArgument(0);
        if (lang.equalsIgnoreCase("fr")) {
            uhcPlayer.setLocale("fr_FR");
            LangManager.get().send(CommonLang.SUCCESSFUL_MODIFICATION,player);
        } else if (lang.equalsIgnoreCase("en")) {
            uhcPlayer.setLocale("en_US");
            LangManager.get().send(CommonLang.SUCCESSFUL_MODIFICATION,player);
        } else {
            LangManager.get().send(CommonLang.ERROR_INVALID_ARGUMENT,player,Map.of("arg", lang));
        }
    }

    @Override
    public List<String> tabComplete(CommandArguments commandArguments) {
        if(commandArguments.getArguments().length == 1){
            return List.of("fr","en");
        }
        return List.of();
    }
}
