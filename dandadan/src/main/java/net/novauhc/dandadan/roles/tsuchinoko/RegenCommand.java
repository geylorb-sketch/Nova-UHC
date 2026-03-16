package net.novauhc.dandadan.roles.tsuchinoko;

import net.novaproject.novauhc.ability.CommandAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RegenCommand extends CommandAbility {
    public RegenCommand() { setMaxUse(1); } // 1x/épisode
    @Override public String getName()       { return "Régénération"; }
    @Override public String getCommandKey() { return "regen"; }

    @Override
    public boolean onCommand(Player player, String[] args) {
        LangManager.get().send(DanDaDanLangExt.TSUCHINOKO_REGEN_ACTIVATED, player);
        // 25% réduction dégâts via Resistance 1 (≈ 20%) + flag custom
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 900, 0, false, false));
        return true;
    }
}