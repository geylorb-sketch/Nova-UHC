package net.novauhc.dandadan.roles.jetbooster;

import net.novaproject.novauhc.ability.PassiveAbility;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.utils.UHCUtils;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class TeteHautePassive extends PassiveAbility {
    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "TETE_HAUTE_SPEED_AMP_NAME", descKey = "TETE_HAUTE_SPEED_AMP_DESC", type = VariableType.INTEGER)
    private int speedAmplifier = 0; // Speed I

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "TETE_HAUTE_HEIGHT_THRESHOLD_NAME", descKey = "TETE_HAUTE_HEIGHT_THRESHOLD_DESC", type = VariableType.INTEGER)
    private int heightThreshold = 150; // Y min pour bonus de vitesse

    @Override public String getName()       { return "Tête haute"; }
    @Override public Material getMaterial() { return null; }
    @Override public boolean onEnable(Player player) {
        player.getWorld().getNearbyEntities(player.getLocation(), 20, 20, 20)
                .stream().filter(e -> e instanceof Player && !e.equals(player))
                .forEach(e -> {
                    Player target = (Player)e;
                    UHCUtils.spawnFloatingDamage(player, "§c" + target.getName() + " §f" + String.format("%.1f", target.getHealth()/2) + "❤");
                });
        return true;
    }
}