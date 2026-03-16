package net.novauhc.dandadan.roles.reze;

import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.DanDaDanRole;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;

public class TorpilleAbility extends UseAbiliy {
    public TorpilleAbility() {}
    
    /** Lookup dynamique — RoleVariableProcessor clone via no-arg constructor */
    private RezeRole getRole(Player player) {
        if (DanDaDan.get() == null || player == null) return null;
        UHCPlayer uhc = UHCPlayerManager.get().getPlayer(player);
        if (uhc == null) return null;
        DanDaDanRole r = DanDaDan.get().getRoleByUHCPlayer(uhc);
        if (r instanceof RezeRole typed) return typed;
        return null;
    }

    @Override public String getName()       { return "Torpille"; }
    @Override public Material getMaterial() { return Material.FIREWORK_CHARGE; }
    @Override public boolean onEnable(Player player) {
        RezeRole role = getRole(player);
        if (role == null) return false;
        if (!role.isCursed()) { player.sendMessage("§c✘ Malédiction requise !"); return false; }
        role.setTorpilleActive(true);
        LangManager.get().send(DanDaDanLangExt3.REZE_TORPILLE_ACTIVATED, player);
        player.sendMessage("§e🔥🔥🔥 §7(3 flammes actives)");
        setCooldown(720); return true;
    }
    // La réception des coups est gérée dans DenjiRole onAttack via hitTorpille
}