package net.novaproject.novauhc.ui.world;

import net.novaproject.novauhc.Common;
import net.novaproject.novauhc.lang.lang.CommonLang;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.ui.CenterUiLang;
import net.novaproject.novauhc.lang.ui.UiTitleLang;
import net.novaproject.novauhc.ui.ConfirmMenu;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.UHCUtils;
import net.novaproject.novauhc.utils.ui.CustomInventory;
import net.novaproject.novauhc.utils.ui.item.ActionItem;
import net.novaproject.novauhc.utils.ui.item.StaticItem;
import net.novaproject.novauhc.world.generation.CenterType;
import net.novaproject.novauhc.world.generation.WorldPopulator;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.Map;

public class CenterUi extends CustomInventory {
    private final String SOON = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmE1MmQ1NzlhZmUyZmRmN2I4ZWNmYTc0NmNkMDE2MTUwZDk2YmViNzUwMDliYjI3MzNhZGUxNWQ0ODdjNDJhMSJ9fX0=";
    private final CustomInventory parent;

    public CenterUi(Player player, CustomInventory parent) {
        super(player);
        this.parent = parent;
    }

    private String t(CenterUiLang key) {
        return LangManager.get().get(key, getPlayer());
    }

    private String t(CenterUiLang key, Map<String, Object> params) {
        return LangManager.get().get(key, getPlayer(), params);
    }

    @Override
    public void setup() {
        fillCorner(getConfig().getInt("menu.world.color"));

        addCenterType(CenterType.ROOFT,  10, new ItemCreator(Material.RED_MUSHROOM).setName(t(CenterUiLang.BIOME_LABEL, Map.of("%name%", "Rooft"))),  Biome.ROOFED_FOREST);
        addCenterType(CenterType.TAIGA,  11, new ItemCreator(Material.SNOW_BALL)   .setName(t(CenterUiLang.BIOME_LABEL, Map.of("%name%", "Taiga"))),  Biome.TAIGA_HILLS);
        addCenterType(CenterType.FOREST, 12, new ItemCreator(Material.SAPLING)     .setName(t(CenterUiLang.BIOME_LABEL, Map.of("%name%", "Forest"))), Biome.FOREST);
        addCenterType(CenterType.FLAT,   13, new ItemCreator(Material.GRASS)       .setName(t(CenterUiLang.BIOME_LABEL, Map.of("%name%", "Flat"))),   Biome.PLAINS);

        String soonLore = LangManager.get().get(CommonLang.CLICK_HERE_TO_ACCESS, getPlayer());
        addItem(new StaticItem(14, UHCUtils.createCustomButon(SOON, t(CenterUiLang.SOON_LABEL), Arrays.asList("", soonLore))));
        addItem(new StaticItem(15, UHCUtils.createCustomButon(SOON, t(CenterUiLang.SOON_LABEL), Arrays.asList("", soonLore))));
        addItem(new StaticItem(16, UHCUtils.createCustomButon(SOON, t(CenterUiLang.SOON_LABEL), Arrays.asList("", soonLore))));

        if (parent != null) addReturn(18, parent);
    }

    @Override
    public String getTitle() {
        return LangManager.get().get(UiTitleLang.CENTER_TITLE, getPlayer());
    }

    @Override
    public int getLines() { return 3; }

    @Override
    public boolean isRefreshAuto() { return false; }

    private void addCenterType(CenterType type, int slot, ItemCreator item, Biome biome) {
        addItem(new ActionItem(slot, item) {
            @Override
            public void onClick(InventoryClickEvent e) {
                new ConfirmMenu(getPlayer(), t(CenterUiLang.CONFIRM_TEXT),
                        () -> new WorldPopulator(Common.get().getArena(), type, biome),
                        null, CenterUi.this).open();
            }
        });
    }
}
