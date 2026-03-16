package net.novauhc.dandadan.roles.nessie;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DelugeAbility extends UseAbiliy {
    @Override public String getName()       { return "Création du Déluge"; }
    @Override public Material getMaterial() { return Material.BUCKET; }
    @Override public boolean onEnable(Player player) {
        LangManager.get().send(DanDaDanLangExt.NESSIE_DELUGE_ACTIVATED, player);
        // Remplit la hotbar de seaux d'eau, se retirent un par un
        for (int i = 0; i < 9; i++) {
            player.getInventory().setItem(i, new ItemStack(Material.WATER_BUCKET));
        }
        var taskId = new int[1];
        int[] slot = {0};
        taskId[0] = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(), () -> {
            if (slot[0] >= 9) { Main.get().getServer().getScheduler().cancelTask(taskId[0]); return; }
            player.getInventory().setItem(slot[0], new ItemStack(Material.AIR));
            slot[0]++;
        }, 40L, 40L); // un seau toutes les 2s
        setCooldown(420); return true;
    }
}