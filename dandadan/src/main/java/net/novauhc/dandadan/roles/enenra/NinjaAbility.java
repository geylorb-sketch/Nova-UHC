package net.novauhc.dandadan.roles.enenra;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Ninja — Clic-Droit
 * 10 clones (ArmorStands invisibles avec le skin d'Enenra) se déplacent aléatoirement.
 * Si un joueur regarde un clone dans les yeux → Blindness 5s.
 * Durée 1 minute.
 */
public class NinjaAbility extends UseAbiliy {

    @AbilityVariable(lang = DanDaDanVarLang.class,
            nameKey = "ENENRA_NINJA_CLONE_COUNT_NAME",
            descKey  = "ENENRA_NINJA_CLONE_COUNT_DESC",
            type = VariableType.INTEGER)
    private int cloneCount = 10;

    @AbilityVariable(lang = DanDaDanVarLang.class,
            nameKey = "ENENRA_NINJA_DURATION_NAME",
            descKey  = "ENENRA_NINJA_DURATION_DESC",
            type = VariableType.TIME)
    private int duration = 60;

    @Override public String getName()       { return "Ninja"; }
    @Override public Material getMaterial() { return Material.INK_SACK; }

    @Override
    public boolean onEnable(Player enenra) {
        List<ArmorStand> clones = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < cloneCount; i++) {
            Location spawnLoc = enenra.getLocation().clone().add(
                    (rand.nextDouble() - 0.5) * 6, 0,
                    (rand.nextDouble() - 0.5) * 6);
            ArmorStand clone = (ArmorStand) enenra.getWorld()
                    .spawnEntity(spawnLoc, org.bukkit.entity.EntityType.ARMOR_STAND);
            clone.setVisible(false);
            clone.setGravity(true);
            clone.setSmall(false);
            clone.setCustomName("§5Clone d'Enenra");
            clone.setCustomNameVisible(true);
            clones.add(clone);
        }

        LangManager.get().send(DanDaDanLang.ENENRA_NINJA_ACTIVATED, enenra);

        // Mouvement aléatoire des clones chaque seconde
        var moveTaskId = new int[1];
        moveTaskId[0] = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(), () -> {
            clones.removeIf(c -> !c.isValid());
            clones.forEach(clone -> {
                Location cur = clone.getLocation();
                Location next = cur.clone().add(
                        (rand.nextDouble() - 0.5) * 4, 0,
                        (rand.nextDouble() - 0.5) * 4);
                clone.teleport(next);
            });

            // Vérifie si un joueur regarde un clone dans les yeux
            enenra.getWorld().getPlayers().stream()
                    .filter(p -> !p.equals(enenra))
                    .forEach(p -> {
                        for (ArmorStand clone : clones) {
                            if (p.getLocation().distance(clone.getLocation()) < 2.0) {
                                double dot = p.getLocation().getDirection()
                                        .dot(clone.getLocation().subtract(p.getLocation()).toVector().normalize());
                                if (dot > 0.95) { // ≈ droit dans les yeux
                                    p.addPotionEffect(new PotionEffect(
                                            PotionEffectType.BLINDNESS, 100, 0, false, true));
                                    break;
                                }
                            }
                        }
                    });
        }, 0L, 20L);

        // Nettoyage après duration
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            Main.get().getServer().getScheduler().cancelTask(moveTaskId[0]);
            clones.forEach(Entity::remove);
        }, 20L * duration);

        setCooldown(600);
        return true;
    }
}
