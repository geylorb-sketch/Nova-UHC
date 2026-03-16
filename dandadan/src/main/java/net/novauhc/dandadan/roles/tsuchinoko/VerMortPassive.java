package net.novauhc.dandadan.roles.tsuchinoko;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.concurrent.ThreadLocalRandom;

public class VerMortPassive extends Ability {
    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "VER_MORT_CHANCE_NAME", descKey = "VER_MORT_CHANCE_DESC", type = VariableType.PERCENTAGE)
    private int poisonChancePct = 2;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "VER_MORT_POISON_DURATION_NAME", descKey = "VER_MORT_POISON_DURATION_DESC", type = VariableType.TIME)
    private int poisonDuration = 5;

    @Override public String getName()       { return "Ver de la mort mongole"; }
    @Override public Material getMaterial() { return null; }

    @Override public boolean onEnable(Player player) { return true; }

    @Override
    public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker)) return;
        if (ThreadLocalRandom.current().nextDouble() >= poisonChancePct / 100.0) return;
        Player victim = victimP.getPlayer();
        if (victim == null) return;
        victim.getWorld().strikeLightningEffect(victim.getLocation());
        victim.damage(2.0, attacker); // 1❤
    }
}