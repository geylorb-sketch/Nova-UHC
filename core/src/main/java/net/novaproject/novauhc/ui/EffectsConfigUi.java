package net.novaproject.novauhc.ui;

import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.lang.CommonLang;
import net.novaproject.novauhc.lang.ui.GameUiLang;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.ui.CustomInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Map;

public class EffectsConfigUi extends CustomInventory {

    public EffectsConfigUi(Player player) {
        super(player);
    }

    private String t(GameUiLang key, Map<String, Object> params) {
        return LangManager.get().get(key, getPlayer(), params);
    }

    @Override
    public void setup() {
        fillCorner(getConfig().getInt("menu.game.color"));
        String clickAccess = LangManager.get().get(CommonLang.CLICK_HERE_TO_ACCESS, getPlayer());

        int forceVal = (int) (UHCManager.get().getGlobalForcePercent() * 100);
        ItemCreator forceItem = new ItemCreator(Material.BLAZE_POWDER)
                .setName(t(GameUiLang.FORCE_ITEM_NAME, Map.of("%value%", forceVal)))
                .addLore("")
                .addLore(t(GameUiLang.FORCE_ITEM_DESC))
                .addLore("")
                .addLore(clickAccess)
                .addLore("");

        int resiVal = (int) (UHCManager.get().getGlobalResistancePercent() * 100);
        ItemCreator resiItem = new ItemCreator(Material.IRON_CHESTPLATE)
                .setName(t(GameUiLang.RESI_ITEM_NAME, Map.of("%value%", resiVal)))
                .addLore("")
                .addLore(t(GameUiLang.RESI_ITEM_DESC))
                .addLore("")
                .addLore(clickAccess)
                .addLore("");

        int critVal = (int) (UHCManager.get().getGlobalForceCriticPercent() * 100);
        ItemCreator critItem = new ItemCreator(Material.NETHER_STAR)
                .setName(t(GameUiLang.CRIT_ITEM_NAME, Map.of("%value%", critVal)))
                .addLore("")
                .addLore(t(GameUiLang.CRIT_ITEM_DESC))
                .addLore("")
                .addLore(clickAccess)
                .addLore("");

        addMenu(11, forceItem, new ConfigVarUi(getPlayer(), 10, 5, 1, 10, 5, 1, forceVal, 0, 200, this) {
            @Override public void onChange(Number newValue) {
                double pct = newValue.intValue() / 100.0;
                UHCManager.get().setGlobalForcePercent(pct);
                UHCPlayerManager.get().getOnlineUHCPlayers().forEach(p -> p.setForcePercent(pct));
            }
        });
        addMenu(13, resiItem, new ConfigVarUi(getPlayer(), 10, 5, 1, 10, 5, 1, resiVal, 0, 200, this) {
            @Override public void onChange(Number newValue) {
                double pct = newValue.intValue() / 100.0;
                UHCManager.get().setGlobalResistancePercent(pct);
                UHCPlayerManager.get().getOnlineUHCPlayers().forEach(p -> p.setResistancePercent(pct));
            }
        });
        addMenu(15, critItem, new ConfigVarUi(getPlayer(), 10, 5, 1, 10, 5, 1, critVal, 0, 200, this) {
            @Override public void onChange(Number newValue) {
                double pct = newValue.intValue() / 100.0;
                UHCManager.get().setGlobalForceCriticPercent(pct);
                UHCPlayerManager.get().getOnlineUHCPlayers().forEach(p -> p.setForceCriticPercent(pct));
            }
        });

        addReturn(22, new GameUi(getPlayer()));
    }

    @Override
    public String getTitle() { return "§8Options — §fEffets globaux"; }

    @Override
    public int getLines() { return 3; }

    @Override
    public boolean isRefreshAuto() { return false; }
}
