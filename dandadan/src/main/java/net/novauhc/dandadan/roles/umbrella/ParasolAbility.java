package net.novauhc.dandadan.roles.umbrella;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.template.UseAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

public class ParasolAbility extends UseAbility {
    @Override public String getName() { return "Parasol"; }
    @Override public Material getMaterial() { return Material.FEATHER; }
    @Override public boolean onEnable(Player p) {
        p.setAllowFlight(true); p.setFlying(true);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10*20, 0, false, true));
        Bukkit.getScheduler().runTaskLater(Main.get(), () -> { p.setFlying(false); p.setAllowFlight(false); }, 10*20L);
        LangManager.get().send(DanDaDanLang.GENERIC_ABILITY_ON, p, Map.of("%name%", getName()));
        setCooldown(180); return true; }
}
