package net.novauhc.dandadan.roles.jiangshi;

import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class JiangshiInvocationAbility extends UseAbiliy {

    @Override public String getName()       { return "Jiangshi"; }
    @Override public Material getMaterial() { return Material.ROTTEN_FLESH; }

    @Override
    public boolean onEnable(Player player) {
        UHCPlayer uhcPlayer = getUHCPlayer(player);
        if (uhcPlayer == null) return false;

        var role = DanDaDan.get() != null
                ? DanDaDan.get().getRoleByUHCPlayer(uhcPlayer)
                : null;

        if (!(role instanceof JiangshiRole jiangshi)) return false;

        int zombieCost = jiangshi.getBaseZombies(); // chaque zombie coûte 1 KI
        int skelCost   = jiangshi.getBaseSkeletons();
        int totalCost  = zombieCost + skelCost;

        if (!jiangshi.spendKi(totalCost)) {
            LangManager.get().send(DanDaDanLang.JIANGSHI_NOT_ENOUGH_KI, player);
            return false;
        }

        Location loc = player.getLocation();

        // Spawn zombies en fer P3 + épée fer T2
        for (int i = 0; i < jiangshi.getBaseZombies(); i++) {
            Zombie zombie = (Zombie) loc.getWorld().spawnEntity(loc.clone().add(Math.random() * 4 - 2, 0, Math.random() * 4 - 2), EntityType.ZOMBIE);
            zombie.setCustomName("§5Jiangshi");
            zombie.setCustomNameVisible(true);
            zombie.setMaxHealth(12); zombie.setHealth(12);
            EntityEquipment eq = zombie.getEquipment();
            ItemStack sword = new ItemStack(Material.IRON_SWORD);
            sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
            eq.setItemInHand(sword);
            eq.setHelmet(new ItemStack(Material.IRON_HELMET));
            eq.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
            eq.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
            eq.setBoots(new ItemStack(Material.IRON_BOOTS));
            zombie.setTarget(null); // ciblera le joueur le plus proche
        }

        // Spawn squelettes avec arc Power 3
        for (int i = 0; i < jiangshi.getBaseSkeletons(); i++) {
            Skeleton skeleton = (Skeleton) loc.getWorld().spawnEntity(loc.clone().add(Math.random() * 4 - 2, 0, Math.random() * 4 - 2), EntityType.SKELETON);
            skeleton.setCustomName("§5Squelette Jiangshi");
            skeleton.setCustomNameVisible(true);
            EntityEquipment eq = skeleton.getEquipment();
            ItemStack bow = new ItemStack(Material.BOW);
            bow.addEnchantment(Enchantment.ARROW_DAMAGE, 3);
            eq.setItemInHand(bow);
        }

        String msg = LangManager.get().get(DanDaDanLang.JIANGSHI_SUMMON, player)
                .replace("%zombies%", String.valueOf(jiangshi.getBaseZombies()))
                .replace("%skeletons%", String.valueOf(jiangshi.getBaseSkeletons()))
                .replace("%ki%", String.valueOf(jiangshi.getKi()));
        player.sendMessage(msg);

        setCooldown(0);
        return true;
    }
}

// ── All-Out (menu de configuration de l'invocation) ──────────────
