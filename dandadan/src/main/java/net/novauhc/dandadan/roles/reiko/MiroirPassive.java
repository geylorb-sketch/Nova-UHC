package net.novauhc.dandadan.roles.reiko;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.concurrent.ThreadLocalRandom;

public class MiroirPassive extends Ability {
    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "MIROIR_PASSIVE_CHANCE_NAME", descKey = "MIROIR_PASSIVE_CHANCE_DESC", type = VariableType.PERCENTAGE)
    private int turnChancePct = 5;

    @Override public String getName()       { return "Miroir"; }
    @Override public Material getMaterial() { return null; }

    @Override public boolean onEnable(Player player) { return true; }

    @Override public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker)) return;
        if (ThreadLocalRandom.current().nextDouble() >= turnChancePct / 100.0) return;
        Location loc = attacker.getLocation().clone();
        loc.setYaw(loc.getYaw() + 180);
        attacker.teleport(loc);
    }
}