package net.novauhc.dandadan.roles.okarun;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.template.PassiveAbility;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.ShortCooldownManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.Map;

/**
 * Toubillion — Passif Okarun
 * À moins de 3❤️ et quand il prend un coup, Okarun voit des particules bleues.
 * Il peut se mettre en spectateur 5s et régénérer 2❤️.
 *
 * Implémentation : onEnable() détecte le seuil HP et affiche les particules.
 * L'activation réelle se fait via triggerToubillion() appelé depuis OkarunRole.onDamage().
 */
public class ToubillionPassive extends PassiveAbility {

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "TOUBILLION_HP_THRESHOLD_NAME",
            descKey = "TOUBILLION_HP_THRESHOLD_DESC", type = VariableType.DOUBLE)
    private double hpThreshold = 6.0; // 3❤️

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "TOUBILLION_SPEC_DURATION_NAME",
            descKey = "TOUBILLION_SPEC_DURATION_DESC", type = VariableType.INTEGER)
    private int specDuration = 5;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "TOUBILLION_REGEN_NAME",
            descKey = "TOUBILLION_REGEN_DESC", type = VariableType.DOUBLE)
    private double regenAmount = 4.0; // 2❤️

    private static final String CD_KEY = "ToubillionCD";
    private boolean ready = false;

    @Override public String getName() { return "Toubillion"; }

    @Override
    public boolean onEnable(Player player) {
        // Chaque seconde, vérifier si on est sous le seuil
        if (player.getHealth() > hpThreshold || ShortCooldownManager.get(player, CD_KEY) > 0) {
            ready = false;
            return false;
        }

        // Afficher les particules bleues au-dessus de la tête
        ready = true;
        Location above = player.getLocation().clone().add(0, 2.5, 0);
        for (int i = 0; i < 8; i++) {
            double angle = Math.toRadians(i * 45);
            new ParticleBuilder(ParticleEffect.REDSTONE)
                    .setColor(java.awt.Color.CYAN)
                    .setLocation(above.clone().add(Math.cos(angle) * 0.5, 0, Math.sin(angle) * 0.5))
                    .setAmount(2).display();
        }
        return false;
    }

    /**
     * Déclenché quand Okarun prend un coup et est sous le seuil.
     * Appelé depuis OkarunRole.
     */
    public void triggerToubillion(Player player) {
        if (!ready) return;
        if (ShortCooldownManager.get(player, CD_KEY) > 0) return;

        ready = false;
        Location savedLoc = player.getLocation().clone();
        GameMode savedMode = player.getGameMode();

        player.setGameMode(GameMode.SPECTATOR);
        LangManager.get().send(DanDaDanLang.OKARUN_TOUBILLION_START, player, Map.of("%duration%", String.valueOf(specDuration)));
        ShortCooldownManager.put(player, CD_KEY, 60000); // 60s cooldown

        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            if (!player.isOnline()) return;
            player.setGameMode(savedMode);
            player.teleport(savedLoc);
            double newHp = Math.min(player.getMaxHealth(), player.getHealth() + regenAmount);
            player.setHealth(newHp);
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 1, false, false));
            LangManager.get().send(DanDaDanLang.OKARUN_TOUBILLION_END, player, Map.of("%hearts%", String.valueOf((int)(regenAmount / 2))));
        }, 20L * specDuration);
    }
}
