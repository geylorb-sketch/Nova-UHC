package net.novauhc.dandadan;

import lombok.experimental.Delegate;
import net.novaproject.novauhc.scenario.role.camps.AbstractCamp;
import net.novaproject.novauhc.scenario.role.camps.Camps;
import org.bukkit.Material;

/**
 * Camps DanDaDan :
 * - SOLO    : Yokai normaux (dernier en vie gagne)
 * - SPECIAL : Yokai spéciaux (malédictions spéciales, désactivés par défaut)
 */
public enum DanDaDanCamps implements Camps {

    SOLO   ("Yokai",   "§5§l", Material.SKULL_ITEM,  null, true),
    SPECIAL("Spécial", "§6§l", Material.NETHER_STAR, null, true);

    @Delegate private final Camps delegate;

    DanDaDanCamps(String name, String color, Material mat, Camps parent, boolean main) {
        this.delegate = new AbstractCamp(name, color, mat, parent, main) {};
    }
}
