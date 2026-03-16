package net.novauhc.dandadan.roles.flatwoods;

import net.novaproject.novauhc.ability.CommandAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

public class SumoCommand extends CommandAbility {
    public SumoCommand() { setMaxUse(3); }
    @Override public String getName()       { return "Sumo"; }
    @Override public String getCommandKey() { return "sumo"; }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (args.length < 2) { LangManager.get().send(DanDaDanLangExt.FLATWOODS_SUMO_USAGE, player); return false; }
        Player target = Bukkit.getPlayer(args[1]);
        if (target==null||target.equals(player)) { player.sendMessage("§c✘ Joueur invalide."); return false; }

        String msg = LangManager.get().get(DanDaDanLangExt.FLATWOODS_SUMO_START, player)
                .replace("%target%", target.getName());
        player.sendMessage(msg);
        // Téléporte les deux joueurs à des positions opposées autour du Flatwoods
        // et démarre un compteur de 10 coups mains nues
        // Simule le résultat pour l'instant
        if (ThreadLocalRandom.current().nextBoolean()) {
            LangManager.get().send(DanDaDanLangExt.FLATWOODS_SUMO_WIN, player);
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999, 0));
        }
        return true;
    }
}