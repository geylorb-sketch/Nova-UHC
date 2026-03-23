package net.novauhc.dandadan.roles.devilman;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.template.UseAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Map;

public class ChaleurAbility extends UseAbility {
    @Override public String getName() { return "Chaleur"; }
    @Override public Material getMaterial() { return Material.MAGMA_CREAM; }
    @Override public boolean onEnable(Player p) {
        var task = Bukkit.getScheduler().runTaskTimer(Main.get(), () -> {
            for (Entity e : p.getNearbyEntities(6,6,6)) {
                if (e instanceof Player t) { t.setFireTicks(40); t.damage(1.0); }
            }
        }, 0L, 20L);
        Bukkit.getScheduler().runTaskLater(Main.get(), task::cancel, 15*20L);
        LangManager.get().send(DanDaDanLang.GENERIC_ABILITY_ON, p, Map.of("%name%", getName()));
        setCooldown(420); return true; }
}
