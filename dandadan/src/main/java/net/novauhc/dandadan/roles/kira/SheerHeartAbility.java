package net.novauhc.dandadan.roles.kira;

import net.novaproject.novauhc.ability.template.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;

import java.util.Map;

public class SheerHeartAbility extends UseAbiliy {
    @Override public String getName() { return "Sheer Heart Attack"; }
    @Override public Material getMaterial() { return Material.EXPLOSIVE_MINECART; }
    @Override public boolean onEnable(Player p) {
        Snowball sb = p.launchProjectile(Snowball.class);
        sb.setVelocity(p.getLocation().getDirection().multiply(2.0));
        LangManager.get().send(DanDaDanLang.GENERIC_ABILITY_ON, p, Map.of("%name%", getName()));
        setCooldown(300); return true; }
}
