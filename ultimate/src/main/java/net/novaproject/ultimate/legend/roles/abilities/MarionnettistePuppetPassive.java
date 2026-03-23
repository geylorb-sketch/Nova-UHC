package net.novaproject.ultimate.legend.roles.abilities;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;


public class MarionnettistePuppetPassive extends PassiveAbility {

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "MARIONNETTISTE_PUPPET_RANGE_NAME", descKey = "MARIONNETTISTE_PUPPET_RANGE_DESC", type = VariableType.DOUBLE)
    private double maxRange = 16.0;

    private final Set<UUID> puppets = ConcurrentHashMap.newKeySet();
    private final Map<UUID, Integer> puppetTypes = new ConcurrentHashMap<>();

    public MarionnettistePuppetPassive() { setCooldown(0); }

    @Override public String getName() { return "Marionnettes"; }
    @Override public Material getMaterial() { return null; }


    @Override
    public boolean onEnable(Player player) {
        if (puppets.isEmpty()) return false;

        for (UUID uuid : puppets) {
            Player puppet = org.bukkit.Bukkit.getPlayer(uuid);
            if (puppet == null || !puppet.isOnline()) continue;


            if (puppet.getLocation().distance(player.getLocation()) > maxRange) {
                puppet.removePotionEffect(PotionEffectType.POISON);
                puppet.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80, 0, false, false));
            }


            Integer type = puppetTypes.get(uuid);
            if (type == null) continue;
            PotionEffect effect = switch (type) {
                case 0 -> new PotionEffect(PotionEffectType.INCREASE_DAMAGE,    80, 0, false, false);
                case 1 -> new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,  80, 0, false, false);
                case 2 -> new PotionEffect(PotionEffectType.SPEED,              80, 0, false, false);
                default -> null;
            };
            if (effect != null) {
                puppet.removePotionEffect(effect.getType());
                puppet.addPotionEffect(effect);
            }
        }
        return true;
    }

    @Override
    public void onDeath(UHCPlayer uhcPlayer, UHCPlayer killer, PlayerDeathEvent event) {
        if (!uhcPlayer.equals(getOwner())) return;
        for (UUID uuid : puppets) {
            Player puppet = org.bukkit.Bukkit.getPlayer(uuid);
            if (puppet != null && puppet.isOnline()) puppet.setHealth(0.0);
        }
        puppets.clear();
        puppetTypes.clear();
    }


    public void createPuppet(UHCPlayer deadPlayer, UHCPlayer master) {
        Player pp = deadPlayer.getPlayer();
        Player mp = master.getPlayer();
        if (pp == null || mp == null) return;

        pp.getInventory().clear();
        pp.getInventory().setArmorContents(new org.bukkit.inventory.ItemStack[4]);
        pp.setMaxHealth(20); pp.setHealth(20);
        pp.setGameMode(GameMode.SURVIVAL);
        if (master.getTeam().isPresent()) deadPlayer.forceSetTeam(Optional.of(master.getTeam().get()));
        pp.teleport(mp.getLocation());

        UUID uuid = pp.getUniqueId();
        puppets.add(uuid);
        puppetTypes.put(uuid, ThreadLocalRandom.current().nextInt(3));
        givePuppetEquipment(pp);
    }

    public boolean isPuppet(UUID uuid) { return puppets.contains(uuid); }

    private void givePuppetEquipment(Player p) {
        var inv = p.getInventory();
        inv.setBoots(new ItemCreator(Material.IRON_BOOTS).setUnbreakable(true).getItemstack());
        inv.setLeggings(new ItemCreator(Material.IRON_LEGGINGS).setUnbreakable(true).getItemstack());
        inv.setChestplate(new ItemCreator(Material.IRON_CHESTPLATE)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setUnbreakable(true).getItemstack());
        inv.setHelmet(new ItemCreator(Material.IRON_HELMET).setUnbreakable(true).getItemstack());
        inv.addItem(new ItemCreator(Material.WOOD_SWORD)
                .addEnchantment(Enchantment.DAMAGE_ALL, 2).setUnbreakable(true).getItemstack());
        inv.addItem(new ItemCreator(Material.BOW).setUnbreakable(true).getItemstack());
        inv.addItem(new ItemCreator(Material.ARROW).setAmount(32).getItemstack());
        inv.addItem(new ItemCreator(Material.COOKED_BEEF).setAmount(64).getItemstack());
        inv.addItem(new ItemCreator(Material.GOLDEN_APPLE).setAmount(5).getItemstack());
    }
}
