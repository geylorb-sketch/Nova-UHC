package net.novaproject.novauhc.listener.effect;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;
import java.util.Set;

public class ArmorDurabilityNerfEvent implements Listener {

    private static final Set<Material> ARMOR_MATERIALS = Set.of(
            Material.GOLD_HELMET,      Material.GOLD_CHESTPLATE,
            Material.GOLD_LEGGINGS,    Material.GOLD_BOOTS,
            Material.LEATHER_HELMET,   Material.LEATHER_CHESTPLATE,
            Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS,
            Material.CHAINMAIL_HELMET,   Material.CHAINMAIL_CHESTPLATE,
            Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS,
            Material.DIAMOND_HELMET,   Material.DIAMOND_CHESTPLATE,
            Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS
    );

    private final Random random = new Random();

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onArmorDurabilityNerf(PlayerItemDamageEvent event) {
        if (event.getItem() == null) return;
        if (!event.getPlayer().hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) return;
        if (!ARMOR_MATERIALS.contains(event.getItem().getType())) return;

        if (random.nextBoolean()) {
            event.setCancelled(true);
        }
    }
}