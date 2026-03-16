package net.novauhc.dandadan.roles.enenra;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Setta — Clic-Droit
 * Reste 3s immobile → vise un joueur → TP à 5 blocs devant lui.
 * Victime : Blindness 10s. Enenra : invisible 20s pour la victime + coups critiques 20s.
 */
public class SettaAbility extends UseAbiliy {

    @AbilityVariable(lang = DanDaDanVarLang.class,
            nameKey = "ENENRA_SETTA_CRIT_DURATION_NAME",
            descKey  = "ENENRA_SETTA_CRIT_DURATION_DESC",
            type = VariableType.TIME)
    private int critDuration = 20;

    private final Map<UUID, Long> chargeStart = new HashMap<>();
    private boolean critActive = false;

    @Override public String getName()       { return "Setta"; }
    @Override public Material getMaterial() { return Material.COAL; }

    @Override
    public boolean onEnable(Player enenra) {
        UUID id = enenra.getUniqueId();
        long now = System.currentTimeMillis();

        if (!chargeStart.containsKey(id)) {
            // Phase 1 : démarre la charge 3s
            chargeStart.put(id, now);
            LangManager.get().send(DanDaDanLang.ENENRA_SETTA_CHARGE, enenra);
            return false; // pas encore compté comme usage
        }

        long elapsed = (now - chargeStart.get(id)) / 1000;
        if (elapsed < 3) {
            enenra.sendMessage("§c✘ Restez encore " + (3 - elapsed) + "s immobile !");
            return false;
        }

        chargeStart.remove(id);

        // Phase 2 : vise un joueur
        Player target = getNearestTarget(enenra, 20);
        if (target == null) { enenra.sendMessage("§c✘ Aucune cible."); return false; }

        // TP 5 blocs devant la cible
        Location dest = target.getLocation().clone().add(
                target.getLocation().getDirection().normalize().multiply(-5));
        enenra.teleport(dest);

        target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 10, 0, false, true));
        enenra.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 20, 0, false, false));

        critActive = true;
        String msg = LangManager.get().get(DanDaDanLang.ENENRA_SETTA_TELEPORT, enenra)
                .replace("%target%", target.getName());
        enenra.sendMessage(msg);

        Main.get().getServer().getScheduler().runTaskLater(Main.get(),
                () -> { critActive = false; enenra.removePotionEffect(PotionEffectType.INVISIBILITY); },
                20L * critDuration);

        setCooldown(900); // 15 mins
        return true;
    }

    public boolean isCritActive() { return critActive; }

    private Player getNearestTarget(Player p, double range) {
        return p.getWorld().getNearbyEntities(p.getLocation(), range, range, range)
                .stream().filter(e -> e instanceof Player && !e.equals(p))
                .map(e -> (Player) e)
                .min((a, b) -> Double.compare(
                        a.getLocation().distance(p.getLocation()),
                        b.getLocation().distance(p.getLocation())))
                .orElse(null);
    }
}
