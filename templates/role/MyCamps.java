// TODO: renommer en <NomScénario>Camps et ajuster le package
package net.novaproject.myscenario;

import net.novaproject.novauhc.scenario.role.camps.Camps;
import org.bukkit.Material;

/**
 * Camps du scénario MyScenario.
 * Chaque camp définit : nom affiché, couleur §, icône Material dans l'UI.
 *
 * Exemples de couleurs :
 *   §a = vert, §c = rouge, §e = jaune, §6 = or, §5 = violet, §b = cyan
 */
public enum MyCamps implements Camps {

    // TODO: remplacer/ajouter des camps selon le scénario
    CAMP_A("§8» §a§lCamp A §8«", "§a", Material.EMERALD),
    CAMP_B("§8» §c§lCamp B §8«", "§c", Material.REDSTONE);
    // Exemples : PASSIF/NOCIF/HYBRIDE/BOSS comme dans Monster UHC

    // ── Boilerplate (ne pas modifier) ────────────────────────────────
    private final String displayName;
    private final String color;
    private final Material material;

    MyCamps(String displayName, String color, Material material) {
        this.displayName = displayName;
        this.color       = color;
        this.material    = material;
    }

    @Override public String getDisplayName() { return displayName; }
    @Override public String getColor()       { return color; }
    @Override public Material getMaterial()  { return material; }
}
