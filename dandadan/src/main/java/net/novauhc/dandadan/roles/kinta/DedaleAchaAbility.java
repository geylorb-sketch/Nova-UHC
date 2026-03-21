package net.novauhc.dandadan.roles.kinta;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Dedale Acha — Actif Kinta (Clic-Gauche pendant Great Kinta)
 * Speed cumulable par coup base sur vie adverse:
 *   +10 coeurs = 0.5% | 10-8 = 1% | 8-5 = 2% | 5-3 = 3%
 * Max 40%, dure 5min. Retire 2min de malediction. CD 8min.
 */
public class DedaleAchaAbility extends Ability {

    private double accumulatedSpeed = 0;
    private boolean active = false;

    @Override public String getName()       { return "Dedale Acha"; }
    @Override public Material getMaterial() { return null; }

    @Override
    public void onClick(PlayerInteractEvent event, ItemStack item) {
        if (item == null || item.getType() != Material.GOLD_HELMET) return;
        if (!event.getAction().name().contains("LEFT")) return;
        tryUse(event.getPlayer());
    }

    @Override
    public boolean onEnable(Player player) {
        active = true;
        accumulatedSpeed = 0;
        LangManager.get().send(DanDaDanLang.KINTA_DEDALE_ON, player);
        setCooldown(480);

        // Fin apres 5min
        Bukkit.getScheduler().runTaskLater(Main.get(), () -> {
            active = false;
            accumulatedSpeed = 0;
            player.removePotionEffect(PotionEffectType.SPEED);
        }, 5 * 60 * 20L);

        return true;
    }

    @Override
    public void onAttack(UHCPlayer victim, EntityDamageByEntityEvent event) {
        if (!active) return;
        if (!(event.getDamager() instanceof Player attacker)) return;
        Player vp = victim.getPlayer();
        if (vp == null) return;

        double hp = vp.getHealth();
        double bonus;
        if (hp > 20)    bonus = 0.5;
        else if (hp > 16) bonus = 1.0;
        else if (hp > 10) bonus = 2.0;
        else if (hp > 6)  bonus = 3.0;
        else              bonus = 3.0;

        accumulatedSpeed = Math.min(40.0, accumulatedSpeed + bonus);

        // Convertir en amplifier potion (speed 1 = ~20%, speed 2 = ~40%)
        int amplifier = (int) (accumulatedSpeed / 20.0);
        attacker.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5 * 60 * 20, amplifier, false, false));
    }

    public boolean isActive() { return active; }
}
