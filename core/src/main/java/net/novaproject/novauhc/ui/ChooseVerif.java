package net.novaproject.novauhc.ui;

import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.lang.lang.CommonLang;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.ui.GameUiLang;
import net.novaproject.novauhc.lang.ui.UiTitleLang;
import net.novaproject.novauhc.ui.config.StuffUi;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.UHCUtils;
import net.novaproject.novauhc.utils.ui.CustomInventory;
import org.bukkit.entity.Player;

public class ChooseVerif extends CustomInventory {

    public ChooseVerif(Player player) {
        super(player);
    }

    private String t(GameUiLang key) {
        return LangManager.get().get(key, getPlayer());
    }

    @Override
    public void setup() {
        fillCorner(getConfig().getInt("menu.game.color"));

        String clickModify = LangManager.get().get(CommonLang.CLICK_HERE_TO_MODIFY, getPlayer());

        ItemCreator def = UHCUtils.createCustomButon(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmRiYmM1ODM2MDliNWYwMjUzN2NjM2NjMzZkNDBhNjBlMTM2NmEyMjJkYzU0ZjFlNzYxMTAwMGE4OTViMjMzNyJ9fX0=",
                t(GameUiLang.START_INV_NAME), null)
                .addLore("")
                .addLore(t(GameUiLang.VERIF_START_DESC))
                .addLore("")
                .addLore(clickModify)
                .addLore("");

        ItemCreator death = UHCUtils.createCustomButon(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjc4NDQ4MTljY2YyMDM1MDQ4M2Y5NDY5YjEwNTA3MmU2ZDQ1MjE0ZDdmMjZjYjg2N2YxODkxMGJjYzFkY2RiIn19fQ==",
                t(GameUiLang.DEATH_INV_NAME), null)
                .addLore("")
                .addLore(t(GameUiLang.VERIF_DEATH_DESC))
                .addLore("")
                .addLore(clickModify)
                .addLore("");

        addMenu(12, death, new StuffUi(getPlayer(), UHCManager.get().death));
        addMenu(14, def,   new StuffUi(getPlayer(), UHCManager.get().start));
        addReturn(18, new GameUi(getPlayer()));
    }

    @Override
    public String getTitle() {
        return LangManager.get().get(UiTitleLang.GAME_TITLE, getPlayer());
    }

    @Override
    public int getLines() { return 3; }

    @Override
    public boolean isRefreshAuto() { return false; }
}
