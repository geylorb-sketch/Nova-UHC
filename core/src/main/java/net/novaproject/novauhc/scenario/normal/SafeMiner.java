package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.lang.LangManager;

public class SafeMiner extends Scenario {


    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "SAFEMINER_VAR_MAX_HEIGHT_NAME", descKey = "SAFEMINER_VAR_MAX_HEIGHT_DESC", type = VariableType.INTEGER)
    private int max_height = 32;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "SAFEMINER_VAR_DISABLE_AT_PVP_NAME", descKey = "SAFEMINER_VAR_DISABLE_AT_PVP_DESC", type = VariableType.BOOLEAN)
    private boolean disable_at_pvp = true;

    private boolean actived = true;

    @Override
    public String getName() {
        return "SafeMiner";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.SAFE_MINER, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.DIAMOND_PICKAXE);
    }

    @Override
    public void onPlayerTakeDamage(Entity entity, EntityDamageEvent event) {
        if (!actived) return;
        if (!(entity instanceof Player player)) return;

        if (player.getLocation().getY() <= max_height) {
            event.setCancelled(true);
        }
    }



    @Override
    public void onSec(Player p) {
        if (!disable_at_pvp) return;

        int timer = UHCManager.get().getTimer();
        int timerpvp = UHCManager.get().getTimerpvp();

        if (timer == timerpvp) {
            actived = false;
        }
    }
}
