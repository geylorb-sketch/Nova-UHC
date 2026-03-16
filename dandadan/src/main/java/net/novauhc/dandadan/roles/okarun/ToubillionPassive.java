package net.novauhc.dandadan.roles.okarun;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.PassiveAbility;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.utils.ShortCooldownManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLangExt4;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.Color;

/**
 * Toubillion — Passif Okarun
 * Gitbook: Lorsque Okarun prend un coup à moins de 3❤️, il voit des particules bleues
 * au-dessus de sa tête sur lesquelles il peut cliquer pour se mettre en spectateur 5s
 * et obtenir une régénération de 2❤️.
 *
 * Implémentation: chaque seconde, si < 3❤️ et pas en cooldown, le joueur peut
 * activer l'esquive (clic droit sur FEATHER donné temporairement).
 * Simplifié: détection automatique quand touché < 3❤️ → mode spectateur 5s + regen.
 */
public class ToubillionPassive extends PassiveAbility {

    @AbilityVariable(lang = DanDaDanVarLangExt4.class, nameKey = "TOUBILLION_HP_THRESHOLD_NAME",
            descKey = "TOUBILLION_HP_THRESHOLD_DESC", type = VariableType.DOUBLE)
    private double hpThreshold = 6.0; // 3❤️

    @AbilityVariable(lang = DanDaDanVarLangExt4.class, nameKey = "TOUBILLION_SPEC_DURATION_NAME",
            descKey = "TOUBILLION_SPEC_DURATION_DESC", type = VariableType.INTEGER)
    private int specDuration = 5; // secondes

    @AbilityVariable(lang = DanDaDanVarLangExt4.class, nameKey = "TOUBILLION_REGEN_AMOUNT_NAME",
            descKey = "TOUBILLION_REGEN_AMOUNT_DESC", type = VariableType.DOUBLE)
    private double regenAmount = 4.0; // 2❤️

    private static final String COOLDOWN_KEY = "ToubillionCD";

    @Override public String getName() { return "Toubillion"; }

    @Override
    public boolean onEnable(Player player) {
        if (player.getHealth() > hpThreshold) return false;
        if (ShortCooldownManager.get(player, COOLDOWN_KEY) > 0) return false;

        // Particules bleues au-dessus de la tête
        Location above = player.getLocation().clone().add(0, 2.5, 0);
        for (int i = 0; i < 8; i++) {
            double angle = Math.toRadians(i * 45);
            Location pt = above.clone().add(Math.cos(angle) * 0.5, 0, Math.sin(angle) * 0.5);
            new ParticleBuilder(ParticleEffect.REDSTONE)
                    .setColor(Color.CYAN).setLocation(pt).setAmount(2).display();
        }

        // Activation automatique si coup reçu (via onHit dans le Role)
        return false; // Passive check only — real activation via triggerToubillion()
    }

    /**
     * Appelé depuis OkarunRole.onDamage() quand Okarun est touché < 3❤️
     */
    public void triggerToubillion(Player player) {
        if (ShortCooldownManager.get(player, COOLDOWN_KEY) > 0) return;

        // Sauvegarder position et mode
        Location savedLoc = player.getLocation().clone();
        GameMode savedMode = player.getGameMode();

        // Mode spectateur
        player.setGameMode(GameMode.SPECTATOR);
        player.sendMessage("§b§l✦ Toubillion ! §r§bMode spectateur pendant " + specDuration + "s...");

        ShortCooldownManager.put(player, COOLDOWN_KEY, 60000); // 60s cooldown

        // Retour après 5 secondes
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            if (player.isOnline()) {
                player.setGameMode(savedMode);
                player.teleport(savedLoc);
                // Regen 2❤️
                double newHp = Math.min(player.getMaxHealth(), player.getHealth() + regenAmount);
                player.setHealth(newHp);
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 1, false, false));
                player.sendMessage("§a§l✦ Toubillion terminé ! §r§a+2❤️ régénérés.");
            }
        }, 20L * specDuration);
    }
}
