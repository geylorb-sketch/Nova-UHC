package net.novaproject.ultimate.legend.roles.abilities;

import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NecroSummonActive extends UseAbiliy {

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "NECRO_SKELETON_COUNT_NAME", descKey = "NECRO_SKELETON_COUNT_DESC", type = VariableType.INTEGER)
    private int skeletonCount = 2;

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "NECRO_ZOMBIE_COUNT_NAME", descKey = "NECRO_ZOMBIE_COUNT_DESC", type = VariableType.INTEGER)
    private int zombieCount = 3;

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "NECRO_SEARCH_RADIUS_NAME", descKey = "NECRO_SEARCH_RADIUS_DESC", type = VariableType.DOUBLE)
    private double searchRadius = 30.0;

    public NecroSummonActive() { setCooldown(600); setMaxUse(-1); }

    @Override public String getName() { return "Nécromancie"; }

    @Override
    public boolean onEnable(Player player) {
        UHCPlayer owner = getUHCPlayer(player);
        Player target = findNearestEnemy(player, owner);
        if (target == null) return false;
        for (int i = 0; i < skeletonCount; i++) {
            Skeleton s = player.getWorld().spawn(player.getLocation(), Skeleton.class);
            s.setTarget(target); s.setMaxHealth(40); s.setHealth(40);
            s.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2400, 0));
        }
        for (int i = 0; i < zombieCount; i++) {
            Zombie z = player.getWorld().spawn(target.getLocation().add(0, 2, 0), Zombie.class);
            z.setTarget(target); z.setMaxHealth(40); z.setHealth(40); z.setBaby(true);
            z.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2400, 0));
        }
        return true;
    }

    private Player findNearestEnemy(Player player, UHCPlayer owner) {
        Player nearest = null; double min = searchRadius;
        for (Entity e : player.getNearbyEntities(searchRadius, searchRadius, searchRadius)) {
            if (!(e instanceof Player t) || t == player) continue;
            UHCPlayer tu = UHCPlayerManager.get().getPlayer(t);
            if (tu == null) continue;
            if (owner.getTeam().isPresent() && tu.getTeam().isPresent()
                    && owner.getTeam().get().equals(tu.getTeam().get())) continue;
            double d = player.getLocation().distance(t.getLocation());
            if (d < min) { min = d; nearest = t; }
        }
        return nearest;
    }
}
