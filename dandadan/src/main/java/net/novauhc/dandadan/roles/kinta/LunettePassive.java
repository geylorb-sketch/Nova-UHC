package net.novauhc.dandadan.roles.kinta;

import net.novaproject.novauhc.ability.PassiveAbility;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/** Lunette — voit le nb de pommes en or au-dessus de la tête des joueurs proches (ActionBar simulé). */
public class LunettePassive extends PassiveAbility {
    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "LUNETTE_RANGE_NAME", descKey = "LUNETTE_RANGE_DESC", type = VariableType.INTEGER)
    private int detectionRange = 20;

    @Override public String getName()       { return "Lunette"; }
    @Override public Material getMaterial() { return null; }

    @Override
    public boolean onEnable(Player player) {
        player.getWorld().getNearbyEntities(player.getLocation(), detectionRange, detectionRange, detectionRange)
                .stream().filter(e -> e instanceof Player && !e.equals(player))
                .forEach(e -> {
                    Player target = (Player) e;
                    long gapples = java.util.Arrays.stream(target.getInventory().getContents())
                            .filter(i -> i != null && (i.getType() == Material.GOLDEN_APPLE))
                            .mapToLong(i -> i.getAmount()).sum();
                    if (gapples > 0) {
                        // Affiché via floating damage (armor stand temporaire)
                        net.novaproject.novauhc.utils.UHCUtils.spawnFloatingDamage(player,
                                "§6" + target.getName() + " §f" + gapples + "🍎");
                    }
                });
        return true;
    }
}
