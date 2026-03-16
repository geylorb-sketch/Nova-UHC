package net.novauhc.dandadan.roles.mantis;

import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JetWaterAbility extends UseAbiliy {
    @Override public String getName()       { return "Jet Water"; }
    @Override public Material getMaterial() { return Material.WATER_BUCKET; }

    @Override
    public boolean onEnable(Player mantis) {
        Player target = getNearestTarget(mantis, 10);
        if (target == null) return false;

        // Retire les seaux d'eau
        ItemStack[] contents = target.getInventory().getContents();
        for (int i = 0; i < contents.length; i++) {
            if (contents[i] != null && (contents[i].getType() == Material.WATER_BUCKET || contents[i].getType() == Material.BUCKET)) {
                contents[i] = new ItemStack(Material.AIR);
            }
        }
        target.getInventory().setContents(contents);

        String msg = LangManager.get().get(DanDaDanLang.MANTIS_JETWATER, mantis)
                .replace("%target%", target.getName());
        mantis.sendMessage(msg);

        // 15s sans seau (flag)
        JetWaterDebuff.mark(target.getUniqueId(), System.currentTimeMillis() + 15_000L);
        setCooldown(180);
        return true;
    }

    private Player getNearestTarget(Player p, double range) {
        return p.getWorld().getNearbyEntities(p.getLocation(), range, range, range)
                .stream().filter(e -> e instanceof Player && !e.equals(p))
                .map(e -> (Player) e)
                .min((a, b) -> Double.compare(a.getLocation().distance(p.getLocation()), b.getLocation().distance(p.getLocation())))
                .orElse(null);
    }

    public static class JetWaterDebuff {
        private static final Map<UUID, Long> debuffed = new HashMap<>();
        public static void mark(UUID uuid, long expiry) { debuffed.put(uuid, expiry); }
        public static boolean isDebuffed(UUID uuid) {
            Long exp = debuffed.get(uuid);
            if (exp == null) return false;
            if (exp < System.currentTimeMillis()) { debuffed.remove(uuid); return false; }
            return true;
        }
    }
}

// ── Crabe ─────────────────────────────────────────────────────────
