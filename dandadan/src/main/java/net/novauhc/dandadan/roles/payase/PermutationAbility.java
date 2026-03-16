package net.novauhc.dandadan.roles.payase;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;

/**
 * Permutation — Clic-Gauche sur INK_SACK :
 * Invisibilité 15s + particules noires autour de Payase.
 */
public class PermutationAbility extends Ability {

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "PERMUTATION_INVIS_DURATION_NAME", descKey = "PERMUTATION_INVIS_DURATION_DESC", type = VariableType.TIME)
    private int invisDuration = 15; // secondes

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "PERMUTATION_COOLDOWN_NAME", descKey = "PERMUTATION_COOLDOWN_DESC", type = VariableType.TIME)
    private int cooldownSec = 120;

    @Override public String getName()       { return "Permutation"; }
    @Override public Material getMaterial() { return null; }

    @Override
    public void onClick(org.bukkit.event.player.PlayerInteractEvent event,
                        org.bukkit.inventory.ItemStack item) {
        if (event.getAction().name().contains("LEFT")
                && item != null && item.getType() == Material.INK_SACK) {
            tryUse(event.getPlayer());
        }
    }

    @Override
    public boolean onEnable(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * invisDuration, 0, false, false));
        LangManager.get().send(DanDaDanLang.PAYASE_PERMUTATION, player);

        int[] taskId = {0};
        taskId[0] = Main.get().getServer().getScheduler()
                .scheduleSyncRepeatingTask(Main.get(), () ->
                        new ParticleBuilder(ParticleEffect.REDSTONE)
                                .setColor(Color.BLACK)
                                .setLocation(player.getLocation().add(0, 1, 0))
                                .setAmount(3).display(), 0L, 4L);

        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            Main.get().getServer().getScheduler().cancelTask(taskId[0]);
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
        }, 20L * invisDuration);

        setCooldown(cooldownSec);
        return true;
    }
}
