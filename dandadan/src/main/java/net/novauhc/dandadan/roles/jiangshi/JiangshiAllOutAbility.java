package net.novauhc.dandadan.roles.jiangshi;

import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Jiangshi All-Out — UseAbility.
 * Jiangshi entre en mode All-Out : 30% de force + 20% résistance pendant 60s.
 * Coûte du Ki (géré via JiangshiRole.spendKi).
 */
public class JiangshiAllOutAbility extends UseAbiliy {

    @Override public String getName()       { return "Jiangshi All-Out"; }
    @Override public Material getMaterial() { return Material.NETHER_STAR; }

    @Override
    public boolean onEnable(Player player) {
        UHCPlayer uhcPlayer = getUHCPlayer(player);
        if (uhcPlayer == null || DanDaDan.get() == null) return false;
        JiangshiRole role = (JiangshiRole) DanDaDan.get().getRoleByUHCPlayer(uhcPlayer);

        if (!role.spendKi(3)) {
            LangManager.get().send(DanDaDanLang.JIANGSHI_NOT_ENOUGH_KI, player);
            return false;
        }

        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,   20 * 60, 1, false, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 60, 0, false, true));
        LangManager.get().send(DanDaDanLang.JIANGSHI_ALLOUT_ACTIVATED, player);
        setCooldown(120);
        return true;
    }
}
