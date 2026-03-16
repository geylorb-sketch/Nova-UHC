package net.novauhc.dandadan.roles.jotaro;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ReactionPassive extends Ability {
    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "REACTION_DODGE_CHANCE_NAME", descKey = "REACTION_DODGE_CHANCE_DESC", type = VariableType.PERCENTAGE)
    private int dodgeChancePct = 10; // chance de bloquer automatiquement

    @Override public String getName()       { return "Réaction"; }
    @Override public Material getMaterial() { return null; }
    // Annulation de projectile si joueur regarde vers eux — géré via ProjectileHitEvent ailleurs

    @Override
    public boolean onEnable(Player player) {
        return true;
    }

}