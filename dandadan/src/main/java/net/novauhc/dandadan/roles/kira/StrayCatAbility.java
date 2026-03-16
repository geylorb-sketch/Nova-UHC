package net.novauhc.dandadan.roles.kira;

import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class StrayCatAbility extends UseAbiliy {
    @Override public String getName()       { return "Stray Cat"; }
    @Override public Material getMaterial() { return Material.GRASS; }
    @Override public boolean onEnable(Player player) {
        LangManager.get().send(DanDaDanLangExt3.KIRA_STRAY_CAT, player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*30, 1));
        player.getWorld().getNearbyEntities(player.getLocation(),5,5,5)
                .stream().filter(e->e instanceof Player&&!e.equals(player))
                .forEach(e->{ Vector kb=e.getLocation().subtract(player.getLocation()).toVector().normalize().multiply(3); ((Player)e).setVelocity(kb); });
        setCooldown(600); return true;
    }
}