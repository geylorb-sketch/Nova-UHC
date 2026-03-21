package net.novauhc.dandadan.roles.kashimoto;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.template.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Esprit Protecteur — Actif Kashimoto (Clic-Gauche, SKULL_ITEM)
 * PNJ tete a cote. 20% force. Re-clic: empeche de courir toutes les 5s 1min.
 * CD 3min.
 */
public class EspritProtecteurAbility extends UseAbiliy {

    private boolean spiritActive = false;

    @Override public String getName()       { return "Esprit Protecteur"; }
    @Override public Material getMaterial() { return Material.SKULL_ITEM; }

    @Override
    public boolean onEnable(Player player) {
        spiritActive = true;
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 60 * 20, 0, false, true));
        LangManager.get().send(DanDaDanLang.KASHIMOTO_ESPRIT_ON, player);
        setCooldown(180); // 3min

        // TODO: spawn NPC tete avec Citizens

        // L'esprit disparait apres 1min
        Bukkit.getScheduler().runTaskLater(Main.get(), () -> spiritActive = false, 60 * 20L);
        return true;
    }

    public boolean isSpiritActive() { return spiritActive; }
}
