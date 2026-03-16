package net.novauhc.dandadan.roles.reze;

import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RezeMalediction extends UseAbiliy {

    @Override public String getName()       { return "Malédiction Reze"; }
    @Override public Material getMaterial() { return Material.TNT; }
    @Override public boolean onEnable(Player player) {
        RezeRole role = (RezeRole) DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(player));
        role.setCursed(true);
        LangManager.get().send(DanDaDanLangExt3.REZE_CURSE_ACTIVATED, player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,           40, 1, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 40, 1, false, false));
        // Détruit feuillages autour
        player.getWorld().getNearbyEntities(player.getLocation(),10,10,10)
                .stream().filter(e -> e instanceof Player).forEach(e -> {});
        for (int dx=-10;dx<=10;dx++) for (int dy=-5;dy<=5;dy++) for (int dz=-10;dz<=10;dz++) {
            Location b = player.getLocation().clone().add(dx,dy,dz);
            if (b.getBlock().getType()==Material.LEAVES||b.getBlock().getType()==Material.LEAVES_2)
                b.getBlock().setType(Material.AIR);
        }
        setCooldown(600); return true;
    }
}