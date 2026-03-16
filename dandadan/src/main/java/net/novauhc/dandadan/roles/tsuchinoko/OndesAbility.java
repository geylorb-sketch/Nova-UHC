package net.novauhc.dandadan.roles.tsuchinoko;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class OndesAbility extends Ability {
    @Override public String getName()       { return "Ondes psychiques"; }
    @Override public Material getMaterial() { return null; }

    @Override
    public void onClick(org.bukkit.event.player.PlayerInteractEvent event, ItemStack item) {
        if (event.getAction().name().contains("LEFT") && item != null
                && item.getType() == Material.FERMENTED_SPIDER_EYE) {
            tryUse(event.getPlayer());
        }
    }

    @Override
    public boolean onEnable(Player player) {
        LangManager.get().send(DanDaDanLangExt.TSUCHINOKO_ONDES_ACTIVATED, player);
        player.getWorld().getNearbyEntities(player.getLocation(), 10, 10, 10)
                .stream().filter(e -> e instanceof Player && !e.equals(player))
                .forEach(e -> {
                    Vector away = e.getLocation().subtract(player.getLocation()).toVector().normalize();
                    e.setVelocity(away.multiply(2).setY(0.3));
                    ((Player)e).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 0));
                });
        setCooldown(600);
        return true;
    }
}