package net.novauhc.dandadan.roles.okarun;

import net.novaproject.novauhc.ability.template.UseAbiliy;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.ShortCooldownManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Rythme — Actif Okarun (Clic-Droit, NETHER_STAR)
 * Après activation, 5 coups sur un même joueur (avec min 1 épée de diff)
 * → no hit delay 60% pendant 7s. Cooldown 10min.
 */
public class RythmeAbility extends UseAbiliy {

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "RYTHME_HITS_NAME",
            descKey = "RYTHME_HITS_DESC", type = VariableType.INTEGER)
    private int requiredHits = 5;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "RYTHME_BUFF_DURATION_NAME",
            descKey = "RYTHME_BUFF_DURATION_DESC", type = VariableType.TIME)
    private int buffDuration = 7;

    private boolean active = false;
    private final Map<UUID, Integer> hitCounters = new HashMap<>();

    @Override public String getName()       { return "Rythme"; }
    @Override public Material getMaterial() { return Material.NETHER_STAR; }

    @Override
    public boolean onEnable(Player player) {
        active = true;
        hitCounters.clear();
        LangManager.get().send(DanDaDanLang.OKARUN_RYTHME_ON, player, Map.of("%hits%", String.valueOf(requiredHits)));
        setCooldown(600); // 10min
        return true;
    }

    @Override
    public void onAttack(UHCPlayer victim, EntityDamageByEntityEvent event) {
        if (!active) return;
        if (!(event.getDamager() instanceof Player attacker)) return;
        if (victim.getPlayer() == null) return;

        UUID victimId = victim.getPlayer().getUniqueId();
        int count = hitCounters.getOrDefault(victimId, 0) + 1;
        hitCounters.put(victimId, count);

        LangManager.get().send(DanDaDanLang.OKARUN_RYTHME_COUNT, attacker, Map.of("%count%", String.valueOf(count), "%max%", String.valueOf(requiredHits)));

        if (count >= requiredHits) {
            active = false;
            hitCounters.clear();
            ShortCooldownManager.put(attacker, "RythmeNoHitDelay", buffDuration * 1000L);
            LangManager.get().send(DanDaDanLang.OKARUN_RYTHME_TRIGGERED, attacker, Map.of("%duration%", String.valueOf(buffDuration)));
        }
    }

    public boolean isNoHitDelayActive(Player player) {
        return ShortCooldownManager.get(player, "RythmeNoHitDelay") > 0;
    }
}
