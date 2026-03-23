package net.novauhc.dandadan.roles.kashimoto;

import net.novaproject.novauhc.ability.template.UseAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

/**
 * Flamme de glace — Actif Kashimoto (Clic-Droit, ICE)
 * Enflamme le joueur vise (pas de degats). Armure perd 1 niveau d'enchant
 * + 1.5x durabilite. Glowing 1min. CD 8min.
 */
public class FlammeGlaceAbility extends UseAbility {

    @Override public String getName()       { return "Flamme de glace"; }
    @Override public Material getMaterial() { return Material.ICE; }

    @Override
    public boolean onEnable(Player player) {
        Player target = getTarget(player, 15);
        if (target == null) {
            LangManager.get().send(DanDaDanLang.KASHIMOTO_NO_TARGET, player);
            return false;
        }

        target.setFireTicks(60 * 20); // Enflamme 1min (visuellement)
        target.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 60 * 20, 0, false, true));

        // Retirer 1 niveau d'enchant sur une piece d'armure aleatoire
        ItemStack[] armor = target.getInventory().getArmorContents();
        for (int i = 0; i < armor.length; i++) {
            if (armor[i] != null && !armor[i].getEnchantments().isEmpty()) {
                Map.Entry<org.bukkit.enchantments.Enchantment, Integer> entry =
                        armor[i].getEnchantments().entrySet().iterator().next();
                if (entry.getValue() > 1) {
                    armor[i].addEnchantment(entry.getKey(), entry.getValue() - 1);
                } else {
                    armor[i].removeEnchantment(entry.getKey());
                }
                break;
            }
        }
        target.getInventory().setArmorContents(armor);

        LangManager.get().send(DanDaDanLang.KASHIMOTO_FLAMME_ON, player);
        setCooldown(480); // 8min
        return true;
    }

    private Player getTarget(Player player, double range) {
        for (Entity e : player.getNearbyEntities(range, range, range)) {
            if (!(e instanceof Player p)) continue;
            org.bukkit.util.Vector toTarget = p.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
            if (player.getLocation().getDirection().normalize().dot(toTarget) > 0.90) return p;
        }
        return null;
    }
}
