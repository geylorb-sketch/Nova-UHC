package net.novauhc.dandadan.roles.kira;

import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BitesDustAbility extends UseAbiliy {
    @Override public String getName()       { return "Bites the Dust"; }
    @Override public Material getMaterial() { return Material.BLAZE_POWDER; }
    @Override public boolean onEnable(Player player) {
        KiraRole role = (KiraRole) DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(player));
        if (!role.isStandActive()) { player.sendMessage("§c✘ Stand requis !"); return false; }
        LangManager.get().send(DanDaDanLangExt3.KIRA_BTD_ACTIVATED, player);
        role.getExplodedPlayers().forEach(uuid -> {
            Player target = Bukkit.getPlayer(uuid);
            Location old = role.getLastPositions().get(uuid);
            if (target != null && old != null) {
                target.teleport(old);
                target.getWorld().createExplosion(target.getLocation(), 1F);
                target.damage(5.0, player);
                player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + 5));
            }
        });
        role.getExplodedPlayers().clear();
        setCooldown(720); return true;
    }
}