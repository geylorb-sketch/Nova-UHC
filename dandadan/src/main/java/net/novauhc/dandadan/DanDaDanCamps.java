package net.novauhc.dandadan;

import lombok.experimental.Delegate;
import net.novaproject.novauhc.scenario.role.camps.AbstractCamp;
import net.novaproject.novauhc.scenario.role.camps.Camps;
import org.bukkit.Material;

/**
 * Camps du scénario DanDaDan.
 * SOLO — dernier en vie gagne (mode yokai unique)
 */
public enum DanDaDanCamps implements Camps {

    SOLO("Solo", "§7", Material.SKULL_ITEM, null, true);

    @Delegate private final Camps delegate;

    DanDaDanCamps(String name, String color, Material mat, Camps parent, boolean main) {
        this.delegate = new AbstractCamp(name, color, mat, parent, main) {};
    }
}
