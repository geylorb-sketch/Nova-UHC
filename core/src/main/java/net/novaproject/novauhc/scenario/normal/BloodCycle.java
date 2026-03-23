package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.lang.LangManager;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.lang.scenario.BloodCycleLang;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;

public class BloodCycle extends Scenario {
    private final Material[] cache = new Material[6];
    int i = 0;
    private BukkitRunnable cycleTask;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "BLOODCYCLE_VAR_BETWEEN_NAME", descKey = "BLOODCYCLE_VAR_BETWEEN_DESC", type = VariableType.TIME)
    private int between = 360;

    @Override
    public String getName() {
        return "BloodCycle";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.BLOOD_CYCLE, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.BONE);
    }

    @Override
    public void onGameStart() {
        cache[0] = Material.DIAMOND_ORE;
        cache[1] = Material.GOLD_ORE;
        cache[2] = Material.IRON_ORE;
        cache[3] = Material.COAL_ORE;
        cache[4] = Material.LAPIS_ORE;
        cache[5] = Material.REDSTONE_ORE;

        if (cycleTask != null) cycleTask.cancel();
        cycleTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (!isActive()) { cancel(); return; }
                i++;
                if (i == 6) i = 0;
                for (UHCPlayer p : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
                    sendOreMessage(p.getPlayer(), cache[i]);
                }
            }
        };
        cycleTask.runTaskTimer(Main.get(), 20L * between, 20L * between);
    }

    @Override
    public void onStart(Player player) {
        if (cache[0] == null) {
            cache[0] = Material.DIAMOND_ORE;
            cache[1] = Material.GOLD_ORE;
            cache[2] = Material.IRON_ORE;
            cache[3] = Material.COAL_ORE;
            cache[4] = Material.LAPIS_ORE;
            cache[5] = Material.REDSTONE_ORE;
        }
        sendOreMessage(player, cache[i]);
    }

    private void sendOreMessage(Player player, Material ore) {
        switch (ore) {
            case DIAMOND_ORE:
                LangManager.get().send(BloodCycleLang.DIAMOND, player);
                break;
            case GOLD_ORE:
                LangManager.get().send(BloodCycleLang.GOLD, player);
                break;
            case IRON_ORE:
                LangManager.get().send(BloodCycleLang.IRON, player);
                break;
            case COAL_ORE:
                LangManager.get().send(BloodCycleLang.COAL, player);
                break;
            case LAPIS_ORE:
                LangManager.get().send(BloodCycleLang.LAPIS, player);
                break;
            case REDSTONE_ORE:
                LangManager.get().send(BloodCycleLang.REDSTONE, player);
                break;
        }
    }

    @Override
    public void onBreak(Player player, Block block, BlockBreakEvent event) {
        if (block.getType().equals(cache[i])) {
            player.damage(1);
            LangManager.get().send(BloodCycleLang.TAKE_DAMAGE, player);
        }
    }
}
