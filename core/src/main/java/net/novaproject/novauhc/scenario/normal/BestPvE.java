package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.lang.LangManager;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.lang.scenario.BestPvELang;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;

public class BestPvE extends Scenario {
    private final List<Player> listPve = new ArrayList<>();
    private final List<Player> listOutPve = new ArrayList<>();
    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "BESTPVE_VAR_TIMER_NAME", descKey = "BESTPVE_VAR_TIMER_DESC", type = VariableType.TIME)
    private int timer = 600;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "BESTPVE_VAR_HEART_GAIN_NAME", descKey = "BESTPVE_VAR_HEART_GAIN_DESC", type = VariableType.INTEGER)
    private int heartGain = 2;

    @Override
    public String getName() {
        return "Best PvE";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.BEST_PVE, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.CACTUS);
    }

    @Override
    public void onStart(Player player) {
        listPve.add(player);
        bestPvE(player);
    }



    private void bestPvE(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (listPve.contains(player)) {
                    player.setMaxHealth(player.getMaxHealth() + heartGain);
                    LangManager.get().send(BestPvELang.GAIN_MESSAGE, player);
                } else if (listOutPve.contains(player)) {
                    listOutPve.remove(player);
                    listPve.add(player);
                    LangManager.get().send(BestPvELang.LIST_JOIN, player);
                }

            }
        }.runTaskTimer(Main.get(), 0, 20L * timer);
    }

    @Override
    public void onPlayerTakeDamage(Entity entity, EntityDamageEvent event) {
        if (entity instanceof Player player) {
            if (listPve.contains(player)) {
                listPve.remove(player);
                if (!listOutPve.contains(player)) {
                    listOutPve.add(player);
                    LangManager.get().send(BestPvELang.LIST_QUIT, player);
                }
            }
        }
    }

}
