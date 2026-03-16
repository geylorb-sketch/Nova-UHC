package net.novauhc.dandadan.roles.rokuro;

import net.novaproject.novauhc.ability.CommandAbility;
import org.bukkit.entity.Player;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.roles.rokuro.RokuroSerpoRole;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;





public class RokuroFormeCommand extends CommandAbility {
    public RokuroFormeCommand() { setCooldown(0); }
    @Override public String getName()       { return "Forme"; }
    @Override public String getCommandKey() { return "forme"; }
    @Override public boolean onCommand(Player player, String[] args) {
        if (DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(player)) instanceof RokuroSerpoRole) ((RokuroSerpoRole) DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(player))).switchForme(player);
        return true;
    }
}