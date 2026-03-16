package net.novauhc.dandadan.roles.momo;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MoeMoeAbility extends UseAbiliy {

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "MOMO_MOEMOE_MAX_HITS_NAME", descKey = "MOMO_MOEMOE_MAX_HITS_DESC", type = VariableType.INTEGER)
    private int maxHits = 30;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "MOMO_MOEMOE_SLOW_DURATION_NAME", descKey = "MOMO_MOEMOE_SLOW_DURATION_DESC", type = VariableType.TIME)
    private int slowDuration = 10;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "MOMO_MOEMOE_KNOCKBACK_PCT_NAME", descKey = "MOMO_MOEMOE_KNOCKBACK_PCT_DESC", type = VariableType.PERCENTAGE)
    private double knockbackPct = 0.30;

    private boolean sessionActive = false;
    private int totalHits = 0;
    private final Map<UUID, Integer> hitCounts = new HashMap<>();

    @Override public String getName()       { return "Moe Moe"; }
    @Override public Material getMaterial() { return Material.STICK; }

    @Override
    public boolean onEnable(Player player) {
        sessionActive = true;
        totalHits = 0;
        hitCounts.clear();
        String msg = LangManager.get().get(DanDaDanLang.MOMO_MOEMOE_ACTIVATED, player)
                .replace("%max%", String.valueOf(maxHits));
        player.sendMessage(msg);
        return true;
    }

    @Override
    public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if (!sessionActive) return;
        if (!(event.getDamager() instanceof Player attacker)) return;
        Player victim = victimP.getPlayer();
        if (victim == null) return;

        UUID id = victim.getUniqueId();
        int hits = hitCounts.getOrDefault(id, 0) + 1;
        hitCounts.put(id, hits);
        totalHits++;

        String counter = LangManager.get().get(DanDaDanLang.MOMO_MOEMOE_COUNTER, attacker)
                .replace("%target%", victim.getName())
                .replace("%hits%", String.valueOf(totalHits))
                .replace("%max%", String.valueOf(maxHits));
        attacker.sendMessage(counter);

        applyStage(attacker, victim, hits, id);

        if (totalHits >= maxHits) {
            sessionActive = false;
            hitCounts.clear();
            LangManager.get().send(DanDaDanLang.MOMO_MOEMOE_EXHAUSTED, attacker);
        }
    }

    private void applyStage(Player attacker, Player victim, int hitsOnTarget, UUID id) {
        switch (hitsOnTarget) {
            case 1 -> {
                victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * slowDuration, 1, false, true));
                LangManager.get().send(DanDaDanLang.MOMO_MOEMOE_SLOW, victim);
            }
            case 2 -> {
                LangManager.get().send(DanDaDanLang.MOMO_MOEMOE_LAVA, victim);
                Block below = victim.getLocation().subtract(0, 1, 0).getBlock();
                Material original = below.getType();
                below.setType(Material.LAVA);
                Main.get().getServer().getScheduler().runTaskLater(Main.get(),
                        () -> below.setType(original), 40L); // retire après 2s
            }
            case 3 -> {
                double dmg = victim.getHealth() * knockbackPct;
                victim.damage(dmg, attacker);
                Vector kb = attacker.getLocation().toVector()
                        .subtract(victim.getLocation().toVector())
                        .normalize().multiply(-1.5).setY(0.5);
                victim.setVelocity(kb);
                LangManager.get().send(DanDaDanLang.MOMO_MOEMOE_PROPULSION, victim);
                hitCounts.remove(id);
            }
        }
    }

    public boolean isSessionActive() { return sessionActive; }
}
