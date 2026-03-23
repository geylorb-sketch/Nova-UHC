package net.novauhc.dandadan.roles.caesar;

import net.novaproject.novauhc.ability.template.UseAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

public class SavonLensesAbility extends UseAbility {
    @Override public String getName() { return "Savon Lenses"; }
    @Override public Material getMaterial() { return Material.GLASS_BOTTLE; }
    @Override public boolean onEnable(Player p) {
        for (Entity e : p.getNearbyEntities(30,30,30)) {
            if (e instanceof Player t) t.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 200, 0, false, true));
        }
        LangManager.get().send(DanDaDanLang.GENERIC_ABILITY_ON, p, Map.of("%name%", getName()));
        setCooldown(300); return true; }
}
