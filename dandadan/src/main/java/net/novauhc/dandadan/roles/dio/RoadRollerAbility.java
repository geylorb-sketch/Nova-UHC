package net.novauhc.dandadan.roles.dio;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class RoadRollerAbility extends UseAbiliy {
    @Override public String getName()       { return "Road Roller"; }
    @Override public Material getMaterial() { return Material.ANVIL; }
    @Override public boolean onEnable(Player player) {
        Player target = player.getWorld().getNearbyEntities(player.getLocation(),20,20,20)
                .stream().filter(e->e instanceof Player&&!e.equals(player)).map(e->(Player)e)
                .min((a,b)->Double.compare(a.getLocation().distance(player.getLocation()),b.getLocation().distance(player.getLocation())))
                .orElse(null);
        if (target == null) return false;
        String msg = LangManager.get().get(DanDaDanLangExt3.DIO_ROAD_ROLLER, player).replace("%target%",target.getName()); player.sendMessage(msg);
        // Fait tomber une enclume
        Location above = target.getLocation().add(0, 15, 0);
        above.getBlock().setType(Material.ANVIL);
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            above.getBlock().setType(Material.AIR);
            target.damage(4.0, player);
            // Trou de 15 blocs de rayon
            for (int dx=-3;dx<=3;dx++) for (int dz=-3;dz<=3;dz++)
                target.getLocation().add(dx,0,dz).getBlock().setType(Material.AIR);
        }, 30L);
        setCooldown(600); return true;
    }
}