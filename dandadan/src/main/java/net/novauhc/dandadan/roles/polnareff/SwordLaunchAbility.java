package net.novauhc.dandadan.roles.polnareff;

import net.novaproject.novauhc.ability.template.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Map;

public class SwordLaunchAbility extends UseAbiliy {
    @Override public String getName() { return "Sword Launch"; }
    @Override public Material getMaterial() { return Material.IRON_SWORD; }
    @Override public boolean onEnable(Player p) {
        p.setVelocity(p.getLocation().getDirection().multiply(3.0));
        LangManager.get().send(DanDaDanLang.GENERIC_ABILITY_ON, p, Map.of("%name%", getName()));
        setCooldown(60); return true; }
}
