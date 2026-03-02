package net.novaproject.ultimate.legend.roles.abilities;

import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CorneMelodieMetal extends UseAbiliy {
    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "CORNE_METAL_DURATION_NAME", descKey = "CORNE_METAL_DURATION_DESC", type = VariableType.TIME)
    private int duration = 5;
    public CorneMelodieMetal() { setCooldown(60); setMaxUse(-1); }
    @Override public String getName() { return "Melodie : Metal"; }
    @Override
    public boolean onEnable(Player player) {
        UHCPlayer owner = getUHCPlayer(player);
        if (owner == null || !owner.getTeam().isPresent()) return false;
        PotionEffect e = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * duration, 1);
        for (UHCPlayer t : owner.getTeam().get().getPlayers()) {
            Player tp = t.getPlayer();
            if (tp != null && tp.isOnline() && tp.getLocation().distance(player.getLocation()) <= 31) tp.addPotionEffect(e);
        }
        return true;
    }
}
