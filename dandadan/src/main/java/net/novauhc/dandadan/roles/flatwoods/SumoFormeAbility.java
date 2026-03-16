package net.novauhc.dandadan.roles.flatwoods;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLangExt;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SumoFormeAbility extends Ability {
    private boolean attacking = true;


    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "SUMO_FORME_ATTACK_STRENGTH_NAME", descKey = "SUMO_FORME_ATTACK_STRENGTH_DESC", type = VariableType.INTEGER)
    private int attackStrengthAmp = 2;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "SUMO_FORME_DEFENSE_RESIST_NAME", descKey = "SUMO_FORME_DEFENSE_RESIST_DESC", type = VariableType.INTEGER)
    private int defenseResistAmp = 1;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "SUMO_FORME_COOLDOWN_NAME", descKey = "SUMO_FORME_COOLDOWN_DESC", type = VariableType.TIME)
    private int cooldown = 10;

    @Override public String getName()       { return "Sumo Forme"; }
    @Override public Material getMaterial() { return Material.DIAMOND_SWORD; }

    @Override
    public void onClick(org.bukkit.event.player.PlayerInteractEvent event, ItemStack item) {
        if (!event.getAction().name().contains("LEFT")) return;
        if (item==null || item.getType()!=Material.DIAMOND_SWORD) return;
        tryUse(event.getPlayer());
    }

    @Override
    public boolean onEnable(Player player) {
        attacking = !attacking;
        if (attacking) {
            LangManager.get().send(DanDaDanLangExt.FLATWOODS_FORME_ATTAQUANT, player);
            ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
            sword.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.DAMAGE_ALL, 4);
            player.getInventory().setItemInHand(sword);
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*120, 0));
        } else {
            LangManager.get().send(DanDaDanLangExt.FLATWOODS_FORME_DEFENSIF, player);
            player.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
            player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
            player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
            player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*120, 0));
        }
        setCooldown(120);
        return true;
    }
}