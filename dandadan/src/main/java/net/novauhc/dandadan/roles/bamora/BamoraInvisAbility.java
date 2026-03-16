package net.novauhc.dandadan.roles.bamora;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Invisibilité — Clic-Droit
 * Invisible 45s. Si Bamora frappe → visible 1s. Si Bamora reçoit un coup → visible 2s.
 */
public class BamoraInvisAbility extends UseAbiliy {

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "BAMORA_INVIS_DURATION_NAME", descKey = "BAMORA_INVIS_DURATION_DESC", type = VariableType.TIME)
    private int duration = 45;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "BAMORA_INVIS_MAX_TIME_NAME", descKey = "BAMORA_INVIS_MAX_TIME_DESC", type = VariableType.TIME)
    private int maxTime = 420; // 7 mins

    private boolean invisible = false;

    @Override public String getName()       { return "Invisibilité"; }
    @Override public Material getMaterial() { return Material.FERMENTED_SPIDER_EYE; }

    @Override
    public boolean onEnable(Player player) {
        invisible = true;
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * duration, 0, false, false));
        LangManager.get().send(DanDaDanLang.BAMORA_INVIS_ACTIVATED, player);
        setCooldown(maxTime);

        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            invisible = false;
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
        }, 20L * duration);
        return true;
    }

    @Override
    public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if (!invisible) return;
        if (!(event.getDamager() instanceof Player attacker)) return;
        // Bamora frappe → visible 1s
        attacker.removePotionEffect(PotionEffectType.INVISIBILITY);
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            if (invisible) attacker.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999, 0, false, false));
        }, 20L);
    }

    public void onHitReceived(Player bamora) {
        if (!invisible) return;
        bamora.removePotionEffect(PotionEffectType.INVISIBILITY);
        LangManager.get().send(DanDaDanLang.BAMORA_INVIS_REVEALED_HIT, bamora);
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            if (invisible) bamora.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999, 0, false, false));
        }, 40L);
    }

    public boolean isInvisible() { return invisible; }
}
