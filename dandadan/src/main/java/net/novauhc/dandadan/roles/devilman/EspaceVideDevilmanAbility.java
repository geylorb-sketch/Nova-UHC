package net.novauhc.dandadan.roles.devilman;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novauhc.dandadan.particularity.EspaceVideManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Enfer — Espace Vide de Devilman
 * TP joueurs dans un rayon de 30 blocs dans dimension pendant 1min.
 */
public class EspaceVideDevilmanAbility extends UseAbiliy {

    private final Random random = new Random();
    private final List<Player> teleported = new ArrayList<>();

    @Override public String getName() { return "Enfer"; }
    @Override public Material getMaterial() { return Material.ENDER_PEARL; }

    public EspaceVideDevilmanAbility() { setCooldown(600); }

    @Override
    public boolean onEnable(Player player) {
        World videWorld = EspaceVideManager.get().getVideWorld();
        if (videWorld == null) { player.sendMessage("§c✘ Dimension non chargée."); return false; }

        Location center = player.getLocation();
        Location spawn = videWorld.getSpawnLocation().clone().add(0, 1, 0);
        teleported.clear();

        for (UHCPlayer uhc : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
            Player p = uhc.getPlayer();
            if (p == null || p.getLocation().distance(center) > 30) continue;
            p.teleport(spawn.clone().add((random.nextDouble() - 0.5) * 15, 0, (random.nextDouble() - 0.5) * 15));
            teleported.add(p);
            if (!p.equals(player)) p.sendMessage("§5✦ Aspiré dans l'espace vide — Enfer !");
        }

        player.sendMessage("§5§l✦ Enfer activé !");
        setCooldown(600);

        Bukkit.getScheduler().runTaskLater(Main.get(), () -> {
            World mainWorld = Bukkit.getWorlds().get(0);
            Location mainSpawn = mainWorld.getSpawnLocation().clone().add(0, 1, 0);
            for (Player p : teleported) {
                if (p.isOnline() && p.getWorld().equals(videWorld))
                    p.teleport(mainSpawn.clone().add((random.nextDouble() - 0.5) * 20, 0, (random.nextDouble() - 0.5) * 20));
            }
            teleported.clear();
        }, 1200L);

        return true;
    }
}
