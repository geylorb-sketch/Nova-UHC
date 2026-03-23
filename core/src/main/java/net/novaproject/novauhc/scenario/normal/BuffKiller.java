package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.lang.LangManager;

public class BuffKiller extends Scenario {

    private final Random random = new Random();

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "BUFFKILLER_VAR_SPEED_ACTIVE_NAME", descKey = "BUFFKILLER_VAR_SPEED_ACTIVE_DESC", type = VariableType.BOOLEAN)
    private boolean speedActive = true;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "BUFFKILLER_VAR_STRENGTH_ACTIVE_NAME", descKey = "BUFFKILLER_VAR_STRENGTH_ACTIVE_DESC", type = VariableType.BOOLEAN)
    private boolean strengthActive = true;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "BUFFKILLER_VAR_RESISTANCE_ACTIVE_NAME", descKey = "BUFFKILLER_VAR_RESISTANCE_ACTIVE_DESC", type = VariableType.BOOLEAN)
    private boolean resistanceActive = true;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "BUFFKILLER_VAR_FIRE_RESISTANCE_ACTIVE_NAME", descKey = "BUFFKILLER_VAR_FIRE_RESISTANCE_ACTIVE_DESC", type = VariableType.BOOLEAN)
    private boolean fireResistanceActive = true;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "BUFFKILLER_VAR_MIN_DURATION_NAME", descKey = "BUFFKILLER_VAR_MIN_DURATION_DESC", type = VariableType.TIME)
    private int minDuration = 5;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "BUFFKILLER_VAR_MAX_DURATION_NAME", descKey = "BUFFKILLER_VAR_MAX_DURATION_DESC", type = VariableType.TIME)
    private int maxDuration = 15;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "BUFFKILLER_VAR_EFFECT_LEVEL_NAME", descKey = "BUFFKILLER_VAR_EFFECT_LEVEL_DESC", type = VariableType.INTEGER)
    private int effectLevel = 0;

    @Override
    public String getName() {
        return "Buff Killer";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.BUFF_KILLER, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.WOOD_SWORD);
    }

    @Override
    public void onDeath(UHCPlayer uhcPlayer, UHCPlayer killer, PlayerDeathEvent event) {
        if (killer == null) return;

        List<PotionEffectType> activeEffects = new ArrayList<>();
        if (speedActive) activeEffects.add(PotionEffectType.SPEED);
        if (strengthActive) activeEffects.add(PotionEffectType.INCREASE_DAMAGE);
        if (resistanceActive) activeEffects.add(PotionEffectType.DAMAGE_RESISTANCE);
        if (fireResistanceActive) activeEffects.add(PotionEffectType.FIRE_RESISTANCE);

        if (activeEffects.isEmpty()) return;

        PotionEffectType randomEffect = activeEffects.get(random.nextInt(activeEffects.size()));
        int duration = 20 * (minDuration + random.nextInt(maxDuration - minDuration + 1));
        killer.getPlayer().addPotionEffect(new PotionEffect(randomEffect, duration, effectLevel));
    }
}
