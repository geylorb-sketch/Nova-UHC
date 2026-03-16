package net.novauhc.dandadan.roles.denji;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.DanDaDanRole;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;

public class ChaineAbility extends UseAbiliy {
    private final Set<UUID> tagged = new HashSet<>();
    public ChaineAbility() {}
    
    /** Lookup dynamique — RoleVariableProcessor clone via no-arg constructor */
    private DenjiRole getRole(Player player) {
        if (DanDaDan.get() == null || player == null) return null;
        UHCPlayer uhc = UHCPlayerManager.get().getPlayer(player);
        if (uhc == null) return null;
        DanDaDanRole r = DanDaDan.get().getRoleByUHCPlayer(uhc);
        if (r instanceof DenjiRole typed) return typed;
        return null;
    }

    @Override public String getName()       { return "Chaine"; }
    @Override public Material getMaterial() { return Material.COMMAND; }
    @Override public boolean onEnable(Player player) {
        DenjiRole role = getRole(player);
        if (role == null) return false;
        if (!role.useBlood(60)) { LangManager.get().send(DanDaDanLangExt3.DENJI_NOT_ENOUGH_BLOOD, player); return false; }
        LangManager.get().send(DanDaDanLangExt3.DENJI_CHAIN_ACTIVATED, player);
        // Envoie ligne de particules vers tous les joueurs proches
        player.getWorld().getNearbyEntities(player.getLocation(), 15, 15, 15)
                .stream().filter(e -> e instanceof Player && !e.equals(player))
                .forEach(e -> tagged.add(e.getUniqueId()));
        setCooldown(60); return true;
    }
    @Override public void onClick(PlayerInteractEvent event, ItemStack item) {
        if (item == null || item.getType() != Material.COMMAND) return;
        Player player = event.getPlayer();
        if (event.getAction().name().contains("LEFT")) {
            // Ramène le joueur taggé le plus proche
            Player nearest = tagged.stream()
                    .map(Bukkit::getPlayer).filter(Objects::nonNull)
                    .min((a,b) -> Double.compare(a.getLocation().distance(player.getLocation()), b.getLocation().distance(player.getLocation())))
                    .orElse(null);
            if (nearest != null) {
                Location dest = player.getLocation().add(player.getLocation().getDirection().multiply(2));
                nearest.teleport(dest);
                nearest.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 10));
                tagged.remove(nearest.getUniqueId());
            }
        } else if (event.getAction().name().contains("RIGHT")) {
            // Dégâts à distance sur joueur taggé (5s)
            tagged.stream().map(Bukkit::getPlayer).filter(Objects::nonNull).forEach(t -> {
                var taskId = new int[1];
                int[] elapsed = {0};
                taskId[0] = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(), () -> {
                    elapsed[0]++;
                    if (elapsed[0] >= 5) { Main.get().getServer().getScheduler().cancelTask(taskId[0]); return; }
                    t.damage(1.0, player);
                }, 0L, 20L);
            });
        }
    }
}