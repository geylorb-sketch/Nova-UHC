package net.novauhc.dandadan.roles.reiko;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Set;

/**
 * Boyfriend — Passif de Reiko Kashima.
 * Si l'attaquant possède un yokai "masculin" ciblé, Reiko reçoit 10% de résistance bonus
 * et lui inflige 10% de dégâts supplémentaires.
 */
public class BoyfriendPassive extends Ability {

    private static final Set<String> TARGET_YOKAIS = Set.of(
            "Kinta", "Kashimoto", "Enenra", "Payase", "Rokuro Serpo", "M. Mantis",
            "L'Œil maléfique", "Jiji", "Monstre de Flatwoods", "Minotaure",
            "Umbrella Boy", "Compte de Saint-Germain", "Devilman", "Doomslayer"
    );

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "BOYFRIEND_DMG_REDUCTION_NAME", descKey = "BOYFRIEND_DMG_REDUCTION_DESC", type = VariableType.PERCENTAGE)
    private int damageReductionPct = 10;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "BOYFRIEND_BONUS_DMG_NAME", descKey = "BOYFRIEND_BONUS_DMG_DESC", type = VariableType.PERCENTAGE)
    private int bonusDamagePct = 10;

    @Override public String getName()       { return "Boyfriend"; }
    @Override public Material getMaterial() { return null; }

    @Override
    public boolean onEnable(Player player) { return true; }

    @Override
    public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        // Vérifie si Reiko (victim) est frappée par un yokai ciblé
        if (!(event.getDamager() instanceof Player attacker)) return;
        if (DanDaDan.get() == null) return;
        var attackerUHC = UHCPlayerManager.get().getPlayer(attacker);
        if (attackerUHC == null) return;
        DanDaDanRole role = DanDaDan.get().getRoleByUHCPlayer(attackerUHC);
        if (role == null || !TARGET_YOKAIS.contains(role.getName())) return;
        // Réduction 10% des dégâts reçus par Reiko + 10% dégâts bonus sur attaquant
        event.setDamage(event.getDamage() * (1.0 - damageReductionPct / 100.0));
        attacker.damage(event.getDamage() * (bonusDamagePct / 100.0), victimP.getPlayer());
    }
}
