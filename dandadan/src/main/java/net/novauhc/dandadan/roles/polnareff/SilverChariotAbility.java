package net.novauhc.dandadan.roles.polnareff;

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

public class SilverChariotAbility extends UseAbiliy {
    public SilverChariotAbility() {}
    
    /** Lookup dynamique — RoleVariableProcessor clone via no-arg constructor */
    private PolnareffRole getRole(Player player) {
        if (DanDaDan.get() == null || player == null) return null;
        UHCPlayer uhc = UHCPlayerManager.get().getPlayer(player);
        if (uhc == null) return null;
        DanDaDanRole r = DanDaDan.get().getRoleByUHCPlayer(uhc);
        if (r instanceof PolnareffRole typed) return typed;
        return null;
    }

    @Override public String getName()       { return "Silver Chariot"; }
    @Override public Material getMaterial() { return Material.DIAMOND_CHESTPLATE; }
    @Override public boolean onEnable(Player player) {
        PolnareffRole role = getRole(player);
        if (role == null) return false;
        role.setStandActive(true);
        role.resetDmgReceived();
        LangManager.get().send(DanDaDanLangExt3.POLNA_STAND_ACTIVATED, player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*600, 0));
        setCooldown(600); return true;
    }
    @Override public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player player)) return;
        PolnareffRole role = getRole(player);
        // Polnareff prend des dégâts
        if (!(event.getEntity() instanceof Player polna)) return;
        if (!role.isStandActive()) return;
        role.addDmgReceived(event.getFinalDamage());
        if (role.getTotalDmgReceived() >= 60) {
            // Switch force → speed
            polna.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
            polna.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*60, 1));
            role.resetDmgReceived();
            polna.sendMessage("§7[Silver Chariot] §fForce → Speed après 30❤ dégâts reçus.");
        }
    }
}