package net.novauhc.dandadan.roles.kinta;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Rocket Punch — Clic-Droit
 * 1er clic : lance l'épée, dégâts 1❤ sur le joueur visé, cercle de particules 10x10.
 * 2ème clic (si cible encore dans la zone) : TP dedans pour un combo.
 */
public class RocketPunchAbility extends UseAbiliy {

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "KINTA_ROCKET_PUNCH_DAMAGE_NAME", descKey = "KINTA_ROCKET_PUNCH_DAMAGE_DESC", type = VariableType.DOUBLE)
    private double damage = 2.0; // 1❤

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "KINTA_ROCKET_PUNCH_ZONE_NAME", descKey = "KINTA_ROCKET_PUNCH_ZONE_DESC", type = VariableType.INTEGER)
    private int zoneRadius = 5;

    // État : phase 1 = lance l'épée, phase 2 = TP
    private final Map<UUID, Location> activeZones = new HashMap<>();
    private final Map<UUID, UUID> lockedTargets = new HashMap<>();

    @Override public String getName()       { return "Rocket Punch"; }
    @Override public Material getMaterial() { return Material.DIAMOND_SWORD; }

    @Override
    public boolean onEnable(Player kinta) {
        UUID kintaId = kinta.getUniqueId();

        if (activeZones.containsKey(kintaId)) {
            // Phase 2 : TP si la cible est encore dans la zone
            Location zone = activeZones.get(kintaId);
            UUID targetId = lockedTargets.get(kintaId);

            Player target = targetId != null ?
                    kinta.getServer().getPlayer(targetId) : null;

            if (target != null && target.isOnline()
                    && target.getLocation().distance(zone) <= zoneRadius) {
                kinta.teleport(target.getLocation().add(1, 0, 0));
                LangManager.get().send(DanDaDanLang.KINTA_ROCKET_PUNCH_TELEPORT, kinta);
            } else {
                LangManager.get().send(DanDaDanLang.KINTA_ROCKET_PUNCH_NO_TARGET, kinta);
            }

            activeZones.remove(kintaId);
            lockedTargets.remove(kintaId);
            return true;
        }

        // Phase 1 : cherche cible
        Player target = getNearestTarget(kinta, 15);
        if (target == null) { kinta.sendMessage("§c✘ Aucune cible à portée."); return false; }

        target.damage(damage, kinta);
        Location zone = target.getLocation().clone();
        activeZones.put(kintaId, zone);
        lockedTargets.put(kintaId, target.getUniqueId());

        drawZoneCircle(zone);
        LangManager.get().send(DanDaDanLang.KINTA_ROCKET_PUNCH_THROWN, kinta);

        // Expire après 10s si pas de 2ème clic
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            activeZones.remove(kintaId);
            lockedTargets.remove(kintaId);
        }, 200L);

        setCooldown(0); // pas de cooldown entre phase 1 et 2
        return true;
    }

    private void drawZoneCircle(Location center) {
        for (double a = 0; a < 2 * Math.PI; a += Math.PI / 20) {
            new ParticleBuilder(ParticleEffect.REDSTONE)
                    .setColor(Color.ORANGE)
                    .setLocation(center.clone().add(Math.cos(a) * zoneRadius, 1, Math.sin(a) * zoneRadius))
                    .setAmount(2).display();
        }
    }

    private Player getNearestTarget(Player kinta, double range) {
        return kinta.getWorld().getNearbyEntities(kinta.getLocation(), range, range, range)
                .stream().filter(e -> e instanceof Player && !e.equals(kinta))
                .map(e -> (Player) e)
                .min((a, b) -> Double.compare(
                        a.getLocation().distance(kinta.getLocation()),
                        b.getLocation().distance(kinta.getLocation())))
                .orElse(null);
    }
}
