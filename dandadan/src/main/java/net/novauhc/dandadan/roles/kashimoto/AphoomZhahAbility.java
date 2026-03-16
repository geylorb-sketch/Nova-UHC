package net.novauhc.dandadan.roles.kashimoto;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Aphoom-Zhah — Clic-Droit
 * Pose un bloc de glace. Attire les joueurs proches vers ce bloc → mode spectateur 5s.
 * À la position de chaque joueur en spec : particule violette.
 * Si Kashimoto frappe cette particule → 10-20% durabilité armure perdus + Regen1 au lieu de 2 (1min).
 */
public class AphoomZhahAbility extends UseAbiliy {

    private final List<Location> particleMarks = new ArrayList<>();
    private Location iceLocation;

    @Override public String getName()       { return "Aphoom-Zhah"; }
    @Override public Material getMaterial() { return Material.ICE; }

    @Override
    public boolean onEnable(Player kashimoto) {
        Location loc = kashimoto.getLocation().clone();
        loc.getBlock().setType(Material.ICE);
        iceLocation = loc.clone();
        LangManager.get().send(DanDaDanLang.KASHIMOTO_APHOOM_ACTIVATED, kashimoto);

        List<Player> attracted = new ArrayList<>();
        kashimoto.getWorld().getNearbyEntities(loc, 15, 15, 15)
                .stream().filter(e -> e instanceof Player && !e.equals(kashimoto))
                .forEach(e -> {
                    Player victim = (Player) e;
                    attracted.add(victim);
                    // Attire vers le bloc
                    Vector dir = loc.toVector().subtract(victim.getLocation().toVector()).normalize();
                    victim.setVelocity(dir.multiply(2));
                });

        // Après 1s (trajectoire) → met en spectateur 5s
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            attracted.forEach(victim -> {
                if (!victim.isOnline()) return;
                Location markLoc = victim.getLocation().clone();
                particleMarks.add(markLoc);

                // Mode spectateur 5s
                victim.setGameMode(GameMode.SPECTATOR);
                Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
                    victim.setGameMode(GameMode.SURVIVAL);
                }, 100L);

                // Particule violette persistante (30s)
                var taskId = new int[1];
                taskId[0] = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(), () -> {
                    new ParticleBuilder(ParticleEffect.REDSTONE)
                            .setColor(java.awt.Color.MAGENTA)
                            .setLocation(markLoc).setAmount(3).display();
                }, 0L, 10L);
                Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
                    Main.get().getServer().getScheduler().cancelTask(taskId[0]);
                    particleMarks.remove(markLoc);
                }, 20L * 30);
            });

            // Retire le bloc de glace
            if (iceLocation != null && iceLocation.getBlock().getType() == Material.ICE) {
                iceLocation.getBlock().setType(Material.AIR);
            }
        }, 20L);

        setCooldown(600); // 10 mins
        return true;
    }

    /**
     * Appelé depuis KashimotoRole.onHit() — vérifie si Kashimoto frappe une particule marquée.
     */
    public void onKashimotoStrike(Player kashimoto, Player victim) {
        Location vLoc = victim.getLocation();
        boolean wasMarked = particleMarks.stream()
                .anyMatch(m -> m.distance(vLoc) <= 2);

        if (!wasMarked) return;

        // Dégrade armure 10-20%
        java.util.Random rand = new java.util.Random();
        ItemStack[] armor = victim.getInventory().getArmorContents();
        for (ItemStack piece : armor) {
            if (piece == null || piece.getType() == Material.AIR) continue;
            double pct = 0.10 + rand.nextDouble() * 0.10;
            short loss = (short)(piece.getType().getMaxDurability() * pct);
            piece.setDurability((short) Math.min(piece.getType().getMaxDurability(), piece.getDurability() + loss));
        }
        victim.getInventory().setArmorContents(armor);

        // Regen1 au lieu de 2 pendant 1min (flag géré par le listener onConsume)
        AphoomZhahDebuff.apply(victim.getUniqueId(), System.currentTimeMillis() + 60_000L);
    }

    /** Map statique accessible depuis l'event onConsume pour réduire Regen sur pomme. */
    public static class AphoomZhahDebuff {
        private static final java.util.Map<UUID, Long> debuffed = new java.util.HashMap<>();
        public static void apply(UUID uuid, long expiry) { debuffed.put(uuid, expiry); }
        public static boolean isDebuffed(UUID uuid) {
            Long exp = debuffed.get(uuid);
            if (exp == null) return false;
            if (exp < System.currentTimeMillis()) { debuffed.remove(uuid); return false; }
            return true;
        }
    }
}
