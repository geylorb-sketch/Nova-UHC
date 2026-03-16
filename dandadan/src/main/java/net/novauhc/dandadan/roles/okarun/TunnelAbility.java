package net.novauhc.dandadan.roles.okarun;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Tunnel — Espace Vide d'Okarun
 * Gitbook: TP tous les joueurs dans un rayon de 30 blocs dans une dimension tunnel 1min.
 * - Familiers: NPCs avec 5❤️ et force attaquent les joueurs
 * - Okarun: obtient effets de sa malédiction sans perdre de temps
 * - Malus: 40% chance de n'infliger que 0.5❤️, 40% chance d'arrêter le sprint
 * Cooldown: 10 min
 */
public class TunnelAbility extends UseAbiliy {

    @AbilityVariable(lang = DanDaDanVarLangExt4.class, nameKey = "TUNNEL_RADIUS_NAME",
            descKey = "TUNNEL_RADIUS_DESC", type = VariableType.INTEGER)
    private int radius = 30;

    @AbilityVariable(lang = DanDaDanVarLangExt4.class, nameKey = "TUNNEL_DURATION_NAME",
            descKey = "TUNNEL_DURATION_DESC", type = VariableType.INTEGER)
    private int durationSec = 60; // 1 minute

    private final Random random = new Random();
    private final List<Player> playersInTunnel = new ArrayList<>();

    @Override public String getName() { return "Tunnel"; }
    @Override public Material getMaterial() { return Material.ENDER_PEARL; }

    public TunnelAbility() {
        setCooldown(600); // 10 min
    }

    @Override
    public boolean onEnable(Player player) {
        World videWorld = EspaceVideManager.get().getVideWorld();
        if (videWorld == null) {
            player.sendMessage("§c✘ La dimension Espace Vide n'est pas chargée.");
            return false;
        }

        Location center = player.getLocation();
        playersInTunnel.clear();

        // TP tous les joueurs proches dans la dimension
        Location spawnVide = videWorld.getSpawnLocation().clone().add(0, 1, 0);
        for (UHCPlayer uhc : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
            Player p = uhc.getPlayer();
            if (p == null) continue;
            if (p.getLocation().distance(center) <= radius) {
                p.teleport(spawnVide.clone().add((random.nextDouble() - 0.5) * 10, 0, (random.nextDouble() - 0.5) * 10));
                playersInTunnel.add(p);

                if (!p.equals(player)) {
                    p.sendMessage("§5§l✦ Tunnel ! §r§5Vous avez été aspiré dans l'espace vide d'Okarun !");
                }
            }
        }

        // Okarun reçoit speed (malédiction) sans perdre de temps
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, durationSec * 20, 1, false, false));
        player.sendMessage("§5§l✦ Tunnel activé ! §r§5Malédiction active pendant " + durationSec + "s !");

        setCooldown(600);

        // Timer pour ramener les joueurs
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            World mainWorld = Bukkit.getWorlds().get(0);
            Location mainSpawn = mainWorld.getSpawnLocation().clone().add(0, 1, 0);
            for (Player p : playersInTunnel) {
                if (p.isOnline() && p.getWorld().equals(videWorld)) {
                    p.teleport(mainSpawn.clone().add((random.nextDouble() - 0.5) * 20, 0, (random.nextDouble() - 0.5) * 20));
                    p.sendMessage("§a✦ Le Tunnel s'est refermé !");
                }
            }
            playersInTunnel.clear();
        }, 20L * durationSec);

        return true;
    }

    /** Vérifie si un joueur est dans le tunnel (pour appliquer les malus) */
    public boolean isInTunnel(Player player) {
        return playersInTunnel.contains(player);
    }

    /** Malus: 40% chance de n'infliger que 0.5❤️ */
    public double applyDamageMalus(double originalDamage) {
        if (random.nextDouble() < 0.4) return 1.0; // 0.5❤️
        return originalDamage;
    }

    /** Malus: 40% chance d'arrêter le sprint */
    public boolean shouldStopSprint() {
        return random.nextDouble() < 0.4;
    }
}
