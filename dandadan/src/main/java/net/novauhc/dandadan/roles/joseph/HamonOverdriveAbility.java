package net.novauhc.dandadan.roles.joseph;

import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class HamonOverdriveAbility extends UseAbiliy {
    @Override public String getName()       { return "Hamon Overdrive"; }
    @Override public Material getMaterial() { return Material.LEASH; }
    @Override public boolean onEnable(Player player) {
        Player target = getNearestTarget(player, 15); if(target==null) return false;
        target.damage(4.0, player);
        Vector kb = target.getLocation().subtract(player.getLocation()).toVector().normalize().multiply(10);
        kb.setY(0.5); target.setVelocity(kb);
        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 10)); // bloqué 3s
        String msg = LangManager.get().get(DanDaDanLangExt3.JOSEPH_HAMON_OVERDRIVE, player).replace("%target%", target.getName()); player.sendMessage(msg);
        setCooldown(720); return true;
    }
    private Player getNearestTarget(Player p, double r) { return p.getWorld().getNearbyEntities(p.getLocation(),r,r,r).stream().filter(e->e instanceof Player&&!e.equals(p)).map(e->(Player)e).min((a,b)->Double.compare(a.getLocation().distance(p.getLocation()),b.getLocation().distance(p.getLocation()))).orElse(null); }
}