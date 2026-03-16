package net.novauhc.dandadan.roles.caesar;

import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.DanDaDanRole;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;

public class SavonCutterAbility extends UseAbiliy {
    public SavonCutterAbility() {}
    
    /** Lookup dynamique — RoleVariableProcessor clone via no-arg constructor */
    private CaesarRole getRole(Player player) {
        if (DanDaDan.get() == null || player == null) return null;
        UHCPlayer uhc = UHCPlayerManager.get().getPlayer(player);
        if (uhc == null) return null;
        DanDaDanRole r = DanDaDan.get().getRoleByUHCPlayer(uhc);
        if (r instanceof CaesarRole typed) return typed;
        return null;
    }

    @Override public String getName()       { return "Savon Cutter"; }
    @Override public Material getMaterial() { return Material.SNOW_BALL; }
    @Override public boolean onEnable(Player player) {
        LangManager.get().send(DanDaDanLangExt3.CAESAR_SAVON_CUTTER, player);
        // Pose une zone anti-lave dans la direction visée (flag dans un Set static)
        NoLavaZone.placeZone(player.getLocation(), System.currentTimeMillis() + 180_000L);
        setCooldown(420); return true;
    }
}