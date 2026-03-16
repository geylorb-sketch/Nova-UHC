package net.novauhc.dandadan.roles.joseph;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ClackerAbility extends UseAbiliy {
    @Override public String getName()       { return "Clacker Boomerang"; }
    @Override public Material getMaterial() { return Material.IRON_SWORD; }
    @Override public boolean onEnable(Player player) {
        Player target = getNearestTarget(player, 15); if(target==null) return false;
        String msg = LangManager.get().get(DanDaDanLangExt3.JOSEPH_CLACKER, player).replace("%target%", target.getName()); player.sendMessage(msg);
        // Bloque target dans 5 blocs de Joseph pendant 90s
        UUID jId = player.getUniqueId();
        var taskId = new int[1];
        taskId[0] = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(), () -> {
            if (!target.isOnline() || !player.isOnline()) { Main.get().getServer().getScheduler().cancelTask(taskId[0]); return; }
            if (target.getLocation().distance(player.getLocation()) > 5) {
                // Perd 5❤ permanents s'il s'éloigne
                target.damage(10.0);
            }
        }, 0L, 20L);
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> Main.get().getServer().getScheduler().cancelTask(taskId[0]), 20L*90);
        setCooldown(420); return true;
    }
    private Player getNearestTarget(Player p, double r) { return p.getWorld().getNearbyEntities(p.getLocation(),r,r,r).stream().filter(e->e instanceof Player&&!e.equals(p)).map(e->(Player)e).min((a,b)->Double.compare(a.getLocation().distance(p.getLocation()),b.getLocation().distance(p.getLocation()))).orElse(null); }
}