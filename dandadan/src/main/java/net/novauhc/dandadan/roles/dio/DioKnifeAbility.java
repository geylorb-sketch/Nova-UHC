package net.novauhc.dandadan.roles.dio;

import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DioKnifeAbility extends UseAbiliy {
    @Override public String getName()       { return "Couteau"; }
    @Override public Material getMaterial() { return Material.SNOW_BALL; }
    @Override public boolean onEnable(Player player) {
        LangManager.get().send(DanDaDanLangExt3.DIO_KNIFE, player);
        ItemStack knives = new ItemStack(Material.SNOW_BALL, 5);
        player.getInventory().addItem(knives);
        setCooldown(420); return true;
    }
}