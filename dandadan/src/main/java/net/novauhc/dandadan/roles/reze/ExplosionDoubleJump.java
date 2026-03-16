package net.novauhc.dandadan.roles.reze;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.DanDaDanRole;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;

public class ExplosionDoubleJump extends Ability {
    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "EXPLOSION_DJ_COOLDOWN_NAME", descKey = "EXPLOSION_DJ_COOLDOWN_DESC", type = VariableType.TIME)
    private int cooldownMs = 3000; // millisecondes

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "EXPLOSION_DJ_LAUNCH_POWER_NAME", descKey = "EXPLOSION_DJ_LAUNCH_POWER_DESC", type = VariableType.DOUBLE)
    private double launchPower = 1.2;

    private long lastUse = 0;
    public ExplosionDoubleJump() {}
    
    /** Lookup dynamique — RoleVariableProcessor clone via no-arg constructor */
    private RezeRole getRole(Player player) {
        if (DanDaDan.get() == null || player == null) return null;
        UHCPlayer uhc = UHCPlayerManager.get().getPlayer(player);
        if (uhc == null) return null;
        DanDaDanRole r = DanDaDan.get().getRoleByUHCPlayer(uhc);
        if (r instanceof RezeRole typed) return typed;
        return null;
    }

    @Override public String getName()       { return "Explosion"; }
    @Override public Material getMaterial() { return null; }

    @Override public boolean onEnable(Player player) { return true; }

    @Override public void onSec(Player player) {
        // Détecte double saut (en l'air) toutes les 15s
        if (!player.isOnGround() && player.getVelocity().getY() < -0.1
                && System.currentTimeMillis() - lastUse > 15_000L) {
            lastUse = System.currentTimeMillis();
            player.setVelocity(player.getVelocity().setY(1.5).add(player.getLocation().getDirection().setY(0).normalize().multiply(0.8)));
            player.getWorld().createExplosion(player.getLocation(), 0F);
        }
    }
}