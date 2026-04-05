package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.Common;
import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.lang.CommonLang;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Random;

public class TpMeetup extends Scenario {

    @ScenarioVariable(lang = net.novaproject.novauhc.lang.lang.ScenarioVarLang.class, nameKey = "TPMEETUP_VAR_TIMER_TP_NAME", descKey = "TPMEETUP_VAR_TIMER_TP_DESC", type = VariableType.TIME)
    private int timerTP = 3600;

    @ScenarioVariable(lang = net.novaproject.novauhc.lang.lang.ScenarioVarLang.class, nameKey = "TPMEETUP_VAR_RADIUS_NAME", descKey = "TPMEETUP_VAR_RADIUS_DESC", type = VariableType.INTEGER)
    private int radius = 250;

    private boolean tpDone = false;
    private static final Random RANDOM = new Random();

    @Override
    public String getName() {
        return "TP Meetup";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.TPMEETUP, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.ENDER_PEARL);
    }

    @Override
    public void onSec(Player p) {
        int timer = UHCManager.get().getTimer();

        if (!isActive()) return;

        if (timer == timerTP && !tpDone) {
            tpDone = true;
            for (UHCPlayer uhcPlayer : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
                int x = RANDOM.nextInt(radius * 2 + 1) - radius;
                int z = RANDOM.nextInt(radius * 2 + 1) - radius;
                int y = Common.get().getArena().getHighestBlockYAt(x, z);
                Location loc = new Location(Common.get().getArena(), x + 0.5, y + 1, z + 0.5);
                uhcPlayer.getPlayer().teleport(loc);
                LangManager.get().sendAll(CommonLang.TP_MESSAGE, Map.of("%player%", uhcPlayer.getPlayer().getName()));
            }
        }
    }
}
