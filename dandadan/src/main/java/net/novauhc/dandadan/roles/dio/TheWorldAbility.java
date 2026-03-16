package net.novauhc.dandadan.roles.dio;

import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TheWorldAbility extends UseAbiliy {

    @Override public String getName()       { return "The World"; }
    @Override public Material getMaterial() { return Material.DIAMOND_CHESTPLATE; }
    @Override public boolean onEnable(Player player) {
        UHCPlayer uhcPlayer = getUHCPlayer(player);
        if (uhcPlayer == null || DanDaDan.get() == null) return false;
        DioRole role = (DioRole) DanDaDan.get().getRoleByUHCPlayer(uhcPlayer);
        role.setStandActive(true);
        role.resetTimeSkip();
        LangManager.get().send(DanDaDanLangExt3.DIO_STAND_ACTIVATED, player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*600, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,20*600, 0));
        setCooldown(600); return true;
    }
}