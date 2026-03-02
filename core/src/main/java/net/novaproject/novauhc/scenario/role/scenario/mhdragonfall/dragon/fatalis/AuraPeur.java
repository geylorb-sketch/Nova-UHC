package net.novaproject.novauhc.scenario.role.scenario.mhdragonfall.dragon.fatalis;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.scenario.ScenarioManager;
import net.novaproject.novauhc.scenario.role.ScenarioRole;
import net.novaproject.novauhc.scenario.role.scenario.mhdragonfall.DragonFall;
import net.novaproject.novauhc.scenario.role.scenario.mhdragonfall.DragonRole;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.uhcteam.UHCTeam;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;

public class AuraPeur extends Ability {

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "AURAPEUR_VAR_RADIUS_NAME", descKey = "AURAPEUR_VAR_RADIUS_DESC", type = VariableType.DOUBLE)
    private final double RADIUS = 4.0;
    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "AURAPEUR_VAR_RESIST_DEBUFF_PERCENT_NAME", descKey = "AURAPEUR_VAR_RESIST_DEBUFF_PERCENT_DESC", type = VariableType.PERCENTAGE)
    private final double RESIST_DEBUFF_PERCENT = 0.05;

    private static final int PARTICLES = 6;

    private final double particleAngle = 0;
    private BukkitRunnable particleTask = null;

    private final ScenarioRole<DragonRole> scenarioRole = ScenarioManager.get().getScenario(DragonFall.class);
    private final Map<Player, Integer> lostResistance = new HashMap<>();

    @Override
    public String getName() {
        return "Aura de Peur";
    }

    @Override
    public Material getMaterial() {
        return null;
    }

    @Override
    public boolean onEnable(Player player) {
        return false;
    }

    @Override
    public void onSec(Player player) {
        if(particleTask == null){

            particleTask = new BukkitRunnable() {
                double angle = 0;

                @Override
                public void run() {
                    if (player == null || !player.isOnline()) {
                        this.cancel();
                        return;
                    }

                    angle += Math.PI / 60;
                    double radius = RADIUS;

                    for (int i = 0; i < PARTICLES; i++) {
                        double particleAngle = 2 * Math.PI * i / PARTICLES + angle;
                        double x = radius * Math.cos(particleAngle);
                        double z = radius * Math.sin(particleAngle);
                        new ParticleBuilder(ParticleEffect.REDSTONE)
                                .setColor(Color.BLACK)
                                .setLocation(player.getLocation().clone().add(x, 0.4, z))
                                .display();
                    }

                    for (int i = 0; i < PARTICLES; i++) {
                        double particleAngle = 2 * Math.PI * i / PARTICLES + angle + Math.PI / PARTICLES;
                        double x = radius * Math.cos(particleAngle);
                        double z = radius * Math.sin(particleAngle);
                        new ParticleBuilder(ParticleEffect.REDSTONE)
                                .setColor(new Color(50, 50, 50))
                                .setLocation(player.getLocation().clone().add(x, 0.6, z))
                                .display();
                    }
                }
            };
            particleTask.runTaskTimer(Main.get(), 1L, 1L);
            return;
        }
        UHCPlayer owner = getUHCPlayer(player);
        if (owner == null) return;

        Set<Player> currentlyInside = new HashSet<>();

        for (Entity entity : player.getNearbyEntities(RADIUS, RADIUS, RADIUS)) {
            if (!(entity instanceof Player target)) continue;

            UHCPlayer targetUhc = UHCPlayerManager.get().getPlayer(target);
            if (targetUhc == null) continue;

            if (owner.getTeam().isPresent()) {
                UHCTeam team = owner.getTeam().get();
                if(team.getPlayers().contains(targetUhc)) continue;
            }

            currentlyInside.add(target);

            if (!lostResistance.containsKey(target)) {
                applyDebuff(targetUhc,scenarioRole.getRoleByUHCPlayer(targetUhc));
            }
        }

        lostResistance.keySet().removeIf(target -> {
            if (!currentlyInside.contains(target)) {
                UHCPlayer targetUhc = UHCPlayerManager.get().getPlayer(target);
                if (targetUhc != null) {
                    removeDebuff(targetUhc,scenarioRole.getRoleByUHCPlayer(targetUhc));
                }
                return true;
            }
            return false;
        });
    }

    private void applyDebuff(UHCPlayer target,DragonRole role) {
        if (role == null) return;

        int loss = (int) Math.round(role.getCurrentResistance() * RESIST_DEBUFF_PERCENT);
        if (loss <= 0) return;

        lostResistance.put(target.getPlayer(), loss);
        role.setCurrentResistance(role.getCurrentResistance() - loss);
    }

    private void removeDebuff(UHCPlayer target,DragonRole role) {
        if (role == null) return;

        Integer loss = lostResistance.remove(target.getPlayer());
        if (loss != null) {
            role.setCurrentResistance(role.getCurrentResistance() + loss);
        }
    }
}