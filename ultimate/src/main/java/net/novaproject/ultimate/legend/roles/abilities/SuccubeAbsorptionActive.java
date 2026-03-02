package net.novaproject.ultimate.legend.roles.abilities;

import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SuccubeAbsorptionActive extends UseAbiliy {

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "SUCCUBE_ABSORB_RADIUS_NAME", descKey = "SUCCUBE_ABSORB_RADIUS_DESC", type = VariableType.DOUBLE)
    private double radius = 11.0;

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "SUCCUBE_ABSORB_LEVEL_NAME", descKey = "SUCCUBE_ABSORB_LEVEL_DESC", type = VariableType.INTEGER)
    private int absorptionLevel = 3;

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "SUCCUBE_ABSORB_DURATION_NAME", descKey = "SUCCUBE_ABSORB_DURATION_DESC", type = VariableType.TIME)
    private int duration = 60;

    public SuccubeAbsorptionActive() { setCooldown(360); setMaxUse(-1); }

    @Override public String getName() { return "Charme Succube"; }

    @Override
    public boolean onEnable(Player player) {
        if (absorptionLevel <= 0) return false;
        UHCPlayer owner = getUHCPlayer(player);
        if (owner == null || !owner.getTeam().isPresent()) return false;
        PotionEffect effect = new PotionEffect(PotionEffectType.ABSORPTION, 20 * duration, absorptionLevel - 1);
        for (UHCPlayer t : owner.getTeam().get().getPlayers()) {
            Player tp = t.getPlayer();
            if (tp != null && tp.isOnline() && tp.getLocation().distance(player.getLocation()) <= radius)
                tp.addPotionEffect(effect);
        }
        return true;
    }
}
