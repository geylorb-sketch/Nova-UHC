package net.novauhc.dandadan.roles.joseph;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.CommandAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.ShortCooldownManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NigerundayoCommand extends CommandAbility {
    public NigerundayoCommand() { setMaxUse(3); }
    @Override public String getName()       { return "NIGERUNDAYO"; }
    @Override public String getCommandKey() { return "run"; }
    @Override public boolean onCommand(Player player, String[] args) {
        LangManager.get().send(DanDaDanLangExt3.JOSEPH_RUN_ACTIVATED, player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*15, 2));
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () ->
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*30, 1)), 20L*15);
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () ->
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*60, 0)), 20L*45);
        // -75% dégâts pendant tout le run
        ShortCooldownManager.put(player, "JosephRunning", 105_000L);
        return true;
    }
}