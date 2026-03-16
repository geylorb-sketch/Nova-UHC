package net.novauhc.dandadan.roles.mantis;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import net.novauhc.dandadan.lang.DanDaDanLangExt2;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BusinessManPassive extends Ability {
    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "BUSINESS_MAN_CHANCE_NAME", descKey = "BUSINESS_MAN_CHANCE_DESC", type = VariableType.PERCENTAGE)
    private int triggerChancePct = 2;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "BUSINESS_MAN_STRENGTH_DURATION_NAME", descKey = "BUSINESS_MAN_STRENGTH_DURATION_DESC", type = VariableType.TIME)
    private int strengthDuration = 30;

    @Override public String getName()       { return "Business Man"; }
    @Override public Material getMaterial() { return null; }
    // La logique est dans MantisRole.onConsume (vérification 2%)

    @Override
    public void onConsume(org.bukkit.event.player.PlayerItemConsumeEvent event) {
        if (event.getItem().getType() != Material.GOLDEN_APPLE) return;
        if (java.util.concurrent.ThreadLocalRandom.current().nextDouble() >= triggerChancePct / 100.0) return;
        // 2% chance : absorbe l'effet de la pomme en or en force temporaire
        event.getPlayer().addPotionEffect(new PotionEffect(
                PotionEffectType.INCREASE_DAMAGE, 20 * strengthDuration, 0, false, false));
        LangManager.get().send(
                DanDaDanLang.MANTIS_BUSINESS_MAN_TRIGGERED, event.getPlayer());
    }

    @Override
    public boolean onEnable(Player player) { return true; }

}