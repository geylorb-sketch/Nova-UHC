package net.novauhc.dandadan.roles.enenra;

import net.novaproject.novauhc.ability.template.UseAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CorpsFumeeAbility extends UseAbility {
    @Override public String getName() { return "Corps de fumee"; }
    @Override public Material getMaterial() { return Material.FLINT; }

    @Override
    public boolean onEnable(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 2400, 2, false, true));
        LangManager.get().send(DanDaDanLang.ENENRA_FUMEE_ON, player);
        setCooldown(420);
        return true;
    }

}
