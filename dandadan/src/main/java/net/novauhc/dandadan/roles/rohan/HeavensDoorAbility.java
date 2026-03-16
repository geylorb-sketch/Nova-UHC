package net.novauhc.dandadan.roles.rohan;

import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.DanDaDanRole;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;

public class HeavensDoorAbility extends UseAbiliy {
    public HeavensDoorAbility() {}
    
    /** Lookup dynamique — RoleVariableProcessor clone via no-arg constructor */
    private RohanRole getRole(Player player) {
        if (DanDaDan.get() == null || player == null) return null;
        UHCPlayer uhc = UHCPlayerManager.get().getPlayer(player);
        if (uhc == null) return null;
        DanDaDanRole r = DanDaDan.get().getRoleByUHCPlayer(uhc);
        if (r instanceof RohanRole typed) return typed;
        return null;
    }

    @Override public String getName()       { return "Heaven's Door"; }
    @Override public Material getMaterial() { return Material.DIAMOND_CHESTPLATE; }
    @Override public boolean onEnable(Player player) {
        RohanRole role = getRole(player);
        if (role == null) return false;
        role.setStandActive(true);
        LangManager.get().send(DanDaDanLangExt3.ROHAN_STAND_ACTIVATED, player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*600, 0));
        setCooldown(600); return true;
    }
}