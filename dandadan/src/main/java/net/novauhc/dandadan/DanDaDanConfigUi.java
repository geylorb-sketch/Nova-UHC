package net.novauhc.dandadan;

import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.ui.CustomInventory;
import net.novaproject.novauhc.utils.ui.item.ActionItem;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.world.YokaiRegistry;
import net.novauhc.dandadan.world.YokaiRegistry.YokaiType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;

/**
 * DanDaDanConfigUi — Menu principal de configuration DanDaDan.
 * Utilise le framework CustomInventory de NovaUHC.
 *
 * 3 boutons :
 *   - Yokai Normaux → ouvre DanDaDanYokaiListUi(NORMAL)
 *   - Yokai Spéciaux → ouvre DanDaDanYokaiListUi(SPECIAL)
 *   - Actions rapides (gauche = tout activer, droite = désactiver spéciaux)
 */
public class DanDaDanConfigUi extends CustomInventory {

    public DanDaDanConfigUi(Player player) {
        super(player);
    }

    @Override public String getTitle()       { return t(DanDaDanLang.CONFIG_UI_TITLE); }
    @Override public int getLines()          { return 3; }
    @Override public boolean isRefreshAuto() { return true; }

    @Override
    public void setup() {
        fillCadre(15); // Black border

        long normalEnabled = Arrays.stream(YokaiRegistry.values())
                .filter(y -> y.getType() == YokaiType.NORMAL && y.isEnabled()).count();
        long normalTotal = Arrays.stream(YokaiRegistry.values())
                .filter(y -> y.getType() == YokaiType.NORMAL).count();

        addMenu(11,
                new ItemCreator(Material.SKULL_ITEM).setDurability((short) 3)
                        .setName(t(DanDaDanLang.CONFIG_NORMAL_TITLE))
                        .addLore("§7" + normalEnabled + "/" + normalTotal + " " + t(DanDaDanLang.CONFIG_ENABLED_COUNT))
                        .addLore("")
                        .addLore(t(DanDaDanLang.CONFIG_CLICK_TO_CONFIGURE)),
                new DanDaDanYokaiListUi(getPlayer(), YokaiType.NORMAL));


        long specialEnabled = Arrays.stream(YokaiRegistry.values())
                .filter(y -> y.getType() == YokaiType.SPECIAL && y.isEnabled()).count();
        long specialTotal = Arrays.stream(YokaiRegistry.values())
                .filter(y -> y.getType() == YokaiType.SPECIAL).count();

        addMenu(13,
                new ItemCreator(Material.NETHER_STAR)
                        .setName(t(DanDaDanLang.CONFIG_SPECIAL_TITLE))
                        .addLore("§7" + specialEnabled + "/" + specialTotal + " " + t(DanDaDanLang.CONFIG_ENABLED_COUNT))
                        .addLore("")
                        .addLore(t(DanDaDanLang.CONFIG_CLICK_TO_CONFIGURE)),
                new DanDaDanYokaiListUi(getPlayer(), YokaiType.SPECIAL));

        // ── Actions rapides ──
        addItem(new ActionItem(15, new ItemCreator(Material.REDSTONE_COMPARATOR)
                .setName(t(DanDaDanLang.CONFIG_QUICK_ACTIONS))
                .addLore(t(DanDaDanLang.CONFIG_LEFT_ENABLE_ALL))
                .addLore(t(DanDaDanLang.CONFIG_RIGHT_DISABLE_SPECIAL))) {
            @Override
            public void onClick(InventoryClickEvent e) {
                if (e.isLeftClick()) {
                    for (YokaiRegistry y : YokaiRegistry.values()) y.setEnabled(true);
                    LangManager.get().send(DanDaDanLang.CONFIG_ALL_ENABLED, getPlayer());
                } else {
                    for (YokaiRegistry y : YokaiRegistry.values()) {
                        if (y.getType() == YokaiType.SPECIAL) y.setEnabled(false);
                    }
                    LangManager.get().send(DanDaDanLang.CONFIG_SPECIAL_DISABLED, getPlayer());
                }
                open();
            }
        });
    }
}
