package net.novaproject.novauhc.scenario.role.scenario.mhdragonfall.dragon.fatalis;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.template.UseAbiliy;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.scenario.ScenarioManager;
import net.novaproject.novauhc.scenario.role.ScenarioRole;
import net.novaproject.novauhc.scenario.role.scenario.mhdragonfall.DragonFall;
import net.novaproject.novauhc.scenario.role.scenario.mhdragonfall.DragonRole;
import net.novaproject.novauhc.scenario.role.scenario.mhdragonfall.ElementType;
import net.novaproject.novauhc.scenario.role.scenario.mhdragonfall.status.StatusEffect;
import net.novaproject.novauhc.scenario.role.scenario.mhdragonfall.status.StatusFactory;
import net.novaproject.novauhc.scenario.role.scenario.mhdragonfall.status.StatusManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;

public class MerFlamme extends UseAbiliy {

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "MERFLAMME_VAR_DAMAGE_NAME", descKey = "MERFLAMME_VAR_DAMAGE_DESC", type = VariableType.DOUBLE)
    private final int DAMAGE = 1000;

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "MERFLAMME_VAR_FIRE_DURATION_NAME", descKey = "MERFLAMME_VAR_FIRE_DURATION_DESC", type = VariableType.INTEGER)
    private final int FIRE_DURATION = 15;

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "MERFLAMME_VAR_RADIUS_NAME", descKey = "MERFLAMME_VAR_RADIUS_DESC", type = VariableType.DOUBLE)
    private final double RADIUS = 25;

    private static final int PARTICLES = 60;
    private static final int TICKS_DURATION = 50;
    private static final double HEIGHT = 1.0;

    public MerFlamme(){
        setCooldown(300);
        setMaxUse(5);
    }

    @Override
    public String getName() {
        return "Inferno Wave";
    }

    @Override
    public Material getMaterial() {
        return Material.BLAZE_POWDER;
    }

    @Override
    public boolean onEnable(Player caster) {
        UHCPlayer casterUhc = getUHCPlayer(caster);
        ScenarioRole<DragonRole> scenario = ScenarioManager.get().getScenario(DragonFall.class);
        DragonRole casterRole = scenario.getRoleByUHCPlayer(casterUhc);

        Location center = caster.getLocation();

        
        Set<UUID> hitPlayers = new HashSet<>();

        Random random = new Random();

        new BukkitRunnable() {
            int tick = 0;

            @Override
            public void run() {
                if (tick++ > TICKS_DURATION) {
                    cancel();
                    return;
                }

                double currentRadius = RADIUS * ((double) tick / TICKS_DURATION);

                
                for (int i = 0; i < PARTICLES; i++) {
                    double angle = 2 * Math.PI * i / PARTICLES;
                    double x = currentRadius * Math.cos(angle);
                    double z = currentRadius * Math.sin(angle);
                    Location particleLoc = center.clone().add(x, HEIGHT, z);
                    new ParticleBuilder(ParticleEffect.REDSTONE)
                            .setColor(Color.BLACK)
                            .setLocation(particleLoc)
                            .display();
                }

                
                for (Player target : center.getWorld().getPlayers()) {
                    
                    if (hitPlayers.contains(target.getUniqueId())) {
                        continue;
                    }

                    
                    if (target.getUniqueId().equals(caster.getUniqueId())) {
                        continue;
                    }

                    if (target.getLocation().distance(center) <= currentRadius + 1) {
                        
                        hitPlayers.add(target.getUniqueId());

                        UHCPlayer targetUhc = getUHCPlayer(target);
                        DragonRole targetRole = scenario.getRoleByUHCPlayer(targetUhc);

                        
                        targetRole.damage(DAMAGE, caster);

                        
                        StatusEffect effect = StatusFactory.create(
                                ElementType.FIRE,
                                target,
                                FIRE_DURATION,
                                targetRole
                        );
                        if (effect != null) StatusManager.get().applyEffect(target, effect);

                        target.playSound(target.getLocation(), Sound.BLAZE_HIT, 1f, 1.2f);
                    }
                }

                for (int dx = (int) -currentRadius; dx <= currentRadius; dx++) {
                    for (int dz = (int) -currentRadius; dz <= currentRadius; dz++) {
                        double distance = Math.sqrt(dx * dx + dz * dz);

                        if (distance <= currentRadius) {
                            double chance = 0.7 + 0.3 * (1 - distance / currentRadius);

                            for (int yOffset = -3; yOffset <= 3; yOffset++) {
                                Location blockLoc = center.clone().add(dx, yOffset, dz);
                                if (blockLoc.getBlock().getType().isSolid() && random.nextDouble() < chance) {
                                    blockLoc.getBlock().setType(Material.NETHERRACK);
                                }
                            }
                        }
                    }
                }

            }
        }.runTaskTimer(Main.get(), 0L, 1L);

        return true;
    }
}