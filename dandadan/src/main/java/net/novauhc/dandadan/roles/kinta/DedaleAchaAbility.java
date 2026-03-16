package net.novauhc.dandadan.roles.kinta;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.ShortCooldownManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLangExt4;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Dédale Acha — Actif Kinta (clic gauche pendant Great Kinta)
 * Gitbook: Speed cumulable à chaque coup, basée sur la vie de l'adversaire:
 *   10+❤️ = 0.5%, 10-8❤️ = 1%, 8-5❤️ = 2%, 5-3❤️ = 3%
 *   Max 40%, dure 5 minutes. Cooldown 8min, retire 2min de malédiction.
 */
public class DedaleAchaAbility extends Ability {

    private double accumulatedSpeed = 0.0;
    private boolean active = false;
    private static final double MAX_SPEED = 40.0;

    @Override public String getName() { return "Dédale Acha"; }
    @Override public Material getMaterial() { return null; }

    @Override
    public void onClick(PlayerInteractEvent event, ItemStack item) {
        if (event.getAction().name().contains("LEFT")
                && item != null && item.getType() == Material.GOLD_INGOT) {
            tryUse(event.getPlayer());
        }
    }

    @Override
    public boolean onEnable(Player player) {
        active = true;
        accumulatedSpeed = 0.0;
        player.sendMessage("§e§l⚡ Dédale Acha activé ! §r§eFrappe pour accumuler de la speed !");
        setCooldown(480); // 8 min
        return true;
    }

    @Override
    public void onAttack(UHCPlayer victim, EntityDamageByEntityEvent event) {
        if (!active) return;
        if (!(event.getDamager() instanceof Player attacker)) return;
        Player target = victim.getPlayer();
        if (target == null) return;

        double hp = target.getHealth();
        double bonus;
        if (hp > 20) bonus = 0.5;
        else if (hp > 16) bonus = 1.0;
        else if (hp > 10) bonus = 2.0;
        else if (hp > 6) bonus = 3.0;
        else bonus = 3.0;

        accumulatedSpeed = Math.min(MAX_SPEED, accumulatedSpeed + bonus);
        int amp = (int)(accumulatedSpeed / 20.0); // 0 ou 1
        attacker.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000, amp, false, false));
        attacker.sendMessage("§e⚡ Speed: " + String.format("%.1f", accumulatedSpeed) + "% / " + (int)MAX_SPEED + "%");
    }
}
