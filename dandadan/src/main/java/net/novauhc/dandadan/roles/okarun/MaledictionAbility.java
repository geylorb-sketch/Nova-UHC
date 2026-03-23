package net.novauhc.dandadan.roles.okarun;

import net.novaproject.novauhc.ability.template.UseAbility;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

/**
 * Malédiction — Actif Okarun (Clic-Droit, BLAZE_POWDER)
 * Okarun obtient 30% de speed. Durée max 10min, +1min par kill.
 */
public class MaledictionAbility extends UseAbility {

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "OKARUN_CURSE_DURATION_NAME",
            descKey = "OKARUN_CURSE_DURATION_DESC", type = VariableType.TIME)
    private int duration = 30; // secondes d'effet

    private boolean transformed = false;

    @Override public String getName()       { return "Malediction"; }
    @Override public Material getMaterial() { return Material.BLAZE_POWDER; }

    @Override
    public boolean onEnable(Player player) {
        transformed = true;
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * duration, 1, false, true));
        LangManager.get().send(DanDaDanLang.OKARUN_MALEDICTION_ON, player, Map.of("%duration%", String.valueOf(duration)));
        setCooldown(duration);
        return true;
    }

    public boolean isTransformed() { return transformed; }
    public void setTransformed(boolean t) { this.transformed = t; }
}
