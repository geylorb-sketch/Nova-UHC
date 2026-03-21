package net.novauhc.dandadan.world;

import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.ui.CustomInventory;
import net.novaproject.novauhc.utils.ui.item.ActionItem;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * YokaiPactUi — Menu d'acceptation/refus du pacte Yokai.
 * Utilise le framework CustomInventory.
 *
 * Layout 3 lignes :
 *   Slot 11 : Icône du Yokai (aperçu pouvoirs)
 *   Slot 13 : Accepter (lime dye)
 *   Slot 15 : Refuser (gray dye)
 *   Bordure noire
 */
public class YokaiPactUi extends CustomInventory {

    private final YokaiRegistry yokai;

    public YokaiPactUi(Player player, YokaiRegistry yokai) {
        super(player);
        this.yokai = yokai;
    }

    @Override
    public String getTitle() {
        return "§5§l" + yokai.name() + " §8— " + t(DanDaDanLang.PACT_MENU_TITLE_SUFFIX);
    }

    @Override public int getLines()          { return 3; }
    @Override public boolean isRefreshAuto() { return false; }

    @Override
    public void setup() {
        fillCadre(15); // Bordure noire

        // ── Slot 11 — Icône du Yokai ──
        DanDaDanRole roleInstance;
        try {
            roleInstance = yokai.getRoleClass().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            roleInstance = null;
        }

        Material iconMat = roleInstance != null ? roleInstance.getIconMaterial() : Material.SKULL_ITEM;
        String displayName = roleInstance != null ? roleInstance.getName() : yokai.name();

        // Click on icon → send description in chat
        final DanDaDanRole finalRole = roleInstance;
        addItem(new ActionItem(4, new ItemCreator(iconMat)
                .setName("§5§l" + displayName)
                .addLore(" ")
                .addLore(t(DanDaDanLang.PACT_MENU_YOKAI_LORE_1))
                .addLore(t(DanDaDanLang.PACT_MENU_YOKAI_LORE_2))
                .addLore(" ")
                .addLore(t(DanDaDanLang.PACT_MENU_YOKAI_LORE_3))) {
            @Override
            public void onClick(InventoryClickEvent e) {
                // Show description in chat without closing
                if (finalRole != null) {
                    finalRole.sendDescription(getPlayer());
                }
            }
        });

        // ── Slot 13 — Accepter ──
        addItem(new ActionItem(11, new ItemCreator(Material.INK_SACK)
                .setDurability((short) 10) // Lime dye
                .setName(t(DanDaDanLang.PACT_MENU_ACCEPT))
                .addLore(t(DanDaDanLang.PACT_MENU_ACCEPT_LORE))) {
            @Override
            public void onClick(InventoryClickEvent e) {
                getPlayer().closeInventory();
                getPlayer().playSound(getPlayer().getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
                YokaiZoneManager.get().acceptPact(getPlayer());
            }
        });

        // ── Slot 15 — Refuser ──
        addItem(new ActionItem(15, new ItemCreator(Material.INK_SACK)
                .setDurability((short) 8) // Gray dye
                .setName(t(DanDaDanLang.PACT_MENU_REFUSE))
                .addLore(t(DanDaDanLang.PACT_MENU_REFUSE_LORE))) {
            @Override
            public void onClick(InventoryClickEvent e) {
                getPlayer().closeInventory();
                YokaiZoneManager.get().refusePact(getPlayer());
            }
        });
    }

}
