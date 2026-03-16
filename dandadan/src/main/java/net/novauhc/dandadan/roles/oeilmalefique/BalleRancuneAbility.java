package net.novauhc.dandadan.roles.oeilmalefique;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BalleRancuneAbility extends Ability {
    @Override public String getName()       { return "Balle de rancune"; }
    @Override public Material getMaterial() { return null; }

    @Override
    public void onClick(org.bukkit.event.player.PlayerInteractEvent event, ItemStack item) {
        if (event.getAction().name().contains("LEFT")
                && item != null && item.getType() == Material.GLASS_BOTTLE) {
            tryUse(event.getPlayer());
        }
    }

    @Override
    public boolean onEnable(Player player) {
        LangManager.get().send(DanDaDanLangExt.OEIL_BALLE_ACTIVATED, player);
        // 8 blocs violets tombent, créent des trous 3x3 profondeur 10
        for (int i = 0; i < 8; i++) {
            double a = (2*Math.PI/8)*i;
            Location loc = player.getLocation().clone().add(Math.cos(a)*8, 5, Math.sin(a)*8);
            // Bloc "violet" → création du trou à l'impact (simplifié)
            createHole(loc, player);
        }
        // Explosion en surface
        player.getWorld().createExplosion(player.getLocation(), 2f, false);
        setCooldown(120);
        return true;
    }

    private void createHole(Location start, Player owner) {
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            Location ground = start.clone();
            ground.setY(owner.getLocation().getBlockY());
            // Trou 3x3 profondeur 10
            for (int dx=-1;dx<=1;dx++) for (int dz=-1;dz<=1;dz++) for (int dy=0;dy>-10;dy--) {
                Location b = ground.clone().add(dx,dy,dz);
                if (b.getBlock().getType() != Material.AIR)
                    b.getBlock().setType(Material.AIR);
            }
            // Wither aux joueurs qui tombent dedans (géré via PassiveAbility générique)
        }, 10L);
    }
}