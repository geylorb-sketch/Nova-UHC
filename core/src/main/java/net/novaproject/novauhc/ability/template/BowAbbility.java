package net.novaproject.novauhc.ability.template;

import lombok.Getter;
import lombok.Setter;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;

@Setter
@Getter
public abstract class BowAbbility extends Ability {
    private UHCPlayer target;

    public BowAbbility(UHCPlayer target) {
        this.target = target;
    }

    @Override
    public void onBow(Entity shooter, Player target, EntityShootBowEvent event) {
        if(!(event.getProjectile() instanceof Arrow)) return;
        if (!(shooter instanceof Player player)) return;
        UHCPlayer uhcPlayer = UHCPlayerManager.get().getPlayer(target);

        setTarget(uhcPlayer);
        tryUse(player);
    }
}