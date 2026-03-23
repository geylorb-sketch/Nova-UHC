package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.lang.lang.CommonLang;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;

public class FinalHeal extends Scenario {

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "FINALHEAL_VAR_HEAL_TIME1_NAME", descKey = "FINALHEAL_VAR_HEAL_TIME1_DESC", type = VariableType.TIME)
    private int healTime1 = 600;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "FINALHEAL_VAR_HEAL_TIME2_NAME", descKey = "FINALHEAL_VAR_HEAL_TIME2_DESC", type = VariableType.TIME)
    private int healTime2 = 1200;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "FINALHEAL_VAR_HEAL_FOOD_NAME", descKey = "FINALHEAL_VAR_HEAL_FOOD_DESC", type = VariableType.BOOLEAN)
    private boolean healFood = true;

    private boolean healed1 = false;
    private boolean healed2 = false;

    @Override
    public String getName() {
        return "FinalHeal";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.FINAL_HEAL, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.POTION);
    }

    @Override
    public void onSec(Player p) {
        int timer = UHCManager.get().getTimer();

        if (!isActive()) return;

        if (timer == healTime1 && !healed1) {
            healed1 = true;
            for (UHCPlayer uhcPlayer : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
                Player player = uhcPlayer.getPlayer();
                player.setHealth(player.getMaxHealth());
                if (healFood) {
                    player.setFoodLevel(20);
                    player.setSaturation(20f);
                }
            }
            Bukkit.broadcastMessage(LangManager.get().get(CommonLang.FINAL_HEAL_BROADCAST));
        } else if (timer == healTime2 && !healed2) {
            healed2 = true;
            for (UHCPlayer uhcPlayer : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
                Player player = uhcPlayer.getPlayer();
                player.setHealth(player.getMaxHealth());
                if (healFood) {
                    player.setFoodLevel(20);
                    player.setSaturation(20f);
                }
            }
            Bukkit.broadcastMessage(LangManager.get().get(CommonLang.FINAL_HEAL_BROADCAST));
        }
    }
}
