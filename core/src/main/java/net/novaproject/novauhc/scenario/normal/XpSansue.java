package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.lang.LangManager;

public class XpSansue extends Scenario {
    private boolean active = false;
    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "XPSANSUE_VAR_DAMAGE_NAME", descKey = "XPSANSUE_VAR_DAMAGE_DESC", type = VariableType.INTEGER)
    private final int damage = 1;
    @Override
    public String getName() {
        return "XpSansue";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.XP_SANSUE, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.EXP_BOTTLE);
    }

    @Override
    public void onStart(Player player) {
        if (!active) {
            active = true;
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (UHCPlayer p : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
                        if (p.getPlayer().getLevel() > 0) {
                            p.getPlayer().setLevel(p.getPlayer().getLevel() - damage );
                        } else {
                            p.getPlayer().damage(2);
                        }
                    }
                }
            }.runTaskTimer(Main.get(), 20 * 300 * 2, 20 * 300 * 2);
        }
    }
}
