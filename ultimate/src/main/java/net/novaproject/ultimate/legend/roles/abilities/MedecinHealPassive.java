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
 * Passif Médecin : Régénération I aux alliés proches.
 * PassiveAbility.onSec() → tryUse() → onEnable() chaque seconde (cooldown 0).
 */
public class MedecinHealPassive extends PassiveAbility {

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "MEDECIN_HEAL_RADIUS_NAME", descKey = "MEDECIN_HEAL_RADIUS_DESC", type = VariableType.DOUBLE)
    private double healRadius = 6.0;

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "MEDECIN_HEAL_LEVEL_NAME", descKey = "MEDECIN_HEAL_LEVEL_DESC", type = VariableType.INTEGER)
    private int healLevel = 1;

    public MedecinHealPassive() { setCooldown(0); }

    @Override public String getName() { return "Zone de Soin"; }
    @Override public Material getMaterial() { return null; }

    @Override
    public boolean onEnable(Player player) {
        if (healLevel <= 0) return false;
        UHCPlayer owner = UHCPlayerManager.get().getPlayer(player);
        if (owner == null || !owner.getTeam().isPresent()) return false;

        boolean healed = false;
        for (UHCPlayer t : owner.getTeam().get().getPlayers()) {
            if (t.equals(owner)) continue;
            Player tp = t.getPlayer();
            if (tp == null || !tp.isOnline()) continue;
            if (tp.getLocation().distance(player.getLocation()) <= healRadius) {
                tp.removePotionEffect(PotionEffectType.REGENERATION);
                tp.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, healLevel - 1, false, false));
                healed = true;
            }
        }
        return healed;
    }
}
