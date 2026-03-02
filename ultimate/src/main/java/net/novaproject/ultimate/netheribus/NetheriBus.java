package net.novaproject.ultimate.netheribus;

import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.uhcteam.UHCTeam;
import net.novaproject.novauhc.uhcteam.UHCTeamManager;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;

public class NetheriBus extends Scenario {

    @ScenarioVariable(
            nameKey = "NETHERIBUS_VAR_TIMER_NAME",
            descKey = "NETHERIBUS_VAR_TIMER_DESC",
            type = VariableType.TIME
    )
    private int startTimer = 600; // secondes avant activation

    @ScenarioVariable(
            nameKey = "NETHERIBUS_VAR_DAMAGE_NAME",
            descKey = "NETHERIBUS_VAR_DAMAGE_DESC",
            type = VariableType.INTEGER
    )
    private int damageOutsideNether = 2;

    @Override
    public String getName() {
        return "NetheriBus";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.NETHERIBUS, player, Map.of(
                "%time%", startTimer,
                "%damage%", damageOutsideNether
        ));
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.MINECART);
    }

    @Override
    public void onSec(Player p) {
        int timer = UHCManager.get().getTimer();
        if (timer > startTimer) {
            for (UHCPlayer uPlayer : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
                if (!uPlayer.getPlayer().getWorld().getEnvironment().equals(World.Environment.NETHER)) {
                    uPlayer.getPlayer().damage(damageOutsideNether);
                }
            }
        }
    }

    @Override
    public void scatter(UHCPlayer uhcPlayer, Location location, HashMap<UHCTeam, Location> teamloc) {
        if (UHCManager.get().getTeam_size() != 1) {
            UHCTeamManager.get().scatterTeam(uhcPlayer, teamloc);
        } else {
            uhcPlayer.getPlayer().teleport(location);
        }
    }

    @Override
    public boolean isSpecial() {
        return true;
    }


}