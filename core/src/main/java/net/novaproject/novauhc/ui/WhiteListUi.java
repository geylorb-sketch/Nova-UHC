package net.novaproject.novauhc.ui;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.cloudnet.CloudNet;
import net.novaproject.novauhc.lang.lang.CommonLang;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.ui.UiTitleLang;
import net.novaproject.novauhc.lang.ui.WhiteListUiLang;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.ui.AnvilUi;
import net.novaproject.novauhc.utils.ui.CustomInventory;
import net.novaproject.novauhc.utils.ui.item.ActionItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WhiteListUi extends CustomInventory {
    public WhiteListUi(Player player) {
        super(player);
    }

    private String t(WhiteListUiLang key) {
        return LangManager.get().get(key, getPlayer());
    }

    private String t(WhiteListUiLang key, Map<String, Object> params) {
        return LangManager.get().get(key, getPlayer(), params);
    }

    @Override
    public void setup() {
        fillCorner(getConfig().getInt("menu.whitelist.corner"));
        addReturn(45, new DefaultUi(getPlayer()));

        String accessHost  = LangManager.get().get(CommonLang.ACCESS_HOST, getPlayer());
        String clickAccess = LangManager.get().get(CommonLang.CLICK_HERE_TO_ACCESS, getPlayer());
        String clickModify = LangManager.get().get(CommonLang.CLICK_HERE_TO_MODIFY, getPlayer());

        
        ItemStack disableWool = new ItemStack(Material.WOOL, 1, (byte) 14);
        ItemMeta disableMeta = disableWool.getItemMeta();
        if (disableMeta != null) {
            disableMeta.setDisplayName(t(WhiteListUiLang.DISABLE_BUTTON));
            disableWool.setItemMeta(disableMeta);
        }
        addItem(new ActionItem(7, disableWool) {
            @Override
            public void onClick(InventoryClickEvent e) {
                Bukkit.getServer().setWhitelist(false);
                LangManager.get().send(CommonLang.SUCCESSFUL_DESACTIVATION, getPlayer());
            }
        });

        
        ItemStack enableWool = new ItemStack(Material.WOOL, 1, (byte) 5);
        ItemMeta enableMeta = enableWool.getItemMeta();
        if (enableMeta != null) {
            enableMeta.setDisplayName(t(WhiteListUiLang.ENABLE_BUTTON));
            enableWool.setItemMeta(enableMeta);
        }
        addItem(new ActionItem(8, enableWool) {
            @Override
            public void onClick(InventoryClickEvent e) {
                Bukkit.getServer().setWhitelist(true);
                LangManager.get().send(CommonLang.SUCCESSFUL_ACTIVATION, getPlayer());
            }
        });

        
        addItem(new ActionItem(0, new ItemCreator(Material.PAPER).setName(t(WhiteListUiLang.ADD_PLAYER_BUTTON))) {
            @Override
            public void onClick(InventoryClickEvent e) {
                new AnvilUi(getPlayer(), event -> {
                    if (event.getSlot() == AnvilUi.AnvilSlot.OUTPUT) {
                        Bukkit.getOfflinePlayer(event.getName()).setWhitelisted(true);
                        LangManager.get().send(CommonLang.SUCCESSFUL_MODIFICATION, getPlayer());
                        openAll();
                        return;
                    }
                    WhiteListUi.this.open();
                }).setSlot(t(WhiteListUiLang.ADD_PLAYER_ANVIL_HINT)).open();
            }
        });

        
        List<OfflinePlayer> whitelistedPlayers = new ArrayList<>(Bukkit.getWhitelistedPlayers());
        int playersPerPage   = 24;
        int totalCategories  = (int) Math.ceil((double) whitelistedPlayers.size() / playersPerPage);
        if (totalCategories > 1) addPage(4);

        for (int i = 0; i < whitelistedPlayers.size(); i++) {
            OfflinePlayer p              = whitelistedPlayers.get(i);
            int categoryForThisPlayer    = (int) Math.ceil((double) (i + 1) / playersPerPage);
            int positionInCategory       = i % playersPerPage;
            int slot                     = calculateSlot(positionInCategory);

            ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta meta  = (SkullMeta) skull.getItemMeta();
            meta.setOwner(p.getName());
            meta.setDisplayName(ChatColor.GOLD + p.getName());
            skull.setItemMeta(meta);

            addItem(new ActionItem(categoryForThisPlayer, slot, skull) {
                @Override
                public void onClick(InventoryClickEvent e) {
                    if (p.isWhitelisted()) {
                        new ConfirmMenu(getPlayer(),
                                t(WhiteListUiLang.CONFIRM_REMOVE, Map.of("%player%", p.getName())),
                                () -> {
                                    p.setWhitelisted(false);
                                    if (p.isOnline() && p.getPlayer() != null) {
                                        p.getPlayer().kickPlayer(LangManager.get().get(CommonLang.KICKED, p.getPlayer()));
                                        Bukkit.broadcastMessage(LangManager.get().get(CommonLang.KICKED_MESSAGE).replace("%player%", p.getName()));
                                    }
                                    openAll();
                                },
                                () -> openAll(),
                                WhiteListUi.this).open();
                    }
                }
            });
        }

        
        if (Main.get().getCloudNet() != null) {
            ItemCreator cloud = new ItemCreator(Material.SLIME_BALL)
                    .setName(t(WhiteListUiLang.CLOUD_ITEM_NAME))
                    .addLore("")
                    .addLore(accessHost)
                    .addLore("")
                    .addLore(t(WhiteListUiLang.CLOUD_ITEM_DESC))
                    .addLore("")
                    .addLore(clickAccess)
                    .addLore("");
            addMenu(53, cloud, CloudNet.get().getCloudNetUi(getPlayer()));
        }
    }

    private int calculateSlot(int position) {
        int row = position / 7;
        int col = position % 7;
        return 10 + col + (row * 9);
    }

    @Override
    public int getCategories() {
        int playersPerPage = 24;
        return Math.max(1, (int) Math.ceil((double) Bukkit.getWhitelistedPlayers().size() / playersPerPage));
    }

    @Override
    public String getTitle() {
        return LangManager.get().get(UiTitleLang.WHITELIST_TITLE, getPlayer());
    }

    @Override
    public int getLines() { return 6; }

    @Override
    public boolean isRefreshAuto() { return false; }
}
