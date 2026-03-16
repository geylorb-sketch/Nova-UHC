package net.novauhc.dandadan.roles.enenra;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;

/**
 * Corps de Fumée — Clic-Droit
 * Réduit 75% des dégâts sur une partie du corps pendant 2min.
 * Particules blanches autour de la partie visée.
 */
public class CorpsDeFumeeAbility extends UseAbiliy {

    private boolean active = false;


    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "CORPS_FUMEE_INVIS_DURATION_NAME", descKey = "CORPS_FUMEE_INVIS_DURATION_DESC", type = VariableType.TIME)
    private int invisDuration = 5;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "CORPS_FUMEE_SMOKE_RANGE_NAME", descKey = "CORPS_FUMEE_SMOKE_RANGE_DESC", type = VariableType.INTEGER)
    private int smokeRange = 8;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "CORPS_FUMEE_COOLDOWN_NAME", descKey = "CORPS_FUMEE_COOLDOWN_DESC", type = VariableType.TIME)
    private int cooldown = 180;

    @Override public String getName()       { return "Corps de Fumée"; }
    @Override public Material getMaterial() { return Material.FEATHER; }

    @Override
    public boolean onEnable(Player player) {
        active = true;
        player.sendMessage("§8★ §7Corps de Fumée actif ! (75% réduction, 2min)");

        var taskId = new int[1];
        taskId[0] = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(), () -> {
            if (!active) { Main.get().getServer().getScheduler().cancelTask(taskId[0]); return; }
            new ParticleBuilder(ParticleEffect.REDSTONE)
                    .setColor(Color.WHITE)
                    .setLocation(player.getLocation().add(0, 1, 0))
                    .setOffset(0.3f, 0.5f, 0.3f).setAmount(5).display();
        }, 0L, 4L);

        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            active = false;
            Main.get().getServer().getScheduler().cancelTask(taskId[0]);
        }, 20L * 120);

        setCooldown(420); // 7 mins
        return true;
    }

    /** Retourne true si les dégâts doivent être réduits de 75%. */
    public boolean isActive() { return active; }
}
