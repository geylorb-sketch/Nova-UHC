package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.lang.LangManager;

import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novaproject.novauhc.lang.scenario.NinjaLang;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;

public class Ninja extends Scenario {

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "NINJA_VAR_INVISIBILITY_DURATION_NAME", descKey = "NINJA_VAR_INVISIBILITY_DURATION_DESC", type = VariableType.TIME)
    private int invisibilityDuration = 200;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "NINJA_VAR_INVISIBILITY_LEVEL_NAME", descKey = "NINJA_VAR_INVISIBILITY_LEVEL_DESC", type = VariableType.INTEGER)
    private int invisibilityLevel = 0;

    @Override
    public String getName() {
        return "Ninja";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.NINJA, player)
                .replace("%seconds%", String.valueOf(invisibilityDuration / 20));
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.FEATHER);
    }



    @Override
    public void onDeath(UHCPlayer uhcPlayer, UHCPlayer killer, PlayerDeathEvent event) {
        if (!isActive()) return;

        if (killer != null) {
            Player killerPlayer = killer.getPlayer();
            killerPlayer.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, invisibilityDuration, invisibilityLevel));
            LangManager.get().send(NinjaLang.KILL_INVISIBILITY, killerPlayer);
        }
    }
}
