package net.novauhc.dandadan.roles.jiji;

import net.novaproject.novauhc.ability.template.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Map;

public class SpiralesAbility extends UseAbiliy {
    @Override public String getName() { return "Spirales"; }
    @Override public Material getMaterial() { return Material.FIREWORK; }
    @Override public boolean onEnable(Player p) {
        for (Entity e : p.getNearbyEntities(10,10,10)) {
            if (e instanceof Player t) { t.damage(4.0, p); t.setFireTicks(60); }
        }
        LangManager.get().send(DanDaDanLang.GENERIC_ABILITY_ON, p, Map.of("%name%", getName()));
        setCooldown(420); return true; }
}
