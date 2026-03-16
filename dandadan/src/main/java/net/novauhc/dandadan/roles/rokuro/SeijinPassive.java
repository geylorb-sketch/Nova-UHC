package net.novauhc.dandadan.roles.rokuro;

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

import java.util.concurrent.ThreadLocalRandom;

import net.novauhc.dandadan.DanDaDan;

public class SeijinPassive extends Ability {

    @AbilityVariable(
            lang = DanDaDanVarLang.class,
            nameKey = "SEIJIN_LIFESTEAL_CHANCE_NAME",
            descKey = "SEIJIN_LIFESTEAL_CHANCE_DESC",
            type = VariableType.PERCENTAGE
    )
    private int lifestealChancePct = 5;

    @AbilityVariable(
            lang = DanDaDanVarLang.class,
            nameKey = "SEIJIN_LIFESTEAL_AMOUNT_NAME",
            descKey = "SEIJIN_LIFESTEAL_AMOUNT_DESC",
            type = VariableType.DOUBLE
    )
    private double lifestealAmount = 2.0; // 1❤

    @Override
    public String getName() {
        return "Seijin";
    }

    @Override
    public Material getMaterial() {
        return null; // éviter null
    }

    @Override
    public boolean onEnable(Player player) {
        return true;
    }

    private boolean isSeijinForm(Player player) {
        Object role = DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(player));

        if (!(role instanceof RokuroSerpoRole rokuroRole)) {
            return false;
        }

        return rokuroRole.getCurrentForme() == RokuroSerpoRole.Forme.SEIJIN;
    }

    @Override
    public void onSec(Player player) {
        if (!isSeijinForm(player)) return;

        player.addPotionEffect(new PotionEffect(
                PotionEffectType.INCREASE_DAMAGE,
                40,
                0,
                false,
                false
        ));

        player.addPotionEffect(new PotionEffect(
                PotionEffectType.DAMAGE_RESISTANCE,
                40,
                0,
                false,
                false
        ));
    }

    @Override
    public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {

        if (!(event.getDamager() instanceof Player attacker)) return;

        if (!isSeijinForm(attacker)) return;

        if (ThreadLocalRandom.current().nextDouble() < lifestealChancePct / 100.0) {

            double newHealth = Math.min(
                    attacker.getMaxHealth(),
                    attacker.getHealth() + lifestealAmount
            );

            attacker.setHealth(newHealth);
        }
    }
}