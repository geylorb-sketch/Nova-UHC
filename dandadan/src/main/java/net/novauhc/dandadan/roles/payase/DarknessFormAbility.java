package net.novauhc.dandadan.roles.payase;

import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

public class DarknessFormAbility extends UseAbiliy {
    @Override public String getName()       { return "Darkness Form"; }
    @Override public Material getMaterial() { return Material.INK_SACK; }

    @Override
    public boolean onEnable(Player player) {
        long time = player.getWorld().getTime();
        boolean night = time >= 12300 && time <= 23850;

        if (night) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 300, 0, false, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,   20 * 300, 0, false, true));
            LangManager.get().send(DanDaDanLang.PAYASE_DARKNESS_NIGHT, player);
        } else {
            boolean strengthBonus = ThreadLocalRandom.current().nextBoolean();
            if (strengthBonus) player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 300, 1, false, true));
            else               player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 300, 1, false, true));
            String effect = strengthBonus ? "Force" : "Résistance";
            String msg = LangManager.get().get(DanDaDanLang.PAYASE_DARKNESS_DAY, player)
                    .replace("%bonus%", "10").replace("%effect%", effect);
            player.sendMessage(msg);
        }
        setCooldown(180); // 3 mins
        return true;
    }
}

// ─────────────────────────────────────────────────────────────

/** Permutation — Clic-Gauche (item de DarknessForm) : ombre invisible + particule noire 15s. */
