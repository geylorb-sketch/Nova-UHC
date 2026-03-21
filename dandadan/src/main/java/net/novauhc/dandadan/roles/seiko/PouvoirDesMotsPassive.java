package net.novauhc.dandadan.roles.seiko;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * Pouvoir des mots — Passif Seiko
 * Renomme ton epee pour un passif :
 *   Nessie: 5% coup crit x1.5
 *   Ongle: 5% fleche + casse armure 1s
 *   Harisen: 5% bloque pouvoirs adverses 1min
 */
public class PouvoirDesMotsPassive extends PassiveAbility {

    private final Random random = new Random();
    private final Set<UUID> blockedPlayers = new HashSet<>();

    @Override public String getName() { return "Pouvoir des mots"; }

    @Override
    public boolean onEnable(Player player) {
        return false;
    }


    public void onHit(Player seiko, Player victim, EntityDamageByEntityEvent event) {
        ItemStack hand = seiko.getItemInHand();
        if (hand == null || !hand.hasItemMeta() || !hand.getItemMeta().hasDisplayName()) return;
        String name = hand.getItemMeta().getDisplayName().toLowerCase();

        if (random.nextDouble() >= 0.05) return; // 5% chance

        if (name.contains("nessie")) {
            event.setDamage(event.getDamage() * 1.5);
            LangManager.get().send(DanDaDanLang.SEIKO_MOTS_NESSIE, seiko);
        } else if (name.contains("ongle")) {
            // Fleche bonus
            Arrow arrow = seiko.launchProjectile(Arrow.class);
            arrow.setVelocity(seiko.getLocation().getDirection().multiply(2.0));
            LangManager.get().send(DanDaDanLang.SEIKO_MOTS_ONGLE, seiko);
        } else if (name.contains("harisen")) {
            blockedPlayers.add(victim.getUniqueId());
            LangManager.get().send(DanDaDanLang.SEIKO_MOTS_HARISEN, seiko);
            // Retirer le blocage apres 1min
            org.bukkit.Bukkit.getScheduler().runTaskLater(
                    net.novaproject.novauhc.Main.get(), () -> blockedPlayers.remove(victim.getUniqueId()), 60 * 20L);
        }
    }

    public boolean isBlocked(UUID uuid) { return blockedPlayers.contains(uuid); }
}
