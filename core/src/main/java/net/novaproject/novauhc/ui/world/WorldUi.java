package net.novaproject.novauhc.ui.world;

import net.novaproject.novauhc.Common;
import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.lang.lang.CommonLang;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.ui.UiTitleLang;
import net.novaproject.novauhc.lang.ui.WorldUiLang;
import net.novaproject.novauhc.task.LoadingChunkTask;
import net.novaproject.novauhc.ui.DefaultUi;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.UHCUtils;
import net.novaproject.novauhc.utils.ui.CustomInventory;
import net.novaproject.novauhc.utils.ui.item.ActionItem;
import net.novaproject.novauhc.world.generation.WorldGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class WorldUi extends CustomInventory {

    private final String accessHost = LangManager.get().get(CommonLang.ACCESS_HOST);

    public WorldUi(Player player) {
        super(player);
    }

    private String t(WorldUiLang key) {
        return LangManager.get().get(key, getPlayer());
    }

    @Override
    public void setup() {
        fillCorner(getConfig().getInt("menu.world.color"));

        String clickAccess = LangManager.get().get(CommonLang.CLICK_HERE_TO_ACCESS, getPlayer());

        ItemCreator orepop = new ItemCreator(Material.DIAMOND_PICKAXE)
                .setName(t(WorldUiLang.ORE_NAME))
                .addLore("")
                .addLore(LangManager.get().get(CommonLang.ACCESS_HOST, getPlayer()))
                .addLore("")
                .addLore(t(WorldUiLang.ORE_DESC1))
                .addLore(t(WorldUiLang.ORE_DESC2))
                .addLore("")
                .addLore(clickAccess)
                .addLore("");

        ItemCreator world = new ItemCreator(Material.ENDER_PEARL)
                .setName(t(WorldUiLang.ARENA_NAME))
                .addLore("")
                .addLore(LangManager.get().get(CommonLang.ACCESS_HOST, getPlayer()))
                .addLore("")
                .addLore(t(WorldUiLang.ARENA_DESC1))
                .addLore(t(WorldUiLang.ARENA_DESC2))
                .addLore("")
                .addLore(clickAccess)
                .addLore("");

        ItemCreator pregen = new ItemCreator(Material.GRASS)
                .setName(t(WorldUiLang.PREGEN_NAME))
                .addLore("")
                .addLore(LangManager.get().get(CommonLang.ACCESS_HOST, getPlayer()))
                .addLore("")
                .addLore(t(WorldUiLang.PREGEN_DESC))
                .addLore("")
                .addLore(clickAccess)
                .addLore("");

        boolean inArena = getPlayer().getWorld().getName().equals(Common.get().getArenaName());
        String tpName = inArena ? t(WorldUiLang.TP_LOBBY_NAME) : t(WorldUiLang.TP_ARENA_NAME);
        String tpDesc = inArena ? t(WorldUiLang.TP_LOBBY_DESC) : t(WorldUiLang.TP_ARENA_DESC);

        ItemCreator prev = new ItemCreator(Material.WOOD_DOOR)
                .setName(tpName)
                .addLore("")
                .addLore(LangManager.get().get(CommonLang.ACCESS_HOST, getPlayer()))
                .addLore("")
                .addLore(tpDesc)
                .addLore("")
                .addLore(clickAccess)
                .addLore("");

        ItemCreator changeSpawn = new ItemCreator(Material.SAPLING)
                .setName(t(WorldUiLang.CENTER_NAME))
                .addLore("")
                .addLore(LangManager.get().get(CommonLang.ACCESS_HOST, getPlayer()))
                .addLore("")
                .addLore(t(WorldUiLang.CENTER_DESC1))
                .addLore(t(WorldUiLang.CENTER_DESC2))
                .addLore("")
                .addLore(clickAccess)
                .addLore("");

        addItem(new ActionItem(22, prev) {
            @Override
            public void onClick(InventoryClickEvent e) {
                if (getPlayer().getWorld().equals(Common.get().getArena())) {
                    getPlayer().teleport(Common.get().getLobbySpawn());
                    getPlayer().getInventory().clear();
                    UHCUtils.giveLobbyItems(getPlayer());
                } else {
                    getPlayer().teleport(new Location(Common.get().getArena(), 0, 100, 0));
                    getPlayer().getInventory().clear();
                    getPlayer().getInventory().setItem(0, Common.get().getRegenArena().getItemstack());
                    getPlayer().getInventory().setItem(4, Common.get().getChangeSpawn().getItemstack());
                    getPlayer().getInventory().setItem(8, new ItemCreator(Material.WOOD_DOOR).setName(tpName).getItemstack());
                }
                openAll();
            }
        });

        addItem(new ActionItem(10, pregen) {
            @Override
            public void onClick(InventoryClickEvent e) {
                openAll();
                LoadingChunkTask.create(Common.get().getArena(),
                        Bukkit.getWorld(Common.get().getArenaName() + "_nether"),
                        (int) Common.get().getArena().getWorldBorder().getSize() / 2);
            }
        });

        addItem(new ActionItem(2, world) {
            @Override
            public void onClick(InventoryClickEvent e) {
                openAll();
                new WorldGenerator(Main.get(), Common.get().getArenaName());
            }
        });

        addReturn(18, new DefaultUi(getPlayer()));
        addMenu(6, orepop, new OrePopulatorUi(getPlayer()));
        addMenu(16, changeSpawn, new CenterUi(getPlayer(), this));
    }

    @Override
    public String getTitle() {
        return LangManager.get().get(UiTitleLang.WORLD_TITLE, getPlayer());
    }

    @Override
    public int getLines() { return 3; }

    @Override
    public boolean isRefreshAuto() { return false; }
}
