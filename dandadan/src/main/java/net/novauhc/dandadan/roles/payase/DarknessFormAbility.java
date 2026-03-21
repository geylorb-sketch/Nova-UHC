package net.novauhc.dandadan.roles.payase;

import net.novaproject.novauhc.ability.template.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class DarknessFormAbility extends UseAbiliy {
    @Override public String getName() { return "Darkness Form"; }
    @Override public Material getMaterial() { return Material.INK_SACK; }

    private final Random random = new Random();
    @Override
    public boolean onEnable(Player player) {
        long time = player.getWorld().getTime();
        boolean night = time > 13000 && time < 23000;
        if (night) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 5*60*20, 0, false, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 5*60*20, 0, false, true));
        } else {
            if (random.nextBoolean())
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 5*60*20, 0, false, true));
            else
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 5*60*20, 0, false, true));
        }
        LangManager.get().send(DanDaDanLang.PAYASE_DARKNESS_ON, player);
        setCooldown(180);
        return true;
    }

}
