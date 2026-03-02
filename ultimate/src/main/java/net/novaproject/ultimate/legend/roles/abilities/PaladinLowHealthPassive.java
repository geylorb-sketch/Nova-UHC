package net.novaproject.ultimate.legend.roles.abilities;

import net.novaproject.novauhc.ability.PassiveAbility;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Passif Paladin : Résistance I à faible vie + Force I près des alliés.
 * PassiveAbility.onSec() → tryUse() → onEnable() chaque seconde (cooldown 0).
 */
public class PaladinLowHealthPassive extends PassiveAbility {

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "PALADIN_LOW_HP_THRESHOLD_NAME", descKey = "PALADIN_LOW_HP_THRESHOLD_DESC", type = VariableType.DOUBLE)
    private double threshold = 10.0;

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "PALADIN_ALLY_RADIUS_NAME", descKey = "PALADIN_ALLY_RADIUS_DESC", type = VariableType.DOUBLE)
    private double allyRadius = 8.0;

    public PaladinLowHealthPassive() { setCooldown(0); }

    @Override public String getName() { return "Foi du Paladin"; }
    @Override public Material getMaterial() { return null; }

    @Override
    public boolean onEnable(Player player) {
        boolean applied = false;

        // Résistance I si vie basse
        if (player.getHealth() <= threshold) {
            player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 80, 0, false, false));
            applied = true;
        }

        // Force I si au moins un allié à portée
        UHCPlayer owner = UHCPlayerManager.get().getPlayer(player);
        if (owner != null && owner.getTeam().isPresent()) {
            long nearby = owner.getTeam().get().getPlayers().stream()
                    .filter(t -> !t.equals(owner))
                    .map(UHCPlayer::getPlayer)
                    .filter(tp -> tp != null && tp.isOnline()
                            && tp.getLocation().distance(player.getLocation()) <= allyRadius)
                    .count();
            if (nearby > 0) {
                player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 80, 0, false, false));
                applied = true;
            }
        }

        return applied;
    }
}
