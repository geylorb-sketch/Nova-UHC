package net.novauhc.dandadan.roles.momo;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.UUID;

/**
 * Psychokinésie — Actif Momo
 *
 * Clic-Droit (Main Droite, DIAMOND) : Bloque un joueur visé en l'air, 
 *   Momo peut le déplacer avec son viseur. Cooldown 5min.
 *
 * Clic-Gauche (Main Gauche, DIAMOND) : Lance 3 blocs de pierre.
 *   Collision = structure de pierre 3s. Cooldown 8min.
 */
public class PsychokinesieAbility extends Ability {

    private UUID heldPlayer = null;
    private BukkitTask holdTask = null;

    @Override public String getName()       { return "Psychokinesie"; }
    @Override public Material getMaterial() { return Material.DIAMOND; }

    @Override
    public void onClick(PlayerInteractEvent event, ItemStack item) {
        if (item == null || item.getType() != Material.DIAMOND) return;
        Player player = event.getPlayer();

        if (event.getAction().name().contains("RIGHT")) {
            activateRightHand(player);
        } else if (event.getAction().name().contains("LEFT")) {
            activateLeftHand(player);
        }
    }

    /**
     * Main Droite : bloque un joueur visé et le déplace avec le viseur.
     */
    private void activateRightHand(Player momo) {
        // Trouver le joueur visé (rayon 30 blocs)
        Player target = null;
        for (org.bukkit.entity.Entity e : momo.getNearbyEntities(30, 30, 30)) {
            if (!(e instanceof Player p)) continue;
            // Vérifier si Momo regarde vers le joueur (angle < 10°)
            Vector toTarget = p.getLocation().toVector().subtract(momo.getLocation().toVector()).normalize();
            double dot = momo.getLocation().getDirection().normalize().dot(toTarget);
            if (dot > 0.95) { // ~18° de tolérance
                target = p;
                break;
            }
        }

        if (target == null) {
            LangManager.get().send(DanDaDanLang.MOMO_PSYCHO_NO_TARGET, momo);
            return;
        }

        Player held = target;
        heldPlayer = held.getUniqueId();

        // Immobiliser la victime
        held.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 127, false, false));
        held.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100, 128, false, false));

        LangManager.get().send(DanDaDanLang.MOMO_PSYCHO_RIGHT, momo);

        // Suivre le viseur de Momo pendant 5s
        holdTask = Bukkit.getScheduler().runTaskTimer(Main.get(), () -> {
            if (!momo.isOnline() || !held.isOnline()) {
                releaseHeld(held);
                return;
            }
            // TP la victime 5 blocs devant Momo, en hauteur
            Location dest = momo.getEyeLocation().add(momo.getLocation().getDirection().multiply(5));
            dest.setY(dest.getY() + 2);
            held.teleport(dest);
        }, 0L, 2L); // Toutes les 2 ticks

        // Relâcher après 5s
        Bukkit.getScheduler().runTaskLater(Main.get(), () -> releaseHeld(held), 100L);

        setCooldown(300); // 5min
    }

    private void releaseHeld(Player held) {
        if (holdTask != null) {
            holdTask.cancel();
            holdTask = null;
        }
        heldPlayer = null;
        if (held != null && held.isOnline()) {
            held.removePotionEffect(PotionEffectType.SLOW);
            held.removePotionEffect(PotionEffectType.JUMP);
        }
    }

    /**
     * Main Gauche : lance 3 blocs de pierre.
     */
    private void activateLeftHand(Player momo) {
        LangManager.get().send(DanDaDanLang.MOMO_PSYCHO_LEFT, momo);

        for (int i = 0; i < 3; i++) {
            Bukkit.getScheduler().runTaskLater(Main.get(), () -> {
                if (!momo.isOnline()) return;
                Location spawnLoc = momo.getEyeLocation().add(0, 1.5, 0);
                FallingBlock block = momo.getWorld().spawnFallingBlock(
                        spawnLoc, Material.STONE, (byte) 0);
                block.setDropItem(false);
                block.setVelocity(momo.getLocation().getDirection().multiply(1.5));

                // Vérifier collision après 1.5s
                Bukkit.getScheduler().runTaskLater(Main.get(), () -> {
                    if (!block.isDead()) {
                        Location bLoc = block.getLocation();
                        block.remove();
                        // Créer structure de pierre temporaire
                        for (int dx = -1; dx <= 1; dx++) {
                            for (int dz = -1; dz <= 1; dz++) {
                                Location sl = bLoc.clone().add(dx, 0, dz);
                                if (sl.getBlock().getType() == Material.AIR) {
                                    sl.getBlock().setType(Material.STONE);
                                }
                            }
                        }
                        // Retirer après 3s
                        Bukkit.getScheduler().runTaskLater(Main.get(), () -> {
                            for (int dx = -1; dx <= 1; dx++) {
                                for (int dz = -1; dz <= 1; dz++) {
                                    Location sl = bLoc.clone().add(dx, 0, dz);
                                    if (sl.getBlock().getType() == Material.STONE) {
                                        sl.getBlock().setType(Material.AIR);
                                    }
                                }
                            }
                        }, 60L);
                    }
                }, 30L);
            }, i * 10L); // Décalage de 0.5s entre chaque bloc
        }

        setCooldown(480); // 8min
    }

    @Override
    public boolean onEnable(Player player) { return false; }
}
