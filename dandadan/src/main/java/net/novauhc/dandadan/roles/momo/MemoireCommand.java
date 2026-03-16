package net.novauhc.dandadan.roles.momo;

import net.novaproject.novauhc.ability.CommandAbility;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Mémoire — Commande Momo
 * Gitbook: /ddd memoire, 5x/partie. Obtient les coordonnées du joueur le plus proche + flèche.
 */
public class MemoireCommand extends CommandAbility {

    public MemoireCommand() { setMaxUse(5); setCooldown(0); }

    @Override public String getName() { return "Mémoire"; }
    @Override public String getCommandKey() { return "memoire"; }

    @Override
    public boolean onCommand(Player player, String[] args) {
        Location loc = player.getLocation();
        Player closest = null;
        double minDist = Double.MAX_VALUE;

        for (UHCPlayer uhc : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
            Player p = uhc.getPlayer();
            if (p == null || p.equals(player) || !p.getWorld().equals(player.getWorld())) continue;
            double dist = p.getLocation().distance(loc);
            if (dist < minDist) {
                minDist = dist;
                closest = p;
            }
        }

        if (closest == null) {
            player.sendMessage("§c✘ Aucun joueur détecté à proximité.");
            return false;
        }

        Location cLoc = closest.getLocation();
        String arrow = getArrowDirection(loc, cLoc, loc.getYaw());

        player.sendMessage("§d§l✦ Mémoire §r§d— Joueur le plus proche :");
        player.sendMessage("§f  " + arrow + " §e" + String.format("%.0f", minDist) + " blocs");
        player.sendMessage("§f  X: §e" + cLoc.getBlockX() + " §fY: §e" + cLoc.getBlockY() + " §fZ: §e" + cLoc.getBlockZ());

        return true;
    }

    private String getArrowDirection(Location from, Location to, float yaw) {
        double dx = to.getX() - from.getX();
        double dz = to.getZ() - from.getZ();
        double angle = Math.toDegrees(Math.atan2(-dx, dz));
        double relative = ((angle - yaw) % 360 + 360) % 360;

        if (relative < 22.5 || relative >= 337.5) return "⬆";
        if (relative < 67.5) return "⬈";
        if (relative < 112.5) return "➡";
        if (relative < 157.5) return "⬊";
        if (relative < 202.5) return "⬇";
        if (relative < 247.5) return "⬋";
        if (relative < 292.5) return "⬅";
        return "⬉";
    }
}
