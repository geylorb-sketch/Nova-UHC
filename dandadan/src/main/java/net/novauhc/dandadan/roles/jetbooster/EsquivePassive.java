package net.novauhc.dandadan.roles.jetbooster;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EsquivePassive extends Ability {
        @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "ESQUIVE_HITS_TO_DODGE_NAME", descKey = "ESQUIVE_HITS_TO_DODGE_DESC", type = VariableType.INTEGER)
    private int hitsBeforeDodge = 3; // nb coups reçus avant esquive automatique

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "ESQUIVE_DODGE_VELOCITY_NAME", descKey = "ESQUIVE_DODGE_VELOCITY_DESC", type = VariableType.DOUBLE)
    private double dodgeVelocity = 1.5;

    private final Map<UUID, Integer> hitReceived = new HashMap<>();
    @Override public String getName()       { return "Esquive"; }
    @Override public Material getMaterial() { return null; }
    // Hook via onAttack (quand KUR est la victime)
    public boolean onHitReceived(Player kur, EntityDamageByEntityEvent event) {
        int c = hitReceived.getOrDefault(kur.getUniqueId(), 0) + 1;
        hitReceived.put(kur.getUniqueId(), c);
        if (c >= 10) {
            hitReceived.put(kur.getUniqueId(), 0);
            event.setCancelled(true); // annule le 10e coup
            Vector away = kur.getLocation().subtract(event.getDamager().getLocation()).toVector().normalize().multiply(2);
            kur.setVelocity(away);
            return true;
        }
        return false;
    }

    @Override
    public boolean onEnable(Player player) { return true; }
}
