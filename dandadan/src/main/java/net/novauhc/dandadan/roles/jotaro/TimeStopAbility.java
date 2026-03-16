package net.novauhc.dandadan.roles.jotaro;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TimeStopAbility extends UseAbiliy {

    @Override public String getName()       { return "Arrêt du Temps"; }
    @Override public Material getMaterial() { return Material.WATCH; }
    @Override public boolean onEnable(Player player) {
        UHCPlayer uhcPlayer = getUHCPlayer(player);
        JotaroRole role = (JotaroRole) DanDaDan.get().getRoleByUHCPlayer(uhcPlayer);
        if (!role.isStandActive()) { player.sendMessage("§c✘ Stand requis !"); return false; }
        double secs = role.getTimeFreeze();
        String msg = LangManager.get().get(DanDaDanLangExt3.JOTARO_TIME_STOP, player).replace("%seconds%", String.valueOf((int)secs));
        player.sendMessage(msg);
        // Gèle tous les joueurs proches
        player.getWorld().getPlayers().stream().filter(p->!p.equals(player))
                .forEach(p->p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int)(20*secs), 10)));
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () ->
                player.getWorld().getPlayers().forEach(p -> p.removePotionEffect(PotionEffectType.SLOW)), (long)(20*secs));
        setCooldown(600); return true;
    }
}