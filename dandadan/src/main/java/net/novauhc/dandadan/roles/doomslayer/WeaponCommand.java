package net.novauhc.dandadan.roles.doomslayer;

import net.novaproject.novauhc.ability.CommandAbility;
import org.bukkit.entity.Player;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.DanDaDanRole;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;

public class WeaponCommand extends CommandAbility {
    public WeaponCommand() {}
    
    /** Lookup dynamique — RoleVariableProcessor clone via no-arg constructor */
    private DoomslayerRole getRole(Player player) {
        if (DanDaDan.get() == null || player == null) return null;
        UHCPlayer uhc = UHCPlayerManager.get().getPlayer(player);
        if (uhc == null) return null;
        DanDaDanRole r = DanDaDan.get().getRoleByUHCPlayer(uhc);
        if (r instanceof DoomslayerRole typed) return typed;
        return null;
    }

    @Override public String getName()       { return "Arme"; }
    @Override public String getCommandKey() { return "weapon"; }
    @Override public boolean onCommand(Player player, String[] args) {
        DoomslayerRole role = getRole(player);
        if (role == null) return false;
        if (!role.hasKillToken()) { player.sendMessage("§c✘ Aucun token d'arme disponible."); return false; }
        // Propose le menu en chat
        player.sendMessage("§4[Doom] §cChoisissez : §f/ddd weapon shotgun §c| §f/ddd weapon bfg §c| §f/ddd weapon baliste §c| §f/ddd weapon lance");
        if (args.length < 2) return false;
        switch (args[1].toLowerCase()) {
            case "shotgun"  -> { role.setWeapon(DoomslayerRole.WeaponMode.SHOTGUN);      player.sendMessage("§c★ Shotgun équipé !"); }
            case "bfg"      -> { role.setWeapon(DoomslayerRole.WeaponMode.BFG);          player.sendMessage("§c★ BFG 10 000 équipé !"); }
            case "baliste"  -> { role.setWeapon(DoomslayerRole.WeaponMode.BALISTE);      player.sendMessage("§c★ Baliste équipée !"); }
            case "lance"    -> { role.setWeapon(DoomslayerRole.WeaponMode.LANCE_FLAMME); player.sendMessage("§c★ Lance-Flammes équipé !"); }
            default -> { player.sendMessage("§c✘ Arme inconnue."); return false; }
        }
        return true;
    }
}