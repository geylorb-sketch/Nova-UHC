package net.novaproject.novauhc.scenario.role.scenario.mhdragonfall.status;

import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.special.DragonFallLang;
import net.novaproject.novauhc.scenario.role.scenario.mhdragonfall.DragonRole;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class FireBlight extends StatusEffect {

    public FireBlight(Player target, String name, int duration, DragonRole dragon) {
        super(target, name, duration, dragon);
    }


    @Override
    public void start() {
        LangManager.get().send(DragonFallLang.FIRE_BLIGHT_START, getTarget());
    }

    @Override
    public void tick() {
        super.tick();

        Player target = getTarget();

        if (target.getLocation().getBlock().getType() == Material.WATER ||
                target.getLocation().getBlock().getType() == Material.STATIONARY_WATER) {
            end();
            return;
        }

        target.getWorld().spigot().playEffect(
                target.getLocation(),
                Effect.MOBSPAWNER_FLAMES,
                0, 0,
                0.3F, 0.3F, 0.3F,
                0.01F, 5, 30
        );

        getDragon().damage((double) getDragon().getCurrentStrength() / 20, target);
    }

    @Override
    public void end() {
        super.end();
        LangManager.get().send(DragonFallLang.FIRE_BLIGHT_END, getTarget());
    }
}
