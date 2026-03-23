package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.lang.LangManager;

public class Cripple extends Scenario {

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "CRIPPLE_VAR_WEAKNESS_DURATION_NAME", descKey = "CRIPPLE_VAR_WEAKNESS_DURATION_DESC", type = VariableType.TIME)
    private int weaknessDuration = 30;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "CRIPPLE_VAR_WEAKNESS_LEVEL_NAME", descKey = "CRIPPLE_VAR_WEAKNESS_LEVEL_DESC", type = VariableType.INTEGER)
    private int weaknessLevel = 0;

    @Override
    public String getName() {
        return "Cripple";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.CRIPPLE, player)
                .replace("%duration%", String.valueOf(weaknessDuration));
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.BONE);
    }

    @Override
    public void onPlayerTakeDamage(Entity entity, EntityDamageEvent event) {
        if (entity instanceof Player player) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                player.addPotionEffect(new PotionEffect(
                        PotionEffectType.WEAKNESS,
                        weaknessDuration * 20,
                        weaknessLevel,
                        false,
                        false
                ));
            }
        }
    }
}
