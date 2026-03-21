package net.novauhc.dandadan.roles.momo;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.template.UseAbiliy;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Moe Moe — Actif Momo (Clic-Droit, STICK)
 * Compteur de coups (max 30). Stades par joueur :
 *   🟢 1er coup : Slowness 2 (10s)
 *   🟠 2e coup : Seau de lave sous les pieds (2s après)
 *   🔴 3e coup : Propulsé 10 blocs + -30% vie actuelle
 * Cooldown : 10min +1min/kill
 */
public class MoeMoeAbility extends UseAbiliy {

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "MOEMOE_MAX_HITS_NAME",
            descKey = "MOEMOE_MAX_HITS_DESC", type = VariableType.INTEGER)
    private int maxHits = 30;

    private boolean active = false;
    private int totalHits = 0;
    private final Map<UUID, Integer> stagePerPlayer = new HashMap<>();

    @Override public String getName()       { return "Moe Moe"; }
    @Override public Material getMaterial() { return Material.STICK; }

    @Override
    public boolean onEnable(Player player) {
        active = true;
        totalHits = 0;
        stagePerPlayer.clear();
        LangManager.get().send(DanDaDanLang.MOMO_MOEMOE_ON, player);
        setCooldown(600); // 10min
        return true;
    }

    @Override
    public void onAttack(UHCPlayer victim, EntityDamageByEntityEvent event) {
        if (!active) return;
        if (!(event.getDamager() instanceof Player attacker)) return;
        Player vp = victim.getPlayer();
        if (vp == null) return;

        totalHits++;
        if (totalHits > maxHits) {
            active = false;
            LangManager.get().send(DanDaDanLang.MOMO_MOEMOE_END, attacker);
            return;
        }

        UUID vid = vp.getUniqueId();
        int stage = stagePerPlayer.getOrDefault(vid, 0) + 1;
        stagePerPlayer.put(vid, stage);

        switch (stage) {
            case 1 -> {
                // 🟢 Slowness 2 pendant 10s
                vp.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 1, false, true));
                LangManager.get().send(DanDaDanLang.MOMO_MOEMOE_GREEN, attacker);
            }
            case 2 -> {
                // 🟠 Seau de lave sous les pieds 2s après
                LangManager.get().send(DanDaDanLang.MOMO_MOEMOE_ORANGE, attacker);
                Bukkit.getScheduler().runTaskLater(Main.get(), () -> {
                    if (vp.isOnline()) {
                        vp.getLocation().getBlock().setType(Material.LAVA);
                        // Retirer après 3s
                        Bukkit.getScheduler().runTaskLater(Main.get(), () -> {
                            if (vp.getLocation().getBlock().getType() == Material.LAVA) {
                                vp.getLocation().getBlock().setType(Material.AIR);
                            }
                        }, 60L);
                    }
                }, 40L); // 2s
            }
            default -> {
                if (stage >= 3) {
                    // 🔴 Propulsé 10 blocs + -30% vie
                    Vector knockback = attacker.getLocation().getDirection().normalize().multiply(3.0);
                    knockback.setY(0.8);
                    vp.setVelocity(knockback);
                    double dmg = vp.getHealth() * 0.3;
                    vp.damage(dmg);
                    LangManager.get().send(DanDaDanLang.MOMO_MOEMOE_RED, attacker);
                    stagePerPlayer.put(vid, 0); // Reset stade pour ce joueur
                }
            }
        }
    }

    public boolean isActive() { return active; }
}
