package net.novauhc.dandadan.roles.bamora;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.utils.UHCUtils;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLangExt4;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Slaughter Mode — Bamora (Espace Vide)
 * Gitbook: Bamora sera dans un golem de fer qu'elle contrôlera, qui possède
 * 20% de force et 20% de speed, ainsi que 50❤️.
 * Si sa vie descend à 0❤️ avant la fin de l'espace vide, ce dernier s'arrête.
 */
public class SlaughterModeAbility extends UseAbiliy {

    @AbilityVariable(lang = DanDaDanVarLangExt4.class, nameKey = "SLAUGHTER_HP_NAME",
            descKey = "SLAUGHTER_HP_DESC", type = VariableType.INTEGER)
    private int slaughterHP = 100; // 50❤️

    @AbilityVariable(lang = DanDaDanVarLangExt4.class, nameKey = "SLAUGHTER_DURATION_NAME",
            descKey = "SLAUGHTER_DURATION_DESC", type = VariableType.INTEGER)
    private int duration = 60; // secondes

    @Override public String getName() { return "Slaughter Mode"; }
    @Override public Material getMaterial() { return Material.IRON_BLOCK; }

    @Override
    public boolean onEnable(Player player) {
        // Appliquer les HP du golem
        UHCUtils.setRealHealth(slaughterHP, slaughterHP, player, 0);

        // Force + Speed
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, duration * 20, 0, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, duration * 20, 0, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, duration * 20, 0, false, false));

        player.sendMessage("§6§l⚙ Slaughter Mode ! §r§6Tu contrôles un golem de fer (" + (slaughterHP / 2) + "❤️) !");

        // Timer pour fin du mode
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            endSlaughterMode(player);
        }, 20L * duration);

        setCooldown(duration);
        return true;
    }

    public void endSlaughterMode(Player player) {
        if (player == null || !player.isOnline()) return;
        player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
        player.removePotionEffect(PotionEffectType.SPEED);
        player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        // Reset HP normal
        player.setMaxHealth(20.0);
        player.setHealth(Math.min(20.0, player.getHealth()));
        player.sendMessage("§7§l⚙ Slaughter Mode terminé.");
    }
}
