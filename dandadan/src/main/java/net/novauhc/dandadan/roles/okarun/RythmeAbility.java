package net.novauhc.dandadan.roles.okarun;

import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.utils.ShortCooldownManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLangExt4;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Rythme — Actif Okarun
 * Gitbook: Après activation, Okarun doit mettre 5 coups (compteur au-dessus de la tête)
 * avec minimum 1 coup d'épée de différence avec le joueur, pour obtenir un no hit delay
 * à hauteur de 60% durant 7 secondes. Cooldown 10min.
 */
public class RythmeAbility extends UseAbiliy {

    @AbilityVariable(lang = DanDaDanVarLangExt4.class, nameKey = "RYTHME_REQUIRED_HITS_NAME",
            descKey = "RYTHME_REQUIRED_HITS_DESC", type = VariableType.INTEGER)
    private int requiredHits = 5;

    @AbilityVariable(lang = DanDaDanVarLangExt4.class, nameKey = "RYTHME_BUFF_DURATION_NAME",
            descKey = "RYTHME_BUFF_DURATION_DESC", type = VariableType.INTEGER)
    private int buffDuration = 7; // seconds

    private boolean active = false;
    private final Map<UUID, Integer> hitCounters = new HashMap<>();

    @Override public String getName() { return "Rythme"; }
    @Override public Material getMaterial() { return Material.NETHER_STAR; }

    public RythmeAbility() {
        setCooldown(600); // 10 min
    }

    @Override
    public boolean onEnable(Player player) {
        active = true;
        hitCounters.clear();
        player.sendMessage("§e§l♪ Rythme activé ! §r§eMets 5 coups pour déclencher le no-hit delay !");
        setCooldown(600);
        return true;
    }

    @Override
    public void onAttack(UHCPlayer victim, EntityDamageByEntityEvent event) {
        if (!active) return;
        if (!(event.getDamager() instanceof Player attacker)) return;

        UUID victimId = victim.getPlayer().getUniqueId();
        int count = hitCounters.getOrDefault(victimId, 0) + 1;
        hitCounters.put(victimId, count);

        // Compteur visible
        attacker.sendMessage("§e♪ Rythme : " + count + "/" + requiredHits + " coups");

        if (count >= requiredHits) {
            // Activer no-hit delay 60% pendant 7s
            active = false;
            hitCounters.clear();
            ShortCooldownManager.put(attacker, "RythmeNoHitDelay", buffDuration * 1000L);
            attacker.sendMessage("§a§l♪ Rythme ! §r§aNo-hit delay 60% pendant " + buffDuration + "s !");
        }
    }

    /**
     * Vérifie si le no-hit delay est actif pour modifier les dégâts.
     * À appeler dans OkarunRole.onHit() ou via un listener.
     */
    public boolean isNoHitDelayActive(Player player) {
        return ShortCooldownManager.get(player, "RythmeNoHitDelay") > 0;
    }
}
