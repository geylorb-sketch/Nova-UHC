package net.novauhc.dandadan.roles.joseph;

import net.novaproject.novauhc.ability.Ability;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HermitPurpleAbility extends Ability {
    @Override public String getName()       { return "Hermit Purple"; }
    @Override public Material getMaterial() { return null; }
    @Override public void onClick(org.bukkit.event.player.PlayerInteractEvent event, ItemStack item) {
        if (!event.getAction().name().contains("RIGHT") || event.getPlayer().getItemInHand() != null) return;
        // TP vers le bloc/joueur visé
        Player player = event.getPlayer();
        Location target = player.getTargetBlock((java.util.Set<Material>) null, 20).getLocation().add(0,1,0);
        player.setVelocity(target.subtract(player.getLocation()).toVector().normalize().multiply(1.5));
        setCooldown(300);
    }

    @Override
    public boolean onEnable(Player player) { return true; }
}
