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
import java.util.Map;

/**
 * DanDaDanYokaiListUi — Liste des Yokai d'un type (toggle on/off).
 * Pagination automatique si > 28 yokai.
 */
public class DanDaDanYokaiListUi extends CustomInventory {

    private final YokaiType type;
    private final YokaiRegistry[] yokais;

    public DanDaDanYokaiListUi(Player player, YokaiType type) {
        super(player);
        this.type = type;
        this.yokais = Arrays.stream(YokaiRegistry.values())
                .filter(y -> y.getType() == type)
                .toArray(YokaiRegistry[]::new);
    }

    @Override
    public String getTitle() {
        return type == YokaiType.NORMAL
                ? t(DanDaDanLang.CONFIG_NORMAL_TITLE)
                : t(DanDaDanLang.CONFIG_SPECIAL_TITLE);
    }

    @Override public int getLines()          { return 6; }
    @Override public boolean isRefreshAuto() { return true; }

    @Override
    public int getCategories() {
        return Math.max(1, (int) Math.ceil(yokais.length / 28.0));
    }

    @Override
    public void setup() {
        fillLine(1, 15); // Top border
        fillLine(6, 15); // Bottom border

        // ── Yokai items (slots 9-35, 28 per page) ──
        int startIndex = (getCategories() > 1 ? 0 : 0); // Categories handle pagination
        int perPage = 28;

        for (int page = 1; page <= getCategories(); page++) {
            int offset = (page - 1) * perPage;
            for (int i = 0; i < perPage; i++) {
                int yokaiIdx = offset + i;
                if (yokaiIdx >= yokais.length) break;

                YokaiRegistry yokai = yokais[yokaiIdx];
                int slot = 9 + i; // Slots 9-36

                boolean enabled = yokai.isEnabled();
                boolean claimed = DanDaDan.get() != null && DanDaDan.get().isRoleClaimed(yokai.getRoleClass());

                short durability = (short) (enabled ? 10 : 8); // Lime=10, Gray=8

                ItemCreator item = new ItemCreator(Material.INK_SACK)
                        .setDurability(durability)
                        .setName((enabled ? "§a" : "§c") + yokai.name());

                if (claimed) {
                    item.addLore(t(DanDaDanLang.CONFIG_YOKAI_CLAIMED));
                } else {
                    item.addLore(enabled ? t(DanDaDanLang.CMD_YOKAI_ENABLED) : t(DanDaDanLang.CMD_YOKAI_DISABLED));
                }
                item.addLore("").addLore(t(DanDaDanLang.CONFIG_CLICK_TO_TOGGLE));

                final YokaiRegistry finalYokai = yokai;
                addItem(new ActionItem(page, slot, item) {
                    @Override
                    public void onClick(InventoryClickEvent e) {
                        finalYokai.setEnabled(!finalYokai.isEnabled());
                        String status = finalYokai.isEnabled()
                                ? LangManager.get().get(DanDaDanLang.CMD_YOKAI_ENABLED)
                                : LangManager.get().get(DanDaDanLang.CMD_YOKAI_DISABLED);
                        LangManager.get().send(DanDaDanLang.CMD_TOGGLE_RESULT, getPlayer(),
                                Map.of("%yokai%", finalYokai.name(), "%status%", status));
                        open();
                    }
                });
            }
        }

        // ── Bottom bar ──
        // Tout activer
        addItem(new ActionItem(47, new ItemCreator(Material.EMERALD)
                .setName(t(DanDaDanLang.CONFIG_ENABLE_ALL_TYPE))) {
            @Override
            public void onClick(InventoryClickEvent e) {
                for (YokaiRegistry y : YokaiRegistry.values()) {
                    if (y.getType() == type) y.setEnabled(true);
                }
                LangManager.get().send(DanDaDanLang.CONFIG_TYPE_ENABLED, getPlayer(),
                        Map.of("%type%", type.name().toLowerCase()));
                open();
            }
        });

        // Tout désactiver
        addItem(new ActionItem(51, new ItemCreator(Material.REDSTONE)
                .setName(t(DanDaDanLang.CONFIG_DISABLE_ALL_TYPE))) {
            @Override
            public void onClick(InventoryClickEvent e) {
                for (YokaiRegistry y : YokaiRegistry.values()) {
                    if (y.getType() == type) y.setEnabled(false);
                }
                LangManager.get().send(DanDaDanLang.CONFIG_TYPE_DISABLED, getPlayer(),
                        Map.of("%type%", type.name().toLowerCase()));
                open();
            }
        });

        // Retour
        addReturn(49, new DanDaDanConfigUi(getPlayer()));

        // Pagination (si nécessaire)
        if (getCategories() > 1) {
            addPage(4); // Top center
        }
    }
}
