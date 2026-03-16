package net.novauhc.dandadan.roles.umbrella;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.ShortCooldownManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt2;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ParasolAbility extends Ability {
    @Override public String getName()       { return "Parasol"; }
    @Override public Material getMaterial() { return null; }
    @Override public void onClick(org.bukkit.event.player.PlayerInteractEvent event, ItemStack item) {
        if (!event.getAction().name().contains("LEFT") || item==null || item.getType()!=Material.DIAMOND_SWORD) return;
        tryUse(event.getPlayer());
    }
    @Override public boolean onEnable(Player player) {
        Player target = getNearestTarget(player, 15);
        if (target != null) {
            String msg = LangManager.get().get(DanDaDanLangExt2.UMBRA_PARASOL_THROWN, player).replace("%target%",target.getName()); player.sendMessage(msg);
            target.damage(3.0, player);
            Location below = target.getLocation().subtract(0,1,0);
            below.getBlock().setType(Material.LAVA);
            Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> { if(below.getBlock().getType()==Material.LAVA) below.getBlock().setType(Material.AIR); }, 40L);
            LangManager.get().send(DanDaDanLangExt2.UMBRA_PARASOL_HIT_PLAYER, target);
            // Flag no-attack 7s via ShortCooldownManager
            ShortCooldownManager.put(target, "UmbrellaNoAttack", 7000L);
        } else {
            // Frappe un bloc → explosion de lave
            Location hit = player.getTargetBlock((java.util.Set<Material>) null, 10).getLocation();
            for (int dx=-1;dx<=1;dx++) for (int dz=-1;dz<=1;dz++) {
                hit.clone().add(dx,0,dz).getBlock().setType(Material.LAVA);
            }
            Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
                for (int dx=-1;dx<=1;dx++) for (int dz=-1;dz<=1;dz++) {
                    Location b = hit.clone().add(dx,0,dz);
                    if(b.getBlock().getType()==Material.LAVA) b.getBlock().setType(Material.AIR);
                }
            }, 40L);
            LangManager.get().send(DanDaDanLangExt2.UMBRA_PARASOL_HIT_BLOCK, player);
        }
        setCooldown(120); return true;
    }
    private Player getNearestTarget(Player p,double r){ return p.getWorld().getNearbyEntities(p.getLocation(),r,r,r).stream().filter(e->e instanceof Player&&!e.equals(p)).map(e->(Player)e).min((a,b)->Double.compare(a.getLocation().distance(p.getLocation()),b.getLocation().distance(p.getLocation()))).orElse(null); }
}