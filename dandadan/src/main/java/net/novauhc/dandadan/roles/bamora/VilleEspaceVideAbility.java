package net.novauhc.dandadan.roles.bamora;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLangExt4;
import net.novauhc.dandadan.particularity.EspaceVideManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Ville — Espace Vide de Bamora
 * Gitbook: TP joueurs 30 blocs dans dimension 1min.
 * - Bamora: Kaiju → Slaughter Mode
 * - Joueurs: Glowing permanent, zones bleues toutes les 5s pendant 3s (rayon 10 blocs)
 *   qui électrocutent et retirent un enchantement.
 * Cooldown: 15 min
 */
public class VilleEspaceVideAbility extends UseAbiliy {

    @AbilityVariable(lang = DanDaDanVarLangExt4.class, nameKey = "VILLE_RADIUS_NAME",
            descKey = "VILLE_RADIUS_DESC", type = VariableType.INTEGER)
    private int radius = 30;

    @AbilityVariable(lang = DanDaDanVarLangExt4.class, nameKey = "VILLE_DURATION_NAME",
            descKey = "VILLE_DURATION_DESC", type = VariableType.INTEGER)
    private int durationSec = 60;

    private final Random random = new Random();
    private final List<Player> playersInVille = new ArrayList<>();
    private BukkitTask zoneTask;

    @Override public String getName() { return "Ville"; }
    @Override public Material getMaterial() { return Material.ENDER_PEARL; }

    public VilleEspaceVideAbility() {
        setCooldown(900); // 15 min
    }

    @Override
    public boolean onEnable(Player player) {
        World videWorld = EspaceVideManager.get().getVideWorld();
        if (videWorld == null) {
            player.sendMessage("§c✘ La dimension Espace Vide n'est pas chargée.");
            return false;
        }

        Location center = player.getLocation();
        playersInVille.clear();

        Location spawnVide = videWorld.getSpawnLocation().clone().add(0, 1, 0);
        for (UHCPlayer uhc : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
            Player p = uhc.getPlayer();
            if (p == null) continue;
            if (p.getLocation().distance(center) <= radius) {
                p.teleport(spawnVide.clone().add((random.nextDouble() - 0.5) * 15, 0, (random.nextDouble() - 0.5) * 15));
                playersInVille.add(p);

                if (!p.equals(player)) {
                    // Glowing permanent
                    p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, durationSec * 20, 0, false, false));
                    p.sendMessage("§9§l✦ Ville ! §r§9Vous avez été aspiré dans la ville de Bamora !");
                }
            }
        }

        player.sendMessage("§6§l✦ Ville activée ! §r§6Slaughter Mode disponible !");

        // Zones bleues toutes les 5 secondes
        zoneTask = Bukkit.getScheduler().runTaskTimer(Main.get(), () -> {
            if (playersInVille.isEmpty()) return;
            Location zoneLoc = spawnVide.clone().add(
                    (random.nextDouble() - 0.5) * 30, 0, (random.nextDouble() - 0.5) * 30);

            // Annoncer la zone
            for (Player p : playersInVille) {
                if (p.isOnline() && p.getWorld().equals(videWorld)) {
                    p.sendMessage("§9⚡ Zone bleue apparue ! Éloignez-vous !");
                }
            }

            // Après 3 secondes, électrocuter
            Bukkit.getScheduler().runTaskLater(Main.get(), () -> {
                for (Player p : playersInVille) {
                    if (p.isOnline() && p.getWorld().equals(videWorld)) {
                        if (p.getLocation().distance(zoneLoc) <= 10) {
                            p.damage(4.0); // 2❤️
                            p.sendMessage("§c⚡ Électrocuté ! Un enchantement a été retiré !");
                            // Retirer un enchantement aléatoire sur une pièce d'armure
                            removeRandomEnchant(p);
                        }
                    }
                }
            }, 60L); // 3 secondes
        }, 100L, 100L); // toutes les 5 secondes

        setCooldown(900);

        // Timer pour ramener les joueurs
        Bukkit.getScheduler().runTaskLater(Main.get(), () -> {
            if (zoneTask != null) zoneTask.cancel();
            World mainWorld = Bukkit.getWorlds().get(0);
            Location mainSpawn = mainWorld.getSpawnLocation().clone().add(0, 1, 0);
            for (Player p : playersInVille) {
                if (p.isOnline() && p.getWorld().equals(videWorld)) {
                    p.teleport(mainSpawn.clone().add((random.nextDouble() - 0.5) * 20, 0, (random.nextDouble() - 0.5) * 20));
                    p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                    p.sendMessage("§a✦ La Ville s'est effondrée !");
                }
            }
            playersInVille.clear();
        }, 20L * durationSec);

        return true;
    }

    private void removeRandomEnchant(Player player) {
        org.bukkit.inventory.ItemStack[] armor = player.getInventory().getArmorContents();
        List<Integer> enchanted = new ArrayList<>();
        for (int i = 0; i < armor.length; i++) {
            if (armor[i] != null && !armor[i].getEnchantments().isEmpty()) enchanted.add(i);
        }
        if (enchanted.isEmpty()) return;
        int idx = enchanted.get(random.nextInt(enchanted.size()));
        org.bukkit.enchantments.Enchantment[] enchs = armor[idx].getEnchantments().keySet()
                .toArray(new org.bukkit.enchantments.Enchantment[0]);
        if (enchs.length > 0) {
            armor[idx].removeEnchantment(enchs[random.nextInt(enchs.length)]);
            player.getInventory().setArmorContents(armor);
        }
    }
}
