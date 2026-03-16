package net.novauhc.dandadan.roles.seiko;

import net.novaproject.novauhc.ability.PassiveAbility;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Dieu de la région — Passif
 * Selon la position X/Z par rapport au centre (0,0) :
 *   Nord-Ouest  (X<0, Z<0) → Speed
 *   Nord-Est    (X>0, Z<0) → Resistance
 *   Sud-Ouest   (X<0, Z>0) → Strength
 *   Sud-Est     (X>0, Z>0) → Fire Resistance
 */
public class DieuRegionPassive extends PassiveAbility {

    @Override public String getName()       { return "Dieu de la région"; }
    @Override public Material getMaterial() { return null; }

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "DIEU_REGION_EFFECT_AMP_NAME", descKey = "DIEU_REGION_EFFECT_AMP_DESC", type = VariableType.INTEGER)
    private int effectAmplifier = 0; // Amplificateur des effets de région

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "DIEU_REGION_EFFECT_DURATION_NAME", descKey = "DIEU_REGION_EFFECT_DURATION_DESC", type = VariableType.TIME)
    private int effectDurationTicks = 60;

    private String lastRegion = "";

    @Override
    public boolean onEnable(Player player) {
        Location loc = player.getLocation();
        double x = loc.getX();
        double z = loc.getZ();

        String region;
        PotionEffectType effect;
        int amplifier = 0;

        if (x < 0 && z < 0)      { region = "NW"; effect = PotionEffectType.SPEED; }
        else if (x > 0 && z < 0) { region = "NE"; effect = PotionEffectType.DAMAGE_RESISTANCE; }
        else if (x < 0)          { region = "SW"; effect = PotionEffectType.INCREASE_DAMAGE; }
        else                     { region = "SE"; effect = PotionEffectType.FIRE_RESISTANCE; }

        // Retire les effets des autres régions
        player.removePotionEffect(PotionEffectType.SPEED);
        player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
        player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);

        player.addPotionEffect(new PotionEffect(effect, 40, amplifier, false, false));

        if (!region.equals(lastRegion)) {
            lastRegion = region;
            DanDaDanLang langKey = switch (region) {
                case "NW" -> DanDaDanLang.SEIKO_REGION_NORTHWEST;
                case "NE" -> DanDaDanLang.SEIKO_REGION_NORTHEAST;
                case "SW" -> DanDaDanLang.SEIKO_REGION_SOUTHWEST;
                default   -> DanDaDanLang.SEIKO_REGION_SOUTHEAST;
            };
            LangManager.get().send(langKey, player);
        }
        return true;
    }
}
