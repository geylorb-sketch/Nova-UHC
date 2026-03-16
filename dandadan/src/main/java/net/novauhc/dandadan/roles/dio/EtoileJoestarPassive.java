package net.novauhc.dandadan.roles.dio;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class EtoileJoestarPassive extends Ability {
    private final Set<UUID> alerted = new HashSet<>();
    @Override public String getName()       { return "Étoile des Joestar"; }
    @Override public Material getMaterial() { return null; }

    @Override public boolean onEnable(Player player) { return true; }

    @Override public void onSec(Player player) {
        player.getWorld().getNearbyEntities(player.getLocation(),100,100,100)
                .stream().filter(e->e instanceof Player&&!e.equals(player))
                .forEach(e -> {
                    UUID id = e.getUniqueId();
                    if (!alerted.contains(id)) {
                        alerted.add(id);
                        String msg = LangManager.get().get(DanDaDanLangExt3.DIO_JOESTAR_ALERT, player)
                                .replace("%player%", e.getName()); player.sendMessage(msg);
                    }
                });
        // Retire les alertes pour joueurs qui sont sortis du rayon
        alerted.removeIf(id -> {
            Player p = Bukkit.getPlayer(id);
            return p == null || p.getLocation().distance(player.getLocation()) > 100;
        });
    }
}