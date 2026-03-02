package net.novaproject.novauhc.scenario.command;

import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.command.Command;
import net.novaproject.novauhc.command.CommandArguments;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.lang.CommonLang;
import net.novaproject.novauhc.scenario.normal.Democracy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DemocracyVoteCMD extends Command {
    @Override
    public void execute(CommandArguments arguments) {
        String[] args = arguments.getArguments();
        if (!(arguments.getSender() instanceof Player player)) return;

        if (args.length < 2) {
            LangManager.get().send(CommonLang.MSG_USAGE, player);
            return ;
        }

        Player target = Bukkit.getPlayerExact(args[0]);

        if (target == null || !target.isOnline()) {
            return;
        }

        if (target == player) {
            return ;
        }
        Democracy democracy = UHCManager.get().getScenarioManager().getScenario(Democracy.class);
        if (democracy == null) return;
        if(!democracy.isActive()) return;

        democracy.vote(player,target.getName());
    }
}
