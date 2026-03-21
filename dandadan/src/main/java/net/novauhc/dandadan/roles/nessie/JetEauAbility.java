package net.novauhc.dandadan.roles.nessie;

import net.novaproject.novauhc.ability.template.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;

import java.util.Map;

public class JetEauAbility extends UseAbiliy {
    @Override public String getName() { return "Jet d'Eau"; }
    @Override public Material getMaterial() { return Material.PRISMARINE_SHARD; }
    @Override public boolean onEnable(Player p) {
        Snowball sb = p.launchProjectile(Snowball.class);
        sb.setVelocity(p.getLocation().getDirection().multiply(2.5));
        LangManager.get().send(DanDaDanLang.GENERIC_ABILITY_ON, p, Map.of("%name%", getName()));
        setCooldown(180); return true; }
}
