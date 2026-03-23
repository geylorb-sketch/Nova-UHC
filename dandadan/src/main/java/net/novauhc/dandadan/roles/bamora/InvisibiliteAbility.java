package net.novauhc.dandadan.roles.bamora;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.template.UseAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Invisibilite — Actif Bamora (Clic-Droit, GLASS)
 * Invisible avec armure 45s. Coup donne = visible 1s. Coup recu = visible 2s.
 * CD 7min -1min/kill.
 */
public class InvisibiliteAbility extends UseAbility {

    private boolean invisActive = false;

    @Override public String getName()       { return "Invisibilite"; }
    @Override public Material getMaterial() { return Material.GLASS; }

    @Override
    public boolean onEnable(Player player) {
        invisActive = true;
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 45 * 20, 0, false, false));
        LangManager.get().send(DanDaDanLang.BAMORA_INVIS_ON, player);
        setCooldown(420); // 7min

        Bukkit.getScheduler().runTaskLater(Main.get(), () -> {
            invisActive = false;
        }, 45 * 20L);
        return true;
    }

    /** Appelé quand Bamora frappe — redevient visible 1s */
    public void onDealtHit(Player player) {
        if (!invisActive) return;
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
        Bukkit.getScheduler().runTaskLater(Main.get(), () -> {
            if (invisActive && player.isOnline())
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 45 * 20, 0, false, false));
        }, 20L); // 1s
    }

    /** Appelé quand Bamora recoit un coup — redevient visible 2s */
    public void onTookHit(Player player) {
        if (!invisActive) return;
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
        Bukkit.getScheduler().runTaskLater(Main.get(), () -> {
            if (invisActive && player.isOnline())
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 45 * 20, 0, false, false));
        }, 40L); // 2s
    }

    public boolean isInvisActive() { return invisActive; }
}
