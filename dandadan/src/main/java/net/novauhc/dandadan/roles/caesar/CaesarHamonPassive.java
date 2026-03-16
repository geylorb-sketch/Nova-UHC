package net.novauhc.dandadan.roles.caesar;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CaesarHamonPassive extends Ability {
    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "HAMON_FIRE_TICKS_NAME", descKey = "HAMON_FIRE_TICKS_DESC", type = VariableType.TIME)
    private int fireTicks = 40;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "HAMON_POISON_TICKS_NAME", descKey = "HAMON_POISON_TICKS_DESC", type = VariableType.TIME)
    private int poisonTicks = 40;

    @Override public String getName()       { return "Hamon"; }
    @Override public Material getMaterial() { return null; }

    @Override public boolean onEnable(Player player) { return true; }

    @Override public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        // Caesar est la victime — renvoie si feu ou poison
        if (!(event.getEntity() instanceof Player caesar)) return;
        if (caesar.getFireTicks() > 0 || caesar.hasPotionEffect(PotionEffectType.POISON)) {
            if (event.getDamager() instanceof Player attacker) {
                attacker.setFireTicks(fireTicks);
                attacker.addPotionEffect(new PotionEffect(PotionEffectType.POISON, poisonTicks, 0));
            }
        }
    }
}