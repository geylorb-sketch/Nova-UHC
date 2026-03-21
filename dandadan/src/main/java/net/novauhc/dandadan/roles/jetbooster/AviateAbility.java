package net.novauhc.dandadan.roles.jetbooster;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.template.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

public class AviateAbility extends UseAbiliy {
    @Override public String getName() { return "Aviate Exosuit"; }
    @Override public Material getMaterial() { return Material.FEATHER; }
    @Override public boolean onEnable(Player p) {
        p.setAllowFlight(true); p.setFlying(true);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10*20, 1, false, true));
        Bukkit.getScheduler().runTaskLater(Main.get(), () -> { p.setFlying(false); p.setAllowFlight(false); }, 10*20L);
        LangManager.get().send(DanDaDanLang.GENERIC_ABILITY_ON, p, Map.of("%name%", getName()));
        setCooldown(300); return true; }
}
