package net.novauhc.dandadan.roles.jotaro;

import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StarFingerAbility extends UseAbiliy {

    @Override public String getName()       { return "Star Finger"; }
    @Override public Material getMaterial() { return Material.BONE; }
    @Override public boolean onEnable(Player player) {
        LangManager.get().send(DanDaDanLangExt3.JOTARO_STAR_FINGER, player);
        UHCPlayer uhcPlayer = getUHCPlayer(player);
        if (uhcPlayer == null) return false;
        JotaroRole role = (JotaroRole) DanDaDan.get().getRoleByUHCPlayer(uhcPlayer);
        role.setStarFingerPierce(3);
        // Prochain coup : portée +5 blocs → simulé en cherchant une cible à 8 blocs
        Player target = player.getWorld().getNearbyEntities(player.getLocation(),8,8,8)
                .stream().filter(e->e instanceof Player&&!e.equals(player)).map(e->(Player)e)
                .min((a,b)->Double.compare(a.getLocation().distance(player.getLocation()),b.getLocation().distance(player.getLocation())))
                .orElse(null);
        if (target != null) {
            target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 0));
            target.damage(2.0, player);
        }
        setCooldown(300); return true;
    }
    @Override public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;
        UHCPlayer uhcPlayer = getUHCPlayer(player);
        JotaroRole role = (JotaroRole) DanDaDan.get().getRoleByUHCPlayer(uhcPlayer);
        if (role.getStarFingerPierce() <= 0) return;
        // Annule la résistance
        if (victimP.getPlayer() != null) {
            victimP.getPlayer().removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        }
        role.useStarFingerPierce();
    }
}