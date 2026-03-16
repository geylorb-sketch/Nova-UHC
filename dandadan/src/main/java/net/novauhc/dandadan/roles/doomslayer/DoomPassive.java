package net.novauhc.dandadan.roles.doomslayer;


import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.scenario.role.Role;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.roles.doomslayer.DoomslayerRole;




public class DoomPassive extends Ability {

    public DoomslayerRole role;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "DOOM_FORCE_BONUS_PER_KILL_NAME", descKey = "DOOM_FORCE_BONUS_PER_KILL_DESC", type = VariableType.INTEGER)
    private int forceBonusPerKill = 10;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "DOOM_MAX_FORCE_NAME", descKey = "DOOM_MAX_FORCE_DESC", type = VariableType.INTEGER)
    private int maxForceBonus = 50;

    @Override public String getName()       { return "Doomslayer"; }
    @Override public Material getMaterial() { return null; }

    @Override public boolean onEnable(Player player) { return true; }

    @Override public void onSec(Player player) {
         role = (DoomslayerRole) DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(player));
        if (role == null) return;
        // Speed permanent 20%
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 0, false, false));
        // Force bonus (kills proches)
        if (((DoomslayerRole) DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(player))).getForceBonus() > 0) {
            int amp = (int)(((DoomslayerRole) DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(player))).getForceBonus() / 10.0);
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 40, amp, false, false));
        }
        // Bloque les dégâts si 💚 > 0 — géré dans onAttack
    }
    @Override public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player attacker)) return;
        Role role = DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(attacker));

        if (role == null) return;
        if(!(role instanceof DoomslayerRole doomslayerRole)){
            return;
        }
        Player player = victimP.getPlayer();
        // Quand Doomslayer REÇOIT un coup — event.getEntity() = Doomslayer
        if (!(event.getEntity() instanceof Player doom)) return;
        if (((DoomslayerRole) DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(player))).getGreenArmor() > 0) {
            event.setDamage(0);
            ((DoomslayerRole) DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(player))).hitGreenArmor();
        }
        // Mode Creuset : 25% lifesteal
        if (((DoomslayerRole) DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(player))).getBladeMode() == DoomslayerRole.BladeMode.CREUSET
                && event.getDamager() instanceof Player
                && ThreadLocalRandom.current().nextDouble() < 0.25) {
            doom.setHealth(Math.min(doom.getMaxHealth(), doom.getHealth() + 4));
        }
    }
}