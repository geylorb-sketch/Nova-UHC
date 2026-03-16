package net.novauhc.dandadan.roles.oeilmalefique;

import net.novaproject.novauhc.ability.CommandAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

public class TransfoCommand extends CommandAbility {
    public TransfoCommand() { setMaxUse(1); }
    @Override public String getName()       { return "Transformation"; }
    @Override public String getCommandKey() { return "transfo"; }
    @Override public boolean onCommand(Player player, String[] args) {
        // Remélanges les effets actifs
        player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
        player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        player.removePotionEffect(PotionEffectType.SPEED);
        int[] shares = randomSplit(40, 3);
        if (shares[0] > 0) player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,   20*600, 0));
        if (shares[1] > 0) player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*600, 0));
        if (shares[2] > 0) player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,             20*600, 0));
        LangManager.get().send(DanDaDanLangExt.OEIL_TRANSFO_ACTIVATED, player);
        return true;
    }
    private int[] randomSplit(int total, int parts) {
        int[] r = new int[parts];
        for (int i=0;i<parts-1;i++) r[i] = ThreadLocalRandom.current().nextInt(total+1-(parts-1-i));
        r[parts-1] = total;
        return r;
    }
}