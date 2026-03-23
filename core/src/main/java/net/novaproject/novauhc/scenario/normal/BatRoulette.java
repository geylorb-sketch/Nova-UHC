package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.Common;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Random;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.lang.LangManager;

public class BatRoulette extends Scenario {

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "BATROULETTE_VAR_CHANCE_NAME", descKey = "BATROULETTE_VAR_CHANCE_DESC", type = VariableType.PERCENTAGE)
    private int chance = 10;

    @Override
    public String getName() {
        return "BatRoulette";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.BAT_ROULETTE, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.MONSTER_EGG);
    }




    @Override
    public void onEntityDeath(Entity entity, Player killer, EntityDeathEvent event) {
        Location loc = entity.getLocation();
        if (entity instanceof Bat && killer != null) {
            int random = new Random().nextInt(100);
            if (random <= chance) {
                killer.teleport(new Location(Common.get().getArena(), loc.getX(), 300, loc.getZ()));
            } else {
                event.getEntity().getWorld().dropItem(loc, new ItemCreator(Material.GOLDEN_APPLE).getItemstack());
            }
        }
    }
}
