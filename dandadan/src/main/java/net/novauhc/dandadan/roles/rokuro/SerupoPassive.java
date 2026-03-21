package net.novauhc.dandadan.roles.rokuro;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.entity.Player;

public class SerupoPassive extends PassiveAbility {
    @Override public String getName() { return "Serupo"; }

    @Override
    public boolean onEnable(Player player) {
        return false;
    }
// Max HP increases with kills. Handled in onKill.
}
