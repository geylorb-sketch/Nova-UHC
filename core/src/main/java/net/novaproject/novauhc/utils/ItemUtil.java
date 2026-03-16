//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.novaproject.novauhc.utils;


import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Iterator;

import java.util.Random;

public class ItemUtil {


    public static ItemStack getTier1Book() {
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta)book.getItemMeta();
        int random = (new Random()).nextInt(9) + 1;
        switch (random) {
            case 1:
                meta.addStoredEnchant(Enchantment.ARROW_INFINITE, 1, true);
                break;
            case 2:
                meta.addStoredEnchant(Enchantment.KNOCKBACK, 1, true);
                break;
            case 3:
                meta.addStoredEnchant(Enchantment.THORNS, 1, true);
                break;
            case 4:
                meta.addStoredEnchant(Enchantment.PROTECTION_PROJECTILE, 1, true);
                break;
            case 5:
                meta.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                break;
            case 6:
                meta.addStoredEnchant(Enchantment.DAMAGE_ALL, 1, true);
            case 7:
            default:
                break;
            case 8:
                meta.addStoredEnchant(Enchantment.ARROW_DAMAGE, 1, true);
                break;
            case 9:
                meta.addStoredEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        }

        book.setItemMeta(meta);
        return book;
    }

    public static ItemStack getRandomEnchantedBook() {
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta)book.getItemMeta();
        int random = (new Random()).nextInt(19) + 1;
        switch (random) {
            case 1:
                meta.addStoredEnchant(Enchantment.ARROW_INFINITE, 1, true);
                break;
            case 2:
                meta.addStoredEnchant(Enchantment.ARROW_FIRE, 1, true);
                break;
            case 3:
                meta.addStoredEnchant(Enchantment.FIRE_ASPECT, 1, true);
                break;
            case 4:
                meta.addStoredEnchant(Enchantment.FIRE_ASPECT, 2, true);
                break;
            case 5:
                meta.addStoredEnchant(Enchantment.KNOCKBACK, 1, true);
                break;
            case 6:
                meta.addStoredEnchant(Enchantment.KNOCKBACK, 2, true);
                break;
            case 7:
                meta.addStoredEnchant(Enchantment.THORNS, 1, true);
                break;
            case 8:
                meta.addStoredEnchant(Enchantment.THORNS, 2, true);
                break;
            case 9:
                meta.addStoredEnchant(Enchantment.THORNS, 3, true);
                break;
            case 10:
                meta.addStoredEnchant(Enchantment.PROTECTION_PROJECTILE, 1, true);
                break;
            case 11:
                meta.addStoredEnchant(Enchantment.PROTECTION_PROJECTILE, 2, true);
                break;
            case 12:
                meta.addStoredEnchant(Enchantment.PROTECTION_PROJECTILE, 3, true);
                break;
            case 13:
                meta.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                break;
            case 14:
                meta.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
                break;
            case 15:
                meta.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
                break;
            case 16:
                meta.addStoredEnchant(Enchantment.DAMAGE_ALL, 1, true);
                break;
            case 17:
                meta.addStoredEnchant(Enchantment.DAMAGE_ALL, 2, true);
                break;
            case 18:
                meta.addStoredEnchant(Enchantment.DAMAGE_ALL, 3, true);
                break;
            case 19:
                meta.addStoredEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        }

        book.setItemMeta(meta);
        return book;
    }


    public static String getTextItem(ItemStack item) {
        return item.getAmount() + "x" + item.getType().name();
    }


    public static boolean isDamageable(Material type) {
        if (type == null) {
            return false;
        } else {
            String[] split = type.toString().split("_");
            int length = split.length;
            switch (split[length - 1]) {
                case "HELMET":
                    return true;
                case "CHESTPLATE":
                    return true;
                case "LEGGINGS":
                    return true;
                case "BOOTS":
                    return true;
                case "SWORD":
                    return true;
                case "AXE":
                    return true;
                case "PICKAXE":
                    return true;
                case "SHOVEL":
                    return true;
                case "BOW":
                    return true;
                case "SPADE":
                    return true;
                case "HOE":
                    return true;
                case "ELYTRA":
                    return true;
                case "TURTLE_HELMET":
                    return true;
                case "TRIDENT":
                    return true;
                case "HORSE_ARMOR":
                    return true;
                case "SHEARS":
                    return true;
                default:
                    return false;
            }
        }
    }

    public static double getAttackDamageItem(ItemStack stack) {
        if (stack != null && stack.getType() != null && stack.getType() != Material.AIR) {
            double baseDamage = 0.0;
            switch (stack.getType()) {
                case WOOD_SPADE:
                case WOOD_HOE:
                case GOLD_SPADE:
                case GOLD_HOE:
                    baseDamage = 1.0;
                    break;
                case STONE_HOE:
                case STONE_SPADE:
                case WOOD_PICKAXE:
                case GOLD_PICKAXE:
                    baseDamage = 2.0;
                    break;
                case STONE_PICKAXE:
                case IRON_SPADE:
                case IRON_HOE:
                case WOOD_AXE:
                case GOLD_AXE:
                    baseDamage = 3.0;
                    break;
                case STONE_AXE:
                case IRON_PICKAXE:
                case DIAMOND_SPADE:
                case DIAMOND_HOE:
                case WOOD_SWORD:
                case GOLD_SWORD:
                    baseDamage = 4.0;
                    break;
                case IRON_AXE:
                case DIAMOND_PICKAXE:
                case STONE_SWORD:
                    baseDamage = 5.0;
                    break;
                case DIAMOND_AXE:
                case IRON_SWORD:
                    baseDamage = 6.0;
                    break;
                case DIAMOND_SWORD:
                    baseDamage = 7.0;
                    break;
                default:
                    baseDamage = 0.0;
            }

            return 1.0 + baseDamage;
        } else {
            return 1.0;
        }
    }

    public static double getEnchantEffect(Player player) {
        if (!player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
            return 0.0;
        } else {
            double level = 0.0;
            Iterator var3 = player.getActivePotionEffects().iterator();

            while(var3.hasNext()) {
                PotionEffect effect = (PotionEffect)var3.next();
                if (effect != null && effect.getType() != null && effect.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {
                    level = 1.0 + 1.0 * (double)effect.getAmplifier();
                    break;
                }
            }

            return level;
        }
    }

    public static double getEnchantDamageItem(ItemStack stack) {
        if (stack != null && stack.getType() != null && stack.getType() != Material.AIR) {
            double enchantDamage = 0.0;
            if (stack.containsEnchantment(Enchantment.DAMAGE_ALL)) {
                enchantDamage = 1.25 * (double)stack.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
            }

            return enchantDamage;
        } else {
            return 0.0;
        }
    }

    public static boolean changeEnchantmentItem(Player player, Enchantment enchantment, int actualLevel, int newLevel) {
        boolean hasItemChanged = false;

        for(int i = 0; i < player.getInventory().getSize(); ++i) {
            try {
                ItemStack item = player.getInventory().getItem(i);
                if (item != null && item.getType() != null && item.containsEnchantment(enchantment)) {
                    int level = item.getEnchantmentLevel(enchantment);
                    if (level == actualLevel) {
                        hasItemChanged = true;
                        item.addUnsafeEnchantment(enchantment, newLevel);
                    }
                }
            } catch (Exception var8) {
                System.out.println("(ERREUR) ItemUtil.changeEnchantmentItem");
                var8.printStackTrace();
            }
        }

        if (hasItemChanged) {
            player.updateInventory();
        }

        return hasItemChanged;
    }

    public static boolean changeEnchantmentItemWithInfos(Player player, Material material, boolean haveLore, Enchantment enchantment, int actualLevel, int newLevel) {
        boolean hasItemChanged = false;

        for(int i = 0; i < player.getInventory().getSize(); ++i) {
            try {
                ItemStack item = player.getInventory().getItem(i);
                if (item != null && item.getType() != null && item.getType() == material && (!haveLore || item.hasItemMeta() && item.getItemMeta().hasLore()) && item.containsEnchantment(enchantment)) {
                    int level = item.getEnchantmentLevel(enchantment);
                    if (level == actualLevel) {
                        hasItemChanged = true;
                        item.addUnsafeEnchantment(enchantment, newLevel);
                    }
                }
            } catch (Exception var10) {
                System.out.println("(ERREUR) ItemUtil.changeEnchantmentItem");
                var10.printStackTrace();
            }
        }

        if (hasItemChanged) {
            player.updateInventory();
        }

        return hasItemChanged;
    }

    public static boolean isCriticalDamage(Player player) {
        return player.getFallDistance() > 0.0F && !player.isOnGround() && !player.hasPotionEffect(PotionEffectType.BLINDNESS) && player.getVehicle() == null;
    }


}
