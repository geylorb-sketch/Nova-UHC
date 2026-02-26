package net.novaproject.novauhc.ui;

import net.novaproject.novauhc.Common;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.ui.MumbleUiLang;
import net.novaproject.novauhc.lang.ui.UiTitleLang;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.ui.CustomInventory;
import net.novaproject.novauhc.utils.ui.item.ActionItem;
import net.novaproject.novauhc.utils.ui.item.StaticItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Collections;
import java.util.Map;

public class MumbleUi extends CustomInventory {
    public MumbleUi(Player player) {
        super(player);
    }

    private String t(MumbleUiLang key) {
        return LangManager.get().get(key, getPlayer());
    }

    private String t(MumbleUiLang key, Map<String, Object> params) {
        return LangManager.get().get(key, getPlayer(), params);
    }

    @Override
    public void setup() {
        fillDesign(getConfig().getInt("menu.mumble.corner"));
        addClose(18);

        ItemCreator item = new ItemCreator(Material.JUKEBOX)
                .setName(t(MumbleUiLang.ITEM_NAME))
                .setLores(Collections.singletonList(t(MumbleUiLang.ITEM_LORE)));

        addItem(new ActionItem(13, item) {
            @Override
            public void onClick(InventoryClickEvent e) {
                getPlayer().sendMessage(t(MumbleUiLang.CLICK_MESSAGE,
                        Map.of("%ip%", Common.get().getMbip(), "%port%", Common.get().getMbport())));
            }
        });

        addItem(new StaticItem(11, new ItemCreator(Material.PAPER)
                .setName(t(MumbleUiLang.PORT_LABEL) + Common.get().getMbport())));
        addItem(new StaticItem(15, new ItemCreator(Material.MAP)
                .setName(t(MumbleUiLang.IP_LABEL) + Common.get().getMbip())));
    }

    @Override
    public String getTitle() {
        return LangManager.get().get(UiTitleLang.MUMBLE_TITLE, getPlayer());
    }

    @Override
    public int getLines() { return 3; }

    @Override
    public boolean isRefreshAuto() { return false; }
}
