package net.novauhc.dandadan.roles.jiji;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLangExt;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AdaptationPassive extends Ability {
        @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "ADAPTATION_DURATION_NAME", descKey = "ADAPTATION_DURATION_DESC", type = VariableType.TIME)
    private int adaptDuration = 10; // secondes de résistance après coup

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "ADAPTATION_RESIST_AMP_NAME", descKey = "ADAPTATION_RESIST_AMP_DESC", type = VariableType.INTEGER)
    private int resistAmplifier = 0; // Résistance I

    private final Map<UUID, Long> adaptExpiry = new HashMap<>();

    @Override public String getName()       { return "Adaptation"; }
    @Override public Material getMaterial() { return null; }

    @Override public boolean onEnable(Player player) { return true; }

    @Override
    public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player jiji)) return;
        Player victim = victimP.getPlayer();
        if (victim == null) return;

        // Copie les effets inversés pendant 1min
        victim.getActivePotionEffects().forEach(eff -> {
            if (eff.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {
                jiji.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*60, eff.getAmplifier()));
                String msg = LangManager.get().get(DanDaDanLangExt.JIJI_ADAPTATION_APPLIED, jiji).replace("%effect%", "Résistance");
                jiji.sendMessage(msg);
            } else if (eff.getType().equals(PotionEffectType.DAMAGE_RESISTANCE)) {
                jiji.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*60, eff.getAmplifier()));
            }
        });
    }
}