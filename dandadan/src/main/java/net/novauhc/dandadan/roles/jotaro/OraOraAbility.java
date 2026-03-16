package net.novauhc.dandadan.roles.jotaro;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

public class OraOraAbility extends UseAbiliy {
    @Override public String getName()       { return "Ora Ora"; }
    @Override public Material getMaterial() { return Material.IRON_BARDING; }
    @Override public boolean onEnable(Player player) {
        UHCPlayer uhcPlayer = getUHCPlayer(player);
        if (uhcPlayer == null) return false;
        JotaroRole role = (JotaroRole) DanDaDan.get().getRoleByUHCPlayer(uhcPlayer);
        if (!role.isStandActive()) { player.sendMessage("§c✘ Stand requis !"); return false; }
        Player target = player.getWorld().getNearbyEntities(player.getLocation(),5,5,5)
                .stream().filter(e->e instanceof Player&&!e.equals(player)).map(e->(Player)e)
                .findFirst().orElse(null);
        if (target == null) return false;
        String msg = LangManager.get().get(DanDaDanLangExt3.JOTARO_ORA_ORA, player).replace("%target%",target.getName()); player.sendMessage(msg);
        target.sendMessage("§1[OraOra] §9Duel de CPS 5s avec Jotaro !");
        // Compte les clic d'action → simulé : Jotaro gagne avec 60% de prob
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            boolean jotaroWins = ThreadLocalRandom.current().nextDouble() < 0.60;
            if (jotaroWins) {
                LangManager.get().send(DanDaDanLangExt3.JOTARO_ORA_WIN, player);
                target.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20*300, 0));
            } else {
                LangManager.get().send(DanDaDanLangExt3.JOTARO_ORA_LOSE, player);
                player.setMaxHealth(Math.max(2, player.getMaxHealth() - 4));
            }
        }, 100L);
        setCooldown(600); return true;
    }
}