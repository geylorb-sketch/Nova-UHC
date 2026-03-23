package net.novauhc.dandadan.roles.bamora;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.template.UseAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

/**
 * Ville — Espace Vide Bamora (Clic-Droit, ENDER_PEARL)
 * TP joueurs 30 blocs dans une ville 1min. Glowing permanent.
 * Bamora obtient Slaughter Mode (golem). CD 15min.
 */
public class VilleAbility extends UseAbility {

    private final List<Player> playersInVille = new ArrayList<>();

    @Override public String getName()       { return "Ville"; }
    @Override public Material getMaterial() { return Material.ENDER_PEARL; }

    @Override
    public boolean onEnable(Player player) {
        Location center = player.getLocation();
        playersInVille.clear();

        for (UHCPlayer uhc : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
            Player p = uhc.getPlayer();
            if (p == null) continue;
            if (p.getLocation().distance(center) <= 30) playersInVille.add(p);
        }

        if (playersInVille.size() <= 1) {
            LangManager.get().send(DanDaDanLang.BAMORA_VILLE_NO_PLAYERS, player);
            return false;
        }

        // TP en hauteur + sol obsidienne
        Location base = center.clone().add(0, 50, 0);
        for (int x = -8; x <= 8; x++)
            for (int z = -8; z <= 8; z++)
                base.clone().add(x, -1, z).getBlock().setType(Material.OBSIDIAN);

        for (Player p : playersInVille) {
            p.teleport(base.clone().add(Math.random() * 10 - 5, 0, Math.random() * 10 - 5));
            p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 60 * 20, 0, false, false));
            if (!p.equals(player)) {
                LangManager.get().send(DanDaDanLang.BAMORA_VILLE_ENTER, p);
            }
        }

        // Bamora: Slaughter Mode (force + speed + 50 coeurs sim)
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 60 * 20, 1, false, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60 * 20, 1, false, true));
        player.setMaxHealth(Math.min(40.0, player.getMaxHealth() + 20));
        player.setHealth(player.getMaxHealth());
        LangManager.get().send(DanDaDanLang.BAMORA_SLAUGHTER_ON, player);

        setCooldown(900); // 15min

        // Cleanup apres 1min
        Bukkit.getScheduler().runTaskLater(Main.get(), () -> {
            for (int x = -8; x <= 8; x++)
                for (int z = -8; z <= 8; z++)
                    base.clone().add(x, -1, z).getBlock().setType(Material.AIR);
            for (Player p : playersInVille) {
                if (p.isOnline()) {
                    p.teleport(center);
                    p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                    LangManager.get().send(DanDaDanLang.BAMORA_VILLE_END, p);
                }
            }
            player.setMaxHealth(20.0);
            playersInVille.clear();
        }, 60 * 20L);
        return true;
    }
}
