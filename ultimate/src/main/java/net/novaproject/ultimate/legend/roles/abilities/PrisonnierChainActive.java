package net.novaproject.ultimate.legend.roles.abilities;

import net.novaproject.novauhc.ability.template.UseAbility;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PrisonnierChainActive extends UseAbility {

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "PRISONNIER_CHAIN_RADIUS_NAME", descKey = "PRISONNIER_CHAIN_RADIUS_DESC", type = VariableType.DOUBLE)
    private double chainRadius = 8.0;

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "PRISONNIER_CHAIN_DURATION_NAME", descKey = "PRISONNIER_CHAIN_DURATION_DESC", type = VariableType.TIME)
    private int chainDuration = 8;

    public PrisonnierChainActive() { setCooldown(300); setMaxUse(-1); }

    @Override public String getName() { return "Chaînes"; }

    @Override
    public boolean onEnable(Player player) {
        UHCPlayer owner = getUHCPlayer(player);
        Player target = null; double minDist = chainRadius;
        for (Entity e : player.getNearbyEntities(chainRadius, chainRadius, chainRadius)) {
            if (!(e instanceof Player t) || t == player) continue;
            UHCPlayer tu = UHCPlayerManager.get().getPlayer(t);
            if (tu == null) continue;
            if (owner.getTeam().isPresent() && tu.getTeam().isPresent()
                    && owner.getTeam().get().equals(tu.getTeam().get())) continue;
            double d = player.getLocation().distance(t.getLocation());
            if (d < minDist) { minDist = d; target = t; }
        }
        if (target == null) return false;
        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * chainDuration, 1));
        target.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * chainDuration, 0));
        return true;
    }
}
