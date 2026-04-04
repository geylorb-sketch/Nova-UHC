package net.novaproject.novauhc.scenario.role.ui;

import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.ui.ScenarioVariableUiLang;
import net.novaproject.novauhc.scenario.ScenarioVariableUi;
import net.novaproject.novauhc.scenario.role.Role;
import net.novaproject.novauhc.scenario.role.ScenarioRole;
import net.novaproject.novauhc.ui.config.ScenariosUi;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.ui.CustomInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Map;

public class ScenarioRoleDispatcherUi<T extends Role> extends CustomInventory {

    private final ScenarioRole<T> scenario;

    public ScenarioRoleDispatcherUi(Player player, ScenarioRole<T> scenario) {
        super(player);
        this.scenario = scenario;
    }

    @Override
    public void setup() {
        fillCorner(0);

        int returnSlot = (getLines() - 1) * 9 + 4;
        addReturn(returnSlot, new ScenariosUi(getPlayer(), true));

        ItemCreator rolesItem = new ItemCreator(Material.NETHER_STAR)
                .setName(LangManager.get().get(ScenarioVariableUiLang.DISPATCHER_ROLES_NAME, getPlayer()))
                .addLore(LangManager.get().get(ScenarioVariableUiLang.DISPATCHER_ROLES_LORE, getPlayer()));
        addMenu(12, rolesItem, new ScenarioCampUi<>(getPlayer(), scenario));

        ItemCreator varsItem = new ItemCreator(Material.BOOK_AND_QUILL)
                .setName(LangManager.get().get(ScenarioVariableUiLang.DISPATCHER_VARS_NAME, getPlayer()))
                .addLore(LangManager.get().get(ScenarioVariableUiLang.DISPATCHER_VARS_LORE, getPlayer()));
        addMenu(14, varsItem, new ScenarioVariableUi(getPlayer(), scenario, ScenarioRoleDispatcherUi.this));
    }

    @Override
    public String getTitle() {
        return LangManager.get().get(
                ScenarioVariableUiLang.DISPATCHER_TITLE,
                getPlayer(),
                Map.of("%scenario%", scenario.getName())
        );
    }

    @Override public int getLines() { return 3; }
    @Override public boolean isRefreshAuto() { return false; }
}
