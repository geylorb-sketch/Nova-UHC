package net.novauhc.dandadan.roles.flatwoods;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novauhc.dandadan.lang.DanDaDanLangExt;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FumeeAbility extends UseAbiliy {
    private final Map<UUID, Integer> counters = new HashMap<>();

    @Override public String getName()       { return "Fumée"; }
    @Override public Material getMaterial() { return Material.INK_SACK; }

    @Override
    public boolean onEnable(Player player) {
        counters.clear();
        player.sendMessage("§7[Flatwoods] §fCompteur Fumée activé !");
        setCooldown(300);
        return true;
    }

    @Override
    public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        Player victim = victimP.getPlayer();
        if (victim == null) return;
        int count = counters.getOrDefault(victim.getUniqueId(), 0) + 1;
        counters.put(victim.getUniqueId(), count);

        String msg = LangManager.get().get(DanDaDanLangExt.FLATWOODS_FUMEE_COUNTER, (Player)event.getDamager())
                .replace("%target%", victim.getName()).replace("%count%", String.valueOf(count));
        ((Player)event.getDamager()).sendMessage(msg);

        if (count >= 5) {
            counters.remove(victim.getUniqueId());
            victim.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*3, 0));
            victim.damage(1.0); // 0.5❤ saignement simulé × 6s
            Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> victim.damage(1.0), 20L);
            Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> victim.damage(1.0), 40L);
            String msg2 = LangManager.get().get(DanDaDanLangExt.FLATWOODS_FUMEE_MAX, (Player)event.getDamager())
                    .replace("%target%", victim.getName());
            ((Player)event.getDamager()).sendMessage(msg2);
        }
    }
}