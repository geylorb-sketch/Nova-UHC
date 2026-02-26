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

public class BestPvE extends Scenario {
    private final List<Player> listPve = new ArrayList<>();
    private final List<Player> listOutPve = new ArrayList<>();
    @ScenarioVariable(name = "Temps entre les gains de cœur", description = "Définit le temps (en secondes) entre chaque gain de cœur pour les joueurs dans le classement PvE.", type = VariableType.TIME)
    private int timer = 600; // 20 minutes

    @Override
    public String getName() {
        return "Best PvE";
    }

    @Override
    public String getDescription() {
        return "Récompense les joueurs qui excellent dans le PvE avec des bonus.";
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

    @Override
    public String getPath() {
        return "bestpve";
    }

    private void bestPvE(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (listPve.contains(player)) {
                    player.setMaxHealth(player.getMaxHealth() + getConfig().getInt("heart_gain"));
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
