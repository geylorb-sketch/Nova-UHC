package net.novauhc.dandadan.roles.momo;

import net.novaproject.novauhc.ability.template.CommandAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Map;

/**
 * Mémoire — Commande Momo (/ddd memoire)
 * 5x/partie. Obtient les coordonnées + direction du joueur le plus proche.
 */
public class MemoireCommand extends CommandAbility {

    public MemoireCommand() {
        setMaxUse(5);
        setCooldown(0);
    }

    @Override public String getName()       { return "Memoire"; }
    @Override public String getCommandKey() { return "memoire"; }

    @Override
    public boolean onCommand(Player player, String[] args) {
        Player nearest = findNearestPlayer(player);
        if (nearest == null) {
            LangManager.get().send(DanDaDanLang.MOMO_MEMOIRE_NONE, player);
            return false;
        }

        Location loc = nearest.getLocation();
        String coords = "§f" + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ();

        // Direction cardinale
        Vector dir = loc.toVector().subtract(player.getLocation().toVector()).normalize();
        String direction = getCardinalDirection(dir);

        LangManager.get().send(DanDaDanLang.MOMO_MEMOIRE_FOUND, player,
                Map.of("%coords%", coords, "%direction%", direction));
        return true;
    }

    private Player findNearestPlayer(Player player) {
        Player nearest = null;
        double minDist = Double.MAX_VALUE;
        for (UHCPlayer uhc : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
            Player p = uhc.getPlayer();
            if (p == null || p.equals(player)) continue;
            if (!p.getWorld().equals(player.getWorld())) continue;
            double dist = p.getLocation().distance(player.getLocation());
            if (dist < minDist) {
                minDist = dist;
                nearest = p;
            }
        }
        return nearest;
    }

    private String getCardinalDirection(Vector dir) {
        double angle = Math.toDegrees(Math.atan2(-dir.getX(), dir.getZ()));
        if (angle < 0) angle += 360;
        if (angle < 22.5 || angle >= 337.5) return "§aSud";
        if (angle < 67.5)  return "§aSud-Ouest";
        if (angle < 112.5) return "§aOuest";
        if (angle < 157.5) return "§aNord-Ouest";
        if (angle < 202.5) return "§aNord";
        if (angle < 247.5) return "§aNord-Est";
        if (angle < 292.5) return "§aEst";
        return "§aSud-Est";
    }
}
