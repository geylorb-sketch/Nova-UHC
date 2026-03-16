package net.novauhc.dandadan.roles.jiji;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.*;

public class PouvoirSpiralesAbility extends UseAbiliy {
    @Override public String getName()       { return "Pouvoir des Spirales"; }
    @Override public Material getMaterial() { return Material.NETHER_STAR; }

    @Override
    public boolean onEnable(Player player) {
        int slot = player.getInventory().getHeldItemSlot(); // 0-8

        if (slot >= 0 && slot <= 3) {
            // Pistolet Maléfique : rayon de blocs d'or
            firePistolet(player);
        } else if (slot == 4) {
            // Rasen : explose + regard aléatoire 30s
            fireRasen(player);
        } else {
            // Kotodama (5-8) : mélange inventaire
            fireKotodama(player);
        }
        setCooldown(300);
        return true;
    }

    private void firePistolet(Player player) {
        LangManager.get().send(DanDaDanLangExt.JIJI_PISTOLET_ACTIVATED, player);
        Vector dir = player.getLocation().getDirection().normalize();
        for (int i = 1; i <= 20; i++) {
            Location point = player.getLocation().clone().add(dir.clone().multiply(i));
            // Pose bloc d'or temporaire
            if (point.getBlock().getType() == Material.AIR) {
                point.getBlock().setType(Material.GOLD_BLOCK);
                final Location fpoint = point.clone();
                Main.get().getServer().getScheduler().runTaskLater(Main.get(),
                        () -> { if(fpoint.getBlock().getType()==Material.GOLD_BLOCK) fpoint.getBlock().setType(Material.AIR); }, 40L);
            }
            // Dégâts sur joueurs proches
            point.getWorld().getNearbyEntities(point, 2.5, 2.5, 2.5).stream()
                    .filter(e -> e instanceof Player && !e.equals(player))
                    .forEach(e -> {
                        // Remplace une pièce d'armure par cuir Prot2 pendant 1min
                        Player victim = (Player)e;
                        ItemStack[] armor = victim.getInventory().getArmorContents();
                        for (int a=0;a<armor.length;a++) {
                            if (armor[a]!=null && armor[a].getType()!=Material.AIR) {
                                Material leather = getLeatherEquiv(armor[a].getType());
                                if (leather != null) {
                                    ItemStack rep = new ItemStack(leather);
                                    rep.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                                    final ItemStack orig = armor[a].clone(); final int idx=a;
                                    armor[a] = rep;
                                    victim.getInventory().setArmorContents(armor);
                                    Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
                                        ItemStack[] cur = victim.getInventory().getArmorContents();
                                        cur[idx] = orig;
                                        victim.getInventory().setArmorContents(cur);
                                    }, 20L*60);
                                }
                                break;
                            }
                        }
                    });
        }
    }

    private void fireRasen(Player player) {
        LangManager.get().send(DanDaDanLangExt.JIJI_RASEN_ACTIVATED, player);
        Player target = getNearestTarget(player, 15);
        if (target == null) return;
        target.damage(4.0, player); // 2❤

        // Regard aléatoire pendant 30s
        var taskId = new int[1];
        int[] elapsed = {0};
        taskId[0] = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(), () -> {
            elapsed[0]++;
            if (elapsed[0] >= 6) { Main.get().getServer().getScheduler().cancelTask(taskId[0]); return; }
            // Force un yaw aléatoire via téléportation
            Location loc = target.getLocation().clone();
            loc.setYaw(new Random().nextFloat() * 360);
            target.teleport(loc);
        }, 0L, 100L); // ttes 5s
    }

    private void fireKotodama(Player player) {
        LangManager.get().send(DanDaDanLangExt.JIJI_KOTODAMA_ACTIVATED, player);
        Vector dir = player.getLocation().getDirection().normalize();

        // Traînée de particules jaunes
        for (int i=1;i<=15;i++) {
            new ParticleBuilder(ParticleEffect.REDSTONE)
                    .setColor(java.awt.Color.YELLOW)
                    .setLocation(player.getLocation().clone().add(dir.clone().multiply(i)))
                    .setAmount(3).display();
        }

        // Mélange l'inventaire des joueurs touchés
        player.getWorld().getNearbyEntities(player.getLocation(), 15, 15, 15).stream()
                .filter(e -> e instanceof Player && !e.equals(player))
                .filter(e -> e.getLocation().distance(player.getLocation().clone().add(dir.multiply(7))) < 3)
                .forEach(e -> {
                    Player victim = (Player)e;
                    List<ItemStack> items = new ArrayList<>(Arrays.asList(victim.getInventory().getContents()));
                    Collections.shuffle(items);
                    victim.getInventory().setContents(items.toArray(new ItemStack[0]));
                });
    }

    private Material getLeatherEquiv(Material m) {
        return switch (m.name()) {
            case "DIAMOND_HELMET","IRON_HELMET","GOLD_HELMET","CHAINMAIL_HELMET" -> Material.LEATHER_HELMET;
            case "DIAMOND_CHESTPLATE","IRON_CHESTPLATE","GOLD_CHESTPLATE","CHAINMAIL_CHESTPLATE" -> Material.LEATHER_CHESTPLATE;
            case "DIAMOND_LEGGINGS","IRON_LEGGINGS","GOLD_LEGGINGS","CHAINMAIL_LEGGINGS" -> Material.LEATHER_LEGGINGS;
            case "DIAMOND_BOOTS","IRON_BOOTS","GOLD_BOOTS","CHAINMAIL_BOOTS" -> Material.LEATHER_BOOTS;
            default -> null;
        };
    }

    private Player getNearestTarget(Player p, double range) {
        return p.getWorld().getNearbyEntities(p.getLocation(), range,range,range)
                .stream().filter(e -> e instanceof Player && !e.equals(p))
                .map(e->(Player)e)
                .min((a,b)->Double.compare(a.getLocation().distance(p.getLocation()),b.getLocation().distance(p.getLocation())))
                .orElse(null);
    }
}