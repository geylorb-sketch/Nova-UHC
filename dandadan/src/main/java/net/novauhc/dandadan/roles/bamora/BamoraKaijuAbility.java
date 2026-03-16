package net.novauhc.dandadan.roles.bamora;

import net.novaproject.novauhc.ability.UseAbiliy;
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
 * Kaiju — Clic-Droit
 * Pose un bloc d'or sous les pieds (le "cœur"). Tant qu'il existe → Strength 1 (≈15% force).
 * Si le bloc est cassé → Kaiju désactivé.
 * Le bloc brisé est détecté via onBreak() dans BamoraRole (délégué au scenario via onBreak hook).
 */
public class BamoraKaijuAbility extends UseAbiliy {

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "BAMORA_KAIJU_MAX_TIME_NAME", descKey = "BAMORA_KAIJU_MAX_TIME_DESC", type = VariableType.TIME)
    private int maxTime = 600;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "BAMORA_INVIS_MAX_TIME_NAME", descKey = "BAMORA_INVIS_MAX_TIME_DESC", type = VariableType.TIME)
    private int killBonus = 60;

    private Location heartLocation;
    private boolean active = false;
    private int remainingTime;

    @Override public String getName()       { return "Kaiju"; }
    @Override public Material getMaterial() { return Material.BLAZE_ROD; }

    @Override
    public boolean onEnable(Player player) {
        Location loc = player.getLocation().subtract(0, 1, 0);
        loc.getBlock().setType(Material.GOLD_BLOCK);
        heartLocation = loc.clone();
        active = true;
        remainingTime = maxTime;
        LangManager.get().send(DanDaDanLang.BAMORA_KAIJU_ACTIVATED, player);
        setCooldown(maxTime);
        return true;
    }

    @Override
    public void onSec(Player player) {
        super.onSec(player);
        if (!active) return;
        remainingTime--;
        if (remainingTime <= 0) deactivate(player);
        else player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 40, 0, false, false));
    }

    /** Appelé par BamoraRole quand un bloc du monde est cassé */
    public void onWorldBreak(Player breaker, Location loc) {
        if (!active || heartLocation == null) return;
        if (loc.getBlockX() == heartLocation.getBlockX()
                && loc.getBlockY() == heartLocation.getBlockY()
                && loc.getBlockZ() == heartLocation.getBlockZ()) {
            deactivate(breaker);
            LangManager.get().send(DanDaDanLang.BAMORA_KAIJU_BROKEN, breaker);
        }
    }

    private void deactivate(Player player) {
        active = false;
        if (heartLocation != null && heartLocation.getBlock().getType() == Material.GOLD_BLOCK) {
            heartLocation.getBlock().setType(Material.AIR);
        }
        player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
        heartLocation = null;
    }

    public void addTime(int seconds) { remainingTime = Math.min(maxTime, remainingTime + seconds); }
    public boolean isKaijuActive()   { return active; }
    public Location getHeartLocation() { return heartLocation; }
}
