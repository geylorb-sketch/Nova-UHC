package net.novauhc.dandadan.roles.kira;

import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class BombeKiraAbility extends UseAbiliy {
    private UUID nextTarget = null;
    @Override public String getName()       { return "Bombe"; }
    @Override public Material getMaterial() { return Material.TNT; }
    @Override public boolean onEnable(Player player) {
        KiraRole role = (KiraRole) DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(player));
        if (nextTarget != null) {
            Player target = Bukkit.getPlayer(nextTarget);
            if (target != null) {
                target.getWorld().createExplosion(target.getLocation(), 1F);
                target.damage(3.0, player);
                // -10% armure
                for (ItemStack piece : target.getInventory().getArmorContents()) {
                    if (piece != null) { short dur = piece.getDurability(); piece.setDurability((short)(dur + piece.getType().getMaxDurability()/10)); }
                }
                role.getExplodedPlayers().remove(nextTarget);
            }
            nextTarget = null;
            return true;
        }
        // Premier clic : pose statut "explosé"
        Player target = player.getWorld().getNearbyEntities(player.getLocation(),8,8,8)
                .stream().filter(e->e instanceof Player&&!e.equals(player)).map(e->(Player)e)
                .min((a,b)->Double.compare(a.getLocation().distance(player.getLocation()),b.getLocation().distance(player.getLocation())))
                .orElse(null);
        if (target == null) return false;
        role.addExploded(target.getUniqueId(), target);
        nextTarget = target.getUniqueId();
        String msg = LangManager.get().get(DanDaDanLangExt3.KIRA_BOMBE_ACTIVATED, player).replace("%target%",target.getName()); player.sendMessage(msg);
        setCooldown(300); return true;
    }
}