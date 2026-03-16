package net.novauhc.dandadan.roles.mantis;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import java.util.UUID;

public class BoxeAbility extends UseAbiliy {

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "MANTIS_BOXE_MAX_TIME_NAME", descKey = "MANTIS_BOXE_MAX_TIME_DESC", type = VariableType.TIME)
    private int duration = 90;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "MANTIS_BOXE_RADIUS_NAME", descKey = "MANTIS_BOXE_RADIUS_DESC", type = VariableType.INTEGER)
    private int repelRadius = 15;

    private UUID lockedTarget = null;
    private boolean active = false;
    private int comboHits = 0;

    @Override public String getName()       { return "Boxe"; }
    @Override public Material getMaterial() { return Material.LEASH; }

    @Override
    public boolean onEnable(Player mantis) {
        Player target = getNearestTarget(mantis, 10);
        if (target == null) { mantis.sendMessage("§c✘ Aucune cible."); return false; }

        lockedTarget = target.getUniqueId();
        active = true;
        comboHits = 0;

        String msg = LangManager.get().get(DanDaDanLang.MANTIS_BOXE_ACTIVATED, mantis)
                .replace("%target%", target.getName());
        mantis.sendMessage(msg);

        // Repousse les autres joueurs proches
        var taskId = new int[1];
        taskId[0] = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(), () -> {
            mantis.getWorld().getNearbyEntities(mantis.getLocation(), repelRadius, repelRadius, repelRadius)
                    .stream().filter(e -> e instanceof Player && !e.equals(mantis)
                            && !e.getUniqueId().equals(lockedTarget))
                    .forEach(e -> {
                        Vector dir = e.getLocation().subtract(mantis.getLocation()).toVector().normalize();
                        e.setVelocity(dir.multiply(1.5));
                    });
        }, 0L, 5L);

        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            active = false;
            lockedTarget = null;
            Main.get().getServer().getScheduler().cancelTask(taskId[0]);
        }, 20L * duration);

        setCooldown(600);
        return true;
    }

    @Override
    public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if (!active || lockedTarget == null) return;
        Player victim = victimP.getPlayer();
        if (victim == null || !victim.getUniqueId().equals(lockedTarget)) return;

        comboHits++;
        if (comboHits % 10 == 0) {
            LangManager.get().send(DanDaDanLang.MANTIS_BOXE_NOHITDELAY, (Player) event.getDamager());
            // No hit delay simulé : les 3 prochaines secondes sans délai (flag)
        }
    }

    private Player getNearestTarget(Player p, double range) {
        return p.getWorld().getNearbyEntities(p.getLocation(), range, range, range)
                .stream().filter(e -> e instanceof Player && !e.equals(p))
                .map(e -> (Player) e)
                .min((a, b) -> Double.compare(a.getLocation().distance(p.getLocation()), b.getLocation().distance(p.getLocation())))
                .orElse(null);
    }
}

// ── Salaire Uppercut ─────────────────────────────────────────────
