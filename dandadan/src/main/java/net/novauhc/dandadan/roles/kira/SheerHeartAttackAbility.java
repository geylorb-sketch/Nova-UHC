package net.novauhc.dandadan.roles.kira;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Objects;

public class SheerHeartAttackAbility extends UseAbiliy {

    @Override public String getName()       { return "Sheer Heart Attack"; }
    @Override public Material getMaterial() { return Material.EGG; }
    @Override public boolean onEnable(Player player) {
        KiraRole role = (KiraRole) DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(player));
        if (!role.isStandActive()) { player.sendMessage("§c✘ Stand requis !"); return false; }
        LangManager.get().send(DanDaDanLangExt3.KIRA_SHEER_ACTIVATED, player);
        // Cherche un joueur "explosé" ayant posé de la lave récemment
        Player target = role.getExplodedPlayers().stream()
                .map(Bukkit::getPlayer).filter(Objects::nonNull)
                .findFirst().orElse(null);
        if (target == null) { player.sendMessage("§c✘ Aucune cible 'explosée'."); return false; }
        // Simule la poursuite
        Player finalTarget = target;
        var taskId = new int[1];
        taskId[0] = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(), () -> {
            if (!finalTarget.isOnline()) { Main.get().getServer().getScheduler().cancelTask(taskId[0]); return; }
            if (finalTarget.getLocation().distance(player.getLocation()) < 2) {
                finalTarget.damage(4.0, player);
                Location b = finalTarget.getLocation().subtract(0,1,0);
                b.getBlock().setType(Material.LAVA);
                Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> { if(b.getBlock().getType()==Material.LAVA) b.getBlock().setType(Material.AIR); }, 40L);
                Main.get().getServer().getScheduler().cancelTask(taskId[0]);
            }
        }, 0L, 5L);
        setCooldown(600); return true;
    }
}