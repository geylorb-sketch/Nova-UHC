package net.novauhc.dandadan.roles.kashimoto;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.ShortCooldownManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Esprit Protecteur — Clic-Gauche → invoque l'esprit (+20% force).
 *                     Clic-Droit  → envoie l'esprit sur un joueur (empêche de courir ttes 5s pendant 1min).
 */
public class EspritProtecteurAbility extends Ability {

    @AbilityVariable(lang = DanDaDanVarLang.class,
            nameKey = "KASHIMOTO_ESPRIT_STRENGTH_NAME",
            descKey  = "KASHIMOTO_ESPRIT_STRENGTH_DESC",
            type = VariableType.PERCENTAGE)
    private double strengthPct = 0.20;

    private boolean espritSummoned = false;
    private Location espritLocation;
    // cible UUID → timestamp d'expiration
    private final Map<UUID, Long> cursedTargets = new HashMap<>();

    @Override public String getName()       { return "Esprit Protecteur"; }
    @Override public Material getMaterial() { return Material.SKULL_ITEM; }

    @Override
    public boolean onEnable(Player player) { return true; }

    @Override
    public void onClick(PlayerInteractEvent event, ItemStack item) {
        if (item == null || item.getType() != Material.SKULL_ITEM) return;
        Player player = event.getPlayer();

        if (event.getAction().name().contains("LEFT")) {
            // Invoque l'esprit à côté de Kashimoto
            if (!espritSummoned) {
                espritSummoned = true;
                espritLocation = player.getLocation().clone().add(1.5, 0, 0);
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 180, 0, false, false));
                LangManager.get().send(DanDaDanLang.KASHIMOTO_ESPRIT_ACTIVATED, player);

                // Particules autour de l'esprit
                var taskId = new int[1];
                taskId[0] = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(), () -> {
                    if (!espritSummoned || espritLocation == null) {
                        Main.get().getServer().getScheduler().cancelTask(taskId[0]); return;
                    }
                    new ParticleBuilder(ParticleEffect.REDSTONE)
                            .setColor(Color.CYAN)
                            .setLocation(espritLocation).setAmount(5).display();
                }, 0L, 5L);

                Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
                    espritSummoned = false;
                    espritLocation = null;
                    player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                    Main.get().getServer().getScheduler().cancelTask(taskId[0]);
                }, 20L * 180);

                ShortCooldownWrapper.set(player, "EspritInvoque", 180_000L);
            }
        } else if (event.getAction().name().contains("RIGHT") && espritSummoned) {
            // Envoie l'esprit sur le joueur le plus proche
            Player target = getNearestTarget(player, 15);
            if (target == null) { player.sendMessage("§c✘ Aucune cible."); return; }
            espritSummoned = false;
            espritLocation = null;
            player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);

            cursedTargets.put(target.getUniqueId(), System.currentTimeMillis() + 60_000L);
            ShortCooldownWrapper.set(player, "EspritEnvoi", 180_000L);
        }
    }

    @Override
    public void onSec(Player player) {
        long now = System.currentTimeMillis();
        // Applique le slow toutes les 5s aux cibles maudites
        cursedTargets.entrySet().removeIf(e -> e.getValue() < now);
        cursedTargets.forEach((uuid, expiry) -> {
            Player target = Main.get().getServer().getPlayer(uuid);
            if (target != null && target.isOnline()) {
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 2, false, false));
            }
        });
    }

    private Player getNearestTarget(Player p, double range) {
        return p.getWorld().getNearbyEntities(p.getLocation(), range, range, range)
                .stream().filter(e -> e instanceof Player && !e.equals(p))
                .map(e -> (Player) e)
                .min((a, b) -> Double.compare(
                        a.getLocation().distance(p.getLocation()),
                        b.getLocation().distance(p.getLocation())))
                .orElse(null);
    }

    // Wrapper minimal pour éviter import circulaire
    private static class ShortCooldownWrapper {
        static void set(Player p, String key, long ms) {
            ShortCooldownManager.put(p, key, ms);
        }
    }
}
