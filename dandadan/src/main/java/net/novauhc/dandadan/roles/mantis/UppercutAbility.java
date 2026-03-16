package net.novauhc.dandadan.roles.mantis;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UppercutAbility extends Ability {

    @Override public String getName()       { return "Salaire Uppercut"; }
    @Override public Material getMaterial() { return null; }

    @Override
    public void onClick(org.bukkit.event.player.PlayerInteractEvent event, ItemStack item) {
        if (event.getAction().name().contains("LEFT") && item != null && item.getType() == Material.LEASH) {
            tryUse(event.getPlayer());
        }
    }

    @Override
    public boolean onEnable(Player mantis) {
        Player target = getNearestTarget(mantis, 10);
        if (target == null) return false;

        String msg = LangManager.get().get(DanDaDanLang.MANTIS_UPPERCUT, mantis)
                .replace("%target%", target.getName());
        mantis.sendMessage(msg);

        target.setVelocity(new Vector(0, 3.0, 0));

        // Téléporte Mantis sur la cible pendant la descente (après 2s)
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            if (!target.isOnline()) return;
            mantis.teleport(target.getLocation().clone().add(0.5, 0, 0.5));
        }, 40L);

        // Combo aérien : 5s de frappe limitée à 0.5❤ par coup, max 50% HP
        // Flag géré via EntityDamageByEntityEvent dans le listener global DanDaDan
        UppercutState.start(target.getUniqueId(), target.getHealth() * 0.5, System.currentTimeMillis() + 5000L);

        // Pas de dégâts de chute pour la cible
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () ->
                UppercutState.end(target.getUniqueId()), 120L);

        setCooldown(420);
        return true;
    }

    private Player getNearestTarget(Player p, double range) {
        return p.getWorld().getNearbyEntities(p.getLocation(), range, range, range)
                .stream().filter(e -> e instanceof Player && !e.equals(p))
                .map(e -> (Player) e)
                .min((a, b) -> Double.compare(a.getLocation().distance(p.getLocation()), b.getLocation().distance(p.getLocation())))
                .orElse(null);
    }

    public static class UppercutState {
        private static final Map<UUID, double[]> states = new HashMap<>();
        public static void start(UUID uuid, double maxDmg, long expiry) { states.put(uuid, new double[]{maxDmg, expiry, 0}); }
        public static void end(UUID uuid) { states.remove(uuid); }
        public static boolean isActive(UUID uuid) {
            double[] s = states.get(uuid);
            if (s == null) return false;
            if (System.currentTimeMillis() > s[1]) { states.remove(uuid); return false; }
            return true;
        }
        /** Retourne les dégâts autorisés (1 demi-❤ = 1.0, cap 50% HP). */
        public static double capDamage(UUID uuid, double original) {
            double[] s = states.get(uuid);
            if (s == null) return original;
            double dealt = s[2];
            double max = s[0];
            double allowed = Math.min(1.0, max - dealt);
            s[2] += allowed;
            return allowed;
        }
    }
}

// ── Jet Water ────────────────────────────────────────────────────
