package net.novauhc.dandadan.roles.kashimoto;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.template.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * Aphoom-Zhah — Actif Kashimoto (Clic-Droit, PACKED_ICE)
 * Bloc de glace. Joueurs attires + spectateur 5s. Structure de glace piege.
 * CD 10min.
 */
public class AphoomZhahAbility extends UseAbiliy {

    @Override public String getName()       { return "Aphoom-Zhah"; }
    @Override public Material getMaterial() { return Material.PACKED_ICE; }

    @Override
    public boolean onEnable(Player player) {
        Location iceLoc = player.getLocation().clone();
        iceLoc.getBlock().setType(Material.PACKED_ICE);

        LangManager.get().send(DanDaDanLang.KASHIMOTO_APHOOM_ON, player);

        // Attirer les joueurs vers le bloc de glace
        for (Entity e : player.getWorld().getNearbyEntities(iceLoc, 15, 15, 15)) {
            if (!(e instanceof Player p)) continue;
            if (p.equals(player)) continue;

            Vector pull = iceLoc.toVector().subtract(p.getLocation().toVector()).normalize().multiply(1.5);
            p.setVelocity(pull);

            // Spectateur 5s
            GameMode saved = p.getGameMode();
            Location savedLoc = p.getLocation().clone();
            p.setGameMode(GameMode.SPECTATOR);

            Bukkit.getScheduler().runTaskLater(Main.get(), () -> {
                if (p.isOnline()) {
                    p.setGameMode(saved);
                    p.teleport(savedLoc);
                    // Structure de glace autour
                    for (int dx = -1; dx <= 1; dx++)
                        for (int dz = -1; dz <= 1; dz++)
                            for (int dy = 0; dy <= 2; dy++)
                                if (savedLoc.clone().add(dx, dy, dz).getBlock().getType() == Material.AIR)
                                    savedLoc.clone().add(dx, dy, dz).getBlock().setType(Material.ICE);
                }
            }, 100L); // 5s

            // Retirer la glace apres 10s
            Bukkit.getScheduler().runTaskLater(Main.get(), () -> {
                for (int dx = -1; dx <= 1; dx++)
                    for (int dz = -1; dz <= 1; dz++)
                        for (int dy = 0; dy <= 2; dy++)
                            if (savedLoc.clone().add(dx, dy, dz).getBlock().getType() == Material.ICE)
                                savedLoc.clone().add(dx, dy, dz).getBlock().setType(Material.AIR);
            }, 200L); // 10s
        }

        // Retirer le bloc de glace central
        Bukkit.getScheduler().runTaskLater(Main.get(), () -> {
            if (iceLoc.getBlock().getType() == Material.PACKED_ICE) iceLoc.getBlock().setType(Material.AIR);
        }, 200L);

        setCooldown(600); // 10min
        return true;
    }
}
