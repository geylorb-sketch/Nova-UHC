package net.novauhc.dandadan.roles.kinta;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Great Kinta — Clic-Droit
 * Donne une armure en or complète (+1 d'armure supplémentaire simulé via Resistance 1).
 * Durée basée sur curseMaxTime du rôle.
 */
public class GreatKintaAbility extends UseAbiliy {

    private boolean active = false;
    private ItemStack[] savedArmor;


    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "GREAT_KINTA_STRENGTH_AMP_NAME", descKey = "GREAT_KINTA_STRENGTH_AMP_DESC", type = VariableType.INTEGER)
    private int strengthAmplifier = 1;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "GREAT_KINTA_RESIST_AMP_NAME", descKey = "GREAT_KINTA_RESIST_AMP_DESC", type = VariableType.INTEGER)
    private int resistAmplifier = 1;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "GREAT_KINTA_DURATION_NAME", descKey = "GREAT_KINTA_DURATION_DESC", type = VariableType.TIME)
    private int activeDuration = 60;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "GREAT_KINTA_COOLDOWN_NAME", descKey = "GREAT_KINTA_COOLDOWN_DESC", type = VariableType.TIME)
    private int cooldown = 300;

    @Override public String getName()       { return "Great Kinta"; }
    @Override public Material getMaterial() { return Material.GOLD_INGOT; }

    @Override
    public boolean onEnable(Player player) {
        if (active) return false;
        active = true;

        savedArmor = player.getInventory().getArmorContents().clone();

        player.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.GOLD_BOOTS));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 600, 0, false, false));

        LangManager.get().send(DanDaDanLang.KINTA_GREAT_KINTA_ACTIVATED, player);
        setCooldown(600);

        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            active = false;
            player.getInventory().setArmorContents(savedArmor);
            player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        }, 20L * 600);
        return true;
    }
}
