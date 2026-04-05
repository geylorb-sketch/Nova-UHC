// TODO: renommer en <NomScénario>Role et ajuster le package
package net.novaproject.myscenario;

import net.novaproject.novauhc.scenario.role.Role;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

/**
 * Classe abstraite de base commune à tous les rôles du module.
 *
 * - Implémenter ici la logique onSec() commune à tous les rôles (effets permanents, etc.)
 * - Chaque rôle concret hérite de cette classe (et non de Role directement).
 */
public abstract class MyRole extends Role {

    // TODO: déclarer ici des méthodes communes à tous les rôles si nécessaire
    //   ex: getEffects(), getDayEffects(), getNightEffects() pour les PotionEffects

    /**
     * Applique les effets permanents définis dans chaque rôle concret.
     * IMPORTANT : toujours garder le pattern getOwner() — NE PAS utiliser le Player p passé en param.
     */
    @Override
    public void onSec(Player p) {
        if (getOwner() == null) return;
        Player owner = getOwner().getPlayer();
        if (owner == null) return;
        super.onSec(owner);

        // TODO: appliquer des effets communs à tous les rôles si nécessaire
        // ex: pour les effets spécifiques à chaque rôle, surcharger dans la sous-classe
    }

    // ── equals / hashCode basés sur l'ID ─────────────────────────────
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof MyRole)) return false;
        return getId() == ((MyRole) obj).getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }
}
