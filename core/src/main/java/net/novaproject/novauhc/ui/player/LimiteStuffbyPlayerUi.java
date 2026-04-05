package net.novaproject.novauhc.ui.player;

import net.novaproject.novauhc.lang.lang.CommonLang;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.ui.LimiteStuffUiLang;
import net.novaproject.novauhc.lang.ui.UiTitleLang;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.ui.ConfigVarUi;
import net.novaproject.novauhc.ui.config.Enchants;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.ui.CustomInventory;
import net.novaproject.novauhc.utils.ui.item.ActionItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Map;

public class  LimiteStuffbyPlayerUi extends CustomInventory {
    Player target;

    public LimiteStuffbyPlayerUi(Player player, Player target) {
        super(player);
        this.target = target;
    }

    private String t(LimiteStuffUiLang key) {
        return LangManager.get().get(key, getPlayer());
    }

    private String t(LimiteStuffUiLang key, Map<String, Object> params) {
        return LangManager.get().get(key, getPlayer(), params);
    }

    @Override
    public void setup() {
        UHCPlayer targetPlayer = UHCPlayerManager.get().getPlayer(target);
        fillCorner(getConfig().getInt("menu.stuff.color"));

        String clickGauche  = LangManager.get().get(CommonLang.CLICK_GAUCHE,          getPlayer());
        String clickDroite  = LangManager.get().get(CommonLang.CLICK_DROITE,          getPlayer());
        String clickAccess  = LangManager.get().get(CommonLang.CLICK_HERE_TO_ACCESS,  getPlayer());

        String diamondLimitValue = targetPlayer.getDimamondLimit() == 0
                ? t(LimiteStuffUiLang.DISABLED_VALUE)
                : String.valueOf(targetPlayer.getDimamondLimit());

        ItemCreator diamondlimite = new ItemCreator(Material.DIAMOND)
                .setAmount(getUHCPlayer().getDimamondLimit())
                .setName(t(LimiteStuffUiLang.DIAMOND_LIMIT_LABEL, Map.of("%value%", diamondLimitValue)));

        ItemCreator diamond = new ItemCreator(Material.DIAMOND_CHESTPLATE)
                .setName(t(LimiteStuffUiLang.DIAMOND_NAME))
                .addLore("")
                .addLore(t(LimiteStuffUiLang.DIAMOND_CURRENT, Map.of("%value%", targetPlayer.getDiamondArmor())))
                .addLore("")
                .addLore(clickGauche + t(LimiteStuffUiLang.PLUS_ONE))
                .addLore(clickDroite + t(LimiteStuffUiLang.MINUS_ONE))
                .addLore("");

        // Row 0 — limites armure (gauche) et effets % (droite)
        addItem(new ActionItem(1, diamond.setAmount(targetPlayer.getDiamondArmor())) {
            @Override
            public void onClick(InventoryClickEvent e) {
                if (e.isLeftClick()  && targetPlayer.getDiamondArmor() < 4) targetPlayer.setDiamondArmor(targetPlayer.getDiamondArmor() + 1);
                if (e.isRightClick() && targetPlayer.getDiamondArmor() > 0) targetPlayer.setDiamondArmor(targetPlayer.getDiamondArmor() - 1);
                openAll();
            }
        });

        ItemCreator protection = new ItemCreator(Material.ENCHANTED_BOOK)
                .setName(t(LimiteStuffUiLang.PROT_NAME))
                .addLore("")
                .addLore(t(LimiteStuffUiLang.PROT_CURRENT, Map.of("%value%", targetPlayer.getProtectionMax())))
                .addLore("")
                .addLore(clickGauche + t(LimiteStuffUiLang.PLUS_ONE))
                .addLore(clickDroite + t(LimiteStuffUiLang.MINUS_ONE))
                .addLore("");

        addItem(new ActionItem(2, protection.setAmount(targetPlayer.getProtectionMax())) {
            @Override
            public void onClick(InventoryClickEvent e) {
                if (e.isLeftClick()  && targetPlayer.getProtectionMax() < 4) targetPlayer.setProtectionMax(targetPlayer.getProtectionMax() + 1);
                if (e.isRightClick() && targetPlayer.getProtectionMax() > 0) targetPlayer.setProtectionMax(targetPlayer.getProtectionMax() - 1);
                openAll();
            }
        });

        addMenu(3, diamondlimite, new ConfigVarUi(getPlayer(), 10, 5, 1, 10, 5, 1, getUHCPlayer().getDimamondLimit(), 0, 0, this) {
            @Override
            public void onChange(Number newValue) {
                targetPlayer.setDimamondLimit((int) newValue);
            }
        });

        int forceVal = (int) (targetPlayer.getForcePercent() * 100);
        ItemCreator forceItem = new ItemCreator(Material.BLAZE_POWDER)
                .setName(t(LimiteStuffUiLang.FORCE_ITEM_NAME, Map.of("%value%", forceVal)))
                .addLore("")
                .addLore(t(LimiteStuffUiLang.FORCE_ITEM_DESC))
                .addLore("")
                .addLore(clickAccess)
                .addLore("");

        addMenu(5, forceItem, new ConfigVarUi(getPlayer(), 10, 5, 1, 10, 5, 1, forceVal, 0, 200, this) {
            @Override public void onChange(Number newValue) {
                targetPlayer.setForcePercent(newValue.intValue() / 100.0);
            }
        });

        int resiVal = (int) (targetPlayer.getResistancePercent() * 100);
        ItemCreator resiItem = new ItemCreator(Material.IRON_CHESTPLATE)
                .setName(t(LimiteStuffUiLang.RESI_ITEM_NAME, Map.of("%value%", resiVal)))
                .addLore("")
                .addLore(t(LimiteStuffUiLang.RESI_ITEM_DESC))
                .addLore("")
                .addLore(clickAccess)
                .addLore("");

        addMenu(6, resiItem, new ConfigVarUi(getPlayer(), 10, 5, 1, 10, 5, 1, resiVal, 0, 200, this) {
            @Override public void onChange(Number newValue) {
                targetPlayer.setResistancePercent(newValue.intValue() / 100.0);
            }
        });

        int critVal = (int) (targetPlayer.getForceCriticPercent() * 100);
        ItemCreator critItem = new ItemCreator(Material.NETHER_STAR)
                .setName(t(LimiteStuffUiLang.CRIT_ITEM_NAME, Map.of("%value%", critVal)))
                .addLore("")
                .addLore(t(LimiteStuffUiLang.CRIT_ITEM_DESC))
                .addLore("")
                .addLore(clickAccess)
                .addLore("");

        addMenu(7, critItem, new ConfigVarUi(getPlayer(), 10, 5, 1, 10, 5, 1, critVal, 0, 200, this) {
            @Override public void onChange(Number newValue) {
                targetPlayer.setForceCriticPercent(newValue.intValue() / 100.0);
            }
        });

        int i = 10;
        for (Enchants enchants : getUHCPlayer().getEnchantLimits().keySet()) {
            addItem(new ActionItem(i, new ItemCreator(Material.ENCHANTED_BOOK)
                    .setName(enchants.getName())
                    .addLore("")
                    .addLore(t(LimiteStuffUiLang.ENCHANT_CURRENT, Map.of("%value%", getUHCPlayer().getEnchantLimits().get(enchants))))
                    .addLore("")
                    .addLore(clickGauche + t(LimiteStuffUiLang.PLUS_ONE))
                    .addLore(clickDroite + t(LimiteStuffUiLang.MINUS_ONE))
                    .addLore("")) {
                @Override
                public void onClick(InventoryClickEvent e) {
                    if (e.isLeftClick()) {
                        int newValue = Math.min(targetPlayer.getEnchantLimits().get(enchants) + 1, enchants.getMax());
                        targetPlayer.setEnchantLimit(enchants, newValue);
                    }
                    if (e.isRightClick()) {
                        int newValue = Math.max(targetPlayer.getEnchantLimits().get(enchants) - 1, enchants.getMin());
                        targetPlayer.setEnchantLimit(enchants, newValue);
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
        return LangManager.get().get(UiTitleLang.ENCHANT_TITLE, getPlayer()).replace("%player%", target.getName());
    }

    @Override
    public int getLines() { return 6; }

    @Override
    public boolean isRefreshAuto() { return false; }
}
