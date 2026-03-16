package net.novauhc.dandadan.roles.okarun;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;

public class AllOutAbility extends Ability {

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "OKARUN_ALLOUT_DASH_NAME", descKey = "OKARUN_ALLOUT_DASH_DESC", type = VariableType.INTEGER)
    private int dashDistance = 20;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "OKARUN_ALLOUT_DAMAGE_NAME", descKey = "OKARUN_ALLOUT_DAMAGE_DESC", type = VariableType.DOUBLE)
    private double collisionDamage = 4.0;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "OKARUN_ALLOUT_SLOW_NAME", descKey = "OKARUN_ALLOUT_SLOW_DESC", type = VariableType.TIME)
    private int slowDuration = 30;

    @Override public String getName()       { return "All-Out"; }
    @Override public Material getMaterial() { return null; }

    @Override
    public void onClick(PlayerInteractEvent event, ItemStack item) {
        if (event.getAction().name().contains("LEFT")
                && item != null && item.getType() == Material.BLAZE_POWDER) {
            tryUse(event.getPlayer());
        }
    }

    @Override
    public boolean onEnable(Player player) {
        Vector dir = player.getLocation().getDirection().normalize();

        for (int i = 1; i <= dashDistance; i++) {
            new ParticleBuilder(ParticleEffect.REDSTONE)
                    .setColor(Color.MAGENTA)
                    .setLocation(player.getLocation().clone().add(dir.clone().multiply(i)))
                    .setAmount(3).display();
        }

        player.setVelocity(dir.clone().multiply(2.5));
        LangManager.get().send(DanDaDanLang.OKARUN_ALLOUT_ACTIVATED, player);

        player.getWorld()
                .getNearbyEntities(player.getLocation().clone().add(dir.clone().multiply(dashDistance)), 2.5, 2.5, 2.5)
                .stream().filter(e -> e instanceof Player && !e.equals(player))
                .forEach(e -> {
                    Player victim = (Player) e;
                    victim.damage(collisionDamage, player);
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * slowDuration, 0, false, true));
                    LangManager.get().send(DanDaDanLang.OKARUN_ALLOUT_HIT, victim);
                });

        return true;
    }
}
