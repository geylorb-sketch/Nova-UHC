package net.novauhc.dandadan.roles.okarun;

import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MaledictionAbility extends UseAbiliy {

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "OKARUN_CURSE_DURATION_NAME", descKey = "OKARUN_CURSE_DURATION_DESC", type = VariableType.TIME)
    private int duration = 30;

    @Override public String getName()       { return "Malédiction"; }
    @Override public Material getMaterial() { return Material.BLAZE_POWDER; }

    @Override
    public boolean onEnable(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * duration, 1, false, true));
        String msg = LangManager.get().get(DanDaDanLang.OKARUN_CURSE_ACTIVATED, player)
                .replace("%duration%", String.valueOf(duration));
        player.sendMessage(msg);
        setCooldown(duration); // cooldown = durée de l'effet
        return true;
    }
}
