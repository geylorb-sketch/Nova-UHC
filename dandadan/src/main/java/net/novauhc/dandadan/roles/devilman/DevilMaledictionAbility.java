package net.novauhc.dandadan.roles.devilman;

import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt2;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DevilMaledictionAbility extends UseAbiliy {
    @Override public String getName()       { return "Malédiction Devilman"; }
    @Override public Material getMaterial() { return Material.BLAZE_ROD; }
    @Override public boolean onEnable(Player player) {
        LangManager.get().send(DanDaDanLangExt2.DEVIL_CURSE_ACTIVATED, player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,   20*600, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*600, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE,   20*600, 0));
        setCooldown(600); return true;
    }
}