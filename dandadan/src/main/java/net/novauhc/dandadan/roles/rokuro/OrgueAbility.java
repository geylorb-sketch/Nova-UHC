package net.novauhc.dandadan.roles.rokuro;

import net.novaproject.novauhc.ability.template.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

public class OrgueAbility extends UseAbiliy {
    @Override public String getName() { return "Orgue des sens"; }
    @Override public Material getMaterial() { return Material.COMPASS; }
    @Override public boolean onEnable(Player p) {
        for (Entity e : p.getNearbyEntities(30,30,30)) {
            if (e instanceof Player t) t.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 200, 0, false, true));
        }
        LangManager.get().send(DanDaDanLang.GENERIC_ABILITY_ON, p, Map.of("%name%", getName()));
        setCooldown(300); return true; }
}
