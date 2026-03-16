package net.novauhc.dandadan.roles.seiko;

import net.novaproject.novauhc.ability.PassiveAbility;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 * Le pouvoir des mots — Passif Seiko
 * Gitbook: En renommant son épée, Seiko acquiert des passifs:
 *  - "Nessie": 5%/coup → critique qui passe à travers la résistance, 1.5x dégâts
 *  - "Ongle": 5%/coup → lance une flèche, casse une pièce d'armure 1s
 *  - "Harisen": 5%/coup → empêche le joueur d'utiliser ses pouvoirs sur Seiko 1min
 */
public class PouvoirDesMotsPassive extends PassiveAbility {

    private final Random random = new Random();

    @Override public String getName() { return "Pouvoir des mots"; }

    @Override
    public void onAttack(UHCPlayer victim, EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker)) return;
        Player target = victim.getPlayer();
        if (target == null) return;

        ItemStack hand = attacker.getItemInHand();
        if (hand == null || !hand.hasItemMeta() || !hand.getItemMeta().hasDisplayName()) return;

        String swordName = hand.getItemMeta().getDisplayName().toLowerCase()
                .replace("§", "").replaceAll("[^a-z]", "");

        if (random.nextDouble() > 0.05) return; // 5% chance

        if (swordName.contains("nessie")) {
            // Critique passant à travers la résistance, 1.5x dégâts
            event.setDamage(event.getDamage() * 1.5);
            target.removePotionEffect(org.bukkit.potion.PotionEffectType.DAMAGE_RESISTANCE);
            attacker.sendMessage("§b§l✦ Nessie ! §r§bCoup critique x1.5 !");

        } else if (swordName.contains("ongle")) {
            // Lancer une flèche + casser une pièce d'armure 1s
            Arrow arrow = attacker.launchProjectile(Arrow.class);
            arrow.setVelocity(attacker.getLocation().getDirection().multiply(2));
            attacker.sendMessage("§e§l✦ Ongle ! §r§eFlèche lancée + armure brisée 1s !");
            // Casser temporairement une pièce d'armure
            ItemStack[] armor = target.getInventory().getArmorContents();
            int idx = random.nextInt(4);
            if (armor[idx] != null) {
                ItemStack saved = armor[idx].clone();
                armor[idx] = null;
                target.getInventory().setArmorContents(armor);
                net.novaproject.novauhc.Main.get().getServer().getScheduler().runTaskLater(
                        net.novaproject.novauhc.Main.get(), () -> {
                            ItemStack[] a = target.getInventory().getArmorContents();
                            if (a[idx] == null) { a[idx] = saved; target.getInventory().setArmorContents(a); }
                        }, 20L); // 1 seconde
            }

        } else if (swordName.contains("harisen")) {
            // Empêche le joueur d'utiliser ses pouvoirs sur Seiko 1min
            net.novaproject.novauhc.utils.ShortCooldownManager.put(target, "HarisenBlock_" + attacker.getUniqueId(), 60000);
            attacker.sendMessage("§d§l✦ Harisen ! §r§d" + target.getName() + " ne peut plus utiliser ses pouvoirs sur toi 1min !");
            target.sendMessage("§c✦ Harisen ! Vos pouvoirs sont bloqués contre Seiko pendant 1min !");
        }
    }

    @Override
    public boolean onEnable(Player player) {
        return false; // Passif pur via onAttack
    }
}
