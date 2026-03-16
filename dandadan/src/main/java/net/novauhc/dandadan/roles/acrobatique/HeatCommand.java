package net.novauhc.dandadan.roles.acrobatique;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.CommandAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt2;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class HeatCommand extends CommandAbility {
    public HeatCommand() { setMaxUse(3); }
    @Override public String getName()       { return "Manger"; }
    @Override public String getCommandKey() { return "heat"; }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (args.length < 2) { LangManager.get().send(DanDaDanLangExt2.ACRO_HEAT_USAGE, player); return false; }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) { player.sendMessage("§c✘ Joueur introuvable."); return false; }

        target.setGameMode(GameMode.SPECTATOR);
        String msg = LangManager.get().get(DanDaDanLangExt2.ACRO_HEAT_ACTIVATED, player)
                .replace("%target%", target.getName());
        player.sendMessage(msg);

        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            if (target.isOnline()) target.setGameMode(GameMode.SURVIVAL);
        }, 20L * 15);
        return true;
    }
}