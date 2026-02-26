package net.novaproject.novauhc.ui.world;

import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.lang.lang.CommonLang;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.ui.OrePopulatorUiLang;
import net.novaproject.novauhc.lang.ui.UiTitleLang;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.UHCUtils;
import net.novaproject.novauhc.utils.ui.CustomInventory;
import net.novaproject.novauhc.utils.ui.item.ActionItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OrePopulatorUi extends CustomInventory {

    UHCManager manager = UHCManager.get();
    private final double previous_Boost_diams = manager.getBoost_Diamond();
    private final double previous_Boost_gold  = manager.getBoost_Gold();
    private final double previous_Boost_iron  = manager.getBoost_Iron();
    private final double previous_Boost_lapis = manager.getBoost_Lapis();
    private boolean change = false;

    public OrePopulatorUi(Player player) {
        super(player);
    }

    private String t(OrePopulatorUiLang key) {
        return LangManager.get().get(key, getPlayer());
    }

    private String t(OrePopulatorUiLang key, Map<String, Object> params) {
        return LangManager.get().get(key, getPlayer(), params);
    }

    @Override
    public void setup() {
        change = false;
        fillLine(1, getConfig().getInt("menu.world.oreboost.color"));

        List<String> lore = Arrays.asList("", t(OrePopulatorUiLang.LORE_LEFT), t(OrePopulatorUiLang.LORE_RIGHT), "");

        ItemCreator lapis = UHCUtils.createCustomButon(getConfig().getString("menu.world.oreboost.lapis_texture"), t(OrePopulatorUiLang.LAPIS_LABEL),  lore);
        ItemCreator iron  = UHCUtils.createCustomButon(getConfig().getString("menu.world.oreboost.iron_texture"),  t(OrePopulatorUiLang.IRON_LABEL),   lore);
        ItemCreator gold  = UHCUtils.createCustomButon(getConfig().getString("menu.world.oreboost.gold_texture"),  t(OrePopulatorUiLang.GOLD_LABEL),   lore);
        ItemCreator diam  = UHCUtils.createCustomButon(getConfig().getString("menu.world.oreboost.diams_texture"), t(OrePopulatorUiLang.DIAMOND_LABEL), lore);

        addItem(new ActionItem(0, new ItemCreator(Material.ARROW).setName(t(OrePopulatorUiLang.BACK_BUTTON))) {
            @Override
            public void onClick(InventoryClickEvent e) {
                onClose();
                new WorldUi(getPlayer()).open();
            }
        });

        addItem(new ActionItem(3, diam.addLore(t(OrePopulatorUiLang.CURRENT_BOOST,
                Map.of("%value%", UHCUtils.calculPourcentage(manager.getBoost_Diamond(), 2.3))))) {
            @Override
            public void onClick(InventoryClickEvent e) {
                if (e.isLeftClick()) manager.setBoost_Diamond(manager.getBoost_Diamond() + 0.23);
                else                 manager.setBoost_Diamond(manager.getBoost_Diamond() - 0.23);
                if (manager.getBoost_Diamond() != previous_Boost_diams) change = true;
                openAll();
            }
        });

        addItem(new ActionItem(4, gold.addLore(t(OrePopulatorUiLang.CURRENT_BOOST,
                Map.of("%value%", UHCUtils.calculPourcentage(manager.getBoost_Gold(), 2.55))))) {
            @Override
            public void onClick(InventoryClickEvent e) {
                if (e.isLeftClick()) manager.setBoost_Gold(manager.getBoost_Gold() + 0.255);
                else                 manager.setBoost_Gold(manager.getBoost_Gold() - 0.255);
                if (manager.getBoost_Gold() != previous_Boost_gold) change = true;
                openAll();
            }
        });

        addItem(new ActionItem(5, iron.addLore(t(OrePopulatorUiLang.CURRENT_BOOST,
                Map.of("%value%", UHCUtils.calculPourcentage(manager.getBoost_Iron(), 2))))) {
            @Override
            public void onClick(InventoryClickEvent e) {
                if (e.isLeftClick()) manager.setBoost_Iron(manager.getBoost_Iron() + 0.2);
                else                 manager.setBoost_Iron(manager.getBoost_Iron() - 0.2);
                if (manager.getBoost_Iron() != previous_Boost_iron) change = true;
                openAll();
            }
        });

        addItem(new ActionItem(6, lapis.addLore(t(OrePopulatorUiLang.CURRENT_BOOST,
                Map.of("%value%", UHCUtils.calculPourcentage(manager.getBoost_Lapis(), 2.22))))) {
            @Override
            public void onClick(InventoryClickEvent e) {
                if (e.isLeftClick()) manager.setBoost_Lapis(manager.getBoost_Lapis() + 0.222);
                else                 manager.setBoost_Lapis(manager.getBoost_Lapis() - 0.222);
                if (manager.getBoost_Lapis() != previous_Boost_lapis) change = true;
                openAll();
            }
        });

        addItem(new ActionItem(8, UHCUtils.getReset()) {
            @Override
            public void onClick(InventoryClickEvent e) {
                manager.setBoost_Diamond(previous_Boost_diams);
                manager.setBoost_Gold(previous_Boost_gold);
                manager.setBoost_Iron(previous_Boost_iron);
                manager.setBoost_Lapis(previous_Boost_lapis);
                change = false;
                openAll();
            }
        });
    }

    @Override
    public String getTitle() {
        return LangManager.get().get(UiTitleLang.OREBOOST_TITLE, getPlayer());
    }

    @Override
    public int getLines() { return 1; }

    @Override
    public boolean isRefreshAuto() { return false; }

    @Override
    public void onClose() {
        if (change) LangManager.get().send(CommonLang.ORE_BOOST, getPlayer());
        super.onClose();
    }
}
