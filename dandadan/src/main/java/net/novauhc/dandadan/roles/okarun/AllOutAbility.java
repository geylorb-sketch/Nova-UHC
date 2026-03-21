package net.novauhc.dandadan.roles.okarun;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.ShortCooldownManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

/**
 * All-Out — Actif Okarun (Clic-Gauche sur BLAZE_POWDER pendant Malédiction)
 * Dash de 20 blocs. Collision = -2❤️ + doigt coupé 30s (changement d'item lent).
 * Retire 2 minutes de malédiction. Cooldown 2min.
 */
public class AllOutAbility extends Ability {

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "ALLOUT_DASH_DIST_NAME",
            descKey = "ALLOUT_DASH_DIST_DESC", type = VariableType.INTEGER)
    private int dashDistance = 20;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "ALLOUT_COLLISION_DMG_NAME",
            descKey = "ALLOUT_COLLISION_DMG_DESC", type = VariableType.DOUBLE)
    private double collisionDamage = 4.0; // 2❤️

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "ALLOUT_SLOW_DURATION_NAME",
            descKey = "ALLOUT_SLOW_DURATION_DESC", type = VariableType.TIME)
    private int slowDuration = 30; // doigt coupé

    @Override public String getName()       { return "All-Out"; }
    @Override public Material getMaterial() { return null; } // Pas d'item propre, utilise BLAZE_POWDER

    @Override
    public void onClick(PlayerInteractEvent event, ItemStack item) {
        if (item == null || item.getType() != Material.BLAZE_POWDER) return;
        if (!event.getAction().name().contains("LEFT")) return;
        tryUse(event.getPlayer());
    }

    @Override
    public boolean onEnable(Player player) {
        Vector dir = player.getLocation().getDirection().normalize();

        // Particules du dash
        for (int i = 1; i <= dashDistance; i++) {
            new ParticleBuilder(ParticleEffect.REDSTONE)
                    .setColor(java.awt.Color.MAGENTA)
                    .setLocation(player.getLocation().clone().add(dir.clone().multiply(i)))
                    .setAmount(3).display();
        }

        // Propulsion
        player.setVelocity(dir.clone().multiply(2.5));

        // Détection collision sur les joueurs proches de la destination
        org.bukkit.Location dest = player.getLocation().clone().add(dir.clone().multiply(dashDistance));
        player.getWorld().getNearbyEntities(dest, 3, 3, 3).stream()
                .filter(e -> e instanceof Player && !e.equals(player))
                .forEach(e -> {
                    Player victim = (Player) e;
                    victim.damage(collisionDamage, player);
                    // Doigt coupé : slowness pour simuler le changement d'item lent
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * slowDuration, 0, false, true));
                    ShortCooldownManager.put(victim, "DoigtCoupe", slowDuration * 1000L);
                    LangManager.get().send(DanDaDanLang.OKARUN_ALLOUT_HIT, victim.getPlayer() != null ? victim.getPlayer() : player, java.util.Map.of("%duration%", String.valueOf(slowDuration)));
                });

        LangManager.get().send(DanDaDanLang.OKARUN_ALLOUT_DASH, player, java.util.Map.of("%distance%", String.valueOf(dashDistance)));
        setCooldown(120); // 2min
        return true;
    }
}
