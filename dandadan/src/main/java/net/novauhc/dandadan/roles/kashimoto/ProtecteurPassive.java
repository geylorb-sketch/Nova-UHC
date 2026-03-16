package net.novauhc.dandadan.roles.kashimoto;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Protecteur — Passif
 * Quand Kashimoto "brise" une pièce d'armure, elle est remplacée par fer Prot2 pendant 5s.
 * Détecte via onAttack quand une pièce est à 0 de durabilité.
 */
public class ProtecteurPassive extends Ability {

        @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "PROTECT_DURATION_NAME", descKey = "PROTECT_DURATION_DESC", type = VariableType.TIME)
    private int replacementDuration = 5; // secondes

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "PROTECT_DURABILITY_THRESHOLD_NAME", descKey = "PROTECT_DURABILITY_THRESHOLD_DESC", type = VariableType.INTEGER)
    private int durabilityThreshold = 5; // dommages restants avant déclenchement

    private final Map<UUID, Long> replacedUntil = new HashMap<>();

    @Override public String getName()       { return "Protecteur"; }
    @Override public Material getMaterial() { return null; }
    @Override public boolean onEnable(Player player) { return true; }

    @Override
    public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        // Protège Kashimoto lui-même — hook onSec pour vérifier l'armure
    }

    @Override
    public void onSec(Player player) {
        long now = System.currentTimeMillis();
        if (replacedUntil.getOrDefault(player.getUniqueId(), 0L) > now) return;

        ItemStack[] armor = player.getInventory().getArmorContents();
        for (int i = 0; i < armor.length; i++) {
            if (armor[i] == null || armor[i].getType() == Material.AIR) continue;
            short durability = armor[i].getDurability();
            short maxDurability = armor[i].getType().getMaxDurability();
            if (maxDurability > 0 && durability >= maxDurability - durabilityThreshold) {
                // Pièce quasi-cassée : remplace par fer Prot2 temporairement
                ItemStack replacement = getIronProtReplacement(i);
                final int slot = i;
                final ItemStack original = armor[i].clone();
                armor[i] = replacement;
                player.getInventory().setArmorContents(armor);
                replacedUntil.put(player.getUniqueId(), now + (replacementDuration * 1000L));
                LangManager.get().send(DanDaDanLang.KASHIMOTO_PROTECTEUR_TRIGGERED, player);

                Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
                    ItemStack[] current = player.getInventory().getArmorContents();
                    current[slot] = original;
                    player.getInventory().setArmorContents(current);
                }, (long)(replacementDuration * 20));
                break;
            }
        }
    }

    private ItemStack getIronProtReplacement(int slot) {
        Material mat = switch (slot) {
            case 3 -> Material.IRON_HELMET;
            case 2 -> Material.IRON_CHESTPLATE;
            case 1 -> Material.IRON_LEGGINGS;
            default -> Material.IRON_BOOTS;
        };
        ItemStack item = new ItemStack(mat);
        item.addEnchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        return item;
    }
}
