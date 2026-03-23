package net.novauhc.dandadan.roles.payase;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.template.UseAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

public class OmbreAbility extends UseAbility {
    @Override public String getName() { return "Ombre"; }
    @Override public Material getMaterial() { return Material.OBSIDIAN; }

    private final Set<Location> shadowBlocks = new HashSet<>();
    @Override
    public boolean onEnable(Player player) {
        shadowBlocks.clear();
        Location center = player.getLocation();
        for (int x = -5; x <= 5; x++)
            for (int z = -5; z <= 5; z++) {
                Location bl = center.clone().add(x, -1, z);
                if (bl.getBlock().getType() != Material.AIR) shadowBlocks.add(bl.getBlock().getLocation());
            }
        LangManager.get().send(DanDaDanLang.PAYASE_OMBRE_ON, player);
        // Check chaque seconde
        var task = Bukkit.getScheduler().runTaskTimer(Main.get(), () -> {
            for (Entity e : player.getWorld().getNearbyEntities(center, 6, 3, 6)) {
                if (!(e instanceof Player p)) continue;
                Location below = p.getLocation().clone().subtract(0, 1, 0);
                if (!shadowBlocks.contains(below.getBlock().getLocation())) continue;
                if (p.equals(player)) {
//                    // Payase = +0.5 absorption
//                    double abs = p.getAbsorptionAmount();
//                    p.setAbsorptionAmount(Math.min(20, abs + 1));
                } else {
                    p.setVelocity(new Vector(0, 3.0, 0));
                    p.damage(3.0);
                }
            }
        }, 0L, 20L);
        Bukkit.getScheduler().runTaskLater(Main.get(), task::cancel, 60*20L);
        setCooldown(600);
        return true;
    }

}
