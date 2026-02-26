package net.novaproject.novauhc.ui;

import net.novaproject.novauhc.lang.lang.CommonLang;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.ui.ConfigVarUiLang;
import net.novaproject.novauhc.lang.ui.UiTitleLang;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.UHCUtils;
import net.novaproject.novauhc.utils.ui.CustomInventory;
import net.novaproject.novauhc.utils.ui.item.ActionItem;
import net.novaproject.novauhc.utils.ui.item.StaticItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.Map;

public abstract class ConfigVarUi extends CustomInventory {

    private final Number max_minus, mid_minus, min_minus;
    private final Number max_plus, mid_plus, min_plus;
    private final Number limitMin, limitMax;
    private final CustomInventory parent;
    private Number change;
    private final Class<?> numberType;

    public ConfigVarUi(Player player,
                       Number max_minus, Number mid_minus, Number min_minus,
                       Number max_plus, Number mid_plus, Number min_plus,
                       Number change, Number limitMin, Number limitMax,
                       CustomInventory parent) {
        super(player);
        this.max_minus = max_minus; this.mid_minus = mid_minus; this.min_minus = min_minus;
        this.max_plus  = max_plus;  this.mid_plus  = mid_plus;  this.min_plus  = min_plus;
        this.change    = change;
        this.limitMin  = limitMin;  this.limitMax  = limitMax;
        this.parent    = parent;
        this.numberType = change.getClass();
    }

    public abstract void onChange(Number newValue);

    private void updateValue(Number delta) {
        double result = change.doubleValue() + delta.doubleValue();
        if (limitMax.doubleValue() != 0) {
            if (result < limitMin.doubleValue() || result > limitMax.doubleValue()) return;
        }
        if      (numberType == Integer.class) change = (int) Math.round(result);
        else if (numberType == Double.class)  change = result;
        else if (numberType == Float.class)   change = (float) result;
        else if (numberType == Long.class)    change = (long) result;
        else                                  change = result;
        onChange(change);
        refresh();
    }

    private String t(ConfigVarUiLang key, Map<String, Object> params) {
        return LangManager.get().get(key, getPlayer(), params);
    }

    private ActionItem plusButton(int slot, Number value, String label) {
        return new ActionItem(slot, UHCUtils.greenPlus(Arrays.asList(
                t(ConfigVarUiLang.PLUS_BUTTON_NAME, Map.of("%value%", value)),
                "",
                t(ConfigVarUiLang.PLUS_DESC1, Map.of()),
                t(ConfigVarUiLang.PLUS_DESC2, Map.of("%value%", value)),
                "",
                LangManager.get().get(CommonLang.CLICK_HERE_TO_MODIFY, getPlayer())
        ), label)) {
            @Override
            public void onClick(InventoryClickEvent e) {
                updateValue(value);
                openAll();
            }
        };
    }

    private ActionItem minusButton(int slot, Number value, String label) {
        return new ActionItem(slot, UHCUtils.redMinus(Arrays.asList(
                t(ConfigVarUiLang.MINUS_BUTTON_NAME, Map.of("%value%", value)),
                "",
                t(ConfigVarUiLang.MINUS_DESC1, Map.of()),
                t(ConfigVarUiLang.MINUS_DESC2, Map.of("%value%", value)),
                "",
                LangManager.get().get(CommonLang.CLICK_HERE_TO_MODIFY, getPlayer())
        ), label)) {
            @Override
            public void onClick(InventoryClickEvent e) {
                updateValue(-value.doubleValue());
                openAll();
            }
        };
    }

    @Override
    public void setup() {
        addItem(plusButton(10, min_plus,  "§a+" + min_plus));
        addItem(plusButton(11, mid_plus,  "§a+" + mid_plus));
        addItem(plusButton(12, max_plus,  "§a+" + max_plus));

        String limitsLine = limitMax.doubleValue() == 0
                ? t(ConfigVarUiLang.LIMITS_INFINITE, Map.of("%min%", limitMin))
                : t(ConfigVarUiLang.LIMITS_BOUNDED,  Map.of("%min%", limitMin, "%max%", limitMax));

        addItem(new StaticItem(13, new ItemCreator(Material.PAPER)
                .setName(t(ConfigVarUiLang.CURRENT_VALUE, Map.of("%value%", change)))
                .setLores(Arrays.asList(
                        "",
                        t(ConfigVarUiLang.CURRENT_VALUE_DESC1, Map.of()),
                        t(ConfigVarUiLang.CURRENT_VALUE_DESC2, Map.of()),
                        "",
                        limitsLine
                ))));

        addItem(minusButton(14, max_minus, "§c-" + max_minus));
        addItem(minusButton(15, mid_minus, "§c-" + mid_minus));
        addItem(minusButton(16, min_minus, "§c-" + min_minus));

        addReturn(18, parent);
        fillCorner(0);
    }

    @Override
    public String getTitle() {
        return LangManager.get().get(UiTitleLang.CONFIGVAR_TITLE, getPlayer());
    }

    @Override
    public int getLines() { return 3; }

    @Override
    public boolean isRefreshAuto() { return false; }
}
