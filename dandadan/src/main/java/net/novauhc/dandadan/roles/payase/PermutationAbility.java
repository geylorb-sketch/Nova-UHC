package net.novauhc.dandadan.roles.payase;

import net.novaproject.novauhc.ability.template.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PermutationAbility extends UseAbiliy {
    @Override public String getName() { return "Permutation"; }
    @Override public Material getMaterial() { return Material.EYE_OF_ENDER; }

    @Override
    public boolean onEnable(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 300, 0, false, false));
        LangManager.get().send(DanDaDanLang.PAYASE_PERMUTATION_ON, player);
        setCooldown(120);
        return true;
    }

}
