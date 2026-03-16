package net.novauhc.dandadan.roles.caesar;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class NoLavaZone {
    private static final List<long[]> zones = new ArrayList<>(); // [x,y,z,radius,expiry]
    public static void placeZone(Location loc, long expiry) {
        zones.add(new long[]{loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 10, expiry});
    }
    public static boolean isInZone(Location loc) {
        long now = System.currentTimeMillis();
        zones.removeIf(z -> z[4] < now);
        return zones.stream().anyMatch(z -> Math.sqrt(Math.pow(loc.getBlockX()-z[0],2)+Math.pow(loc.getBlockZ()-z[2],2)) <= z[3]);
    }
}