package net.novaproject.ultimate.legend;

import lombok.Getter;
import lombok.Setter;
import net.novaproject.novauhc.lang.Lang;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.role.Role;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Classe abstraite de base pour toutes les légendes.
 *
 * Stocke le owner (UHCPlayer) pour permettre aux onHit/onKill
 * de vérifier si c'est bien CE joueur qui est impliqué.
 */
@Getter
@Setter
public abstract class LegendRole extends Role {

    /** Le joueur qui possède ce rôle — set dans onGive() */
    private transient UHCPlayer owner;

    public LegendRole() {
        setCamp(LegendCamps.LEGEND);
    }

    /** ID unique de la légende */
    public abstract int getId();

    /** Matériau pour l'icône dans l'UI de sélection */
    public abstract Material getIconMaterial();

    /** Clé Lang pour la description traduite */
    public abstract Lang getDescriptionLang();

    /**
     * getDescription() retourne un fallback FR pour l'UI (pas de contexte Player).
     * Pour le message au joueur, on utilise getDescriptionLang() + LangManager dans onGive().
     */
    @Override
    public String getDescription() {
        return LangManager.get().get(getDescriptionLang());
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        this.owner = uhcPlayer;
        Player player = uhcPlayer.getPlayer();
        if (player == null) return;

        // Description traduite via LangManager
        String desc = LangManager.get().get(getDescriptionLang(), player);
        desc = applyPlaceholders(desc);
        player.sendMessage(desc);

        // Donner les items d'abilities
        if (!getAbilities().isEmpty()) {
            getAbilities().forEach(ability -> ability.onGive(uhcPlayer));
        }
    }

    /**
     * Override dans les rôles qui ont des placeholders dans la description.
     * Ex: %extra_hearts%, %cooldown%, etc.
     */
    protected String applyPlaceholders(String desc) {
        return desc;
    }

    /**
     * Vérifie si l'entité est le joueur owner de ce rôle.
     */
    public boolean isOwner(org.bukkit.entity.Entity entity) {
        if (owner == null || !(entity instanceof Player p)) return false;
        Player ownerPlayer = owner.getPlayer();
        return ownerPlayer != null && ownerPlayer.equals(p);
    }

    /**
     * Vérifie si le damager est le owner (melee uniquement, pas flèche).
     */
    public boolean isOwnerMelee(org.bukkit.entity.Entity damager) {
        return damager instanceof Player && isOwner(damager);
    }

    /**
     * Vérifie si le damager est une flèche tirée par le owner.
     */
    public boolean isOwnerArrow(org.bukkit.entity.Entity damager) {
        if (!(damager instanceof org.bukkit.entity.Arrow arrow)) return false;
        if (!(arrow.getShooter() instanceof Player shooter)) return false;
        return isOwner(shooter);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return getId() == ((LegendRole) obj).getId();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(getId());
    }
}
