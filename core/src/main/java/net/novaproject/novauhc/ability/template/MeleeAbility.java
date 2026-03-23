package net.novaproject.novauhc.ability.template;

import lombok.Getter;
import lombok.Setter;
import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

@Getter
@Setter
public abstract class MeleeAbility extends Ability {

    private UHCPlayer target;

    public MeleeAbility() {
    }

    @Override
    public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;
        if (getOwner() == null || !player.equals(getOwner().getPlayer())) return;
        setTarget(victimP);
        tryUse(player);
    }
}
