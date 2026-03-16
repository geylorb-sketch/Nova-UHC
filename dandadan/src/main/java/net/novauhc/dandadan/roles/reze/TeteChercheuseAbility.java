package net.novauhc.dandadan.roles.reze;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class TeteChercheuseAbility extends UseAbiliy {
    @Override public String getName()       { return "Tête chercheuse"; }
    @Override public Material getMaterial() { return Material.SKULL_ITEM; }
    @Override public boolean onEnable(Player player) {
        Player target = player.getWorld().getNearbyEntities(player.getLocation(),30,30,30)
                .stream().filter(e->e instanceof Player&&!e.equals(player)).map(e->(Player)e)
                .min((a,b)->Double.compare(a.getLocation().distance(player.getLocation()),b.getLocation().distance(player.getLocation())))
                .orElse(null);
        if (target == null) return false;
        String msg = LangManager.get().get(DanDaDanLangExt3.REZE_TETE_ACTIVATED, player).replace("%target%",target.getName()); player.sendMessage(msg);
        // Simule le PNJ qui fonce → TP côté target + dégâts
        Player finalTarget = target;
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            finalTarget.damage(4.0, player);
            finalTarget.getWorld().createExplosion(finalTarget.getLocation(), 0F);
        }, 60L);
        setCooldown(120); return true;
    }
}