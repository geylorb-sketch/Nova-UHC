package net.novauhc.dandadan.roles.minotaure;

import net.novaproject.novauhc.ability.CommandAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt2;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FerCommand extends CommandAbility {
    public FerCommand() { setMaxUse(1); }
    @Override public String getName()       { return "Fer"; }
    @Override public String getCommandKey() { return "fer"; }
    @Override public boolean onCommand(Player player, String[] args) {
        LangManager.get().send(DanDaDanLangExt2.MINO_FER_ACTIVATED, player);
        ItemStack[] armor = player.getInventory().getArmorContents();
        for (ItemStack piece : armor) {
            if (piece != null && piece.getType().name().contains("IRON")) piece.setDurability((short) 0);
        }
        player.getInventory().setArmorContents(armor);
        return true;
    }
}