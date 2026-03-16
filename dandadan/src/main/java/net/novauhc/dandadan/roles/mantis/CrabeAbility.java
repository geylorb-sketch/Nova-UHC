package net.novauhc.dandadan.roles.mantis;

import net.novaproject.novauhc.ability.CommandAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CrabeAbility extends CommandAbility {
    public CrabeAbility() { setMaxUse(-1); setCooldown(0); }
    @Override public String getName()       { return "Crabe"; }
    @Override public String getCommandKey() { return "crabe"; }

    @Override
    public boolean onCommand(Player player, String[] args) {
        // Coûte 20 pommes en or
        int gappleCount = 0;
        ItemStack[] inv = player.getInventory().getContents();
        for (ItemStack item : inv) {
            if (item != null && (item.getType() == Material.GOLDEN_APPLE))
                gappleCount += item.getAmount();
        }
        if (gappleCount < 20) {
            LangManager.get().send(DanDaDanLang.MANTIS_CRABE_NO_GAP, player);
            return false;
        }

        // Retire 20 pommes
        int toRemove = 20;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null) continue;
            if (item.getType() == Material.GOLDEN_APPLE) {
                if (item.getAmount() <= toRemove) {
                    toRemove -= item.getAmount();
                    item.setType(Material.AIR);
                } else {
                    item.setAmount(item.getAmount() - toRemove);
                    toRemove = 0; break;
                }
            }
        }

        // +1 Death Rider sur les bottes (Knockback enchant comme proxy Death Rider)
        ItemStack boots = player.getInventory().getBoots();
        if (boots != null && boots.getType() != Material.AIR) {
            int current = boots.getEnchantmentLevel(Enchantment.KNOCKBACK);
            boots.addUnsafeEnchantment(Enchantment.KNOCKBACK, current + 1);
            player.getInventory().setBoots(boots);
        }

        LangManager.get().send(DanDaDanLang.MANTIS_CRABE_SUCCESS, player);
        return true;
    }
}
