package net.novaproject.novauhc.ui.config;

import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.lang.lang.CommonLang;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.ui.TeamConfigUiLang;
import net.novaproject.novauhc.lang.ui.UiTitleLang;
import net.novaproject.novauhc.scenario.ScenarioManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.uhcteam.UHCTeamManager;
import net.novaproject.novauhc.ui.DefaultUi;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.UHCUtils;
import net.novaproject.novauhc.utils.ui.AnvilUi;
import net.novaproject.novauhc.utils.ui.CustomInventory;
import net.novaproject.novauhc.utils.ui.item.ActionItem;
import net.novaproject.novauhc.utils.ui.item.StaticItem;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Map;
import java.util.Optional;

public class TeamConfigUi extends CustomInventory {

    public TeamConfigUi(Player player) {
        super(player);
    }

    private String t(TeamConfigUiLang key) {
        return LangManager.get().get(key, getPlayer());
    }
    private String t(TeamConfigUiLang key, Map<String, Object> p) {
        return LangManager.get().get(key, getPlayer(), p);
    }

    @Override
    public void setup() {
        fillCadre(0);
        addReturn(18, new DefaultUi(getPlayer()));

        ItemCreator updateTeam = new ItemCreator(Material.BOOK)
                .setName(t(TeamConfigUiLang.UPDATE_TEAMS_NAME))
                .addLore("")
                .addLore(t(TeamConfigUiLang.UPDATE_TEAMS_DESC1))
                .addLore(t(TeamConfigUiLang.UPDATE_TEAMS_DESC2))
                .addLore("")
                .addLore(LangManager.get().get(CommonLang.CLICK_HERE_TO_MODIFY))
                .addLore("");

        ItemCreator teamSize = new ItemCreator(Material.PAPER)
                .setName(t(TeamConfigUiLang.TEAM_SIZE_LABEL, Map.of("%value%", UHCUtils.formatValue(UHCManager.get().getTeam_size(), 1))));

        ItemCreator custom = new ItemCreator(Material.REDSTONE_TORCH_ON)
                .setName(t(TeamConfigUiLang.CUSTOM_NAME))
                .addLore("")
                .addLore(t(TeamConfigUiLang.CUSTOM_DESC1))
                .addLore(t(TeamConfigUiLang.CUSTOM_DESC2))
                .addLore("")
                .addLore(LangManager.get().get(CommonLang.CLICK_HERE_TO_MODIFY))
                .addLore("");

        ItemCreator desac = new ItemCreator(Material.BARRIER)
                .setName(t(TeamConfigUiLang.DISABLE_NAME))
                .addLore("")
                .addLore(t(TeamConfigUiLang.DISABLE_DESC))
                .addLore("")
                .addLore(LangManager.get().get(CommonLang.CLICK_HERE_TO_MODIFY))
                .addLore("");

        addItem(new StaticItem(4, teamSize));
        addItem(new ActionItem(22, custom) {
            @Override
            public void onClick(InventoryClickEvent e) {
                new AnvilUi(getPlayer(), event -> {
                    if (event.getSlot() == AnvilUi.AnvilSlot.OUTPUT) {
                        int enteredText = Integer.parseInt(event.getName());
                        enteredText = Math.max(1, enteredText);
                        UHCManager.get().setTeam_size(enteredText);
                        LangManager.get().send(CommonLang.SUCCESSFUL_MODIFICATION, getPlayer());
                        LangManager.get().sendAll(CommonLang.TEAM_UPDATED);
                        ScenarioManager.get().getActiveScenarios().forEach(scenario -> scenario.onTeamUpdate());
                        openAll();
                    }
                }).setSlot(t(TeamConfigUiLang.CUSTOM_ANVIL_HINT)).open();
            }
        });

        addItem(new ActionItem(5, desac) {
            @Override
            public void onClick(InventoryClickEvent e) {
                UHCManager.get().setTeam_size(1);
                openAll();
                Bukkit.broadcastMessage(LangManager.get().get(CommonLang.TEAM_DESACTIVATED, getPlayer()));
                ScenarioManager.get().getActiveScenarios().forEach(scenario -> scenario.onTeamUpdate());
            }
        });

        addTeamItem(10, 2, DyeColor.LIGHT_BLUE);
        addTeamItem(11, 3, DyeColor.GREEN);
        addTeamItem(12, 4, DyeColor.RED);
        addTeamItem(13, 5, DyeColor.YELLOW);
        addTeamItem(14, 6, DyeColor.PURPLE);
        addTeamItem(15, 7, DyeColor.ORANGE);
        addTeamItem(16, 8, DyeColor.WHITE);

        addItem(new ActionItem(3, updateTeam) {
            @Override
            public void onClick(InventoryClickEvent e) {
                for (UHCPlayer player : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
                    player.setTeam(Optional.empty());
                }
                UHCTeamManager.get().createTeams();
                openAll();
                Bukkit.broadcastMessage(t(TeamConfigUiLang.UPDATE_BROADCAST));
                ScenarioManager.get().getActiveScenarios().forEach(scenario -> scenario.onTeamUpdate());
            }
        });
    }

    @Override
    public String getTitle() {
        return LangManager.get().get(UiTitleLang.TEAM_CONFIG_TITLE, getPlayer());
    }

    @Override
    public int getLines() { return 3; }

    @Override
    public boolean isRefreshAuto() { return false; }

    public void addTeamItem(int slot, int team_size, DyeColor color) {
        ItemCreator item = new ItemCreator(Material.BANNER)
                .setName(t(TeamConfigUiLang.TEAM_ITEM_NAME, Map.of("%value%", String.valueOf(team_size))))
                .setBasecolor(color).addallItemsflags()
                .addLore("")
                .addLore(t(TeamConfigUiLang.TEAM_ITEM_DESC1))
                .addLore(t(TeamConfigUiLang.TEAM_ITEM_DESC2))
                .addLore("")
                .addLore(LangManager.get().get(CommonLang.CLICK_HERE_TO_MODIFY))
                .addLore("");

        addItem(new ActionItem(slot, item) {
            @Override
            public void onClick(InventoryClickEvent e) {
                UHCManager.get().setTeam_size(team_size);
                openAll();
                LangManager.get().sendAll(CommonLang.TEAM_UPDATED);
                ScenarioManager.get().getActiveScenarios().forEach(scenario -> scenario.onTeamUpdate());
            }
        });
    }
}
