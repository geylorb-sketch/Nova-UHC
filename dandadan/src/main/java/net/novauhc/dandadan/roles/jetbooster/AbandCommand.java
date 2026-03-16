package net.novauhc.dandadan.roles.jetbooster;

import net.novaproject.novauhc.ability.CommandAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.ShortCooldownManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class AbandCommand extends CommandAbility {
    public AbandCommand() { setMaxUse(3); }
    @Override public String getName()       { return "Abandon"; }
    @Override public String getCommandKey() { return "aband"; }
    @Override public boolean onCommand(Player player, String[] args) {
        LangManager.get().send(DanDaDanLangExt.KUR_ABAND_ACTIVATED, player);
        // Abandon : Kur est téléporté à une position aléatoire dans 50 blocs
        World world = player.getWorld();
        double x = player.getLocation().getX() + (Math.random()-0.5)*100;
        double z = player.getLocation().getZ() + (Math.random()-0.5)*100;
        player.teleport(new Location(world, x, world.getHighestBlockYAt((int)x,(int)z)+1, z));
        // Bloque l'utilisation de la capacité principale 10min
        ShortCooldownManager.put(player, "KurMainCooldown", 600_000L);
        return true;
    }
}