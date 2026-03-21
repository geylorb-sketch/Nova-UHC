package net.novaproject.ultimate.legend.roles.abilities;

import net.novaproject.novauhc.ability.template.UseAbiliy;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import org.bukkit.entity.Player;

public class CorneMelodieHeal extends UseAbiliy {
    public CorneMelodieHeal() { setCooldown(600); setMaxUse(-1); }
    @Override public String getName() { return "Melodie : Heal"; }
    @Override
    public boolean onEnable(Player player) {
        UHCPlayer owner = getUHCPlayer(player);
        if (owner == null || !owner.getTeam().isPresent()) return false;
        for (UHCPlayer t : owner.getTeam().get().getPlayers()) {
            Player tp = t.getPlayer();
            if (tp != null && tp.isOnline() && tp.getLocation().distance(player.getLocation()) <= 31) tp.setHealth(tp.getMaxHealth());
        }
        return true;
    }
}
