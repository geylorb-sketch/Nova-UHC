package net.novauhc.dandadan.roles.kashimoto;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Flamme de Glace — Clic-Droit (vise un joueur)
 * Enflamme visuellement sans dégâts. Armure de la cible : perd 1 niveau d'enchant sur une pièce.
 * Durabilité : -150% quand elle prend un coup. Glowing pendant la durée. 1min.
 */
public class FlammeGlaceAbility extends UseAbiliy {

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "KASHIMOTO_FLAME_DURATION_NAME", descKey = "KASHIMOTO_FLAME_DURATION_DESC", type = VariableType.TIME)
    private int duration = 60;

    private final Set<UUID> afflicted = new HashSet<>();

    @Override public String getName()       { return "Flamme de Glace"; }
    @Override public Material getMaterial() { return Material.BLAZE_POWDER; }

    @Override
    public boolean onEnable(Player kashimoto) {
        Player target = getNearestTarget(kashimoto, 10);
        if (target == null) { kashimoto.sendMessage("§c✘ Aucune cible à portée."); return false; }

        afflicted.add(target.getUniqueId());
        target.setFireTicks(20 * duration);

        // Retire un enchant de l'armure
        removeOneEnchantFromArmor(target);

        String msg = LangManager.get().get(DanDaDanLang.KASHIMOTO_FLAME_ACTIVATED, kashimoto)
                .replace("%target%", target.getName());
        kashimoto.sendMessage(msg);
        String recv = LangManager.get().get(DanDaDanLang.KASHIMOTO_FLAME_RECEIVED, target)
                .replace("%kashimoto%", kashimoto.getName());
        target.sendMessage(recv);

        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            afflicted.remove(target.getUniqueId());
            target.setFireTicks(0);
        }, 20L * duration);

        setCooldown(480); // 8 mins
        return true;
    }

    private void removeOneEnchantFromArmor(Player player) {
        ItemStack[] armor = player.getInventory().getArmorContents();
        for (ItemStack piece : armor) {
            if (piece == null || piece.getType() == Material.AIR) continue;
            if (!piece.getEnchantments().isEmpty()) {
                var enchant = piece.getEnchantments().entrySet().iterator().next();
                int level = enchant.getValue();
                if (level <= 1) piece.removeEnchantment(enchant.getKey());
                else piece.addUnsafeEnchantment(enchant.getKey(), level - 1);
                break;
            }
        }
        player.getInventory().setArmorContents(armor);
    }

    private Player getNearestTarget(Player p, double range) {
        return p.getWorld().getNearbyEntities(p.getLocation(), range, range, range)
                .stream().filter(e -> e instanceof Player && !e.equals(p))
                .map(e -> (Player) e)
                .min((a, b) -> Double.compare(a.getLocation().distance(p.getLocation()), b.getLocation().distance(p.getLocation())))
                .orElse(null);
    }

    public boolean isAfflicted(UUID uuid) { return afflicted.contains(uuid); }
}
