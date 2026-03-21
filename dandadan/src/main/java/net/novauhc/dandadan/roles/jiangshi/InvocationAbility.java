package net.novauhc.dandadan.roles.jiangshi;

import net.novaproject.novauhc.ability.template.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Map;

public class InvocationAbility extends UseAbiliy {
    @Override public String getName() { return "Invocation"; }
    @Override public Material getMaterial() { return Material.BONE; }
    @Override public boolean onEnable(Player p) {
        for (int i = 0; i < 5; i++) {
            Location loc = p.getLocation().clone().add((Math.random()-0.5)*8, 0, (Math.random()-0.5)*8);
            p.getWorld().spawnEntity(loc, i % 2 == 0 ? EntityType.ZOMBIE : EntityType.SKELETON);
        }
        LangManager.get().send(DanDaDanLang.GENERIC_ABILITY_ON, p, Map.of("%name%", getName()));
        setCooldown(420); return true; }
}
