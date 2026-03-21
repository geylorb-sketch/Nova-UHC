package net.novauhc.dandadan.roles.bamora;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * Projectile electrique — Passif Bamora
 * Tous les 15 coups d'epee, rangee d'eclairs infligeant 1.5 coeurs.
 */
public class ProjectilePassive extends PassiveAbility {

    private int hitCount = 0;
    private static final int TRIGGER_HITS = 15;
    private static final double LIGHTNING_DMG = 3.0; // 1.5 coeurs

    @Override public String getName() { return "Projectile electrique"; }

    @Override
    public boolean onEnable(Player player) { return false; }

    public void onHit(Player attacker, Player victim) {
        hitCount++;
        if (hitCount >= TRIGGER_HITS) {
            hitCount = 0;
            // Ligne d'eclairs devant Bamora
            Location start = attacker.getLocation();
            org.bukkit.util.Vector dir = start.getDirection().normalize();
            for (int i = 2; i <= 8; i += 2) {
                Location strikePos = start.clone().add(dir.clone().multiply(i));
                attacker.getWorld().strikeLightningEffect(strikePos);
                // Degats aux joueurs proches de l'eclair
                for (Entity e : attacker.getWorld().getNearbyEntities(strikePos, 2, 2, 2)) {
                    if (e instanceof Player p && !p.equals(attacker)) {
                        p.damage(LIGHTNING_DMG, attacker);
                    }
                }
            }
            LangManager.get().send(DanDaDanLang.BAMORA_PROJECTILE_TRIGGER, attacker);
        }
    }
}
