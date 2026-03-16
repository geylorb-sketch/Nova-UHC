package net.novauhc.dandadan.roles.jiangshi;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Random;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.roles.jiangshi.JiangshiRole;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;





public class JiangshiRevivePassive extends Ability {
    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "JIANGSHI_REVIVE_HP_NAME", descKey = "JIANGSHI_REVIVE_HP_DESC", type = VariableType.DOUBLE)
    private double surviveHp = 2.0; // 1❤ après survie

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "JIANGSHI_REVIVE_TP_RANGE_NAME", descKey = "JIANGSHI_REVIVE_TP_RANGE_DESC", type = VariableType.INTEGER)
    private int tpRange = 10;

    private long lastRevive = 0;

    @Override public String getName()       { return "Survie Jiangshi"; }
    @Override public Material getMaterial() { return null; }

    /**
     * Appelé depuis le listener de dégâts (EntityDamageEvent, pre-death).
     * Si les dégâts tueraient Jiangshi → échange position avec un "fantôme" et annule la mort.
     */
    public boolean tryRevive(Player player) {
        long now = System.currentTimeMillis();
        if (now - lastRevive < ((JiangshiRole) DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(player))).getReviveCooldown() * 1000L) return false;
        lastRevive = now;

        // Téléporte à une position aléatoire dans 10 blocs
        Random rand = new Random();
        Location newLoc = player.getLocation().clone().add(
                (rand.nextDouble() - 0.5) * (tpRange * 2), 0, (rand.nextDouble() - 0.5) * (tpRange * 2));
        player.teleport(newLoc);
        player.setHealth(surviveHp);
        LangManager.get().send(DanDaDanLang.JIANGSHI_SURVIVED, player);
        return true;
    }

    @Override
    public boolean onEnable(Player player) { return tryRevive(player); }
}