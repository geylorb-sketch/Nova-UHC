package net.novauhc.dandadan.roles.okarun;

import net.novaproject.novauhc.ability.CommandAbility;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CourseAbility extends CommandAbility {

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "OKARUN_COURSE_WIN_HP_NAME", descKey = "OKARUN_COURSE_WIN_HP_DESC", type = VariableType.DOUBLE)
    private double winHpBonus = 2.0;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "OKARUN_COURSE_WIN_CURSE_BONUS_NAME", descKey = "OKARUN_COURSE_WIN_CURSE_BONUS_DESC", type = VariableType.TIME)
    private int winCurseBonus = 30;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "OKARUN_COURSE_LOSE_SPEED_NAME", descKey = "OKARUN_COURSE_LOSE_SPEED_DESC", type = VariableType.TIME)
    private int loseSpeedDuration = 60;

    public CourseAbility() { setMaxUse(3); setCooldown(0); }

    @Override public String getName()       { return "Course"; }
    @Override public String getCommandKey() { return "course"; }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (args.length < 2) {
            LangManager.get().send(DanDaDanLang.OKARUN_COURSE_USAGE, player);
            return false;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null || target.equals(player)) {
            player.sendMessage("§c✘ Joueur introuvable."); return false;
        }
        UHCPlayer uhcTarget = getUHCPlayer(target);
        if (uhcTarget == null || !uhcTarget.isPlaying()) {
            player.sendMessage("§c✘ Ce joueur n'est pas en jeu."); return false;
        }

        // La structure de course sera chargée via LobbyCreator dans une future implémentation
        boolean okarunWins = Math.random() < 0.5;

        if (okarunWins) {
            player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + winHpBonus));
            String msg = LangManager.get().get(DanDaDanLang.OKARUN_COURSE_WIN, player)
                    .replace("%hp%", String.valueOf((int)(winHpBonus / 2)))
                    .replace("%time%", String.valueOf(winCurseBonus));
            player.sendMessage(msg);

            UHCPlayer uhcPlayer = getUHCPlayer(player);
            if (uhcPlayer != null && DanDaDan.get() != null) {
                var role = DanDaDan.get().getRoleByUHCPlayer(uhcPlayer);
                if (role instanceof OkarunRole okarun) okarun.addCurseTime(winCurseBonus);
            }
            LangManager.get().send(DanDaDanLang.OKARUN_COURSE_LOSE, target);
        } else {
            target.setHealth(Math.min(target.getMaxHealth(), target.getHealth() + winHpBonus));
            target.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * loseSpeedDuration, 0, false, true));
            String msg = LangManager.get().get(DanDaDanLang.OKARUN_COURSE_TARGET_WIN, target)
                    .replace("%player%", player.getName())
                    .replace("%hp%", String.valueOf((int)(winHpBonus / 2)));
            target.sendMessage(msg);
            LangManager.get().send(DanDaDanLang.OKARUN_COURSE_LOSE, player);
        }
        return true;
    }
}
