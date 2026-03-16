package net.novauhc.dandadan.roles.joseph;

import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import net.novauhc.dandadan.roles.caesar.CaesarRole;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RippleHairAbility extends UseAbiliy {
    @Override public String getName()       { return "Ripple Hair Attack"; }
    @Override public Material getMaterial() { return Material.STRING; }
    @Override public boolean onEnable(Player player) {
        LangManager.get().send(DanDaDanLangExt3.JOSEPH_RIPPLE, player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*180, 0));
        // Donne 10% résistance à Caesar
        if (DanDaDan.get() != null) {
            UHCPlayerManager.get().getPlayingOnlineUHCPlayers().forEach(p -> {
                var role = DanDaDan.get().getRoleByUHCPlayer(p);
                if (role instanceof CaesarRole && p.getPlayer() != null) {
                    p.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*180, 0));
                }
            });
        }
        setCooldown(420); return true;
    }
}