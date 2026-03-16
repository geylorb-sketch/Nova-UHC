package net.novauhc.dandadan.roles.umbrella;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt2;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AirStrikeAbility extends UseAbiliy {
    @Override public String getName()       { return "Umbrella Boy Air"; }
    @Override public Material getMaterial() { return Material.FEATHER; }
    @Override public boolean onEnable(Player player) {
        LangManager.get().send(DanDaDanLangExt2.UMBRA_AIR_ACTIVATED, player);
        player.setVelocity(player.getVelocity().setY(2.5));
        // Donne un item "Parasol Position" cliquable 3 fois (compteur via ShortCooldownManager)
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () ->
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*300, 0)), 200L);
        setCooldown(600); return true;
    }
}