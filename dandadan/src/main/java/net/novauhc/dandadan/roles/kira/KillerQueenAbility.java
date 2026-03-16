package net.novauhc.dandadan.roles.kira;

import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KillerQueenAbility extends UseAbiliy {

    @Override public String getName()       { return "Killer Queen"; }
    @Override public Material getMaterial() { return Material.DIAMOND_CHESTPLATE; }
    @Override public boolean onEnable(Player player) {
        KiraRole role = (KiraRole) DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(player));
        role.setStandActive(true);
        LangManager.get().send(DanDaDanLangExt3.KIRA_STAND_ACTIVATED, player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*600, 0));
        setCooldown(600); return true;
    }
    @Override public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        Player player = event.getDamager() instanceof Player ? (Player) event.getDamager() : null;
        if (player == null) return;
        KiraRole role = (KiraRole) DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(player));
        if (!role.isStandActive() || victimP.getPlayer() == null) return;
        Player victim = victimP.getPlayer();
        double totalDmg = 0; // cumule la vie perdue dans cette session — simplifié : on check 10❤ de dégâts cumulés séparement
        // Pour simplification : on tag "explosé" à chaque hit si stand actif
        if (!role.isExploded(victim.getUniqueId())) role.addExploded(victim.getUniqueId(), victim);
    }
}