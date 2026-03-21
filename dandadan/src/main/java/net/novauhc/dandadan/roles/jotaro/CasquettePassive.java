package net.novauhc.dandadan.roles.jotaro;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.entity.Player;

public class CasquettePassive extends PassiveAbility {
    @Override public String getName() { return "Casquette"; }

    @Override
    public boolean onEnable(Player player) {
        return false;
    }
// Unbreakable helmets handled via PlayerItemDamageEvent.
}
