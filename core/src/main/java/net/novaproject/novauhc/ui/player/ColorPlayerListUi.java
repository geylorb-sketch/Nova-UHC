package net.novaproject.novauhc.ui.player;

import net.novaproject.novauhc.utils.PlayerColorManager;
import net.novaproject.novauhc.utils.ui.CustomInventory;
import net.novaproject.novauhc.utils.ui.item.ActionItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * Menu listant tous les joueurs en ligne.
 * Cliquer sur une tête ouvre le ColorPickerUi pour choisir la couleur du nom dans le TAB.
 */
public class ColorPlayerListUi extends CustomInventory {

    public ColorPlayerListUi(Player player) {
        super(player);
    }

    @Override
    public void setup() {
        fillCorner(getConfig().getInt("menu.stuff.color"));

        int slot = 0;
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (target.equals(getPlayer())) continue;
            if (slot >= 54) break;

            ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            meta.setOwner(target.getName());
            meta.setDisplayName("§f" + target.getName());
            skull.setItemMeta(meta);

            final Player finalTarget = target;
            addItem(new ActionItem(slot, skull) {
                @Override
                public void onClick(InventoryClickEvent e) {
                    if (e.getCurrentItem() == null) return;
                    new ColorPickerUi(getPlayer(), finalTarget).open();
                }
            });
            slot++;
        }
    }

    @Override
    public String getTitle() { return "§8Colorer un joueur dans le TAB"; }

    @Override
    public int getLines() { return 6; }

    @Override
    public boolean isRefreshAuto() { return false; }
}
