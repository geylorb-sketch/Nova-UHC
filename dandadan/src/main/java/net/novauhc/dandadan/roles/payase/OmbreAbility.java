package net.novauhc.dandadan.roles.payase;

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
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OmbreAbility extends UseAbiliy {

    @AbilityVariable(lang = DanDaDanVarLang.class,
            nameKey = "PAYASE_OMBRE_TRAP_COUNT_NAME",
            descKey  = "PAYASE_OMBRE_TRAP_COUNT_DESC",
            type = VariableType.INTEGER)
    private int trapCount = 10;

    private final List<Location> traps = new ArrayList<>();
    private float absorption = 0;

    @Override public String getName()       { return "Ombre"; }
    @Override public Material getMaterial() { return Material.COAL; }

    @Override
    public boolean onEnable(Player player) {
        traps.clear();
        Location center = player.getLocation().clone();

        for (int i = 0; i < trapCount; i++) {
            double angle = (2 * Math.PI / trapCount) * i;
            Location trap = center.clone().add(Math.cos(angle) * 5, 0, Math.sin(angle) * 5);
            traps.add(trap);
        }

        LangManager.get().send(DanDaDanLang.PAYASE_OMBRE_ACTIVATED, player);

        var taskId = new int[1];
        taskId[0] = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(), () -> {
            traps.forEach(trap -> new ParticleBuilder(ParticleEffect.REDSTONE)
                    .setColor(Color.BLACK).setLocation(trap).setAmount(2).display());

            // Vérifie si un joueur marche dessus
            player.getWorld().getPlayers().forEach(p -> {
                for (int i = 0; i < traps.size(); i++) {
                    Location trap = traps.get(i);
                    if (p.getLocation().distance(trap) < 1.0) {
                        if (p.equals(player)) {
                            // Payase marche dessus → absorption cumulable
                            absorption += 1.0;
                            p.sendMessage(LangManager.get().get(DanDaDanLang.PAYASE_TRAP_SELF, p));
                            traps.remove(i);
                            break;
                        } else {
                            // Ennemi → propulsé 20 blocs + dégâts
                            p.setVelocity(new Vector(0, 2.5, 0));
                            p.damage(3.0); // 1.5❤
                            LangManager.get().send(DanDaDanLang.PAYASE_TRAP_ENEMY, p);
                            traps.remove(i);
                            break;
                        }
                    }
                }
            });

            if (traps.isEmpty()) Main.get().getServer().getScheduler().cancelTask(taskId[0]);
        }, 0L, 5L);

        setCooldown(600);
        return true;
    }
}
