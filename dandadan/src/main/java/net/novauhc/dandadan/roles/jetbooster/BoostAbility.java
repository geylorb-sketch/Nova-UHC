package net.novauhc.dandadan.roles.jetbooster;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BoostAbility extends UseAbiliy {
    @Override public String getName()       { return "Boost"; }
    @Override public Material getMaterial() { return Material.FIREWORK_CHARGE; }
    @Override public boolean onEnable(Player player) {
        LangManager.get().send(DanDaDanLangExt.KUR_BOOST_ACTIVATED, player);
        player.setVelocity(player.getLocation().getDirection().normalize().multiply(3).setY(1.5));
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () ->
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*60, 0)), 20L);
        setCooldown(600); return true;
    }
}