package net.novauhc.dandadan.roles.nessie;

import net.novaproject.novauhc.ability.CommandAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CouCommand extends CommandAbility {
    public CouCommand() { setCooldown(300); }
    @Override public String getName()       { return "Cou"; }
    @Override public String getCommandKey() { return "cou"; }
    @Override public boolean onCommand(Player player, String[] args) {
        if (args.length < 2) { LangManager.get().send(DanDaDanLangExt.NESSIE_COU_USAGE, player); return false; }
        Player target = Bukkit.getPlayer(args[1]);
        if (target==null||target.equals(player)) { player.sendMessage("§c✘ Joueur invalide."); return false; }

        int stolen = 0;
        for (ItemStack item : target.getInventory().getContents()) {
            if (item!=null && item.getType()==Material.WATER_BUCKET) {
                stolen += item.getAmount();
                item.setType(Material.AIR);
            }
        }
        if (stolen > 0 && (player.getInventory().firstEmpty() != -1)) {
            player.getInventory().addItem(new ItemStack(Material.WATER_BUCKET, stolen));
            String msg = LangManager.get().get(DanDaDanLangExt.NESSIE_COU_SUCCESS, player).replace("%target%",target.getName());
            player.sendMessage(msg);
        }
        return true;
    }
}