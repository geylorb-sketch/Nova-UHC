package net.novauhc.dandadan.roles.minotaure;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLangExt2;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KungFuAbility extends Ability {
    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "KUNGFU_DURATION_NAME", descKey = "KUNGFU_DURATION_DESC", type = VariableType.TIME)
    private int durationSec = 20;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "KUNGFU_RADIUS_NAME", descKey = "KUNGFU_RADIUS_DESC", type = VariableType.INTEGER)
    private int radius = 5;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "KUNGFU_KNOCKBACK_NAME", descKey = "KUNGFU_KNOCKBACK_DESC", type = VariableType.DOUBLE)
    private double knockbackStrength = 1.2;

    @Override public String getName()       { return "Kung-Fu"; }
    @Override public Material getMaterial() { return null; }
    @Override public void onClick(org.bukkit.event.player.PlayerInteractEvent event, ItemStack item) {
        if (!event.getAction().name().contains("LEFT") || item==null || item.getType()!=Material.IRON_INGOT) return;
        tryUse(event.getPlayer());
    }
    @Override public boolean onEnable(Player player) {
        LangManager.get().send(DanDaDanLangExt2.MINO_KUNGFU_ACTIVATED, player);
        var taskId = new int[1];
        int[] elapsed = {0};
        taskId[0] = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(), () -> {
            elapsed[0]++;
            if (elapsed[0] >= durationSec) { Main.get().getServer().getScheduler().cancelTask(taskId[0]); return; }
            player.getWorld().getNearbyEntities(player.getLocation(), radius, radius, radius)
                    .stream().filter(e -> e instanceof Player && !e.equals(player))
                    .forEach(e -> {
                        Player target = (Player) e;
                        target.setFireTicks(40);
                        // Vole la résistance si elle en a
                        target.getActivePotionEffects().stream()
                                .filter(ef -> ef.getType().equals(PotionEffectType.DAMAGE_RESISTANCE))
                                .findFirst().ifPresent(ef -> {
                                    int bonusAmp = Math.max(0, ef.getAmplifier() / 2);
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40, bonusAmp, false, false));
                                });
                    });
        }, 0L, 20L);
        setCooldown(120); return true;
    }
}