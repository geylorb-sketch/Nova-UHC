package net.novaproject.novauhc.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class LongCooldownManager {

    private static final Map<UUID, Map<String, Long>> _COOLDOWNS;

    static {
        _COOLDOWNS = new HashMap<>();
    }


    public static boolean put(Player player, String key, long duration) {
        return put(player.getUniqueId(), key, duration);
    }


    public static boolean put(UUID uuid, String key, long duration) {
        if (_COOLDOWNS.containsKey(uuid)) {
            Map<String, Long> playerCooldowns = _COOLDOWNS.get(uuid);
            if (!playerCooldowns.containsKey(key)) {
                playerCooldowns.put(key, System.currentTimeMillis() + duration);
            } else {
                return false;
            }
        } else {
            Map<String, Long> playerCooldowns = new HashMap<>();
            playerCooldowns.put(key, System.currentTimeMillis() + duration);
            _COOLDOWNS.put(uuid, playerCooldowns);
        }
        return true;
    }


    public static long get(Player player, String key) {
        return get(player.getUniqueId(), key);
    }


    public static long get(UUID uuid, String key) {
        if (_COOLDOWNS.containsKey(uuid)) {
            Map<String, Long> playerCooldowns = _COOLDOWNS.get(uuid);
            if (playerCooldowns.containsKey(key)) {
                long durationLeft = playerCooldowns.get(key);
                if (durationLeft > System.currentTimeMillis()) {
                    return durationLeft - System.currentTimeMillis();
                } else {
                    playerCooldowns.remove(key);
                    if (playerCooldowns.isEmpty()) {
                        _COOLDOWNS.remove(uuid);
                    }
                    return -1;
                }
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }


    public static long remove(Player player, String key) {
        return remove(player.getUniqueId(), key);
    }


    public static long remove(UUID uuid, String key) {
        if (_COOLDOWNS.containsKey(uuid)) {
            Map<String, Long> playerCooldowns = _COOLDOWNS.get(uuid);
            if (playerCooldowns.containsKey(key)) {
                long durationLeft = playerCooldowns.remove(key);
                if (durationLeft > System.currentTimeMillis()) {
                    return durationLeft - System.currentTimeMillis();
                } else {
                    playerCooldowns.remove(key);
                    if (playerCooldowns.isEmpty()) {
                        _COOLDOWNS.remove(uuid);
                    }
                    return -1;
                }
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }
}
