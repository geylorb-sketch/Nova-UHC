package net.novaproject.ultimate.legend.roles.abilities;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class SoldatEquipmentPassive extends Ability {

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "SOLDAT_BONUS_DAMAGE_NAME", descKey = "SOLDAT_BONUS_DAMAGE_DESC", type = VariableType.DOUBLE)
    private double bonusDamage = 1.0;


    private EntityDamageByEntityEvent pendingEvent;
    private Player pendingAttacker;

    public SoldatEquipmentPassive() { setCooldown(0); }

    @Override public String getName() { return "Discipline Militaire"; }
    @Override public Material getMaterial() { return null; }


    @Override
    public void onSec(Player player) {
        player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 80, 0, false, false));
        super.onSec(player);
    }


    @Override
    public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker)) return;
        if (getOwner() == null || !attacker.equals(getOwner().getPlayer())) return;
        pendingAttacker = attacker;
        pendingEvent    = event;
        tryUse(attacker);
    }


    @Override
    public boolean onEnable(Player player) {
        if (pendingEvent == null || pendingAttacker == null) return false;

        Material m = pendingAttacker.getItemInHand().getType();
        boolean isSword = m == Material.WOOD_SWORD   || m == Material.STONE_SWORD
                       || m == Material.IRON_SWORD   || m == Material.GOLD_SWORD
                       || m == Material.DIAMOND_SWORD;

        if (isSword) pendingEvent.setDamage(pendingEvent.getDamage() + bonusDamage);

        pendingEvent    = null;
        pendingAttacker = null;
        return isSword;
    }
}
