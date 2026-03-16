package net.novauhc.dandadan.roles.polnareff;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class ImageRemanteAbility extends UseAbiliy {

    private UUID lastTarget = null;

    @Override public String getName()       { return "Image rémanente"; }
    @Override public Material getMaterial() { return Material.PAPER; }
    @Override public boolean onEnable(Player player) {
        PolnareffRole role = (PolnareffRole) DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(player));
        if (!role.isStandActive()) { player.sendMessage("§c✘ Stand requis !"); return false; }
        LangManager.get().send(DanDaDanLangExt3.POLNA_IMAGE_REMANENTE, player);
        Player target = lastTarget != null ? Bukkit.getPlayer(lastTarget) : null;
        Player finalTarget = target;
        for (int i=0;i<6;i++) {
            Location spawnLoc = player.getLocation().clone().add(
                    ThreadLocalRandom.current().nextDouble(-3,3),0,ThreadLocalRandom.current().nextDouble(-3,3));
            Zombie zombie = (Zombie) player.getWorld().spawnEntity(spawnLoc, org.bukkit.entity.EntityType.ZOMBIE);
            zombie.setCustomName("§f" + player.getName());
            zombie.setMaxHealth(60); zombie.setHealth(60);
            zombie.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20*60, 2));
            if (finalTarget != null) zombie.setTarget(finalTarget);
            Zombie finalZombie = zombie;
            Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> { if(!finalZombie.isDead()) finalZombie.remove(); }, 20L*60);
        }
        setCooldown(720); return true;
    }
    @Override public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if (victimP.getPlayer() != null) lastTarget = victimP.getPlayer().getUniqueId();
    }
}