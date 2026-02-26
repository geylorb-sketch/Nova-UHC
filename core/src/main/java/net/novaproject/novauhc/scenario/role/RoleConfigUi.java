package net.novaproject.novauhc.scenario.role;

import net.novaproject.novauhc.ability.utils.AbbilityConfigUi;
import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.ui.ScenarioVariableUiLang;
import net.novaproject.novauhc.ui.ConfigVarUi;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.UHCUtils;
import net.novaproject.novauhc.utils.ui.AnvilUi;
import net.novaproject.novauhc.utils.ui.CustomInventory;
import net.novaproject.novauhc.utils.ui.item.ActionItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

public class RoleConfigUi extends CustomInventory {

    private final Role role;
    private final CustomInventory parent;

    public RoleConfigUi(Player player, Role role, CustomInventory parent) {
        super(player);
        this.role = role;
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

        for (Field field : role.getClass().getDeclaredFields()) {
            if (!field.isAnnotationPresent(RoleVariable.class)) continue;
            RoleVariable annotation = field.getAnnotation(RoleVariable.class);
            field.setAccessible(true);

            try {
                Object rawValue = field.get(role);

                if (rawValue instanceof Ability ability) {
                    for (Field abilityField : ability.getClass().getDeclaredFields()) {
                        if (abilityField.isAnnotationPresent(AbilityVariable.class)) {
                            addItem(new ActionItem(slot, new ItemCreator(Material.PAPER)
                                    .setName(t(ScenarioVariableUiLang.CONFIG_BUTTON, Map.of("%name%", ability.getName())))
                                    .setLores(Arrays.asList(
                                            t(ScenarioVariableUiLang.CONFIG_LORE),
                                            "",
                                            t(ScenarioVariableUiLang.CONFIG_ABILITY_DESC1),
                                            t(ScenarioVariableUiLang.CONFIG_ABILITY_DESC2, Map.of("%name%", ability.getName()))
                                    ))) {
                                @Override
                                public void onClick(InventoryClickEvent e) {
                                    new AbbilityConfigUi(getPlayer(), ability, RoleConfigUi.this).open();
                                }
                            });
                        }
                    }
                }

                String displayValue = rawValue.toString();
                switch (annotation.type()) {
                    case TIME -> {
                        if (rawValue instanceof Integer i)
                            displayValue = UHCUtils.getFormattedTime(i);
                    }
                    case PERCENTAGE -> {
                        if (rawValue instanceof Double d)
                            displayValue = String.format("%.2f%%", d * 100);
                        else if (rawValue instanceof Integer i)
                            displayValue = i + "%";
                    }
                }

                ItemCreator icon = new ItemCreator(Material.PAPER)
                        .setName("§e" + annotation.name())
                        .addLore("§7" + annotation.description())
                        .addLore("")
                        .addLore(t(ScenarioVariableUiLang.CURRENT_VALUE, Map.of("%value%", displayValue)))
                        .addLore("")
                        .addLore(t(ScenarioVariableUiLang.CLICK_CHANGE));

                if (rawValue instanceof Boolean) {
                    addItem(new ActionItem(slot, icon) {
                        @Override
                        public void onClick(InventoryClickEvent e) {
                            try {
                                field.set(role, !(boolean) field.get(role));
                                openAll();
                            } catch (IllegalAccessException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });

                } else if (rawValue instanceof String) {
                    addItem(new ActionItem(slot, icon) {
                        @Override
                        public void onClick(InventoryClickEvent e) {
                            new AnvilUi(getPlayer(), event -> {
                                if (event.getSlot() == AnvilUi.AnvilSlot.OUTPUT) {
                                    try {
                                        field.set(role, event.getName());
                                        openAll();
                                    } catch (IllegalAccessException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            }).setSlot(t(ScenarioVariableUiLang.ANVIL_NEW_VALUE)).open();
                        }
                    });

                } else if (rawValue instanceof Number number) {
                    addMenu(slot, icon,
                            new ConfigVarUi(getPlayer(), 10, 5, 1, 10, 5, 1, number, 0, 0, RoleConfigUi.this) {
                                @Override
                                public void onChange(Number newValue) {
                                    try {
                                        Class<?> type = field.getType();
                                        if (type == int.class || type == Integer.class)
                                            field.set(role, newValue.intValue());
                                        else if (type == double.class || type == Double.class)
                                            field.set(role, newValue.doubleValue());
                                        else if (type == float.class || type == Float.class)
                                            field.set(role, newValue.floatValue());
                                        else if (type == long.class || type == Long.class)
                                            field.set(role, newValue.longValue());
                                    } catch (IllegalAccessException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            slot++;
            if ((slot + 1) % 9 == 0) slot += 2;
            if (slot >= 44) break;
        }
    }

    @Override
    public String getTitle() {
        return t(ScenarioVariableUiLang.ROLE_CONFIG_TITLE, Map.of("%name%", role.getName()));
    }

    @Override public int getLines() { return 5; }
    @Override public boolean isRefreshAuto() { return false; }
}