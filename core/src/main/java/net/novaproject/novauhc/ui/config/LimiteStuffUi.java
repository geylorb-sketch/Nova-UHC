package net.novaproject.novauhc.ui.config;

import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.lang.lang.CommonLang;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.ui.LimiteStuffUiLang;
import net.novaproject.novauhc.lang.ui.UiTitleLang;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.ui.GameUi;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.ui.CustomInventory;
import net.novaproject.novauhc.utils.ui.item.ActionItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Map;

public class LimiteStuffUi extends CustomInventory {

    public LimiteStuffUi(Player player) {
        super(player);
    }

    private String t(LimiteStuffUiLang key) {
        return LangManager.get().get(key, getPlayer());
    }

    private String t(LimiteStuffUiLang key, Map<String, Object> params) {
        return LangManager.get().get(key, getPlayer(), params);
    }

    @Override
    public void setup() {
        fillCorner(getConfig().getInt("menu.stuff.color"));
        addReturn(45, new GameUi(getPlayer()));

        String clickGauche = LangManager.get().get(CommonLang.CLICK_GAUCHE, getPlayer());
        String clickDroite = LangManager.get().get(CommonLang.CLICK_DROITE, getPlayer());

        ItemCreator diamond = new ItemCreator(Material.DIAMOND_CHESTPLATE)
                .setName(t(LimiteStuffUiLang.DIAMOND_NAME))
                .addLore("")
                .addLore(t(LimiteStuffUiLang.DIAMOND_CURRENT, Map.of("%value%", UHCManager.get().getDiamondArmor())))
                .addLore("")
                .addLore(clickGauche + t(LimiteStuffUiLang.PLUS_ONE))
                .addLore(clickDroite + t(LimiteStuffUiLang.MINUS_ONE))
                .addLore("");

        addItem(new ActionItem(3, diamond.setAmount(UHCManager.get().getDiamondArmor())) {
            @Override
            public void onClick(InventoryClickEvent e) {
                int current = UHCManager.get().getDiamondArmor();
                if (e.isLeftClick()) {
                    int newValue = Math.min(current + 1, 4);
                    UHCPlayerManager.get().getOnlineUHCPlayers().forEach(p -> p.setDiamondArmor(newValue));
                    UHCManager.get().setDiamondArmor(newValue);
                }
                if (e.isRightClick()) {
                    int newValue = Math.max(current - 1, 0);
                    UHCPlayerManager.get().getOnlineUHCPlayers().forEach(p -> p.setDiamondArmor(newValue));
                    UHCManager.get().setDiamondArmor(newValue);
                }
                openAll();
            }
        });

        ItemCreator protection = new ItemCreator(Material.ENCHANTED_BOOK)
                .setName(t(LimiteStuffUiLang.PROT_NAME))
                .addLore("")
                .addLore(t(LimiteStuffUiLang.PROT_CURRENT, Map.of("%value%", UHCManager.get().getProtectionMax())))
                .addLore("")
                .addLore(clickGauche + t(LimiteStuffUiLang.PLUS_ONE))
                .addLore(clickDroite + t(LimiteStuffUiLang.MINUS_ONE))
                .addLore("");

        addItem(new ActionItem(5, protection.setAmount(UHCManager.get().getProtectionMax())) {
            @Override
            public void onClick(InventoryClickEvent e) {
                int current = UHCManager.get().getProtectionMax();
                if (e.isLeftClick()) {
                    int newValue = Math.min(current + 1, 4);
                    UHCPlayerManager.get().getOnlineUHCPlayers().forEach(p -> p.setProtectionMax(newValue));
                    UHCManager.get().setProtectionMax(newValue);
                }
                if (e.isRightClick()) {
                    int newValue = Math.max(current - 1, 0);
                    UHCPlayerManager.get().getOnlineUHCPlayers().forEach(p -> p.setProtectionMax(newValue));
                    UHCManager.get().setProtectionMax(newValue);
                }
                openAll();
            }
        });

        int i = 10;
        for (Enchants enchants : Enchants.values()) {
            addItem(new ActionItem(i, new ItemCreator(Material.ENCHANTED_BOOK)
                    .setName(enchants.getName())
                    .addLore("")
                    .addLore(t(LimiteStuffUiLang.ENCHANT_CURRENT, Map.of("%value%", enchants.getConfigValue())))
                    .addLore("")
                    .addLore(clickGauche + t(LimiteStuffUiLang.PLUS_ONE))
                    .addLore(clickDroite + t(LimiteStuffUiLang.MINUS_ONE))
                    .addLore("")) {
                @Override
                public void onClick(InventoryClickEvent e) {
                    if (e.isLeftClick()) {
                        enchants.addConfigValue();
                        UHCPlayerManager.get().getOnlineUHCPlayers().forEach(p -> p.setEnchantLimit(enchants, enchants.getConfigValue()));
                    }
                    if (e.isRightClick()) {
                        enchants.removeConfigValue();
                        UHCPlayerManager.get().getOnlineUHCPlayers().forEach(p -> p.setEnchantLimit(enchants, enchants.getConfigValue()));
                    }
                    openAll();
                }
            });
            switch (i) {
                case 16: i = 19; break;
                case 25: i = 28; break;
                case 34: i = 37; break;
                case 43: i = 46; break;
                default: i++;    break;
            }
        }
    }

    @Override
    public String getTitle() {
        return LangManager.get().get(UiTitleLang.STUFF_TITLE, getPlayer());
    }

    @Override
    public int getLines() { return 6; }

    @Override
    public boolean isRefreshAuto() { return false; }
}
