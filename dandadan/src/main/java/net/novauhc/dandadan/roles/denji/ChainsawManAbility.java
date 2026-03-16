package net.novauhc.dandadan.roles.denji;

import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.DanDaDanRole;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;

public class ChainsawManAbility extends UseAbiliy {
    public ChainsawManAbility() {}
    
    /** Lookup dynamique — RoleVariableProcessor clone via no-arg constructor */
    private DenjiRole getRole(Player player) {
        if (DanDaDan.get() == null || player == null) return null;
        UHCPlayer uhc = UHCPlayerManager.get().getPlayer(player);
        if (uhc == null) return null;
        DanDaDanRole r = DanDaDan.get().getRoleByUHCPlayer(uhc);
        if (r instanceof DenjiRole typed) return typed;
        return null;
    }

    @Override public String getName()       { return "Chainsaw Man"; }
    @Override public Material getMaterial() { return Material.DIAMOND_AXE; }
    @Override public boolean onEnable(Player player) {
        DenjiRole role = getRole(player);
        if (role == null) return false;
        if (!role.useBlood(100)) { LangManager.get().send(DanDaDanLangExt3.DENJI_NOT_ENOUGH_BLOOD, player); return false; }
        LangManager.get().send(DanDaDanLangExt3.DENJI_CHAINSAW_ACTIVATED, player);
        return true;
    }
    @Override public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker)) return;
        Player victim = victimP.getPlayer(); if (victim == null) return;
        DenjiRole role = getRole(attacker); if (role == null) return;
        role.incrementTarget(victim.getUniqueId());
        int count = role.getTargetCounter(victim.getUniqueId());
        // Paliers de malus
        if (count >= 1 && count <= 3)
            victim.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 120, 0));
        if (count >= 3 && count <= 5)
            event.setDamage(event.getDamage() + 2);
        if (count >= 5 && count <= 7)
            victim.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20, 0));
        if (count >= 7) {
            event.setDamage(4.0); // 2❤ garanti
            role.resetTarget(victim.getUniqueId());
        }
    }
}