package net.novaproject.novauhc.ui.world;

import net.novaproject.novauhc.Common;
import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.lang.lang.CommonLang;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.ui.BorderConfigUiLang;
import net.novaproject.novauhc.lang.ui.UiTitleLang;
import net.novaproject.novauhc.ui.ConfigVarUi;
import net.novaproject.novauhc.ui.DefaultUi;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.ui.CustomInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Map;

public class BorderConfig extends CustomInventory {
    public BorderConfig(Player player) {
        super(player);
    }

    private String t(BorderConfigUiLang key, Map<String, Object> params) {
        return LangManager.get().get(key, getPlayer(), params);
    }

    @Override
    public void setup() {
        fillCorner(getConfig().getInt("menu.border.color"));

        double borderSize  = Common.get().getArena().getWorldBorder().getSize();
        long   borderSpeed = UHCManager.get().getReducSpeed();
        double targetSize  = UHCManager.get().getTargetSize();

        String clickModify = LangManager.get().get(CommonLang.CLICK_HERE_TO_MODIFY, getPlayer());

        ItemCreator border = new ItemCreator(Material.STAINED_GLASS).setDurability((short) 3)
                .setName(t(BorderConfigUiLang.INITIAL_NAME, Map.of("%value%", borderSize)))
                .addLore("")
                .addLore(t(BorderConfigUiLang.INITIAL_DESC1, Map.of()))
                .addLore(t(BorderConfigUiLang.INITIAL_DESC2, Map.of()))
                .addLore("")
                .addLore(clickModify)
                .addLore("");

        ItemCreator borderSpeedI = new ItemCreator(Material.WATCH)
                .setName(t(BorderConfigUiLang.SPEED_NAME, Map.of("%value%", borderSpeed)))
                .addLore("")
                .addLore(t(BorderConfigUiLang.SPEED_DESC1, Map.of()))
                .addLore(t(BorderConfigUiLang.SPEED_DESC2, Map.of()))
                .addLore("")
                .addLore(clickModify)
                .addLore("");

        ItemCreator finalSize = new ItemCreator(Material.STAINED_GLASS).setDurability((short) 14)
                .setName(t(BorderConfigUiLang.FINAL_NAME, Map.of("%value%", targetSize)))
                .addLore("")
                .addLore(t(BorderConfigUiLang.FINAL_DESC1, Map.of()))
                .addLore(t(BorderConfigUiLang.FINAL_DESC2, Map.of()))
                .addLore("")
                .addLore(clickModify)
                .addLore("");

        addMenu(12, border, new ConfigVarUi(getPlayer(), 500, 250, 100, 500, 250, 100, (int) borderSize, 250, 4000, this) {
            @Override
            public void onChange(Number newValue) {
                Common.get().getArena().getWorldBorder().setSize(newValue.doubleValue());
            }
        });
        addMenu(13, borderSpeedI, new ConfigVarUi(getPlayer(), 10, 5, 1, 10, 5, 1, (int) UHCManager.get().getReducSpeed(), 1, 15, this) {
            @Override
            public void onChange(Number newValue) {
                UHCManager.get().setReducSpeed((long) newValue);
            }
        });
        addMenu(14, finalSize, new ConfigVarUi(getPlayer(), 100, 50, 10, 100, 50, 10, (int) targetSize, 10, 500, this) {
            @Override
            public void onChange(Number newValue) {
                UHCManager.get().setTargetSize(newValue.doubleValue());
            }
        });
        addReturn(22, new DefaultUi(getPlayer()));
    }

    @Override
    public String getTitle() {
        return LangManager.get().get(UiTitleLang.BORDER_TITLE, getPlayer());
    }

    @Override
    public int getLines() { return 3; }

    @Override
    public boolean isRefreshAuto() { return false; }
}
