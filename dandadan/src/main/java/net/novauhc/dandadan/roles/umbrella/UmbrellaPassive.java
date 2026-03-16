package net.novauhc.dandadan.roles.umbrella;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.concurrent.ThreadLocalRandom;

public class UmbrellaPassive extends Ability {

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "UMBRELLA_PASSIVE_CHANCE_NAME", descKey = "UMBRELLA_PASSIVE_CHANCE_DESC", type = VariableType.PERCENTAGE)
    private int triggerChancePct = 20;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "UMBRELLA_PASSIVE_KB_RANGE_NAME", descKey = "UMBRELLA_PASSIVE_KB_RANGE_DESC", type = VariableType.INTEGER)
    private int knockbackRange = 10;

    @Override public String getName()       { return "Umbrella Boy"; }
    @Override public Material getMaterial() { return null; }

    @Override public boolean onEnable(Player player) { return true; }

    @Override public void onConsume(org.bukkit.event.player.PlayerItemConsumeEvent event) {
        if (event.getItem().getType() != Material.GOLDEN_APPLE) return;
        if (ThreadLocalRandom.current().nextDouble() >= triggerChancePct / 100.0) return;
        // Traînée de particules devant le joueur
        Player player = (Player) event.getPlayer();
        Vector dir = player.getLocation().getDirection().normalize();
        for (int i = 1; i <= knockbackRange; i++) {
            Location pt = player.getLocation().clone().add(dir.clone().multiply(i));
            new ParticleBuilder(ParticleEffect.REDSTONE)
                    .setColor(java.awt.Color.CYAN).setLocation(pt).setAmount(2).display();
            // Vole absorption aux joueurs proches
            player.getWorld().getNearbyEntities(pt, 1, 1, 1).stream()
                    .filter(e -> e instanceof Player && !e.equals(player)).findFirst()
                    .ifPresent(e -> {
                        // Transfert absorption simulé
                        player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + 1));
                    });
        }
    }
}