package net.novauhc.dandadan.roles.reiko;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class EmprisonnementAbility extends UseAbiliy {
    @Override public String getName()       { return "Emprisonnement"; }
    @Override public Material getMaterial() { return Material.GLASS; }
    @Override public boolean onEnable(Player player) {
        Player target = getNearestTarget(player,8); if(target==null) return false;
        Location c=target.getLocation().clone();
        for(int dx=-1;dx<=1;dx++) for(int dz=-1;dz<=1;dz++) for(int dy=0;dy<=3;dy++) {
            if(Math.abs(dx)==1||Math.abs(dz)==1||dy==0||dy==3) {
                Location b=c.clone().add(dx,dy,dz);
                if(b.getBlock().getType()==Material.AIR) b.getBlock().setType(Material.GLASS);
            }
        }
        String msg=LangManager.get().get(DanDaDanLangExt.REIKO_EMPRISONNEMENT,player).replace("%target%",target.getName()); player.sendMessage(msg);
        Main.get().getServer().getScheduler().runTaskLater(Main.get(),()->{
            for(int dx=-1;dx<=1;dx++) for(int dz=-1;dz<=1;dz++) for(int dy=0;dy<=3;dy++){
                Location b=c.clone().add(dx,dy,dz); if(b.getBlock().getType()==Material.GLASS) b.getBlock().setType(Material.AIR);
            }
        },20L*60);
        setCooldown(300); return true;
    }
    private Player getNearestTarget(Player p,double r){ return p.getWorld().getNearbyEntities(p.getLocation(),r,r,r).stream().filter(e->e instanceof Player&&!e.equals(p)).map(e->(Player)e).min((a,b)->Double.compare(a.getLocation().distance(p.getLocation()),b.getLocation().distance(p.getLocation()))).orElse(null); }
}