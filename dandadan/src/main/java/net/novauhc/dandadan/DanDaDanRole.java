package net.novauhc.dandadan;

import net.novaproject.novauhc.scenario.role.Role;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.Material;

/**
 * Classe abstraite de base pour tous les rôles DanDaDan.
 * Pour l'instant AUCUN pouvoir — uniquement le squelette.
 * Les pouvoirs seront ajoutés plus tard.
 */
public abstract class DanDaDanRole extends Role {

    public DanDaDanRole() {
        setCamp(DanDaDanCamps.SOLO);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(getIconMaterial())
                .setName("§5" + getName());
    }

    public abstract Material getIconMaterial();
}
