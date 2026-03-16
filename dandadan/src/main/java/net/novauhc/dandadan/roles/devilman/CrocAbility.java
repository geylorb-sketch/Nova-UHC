package net.novauhc.dandadan.roles.devilman;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt2;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CrocAbility extends Ability {
    private final Map<UUID, Long> devoured = new HashMap<>();
    @Override public String getName()       { return "Croc"; }
    @Override public Material getMaterial() { return null; }
    @Override public void onClick(org.bukkit.event.player.PlayerInteractEvent event, ItemStack item) {
        if (!event.getAction().name().contains("LEFT") || item==null || item.getType()!=Material.BLAZE_ROD) return;
        tryUse(event.getPlayer());
    }
    @Override public boolean onEnable(Player player) {
        Player target = getNearestTarget(player, 10); if(target==null) return false;
        devoured.put(target.getUniqueId(), System.currentTimeMillis() + 60_000L);
        String msg = LangManager.get().get(DanDaDanLangExt2.DEVIL_CROC_ACTIVATED, player).replace("%target%",target.getName()); player.sendMessage(msg);
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            devoured.remove(target.getUniqueId());
            // Échange 2 slots hotbar
            if (target.isOnline()) {
                ItemStack a=target.getInventory().getItem(0), b=target.getInventory().getItem(1);
                target.getInventory().setItem(0,b); target.getInventory().setItem(1,a);
                LangManager.get().send(DanDaDanLangExt2.DEVIL_CROC_EXPIRED, target);
            }
        }, 20L*60);
        setCooldown(120); return true;
    }
    public boolean isDevoured(UUID u) { Long e=devoured.get(u); if(e==null)return false; if(e<System.currentTimeMillis()){devoured.remove(u);return false;} return true; }
    private Player getNearestTarget(Player p,double r){return p.getWorld().getNearbyEntities(p.getLocation(),r,r,r).stream().filter(e->e instanceof Player&&!e.equals(p)).map(e->(Player)e).min((a,b)->Double.compare(a.getLocation().distance(p.getLocation()),b.getLocation().distance(p.getLocation()))).orElse(null);}
}