package net.novauhc.dandadan.roles.csg;

import net.novaproject.novauhc.ability.CommandAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CouteauCommand extends CommandAbility {
    // UUID cible → will steal on death
    private final Map<UUID, String> targeted = new HashMap<>();
    public CouteauCommand() { setMaxUse(2); }
    @Override public String getName()       { return "Couteau"; }
    @Override public String getCommandKey() { return "couteau"; }
    @Override public boolean onCommand(Player player, String[] args) {
        if (args.length < 2) { LangManager.get().send(DanDaDanLangExt3.CSG_COUTEAU_USAGE, player); return false; }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) { player.sendMessage("§c✘ Joueur introuvable."); return false; }
        targeted.put(target.getUniqueId(), target.getName());
        String msg = LangManager.get().get(DanDaDanLangExt3.CSG_COUTEAU_ACTIVATED, player).replace("%target%", target.getName());
        player.sendMessage(msg);
        return true;
    }
    public boolean isTargeted(UUID uuid) { return targeted.containsKey(uuid); }
    public String getTargetName(UUID uuid) { return targeted.get(uuid); }
    public void removeTarget(UUID uuid) { targeted.remove(uuid); }
}