package net.novaproject.novauhc.scenario.random;

import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.LangResolver;
import net.novaproject.novauhc.lang.ui.ScenarioVariableUiLang;
import net.novaproject.novauhc.ui.ConfigVarUi;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.ui.CustomInventory;
import net.novaproject.novauhc.utils.ui.item.ActionItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

public class RandomEventConfigUi extends CustomInventory {

    private final RandomGameEvent<?> event;
    private final CustomInventory parent;

    public RandomEventConfigUi(Player player, RandomGameEvent<?> event, CustomInventory parent) {
        super(player);
        this.event = event;
        this.parent = parent;
    }

    private String t(ScenarioVariableUiLang key) {
        return LangManager.get().get(key, getPlayer());
    }

    private String t(ScenarioVariableUiLang key, Map<String, Object> p) {
        return LangManager.get().get(key, getPlayer(), p);
    }

    @Override
    public void setup() {
        fillCadre(7);
        addReturn(40, parent);
        int slot = 10;

        Class<?> clazz = event.getClass();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (!field.isAnnotationPresent(RandomEventVariable.class)) continue;
                field.setAccessible(true);
                RandomEventVariable annotation = field.getAnnotation(RandomEventVariable.class);

                try {
                    Object rawValue = field.get(event);
                    if (rawValue == null) rawValue = t(ScenarioVariableUiLang.NOT_DEFINED);

                    String displayValue = rawValue.toString();
                    switch (annotation.type()) {
                        case TIME -> { if (rawValue instanceof Integer i) displayValue = i + "s"; }
                        case PERCENTAGE -> {
                            if (rawValue instanceof Double d) displayValue = String.format("%.2f%%", d * 100);
                            else if (rawValue instanceof Integer i) displayValue = i + "%";
                        }
                    }

                    String varName = LangResolver.resolve(annotation.lang(), annotation.nameKey(), getPlayer());
                    String varDesc = LangResolver.resolve(annotation.lang(), annotation.descKey(), getPlayer());

                    addItem(new ActionItem(slot, new ItemCreator(Material.PAPER)
                            .setName("§e" + varName)
                            .setLores(Arrays.asList(
                                    "§7" + varDesc,
                                    "",
                                    t(ScenarioVariableUiLang.CURRENT_VALUE, Map.of("%value%", displayValue)),
                                    "",
                                    t(ScenarioVariableUiLang.CLICK_CHANGE)
                            ))) {
                        @Override
                        public void onClick(InventoryClickEvent e) {
                            try {
                                Object value = field.get(event);
                                if (value instanceof Boolean b) {
                                    field.set(event, !b);
                                    openAll();
                                } else if (value instanceof Number n) {
                                    new ConfigVarUi(getPlayer(), 1, 10, 1, 1, 10, 1, n, 0, 0, RandomEventConfigUi.this) {
                                        @Override
                                        public void onChange(Number newValue) {
                                            try {
                                                Class<?> type = field.getType();
                                                if (type == int.class || type == Integer.class)
                                                    field.set(event, newValue.intValue());
                                                else if (type == double.class || type == Double.class)
                                                    field.set(event, newValue.doubleValue());
                                                else if (type == float.class || type == Float.class)
                                                    field.set(event, newValue.floatValue());
                                                else if (type == long.class || type == Long.class)
                                                    field.set(event, newValue.longValue());
                                            } catch (IllegalAccessException ex) {
                                                ex.printStackTrace();
                                            }
                                        }
                                    }.open();
                                }
                            } catch (IllegalAccessException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                slot++;
                if ((slot + 1) % 9 == 0) slot += 2;
                if (slot >= 44) break;
            }
            clazz = clazz.getSuperclass();
        }
    }

    @Override
    public String getTitle() {
        return t(ScenarioVariableUiLang.ROLE_CONFIG_TITLE, Map.of("%name%", event.getName()));
    }

    @Override public int getLines() { return 5; }
    @Override public boolean isRefreshAuto() { return false; }
}
