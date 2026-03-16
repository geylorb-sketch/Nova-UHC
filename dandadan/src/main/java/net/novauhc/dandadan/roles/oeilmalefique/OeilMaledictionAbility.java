package net.novauhc.dandadan.roles.oeilmalefique;

import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

public class OeilMaledictionAbility extends UseAbiliy {
    @Override public String getName()       { return "Malédiction"; }
    @Override public Material getMaterial() { return Material.GLASS_BOTTLE; }

    @Override
    public boolean onEnable(Player player) {
        // 40% total réparti aléatoirement entre Force/Résistance/Speed
        int[] shares = randomSplit(40, 3);
        if (shares[0] > 0) player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,   20*600, 0));
        if (shares[1] > 0) player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*600, 0));
        if (shares[2] > 0) player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,             20*600, 0));
        LangManager.get().send(DanDaDanLangExt.OEIL_CURSE_ACTIVATED, player);
        setCooldown(600);
        return true;
    }
    private int[] randomSplit(int total, int parts) {
        int[] r = new int[parts];
        for (int i=0;i<parts-1;i++) r[i] = ThreadLocalRandom.current().nextInt(total+1-(parts-1-i));
        r[parts-1] = total;
        return r;
    }
}