package net.novaproject.novauhc.ui;

import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.lang.lang.CommonLang;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.ui.GameUiLang;
import net.novaproject.novauhc.lang.ui.UiTitleLang;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.ui.config.DropUi;
import net.novaproject.novauhc.ui.config.LimiteStuffUi;
import net.novaproject.novauhc.ui.config.PotionUi;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.UHCUtils;
import net.novaproject.novauhc.utils.ui.CustomInventory;
import net.novaproject.novauhc.utils.ui.item.ActionItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Map;

public class GameUi extends CustomInventory {
    public GameUi(Player player) {
        super(player);
    }

    private String t(GameUiLang key) {
        return LangManager.get().get(key, getPlayer());
    }

    private String t(GameUiLang key, Map<String, Object> params) {
        return LangManager.get().get(key, getPlayer(), params);
    }

    @Override
    public void setup() {
        fillCorner(getConfig().getInt("menu.game.color"));

        int limit_diams  = UHCManager.get().getDimamondLimit();
        int border_time  = UHCManager.get().getTimerborder();
        int pvp_time     = UHCManager.get().getTimerpvp();

        String clickAccess = LangManager.get().get(CommonLang.CLICK_HERE_TO_ACCESS, getPlayer());
        String clickModify = LangManager.get().get(CommonLang.CLICK_HERE_TO_MODIFY, getPlayer());

        ItemCreator potion = new ItemCreator(Material.POTION)
                .setName(t(GameUiLang.POTION_ITEM_NAME))
                .addLore("")
                .addLore(t(GameUiLang.POTION_ITEM_DESC))
                .addLore("")
                .addLore(clickAccess)
                .addLore("");

        ItemCreator pvp = new ItemCreator(Material.DIAMOND_SWORD)
                .setName(t(GameUiLang.PVP_ITEM_NAME, Map.of("%time%", UHCUtils.getFormattedTime(pvp_time))))
                .addLore("")
                .addLore(t(GameUiLang.PVP_ITEM_DESC))
                .addLore("");

        ItemCreator bordure = new ItemCreator(Material.STAINED_GLASS).setDurability((short) 4)
                .setName(t(GameUiLang.BORDER_ITEM_NAME, Map.of("%time%", UHCUtils.getFormattedTime(border_time))))
                .addLore("")
                .addLore(t(GameUiLang.BORDER_ITEM_DESC))
                .addLore("");

        ItemCreator diams = new ItemCreator(Material.DIAMOND)
                .setName(t(GameUiLang.DIAMS_ITEM_NAME, Map.of("%value%", limit_diams)))
                .addLore("")
                .addLore(t(GameUiLang.DIAMS_ITEM_DESC))
                .addLore("")
                .addLore(clickAccess)
                .addLore("");

        ItemCreator enchant = new ItemCreator(Material.ENCHANTED_BOOK)
                .setName(t(GameUiLang.ENCHANT_ITEM_NAME))
                .addLore("")
                .addLore(t(GameUiLang.ENCHANT_ITEM_DESC))
                .addLore("")
                .addLore(clickModify)
                .addLore("");

        ItemCreator verif = UHCUtils.createCustomButon(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTkzMmI5Yzc0NGIxYTgwYjEzOTQwYjc0NmM4MTFjMTUwN2Y3YWMyMDJhYTI2OGRhMDFiMzU0ZjU0NmJlYWY0NCJ9fX0=",
                t(GameUiLang.VERIF_ITEM_NAME), null)
                .addLore("")
                .addLore(t(GameUiLang.VERIF_ITEM_DESC))
                .addLore("")
                .addLore(clickModify)
                .addLore("");

        ItemCreator def = UHCUtils.createCustomButon(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmRiYmM1ODM2MDliNWYwMjUzN2NjM2NjMzZkNDBhNjBlMTM2NmEyMjJkYzU0ZjFlNzYxMTAwMGE4OTViMjMzNyJ9fX0=",
                t(GameUiLang.START_INV_NAME), null)
                .addLore("")
                .addLore(t(GameUiLang.START_INV_DESC))
                .addLore("")
                .addLore(clickModify)
                .addLore("");

        ItemCreator death = UHCUtils.createCustomButon(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjc4NDQ4MTljY2YyMDM1MDQ4M2Y5NDY5YjEwNTA3MmU2ZDQ1MjE0ZDdmMjZjYjg2N2YxODkxMGJjYzFkY2RiIn19fQ==",
                t(GameUiLang.DEATH_INV_NAME), null)
                .addLore("")
                .addLore(t(GameUiLang.DEATH_INV_DESC))
                .addLore("")
                .addLore(clickModify)
                .addLore("");

        ItemCreator drop = new ItemCreator(Material.APPLE)
                .setName(t(GameUiLang.DROP_ITEM_NAME))
                .addLore("")
                .addLore(t(GameUiLang.DROP_ITEM_DESC))
                .addLore("")
                .addLore(clickAccess)
                .addLore("");

        addItem(new ActionItem(18, def) {
            @Override
            public void onClick(InventoryClickEvent e) {
                getPlayer().closeInventory();
                Bukkit.dispatchCommand(getPlayer(), "h stuff start modif ");
            }
        });
        addItem(new ActionItem(26, death) {
            @Override
            public void onClick(InventoryClickEvent e) {
                getPlayer().closeInventory();
                Bukkit.dispatchCommand(getPlayer(), "h stuff death modif ");
            }
        });

        addMenu(22, verif,  new ChooseVerif(getPlayer()));
        addMenu(29, potion, new PotionUi(getPlayer()));
        addMenu(33, drop,   new DropUi(getPlayer()));
        addMenu(15, diams,  new ConfigVarUi(getPlayer(), 10, 5, 1, 10, 5, 1, UHCManager.get().getDimamondLimit(), 0, 0, this) {
            @Override public void onChange(Number newValue) {
                UHCManager.get().setDimamondLimit((int) newValue);
                UHCPlayerManager.get().getOnlineUHCPlayers().forEach(p -> p.setDimamondLimit((int) newValue));
            }
        });
        addMenu(10, pvp, new ConfigVarUi(getPlayer(), 10, 5, 1, 10, 5, 1, UHCManager.get().getTimerpvp() / 60, 1, 60, this) {
            @Override public void onChange(Number newValue) { UHCManager.get().setTimerpvp((int) newValue * 60); }
        });
        addMenu(11, bordure, new ConfigVarUi(getPlayer(), 10, 5, 1, 10, 5, 1, UHCManager.get().getTimerborder() / 60, 60, 120, this) {
            @Override public void onChange(Number newValue) { UHCManager.get().setTimerborder((int) newValue * 60); }
        });
        addMenu(16, enchant, new LimiteStuffUi(getPlayer()));
        addReturn(36, new DefaultUi(getPlayer()));
    }

    @Override
    public String getTitle() {
        return LangManager.get().get(UiTitleLang.GAME_TITLE, getPlayer());
    }

    @Override
    public int getLines() { return 5; }

    @Override
    public boolean isRefreshAuto() { return false; }
}
