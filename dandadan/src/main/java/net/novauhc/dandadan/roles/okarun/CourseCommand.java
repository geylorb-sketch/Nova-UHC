package net.novauhc.dandadan.roles.okarun;

import net.novaproject.novauhc.ability.template.CommandAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.world.CourseManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * Course — Commande Okarun (/ddd course <pseudo>)
 * 3x/partie. TP les 2 joueurs dans un parcours.
 * Gagnant : +1❤ permanent.
 */
public class CourseCommand extends CommandAbility {

    public CourseCommand() {
        setMaxUse(3);
        setCooldown(0);
    }

    @Override public String getName()       { return "Course"; }
    @Override public String getCommandKey() { return "course"; }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (args.length < 2) {
            LangManager.get().send(DanDaDanLang.OKARUN_COURSE_USAGE, player);
            return false;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null || !target.isOnline()) {
            LangManager.get().send(DanDaDanLang.OKARUN_COURSE_NOT_FOUND, player, Map.of("%name%", args[1]));
            return false;
        }

        if (target.equals(player)) {
            LangManager.get().send(DanDaDanLang.OKARUN_COURSE_SELF, player);
            return false;
        }

        if (CourseManager.get().isRaceActive()) {
            LangManager.get().send(DanDaDanLang.COURSE_ALREADY_ACTIVE, player);
            return false;
        }

        if (!CourseManager.get().isConfigured()) {
            LangManager.get().send(DanDaDanLang.COURSE_NOT_CONFIGURED, player);
            return false;
        }

        LangManager.get().send(DanDaDanLang.OKARUN_COURSE_CHALLENGE, player, Map.of("%target%", target.getName()));
        LangManager.get().send(DanDaDanLang.OKARUN_COURSE_CHALLENGED, target, Map.of("%player%", player.getName()));

        boolean started = CourseManager.get().startRace(player, target);
        if (!started) {
            LangManager.get().send(DanDaDanLang.COURSE_ALREADY_ACTIVE, player);
            return false;
        }

        return true;
    }
}
