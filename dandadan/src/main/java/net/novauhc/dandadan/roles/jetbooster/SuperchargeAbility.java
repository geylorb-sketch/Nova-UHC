package net.novauhc.dandadan.roles.jetbooster;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SuperchargeAbility extends UseAbiliy {
    private final Map<UUID, Long> zones = new HashMap<>();
    @Override public String getName()       { return "Supercharge"; }
    @Override public Material getMaterial() { return Material.GOLD_NUGGET; }
    @Override public boolean onEnable(Player player) {
        Player target = getNearestTarget(player, 15); if(target==null)return false;
        zones.put(target.getUniqueId(), System.currentTimeMillis() + 30_000L);
        String msg = LangManager.get().get(DanDaDanLangExt.KUR_SUPERCHARGE_ACTIVATED, player).replace("%target%",target.getName()); player.sendMessage(msg);
        var taskId = new int[1];
        taskId[0] = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(), () -> {
            zones.entrySet().removeIf(e -> e.getValue() < System.currentTimeMillis());
            if (zones.isEmpty()) { Main.get().getServer().getScheduler().cancelTask(taskId[0]); return; }
            zones.forEach((uuid, exp) -> {
                Player t = Main.get().getServer().getPlayer(uuid);
                if (t != null && t.isOnline()) {
                    t.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 0)); // -30% speed
                    // Regen proportionnelle pour Kur
                    player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + 0.5));
                }
            });
        }, 0L, 20L);
        setCooldown(600); return true;
    }
    private Player getNearestTarget(Player p,double r){ return p.getWorld().getNearbyEntities(p.getLocation(),r,r,r).stream().filter(e->e instanceof Player&&!e.equals(p)).map(e->(Player)e).min((a,b)->Double.compare(a.getLocation().distance(p.getLocation()),b.getLocation().distance(p.getLocation()))).orElse(null); }
}