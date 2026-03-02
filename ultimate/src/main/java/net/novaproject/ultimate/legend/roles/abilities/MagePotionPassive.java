package net.novaproject.ultimate.legend.roles.abilities;

import net.novaproject.novauhc.ability.PassiveAbility;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Passif Mage : Régénère 3 potions toutes les N secondes.
 * PassiveAbility.onSec() → tryUse() → onEnable().
 * Le ShortCooldownManager gère l'intervalle : la première exécution est immédiate
 * (pas encore de cooldown en mémoire), puis regenInterval secondes entre chaque lot.
 */
public class MagePotionPassive extends PassiveAbility {

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "MAGE_REGEN_INTERVAL_NAME", descKey = "MAGE_REGEN_INTERVAL_DESC", type = VariableType.TIME)
    private int regenInterval = 600;

    public MagePotionPassive() { setCooldown(regenInterval); }

    @Override public String getName() { return "Potions Magiques"; }
    @Override public Material getMaterial() { return null; }

    @Override
    public boolean onEnable(Player player) {
        // Le cooldown est mis à jour après onEnable → l'intervalle est respecté automatiquement
        setCooldown(regenInterval); // au cas où la variable a été modifiée en cours de jeu
        dropPotions(player);
        player.sendMessage("§6[Mage] §aNouvelles potions reçues !");
        return true;
    }

    private void dropPotions(Player p) {
        p.getWorld().dropItemNaturally(p.getLocation(),
                new ItemCreator(Material.POTION)
                        .addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600, 0), true)
                        .setName("§cPotion de Force").getItemstack()).setPickupDelay(0);
        p.getWorld().dropItemNaturally(p.getLocation(),
                new ItemCreator(Material.POTION)
                        .addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 0), true)
                        .setName("§7Potion de Résistance").getItemstack()).setPickupDelay(0);
        p.getWorld().dropItemNaturally(p.getLocation(),
                new ItemCreator(Material.POTION)
                        .addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 0), true)
                        .setName("§bPotion de Vitesse").getItemstack()).setPickupDelay(0);
    }
}
