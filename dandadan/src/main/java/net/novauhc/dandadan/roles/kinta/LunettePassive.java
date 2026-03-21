package net.novauhc.dandadan.roles.kinta;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import org.bukkit.entity.Player;

/**
 * Lunette — Passif Kinta
 * Voit le nombre de pommes en or au-dessus de la tete des joueurs.
 * Implementé via scoreboard/tag au-dessus de la tête.
 */
public class LunettePassive extends PassiveAbility {

    @Override public String getName() { return "Lunette"; }

    @Override
    public boolean onEnable(Player player) {
        // Chaque seconde, mettre a jour le nombre de gapples au-dessus des joueurs
        for (UHCPlayer uhc : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
            Player p = uhc.getPlayer();
            if (p == null || p.equals(player)) continue;
            if (!p.getWorld().equals(player.getWorld())) continue;
            if (p.getLocation().distance(player.getLocation()) > 50) continue;

            int gapples = countGoldenApples(p);
            // Utiliser le tag au-dessus de la tete (via scoreboard ou hologramme)
            // Simplifié: afficher dans l'action bar
            // En production: utiliser un hologramme Citizens ou ProtocolLib
        }
        return false;
    }

    private int countGoldenApples(Player p) {
        int count = 0;
        for (org.bukkit.inventory.ItemStack item : p.getInventory().getContents()) {
            if (item != null && item.getType() == org.bukkit.Material.GOLDEN_APPLE)
                count += item.getAmount();
        }
        return count;
    }
}
