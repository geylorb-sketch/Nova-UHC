package net.novauhc.dandadan.roles.nessie;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PoissonPassive extends Ability {
    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "POISSON_DEPTH_STRIDER_LVL_NAME", descKey = "POISSON_DEPTH_STRIDER_LVL_DESC", type = VariableType.INTEGER)
    private int depthStriderLevel = 3;

    @Override public String getName()       { return "Poisson"; }
    @Override public Material getMaterial() { return null; }

    @Override public boolean onEnable(Player player) { return true; }

    @Override public void onSec(Player player) {
        ItemStack boots = player.getInventory().getBoots();
        if (boots != null && boots.getType().name().contains("BOOTS")
                && boots.getEnchantmentLevel(Enchantment.DEPTH_STRIDER) < 3) {
            boots.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, depthStriderLevel);
            player.getInventory().setBoots(boots);
        }
    }
}