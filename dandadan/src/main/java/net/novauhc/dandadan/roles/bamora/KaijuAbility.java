package net.novauhc.dandadan.roles.bamora;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.template.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Kaiju — Actif Bamora (Clic-Droit, GOLD_BLOCK)
 * Bloc d'or (coeur) sous les pieds. Tant qu'intact : 15% de force.
 * CD 10min +1min/kill.
 */
public class KaijuAbility extends UseAbiliy {

    private Location heartBlock = null;
    private boolean active = false;

    @Override public String getName()       { return "Kaiju"; }
    @Override public Material getMaterial() { return Material.GOLD_BLOCK; }

    @Override
    public boolean onEnable(Player player) {
        heartBlock = player.getLocation().clone().subtract(0, 1, 0);
        heartBlock.getBlock().setType(Material.GOLD_BLOCK);
        active = true;
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 30 * 20, 0, false, true));
        LangManager.get().send(DanDaDanLang.BAMORA_KAIJU_ON, player);
        setCooldown(600);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!active) { cancel(); return; }
                if (heartBlock.getBlock().getType() != Material.GOLD_BLOCK) {
                    active = false;
                    player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                    LangManager.get().send(DanDaDanLang.BAMORA_KAIJU_BROKEN, player);
                    cancel();
                } else {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 40, 0, false, false));
                }
            }
        }.runTaskTimer(Main.get(),20L,20L);
        return true;
    }

    public boolean isActive()           { return active; }
    public Location getHeartLocation()  { return heartBlock; }
    public void deactivate()            { active = false; }
}
