package net.novaproject.ultimate.slavemarket;

import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.special.SlaveMarketLang;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.ui.ConfigVarUi;
import net.novaproject.novauhc.ui.config.ScenariosUi;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.Titles;
import net.novaproject.novauhc.utils.ui.AnvilUi;
import net.novaproject.novauhc.utils.ui.CustomInventory;
import net.novaproject.novauhc.utils.ui.item.ActionItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Map;

public class SlaveMarketUi extends CustomInventory {

    private final SlaveMarket slave = SlaveMarket.get();

    public SlaveMarketUi(Player player) {
        super(player);
    }

    @Override
    public void setup() {
        fillCadre(0);
        addReturn(36, new ScenariosUi(getPlayer(), true));

        addItem(new ActionItem(16, new ItemCreator(Material.PAPER)
                .setName(LangManager.get().get(SlaveMarketLang.UI_ADD_PLAYER_NAME, getPlayer()))) {
            @Override
            public void onClick(InventoryClickEvent e) {
                new AnvilUi(getPlayer(), new SlaveMarketUi(getPlayer()), event -> {
                    if (event.getSlot() == AnvilUi.AnvilSlot.OUTPUT) {
                        Player found = Bukkit.getPlayer(event.getName());
                        if (found == null) {
                                    LangManager.get().get(SlaveMarketLang.UI_PLAYER_NOT_FOUND, getPlayer());
                            return;
                        }
                        UHCPlayer uhcPlayer = UHCPlayerManager.get().getPlayer(found);
                        if (slave.getOwners().size() + 1 <= slave.getTeamPlaces().size()) {
                            slave.addOwner(uhcPlayer);
                        } else {
                            LangManager.get().send(SlaveMarketLang.UI_NO_SLOT_AVAILABLE, getPlayer());
                        }
                    }
                }).setSlot(LangManager.get().get(SlaveMarketLang.UI_ADD_PLAYER_ANVIL, getPlayer())).open();
            }
        });

        int slot = 19;
        for (UHCPlayer uhcPlayer : slave.getOwners()) {
            ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            meta.setOwner(uhcPlayer.getPlayer().getName());
            meta.setDisplayName(ChatColor.GOLD + uhcPlayer.getPlayer().getName());
            skull.setItemMeta(meta);

            addItem(new ActionItem(slot, skull) {
                @Override
                public void onClick(InventoryClickEvent e) {
                    slave.removeOwner(uhcPlayer);
                    openAll();
                }
            });

            if (slot == 25) {
                slot = 31;
            } else {
                slot++;
            }
        }

        addMenu(10, new ItemCreator(Material.DIAMOND)
                        .setName(LangManager.get().get(SlaveMarketLang.UI_DIAMOND_ITEM_NAME, getPlayer(),
                                Map.of("%value%", String.valueOf(slave.getNbDiamond()))))
                        .addLore("")
                        .addLore(LangManager.get().get(SlaveMarketLang.UI_DIAMOND_LORE_MODIFY, getPlayer()))
                        .addLore(LangManager.get().get(SlaveMarketLang.UI_DIAMOND_LORE_COUNT, getPlayer()))
                        .addLore(LangManager.get().get(SlaveMarketLang.UI_DIAMOND_LORE_OWNER, getPlayer()))
                        .addLore(""),
                new ConfigVarUi(getPlayer(), 10, 5, 1, 10, 5, 1, slave.getNbDiamond(), 20, 100, this) {
                    @Override
                    public void onChange(Number newValue) {
                        slave.setNbDiamond((int) newValue);
                    }
                });

        addItem(new ActionItem(4, getWool(UHCManager.get().isStarted())) {
            @Override
            public void onClick(InventoryClickEvent e) {
                if (slave.canBuy()) {
                    slave.startEnchere();
                } else {
                    slave.cancelAction();
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        new Titles().sendTitle(p,
                                LangManager.get().get(SlaveMarketLang.UI_AUCTION_CANCELLED_TITLE, p),
                                "", 8);
                    }
                }
                openAll();
            }
        });
    }

    private ItemStack getWool(boolean started) {
        Material material = Material.WOOL;
        byte color = started ? (byte) 14 : (byte) 5;
        ItemStack wool = new ItemStack(material, 1, color);
        ItemMeta meta = wool.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(LangManager.get().get(
                    started ? SlaveMarketLang.UI_WOOL_CANCEL : SlaveMarketLang.UI_WOOL_START,
                    getPlayer()
            ));
            wool.setItemMeta(meta);
        }
        return wool;
    }

    @Override
    public String getTitle() {
        return LangManager.get().get(SlaveMarketLang.UI_TITLE, getPlayer());
    }

    @Override
    public int getLines() {
        return 5;
    }

    @Override
    public boolean isRefreshAuto() {
        return false;
    }
}