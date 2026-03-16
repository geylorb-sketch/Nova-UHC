package net.novauhc.dandadan.roles.momo;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLangExt4;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

/**
 * Psychokinésie — Actif Momo (2 modes via clic gauche/droit)
 * Gitbook:
 *  - Main Droite (clic-droit, cd 5min): Fait apparaître une main bleue, bloque un joueur visé
 *    et Momo peut le déplacer via son viseur
 *  - Main Gauche (clic-gauche, cd 8min): 3 blocs de pierre lancés, créent structure pierre 3s
 *
 * Simplifié pour Minecraft 1.8.8:
 *  - Clic droit: joueur visé immobilisé 3s (slowness 127 + jump 128)
 *  - Clic gauche: lance 3 FallingBlock STONE dans la direction du viseur
 */
public class PsychokinesieAbility extends UseAbiliy {

    @AbilityVariable(lang = DanDaDanVarLangExt4.class, nameKey = "PSYCHO_GRAB_DURATION_NAME",
            descKey = "PSYCHO_GRAB_DURATION_DESC", type = VariableType.INTEGER)
    private int grabDuration = 3; // seconds

    @AbilityVariable(lang = DanDaDanVarLangExt4.class, nameKey = "PSYCHO_STONE_COUNT_NAME",
            descKey = "PSYCHO_STONE_COUNT_DESC", type = VariableType.INTEGER)
    private int stoneCount = 3;

    private boolean rightClickMode = true; // true = Main Droite, false = Main Gauche

    @Override public String getName() { return "Psychokinésie"; }
    @Override public Material getMaterial() { return Material.LAPIS_BLOCK; }

    @Override
    public void onClick(PlayerInteractEvent event, ItemStack item) {
        if (item == null || item.getType() != getMaterial()) return;

        if (event.getAction().name().contains("LEFT")) {
            // Mode Main Gauche — lancer blocs
            rightClickMode = false;
            tryUse(event.getPlayer());
        } else if (event.getAction().name().contains("RIGHT")) {
            // Mode Main Droite — grab joueur
            rightClickMode = true;
            tryUse(event.getPlayer());
        }
    }

    @Override
    public boolean onEnable(Player player) {
        if (rightClickMode) {
            return useMainDroite(player);
        } else {
            return useMainGauche(player);
        }
    }

    /** Main Droite: immobilise le joueur visé */
    private boolean useMainDroite(Player player) {
        // Trouver le joueur visé (dans la ligne de visée, 20 blocs max)
        Player target = getTargetPlayer(player, 20);
        if (target == null) {
            player.sendMessage("§c✘ Aucun joueur dans votre viseur.");
            return false;
        }

        // Immobiliser la cible
        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, grabDuration * 20, 127, false, false));
        target.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, grabDuration * 20, 128, false, false));
        target.sendMessage("§d§l✋ Psychokinésie ! §r§dVous êtes immobilisé par Momo !");
        player.sendMessage("§d§l✋ Main Droite ! §r§d" + target.getName() + " est bloqué " + grabDuration + "s !");

        setCooldown(300); // 5 min
        return true;
    }

    /** Main Gauche: lance 3 FallingBlock STONE */
    private boolean useMainGauche(Player player) {
        Vector dir = player.getLocation().getDirection().normalize();
        Location spawnLoc = player.getEyeLocation().clone().add(0, 1, 0);

        for (int i = 0; i < stoneCount; i++) {
            Location offset = spawnLoc.clone().add(
                    (Math.random() - 0.5) * 1.5, i * 0.5, (Math.random() - 0.5) * 1.5);
            FallingBlock fb = player.getWorld().spawnFallingBlock(offset, Material.STONE, (byte) 0);
            fb.setVelocity(dir.clone().multiply(1.5 + i * 0.3));
            fb.setDropItem(false);

            // Les blocs disparaissent après 3 secondes
            Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
                if (!fb.isDead()) {
                    Block landed = fb.getLocation().getBlock();
                    fb.remove();
                    // Structure de pierre temporaire
                    if (landed.getType() == Material.AIR || landed.getType() == Material.STONE) {
                        landed.setType(Material.STONE);
                        Main.get().getServer().getScheduler().runTaskLater(Main.get(),
                                () -> { if (landed.getType() == Material.STONE) landed.setType(Material.AIR); },
                                60L); // 3 secondes
                    }
                }
            }, 40L); // 2s de vol
        }

        player.sendMessage("§d§l✋ Main Gauche ! §r§d" + stoneCount + " blocs de pierre lancés !");
        setCooldown(480); // 8 min
        return true;
    }

    /** Cherche le joueur dans la ligne de visée */
    private Player getTargetPlayer(Player player, int maxDist) {
        Vector dir = player.getEyeLocation().getDirection().normalize();
        for (int i = 1; i <= maxDist; i++) {
            Location check = player.getEyeLocation().clone().add(dir.clone().multiply(i));
            for (UHCPlayer uhc : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
                Player p = uhc.getPlayer();
                if (p == null || p.equals(player)) continue;
                if (p.getLocation().distance(check) <= 1.5) return p;
            }
        }
        return null;
    }
}
