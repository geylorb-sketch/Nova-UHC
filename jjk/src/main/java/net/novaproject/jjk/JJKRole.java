package net.novaproject.jjk;

import net.novaproject.novauhc.Common;
import net.novaproject.novauhc.scenario.role.Role;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public abstract class JJKRole extends Role {

    public PotionEffect[] getEffects() {
        return new PotionEffect[0];
    }

    public PotionEffect[] getDayEffects() {
        return new PotionEffect[0];
    }

    public PotionEffect[] getNightEffects() {
        return new PotionEffect[0];
    }

    public boolean isNight() {
        long time = Common.get().getArena().getTime();
        return time >= 13000 && time < 23500;
    }

    public boolean isDay() {
        long time = Common.get().getArena().getTime();
        return time >= 23500 || time < 13000;
    }

    public abstract void sendDescription(Player p);




    @Override
    public void onSec(Player p) {
        if (getOwner() == null) return;
        Player owner = getOwner().getPlayer();
        if (owner == null) return;

        super.onSec(owner);

        for (PotionEffect effect : getEffects()) {
            owner.removePotionEffect(effect.getType());
            effect.apply(owner);
        }

        if (isNight()) {
            for (PotionEffect effect : getNightEffects()) {
                owner.removePotionEffect(effect.getType());
                effect.apply(owner);
            }
        }

        if (isDay()) {
            for (PotionEffect effect : getDayEffects()) {
                owner.removePotionEffect(effect.getType());
                effect.apply(owner);
            }
        }
    }
}