package net.novauhc.dandadan.roles.jotaro;

import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StarPlatinumAbility extends UseAbiliy {

    @Override public String getName()       { return "Star Platinum"; }
    @Override public Material getMaterial() { return Material.DIAMOND_CHESTPLATE; }
    @Override public boolean onEnable(Player player) {
        UHCPlayer uhcPlayer = getUHCPlayer(player);
        JotaroRole role = (JotaroRole) DanDaDan.get().getRoleByUHCPlayer(uhcPlayer);
        role.setStandActive(true);
        LangManager.get().send(DanDaDanLangExt3.JOTARO_STAND_ACTIVATED, player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*600, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,             20*600, 0));
        setCooldown(600); return true;
    }
}