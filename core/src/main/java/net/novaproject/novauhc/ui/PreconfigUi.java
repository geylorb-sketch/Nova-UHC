package net.novaproject.novauhc.ui;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.lang.lang.CommonLang;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.ui.PreconfigUiLang;
import net.novaproject.novauhc.lang.ui.UiTitleLang;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.ui.AnvilUi;
import net.novaproject.novauhc.utils.ui.CustomInventory;
import net.novaproject.novauhc.utils.ui.item.ActionItem;
import net.novaproject.novauhc.utils.ui.item.StaticItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PreconfigUi extends CustomInventory {

    private List<String> configNames = new ArrayList<>();
    private final CustomInventory parent;
    private boolean configsLoaded = false;

    public PreconfigUi(Player player, CustomInventory parent) {
        super(player);
        this.parent = parent;
        loadConfigsAsync();
    }

    private void loadConfigsAsync() {
        Main.getDatabaseManager()
                .getPlayerUHCConfigNames(getPlayer().getUniqueId())
                .thenAccept(configs -> new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (configs != null) configNames = configs;
                        configsLoaded = true;
                    }
                }.runTask(Main.get()))
                .exceptionally(ex -> {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            getPlayer().sendMessage("§c❌ Erreur chargement configs: " + ex.getMessage());
                            ex.printStackTrace();
                            configsLoaded = true;
                        }
                    }.runTask(Main.get());
                    return null;
                });
    }

    private String t(PreconfigUiLang key) {
        return LangManager.get().get(key, getPlayer());
    }

    private String t(PreconfigUiLang key, Map<String, Object> params) {
        return LangManager.get().get(key, getPlayer(), params);
    }

    @Override
    public void setup() {
        fillCorner(getConfig().getInt("menu.preconfig.corner"));
        addReturn(45, new DefaultUi(getPlayer()));

        String accessHost = LangManager.get().get(CommonLang.ACCESS_HOST, getPlayer());

        if (!configsLoaded) {
            addItem(new StaticItem(22, new ItemCreator(Material.WATCH)
                    .setName(t(PreconfigUiLang.LOADING_NAME))
                    .addLore("")
                    .addLore(t(PreconfigUiLang.LOADING_DESC))));
            return;
        }

        if (configNames == null || configNames.isEmpty()) {
            addItem(new StaticItem(22, new ItemCreator(Material.BARRIER)
                    .setName(t(PreconfigUiLang.EMPTY_NAME))
                    .addLore("")
                    .addLore(t(PreconfigUiLang.EMPTY_DESC))
                    .addLore(t(PreconfigUiLang.EMPTY_HINT))));
            setupAddConfigButton(accessHost);
            return;
        }

        int configperpage    = 24;
        int totalCategories  = (int) Math.ceil((double) configNames.size() / configperpage);
        if (totalCategories > 1) addPage(4);

        for (int i = 0; i < configNames.size(); i++) {
            String configName       = configNames.get(i);
            int    category         = (i / configperpage) + 1;
            int    slot             = calculateSlot(i % configperpage);

            ItemCreator item = new ItemCreator(Material.PAPER)
                    .setName("§8┃ §f" + configName)
                    .addLore("")
                    .addLore(accessHost)
                    .addLore("")
                    .addLore(t(PreconfigUiLang.CLICK_LOAD))
                    .addLore(t(PreconfigUiLang.CLICK_DELETE))
                    .addLore("");

            addItem(new ActionItem(category, slot, item) {
                @Override
                public void onClick(InventoryClickEvent e) {
                    if (e.isRightClick()) {
                        new ConfirmMenu(getPlayer(),
                                t(PreconfigUiLang.CONFIRM_DELETE, Map.of("%name%", configName)),
                                () -> getPlayer().performCommand("config delete " + configName),
                                () -> {},
                                new PreconfigUi(getPlayer(), parent)).open();
                    } else {
                        new ConfirmMenu(getPlayer(),
                                t(PreconfigUiLang.CONFIRM_LOAD, Map.of("%name%", configName)),
                                () -> getPlayer().performCommand("config load " + configName),
                                () -> {},
                                new PreconfigUi(getPlayer(), parent)).open();
                    }
                }
            });
        }

        setupAddConfigButton(accessHost);
    }

    private void setupAddConfigButton(String accessHost) {
        addItem(new ActionItem(0, new ItemCreator(Material.PAPER)
                .setName(t(PreconfigUiLang.ADD_CONFIG_NAME))
                .addLore("")
                .addLore(accessHost)
                .addLore("")
                .addLore(t(PreconfigUiLang.ADD_CONFIG_DESC))
                .addLore("")
                .addLore(LangManager.get().get(CommonLang.CLICK_HERE_TO_MODIFY, getPlayer()))
                .addLore("")) {
            @Override
            public void onClick(InventoryClickEvent e) {
                new AnvilUi(getPlayer(), new PreconfigUi(getPlayer(), new DefaultUi(getPlayer())), event -> {
                    if (event.getSlot() == AnvilUi.AnvilSlot.OUTPUT) {
                        getPlayer().performCommand("config save " + event.getName());
                    }
                    new WhiteListUi(getPlayer()).open();
                }).setSlot(t(PreconfigUiLang.ADD_CONFIG_ANVIL_HINT)).open();
            }
        });
    }

    @Override
    public String getTitle() {
        return LangManager.get().get(UiTitleLang.PRECONFIG_TITLE, getPlayer());
    }

    @Override
    public int getLines() { return 6; }

    @Override
    public boolean isRefreshAuto() { return false; }

    private int calculateSlot(int position) {
        int row = position / 7;
        int col = position % 7;
        return 10 + col + (row * 9);
    }

    @Override
    public int getCategories() {
        if (configNames == null || configNames.isEmpty()) return 1;
        return (int) Math.ceil((double) configNames.size() / 24.0);
    }
}
