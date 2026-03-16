package net.novauhc.dandadan.roles.csg;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.ShortCooldownManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

public class CarteAbility extends UseAbiliy {
    private String currentCard = null;
    private static final String[] CARDS = {"VISAGE","BOUCHE","OEIL","OREILLE"};
    @Override public String getName()       { return "Carte de conte de fées"; }
    @Override public Material getMaterial() { return Material.MAP; }
    @Override public boolean onEnable(Player player) {
        if (currentCard == null) {
            currentCard = CARDS[ThreadLocalRandom.current().nextInt(CARDS.length)];
            player.sendMessage("§6[Carte] §7Carte tirée : §e" + currentCard + " §7— Reclique pour activer.");
            return true;
        }
        // Active la carte
        Player target = getNearestTarget(player, 10);
        if (target == null && !currentCard.equals("VISAGE")) { player.sendMessage("§c✘ Aucune cible."); return false; }
        switch (currentCard) {
            case "VISAGE" -> {
                LangManager.get().send(DanDaDanLangExt3.CSG_CARD_VISAGE, player);
                if (target != null) {
                    Location b = target.getLocation().subtract(0,1,0);
                    b.getBlock().setType(Material.LAVA);
                    Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> { if(b.getBlock().getType()==Material.LAVA) b.getBlock().setType(Material.AIR); }, 40L);
                }
                setCooldown(300);
            }
            case "BOUCHE" -> {
                LangManager.get().send(DanDaDanLangExt3.CSG_CARD_BOUCHE, player);
                assert target != null;
                ItemStack gold = new ItemStack(Material.GOLD_HELMET);
                ItemStack orig = target.getInventory().getHelmet();
                target.getInventory().setHelmet(gold);
                Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> target.getInventory().setHelmet(orig), 20L*60);
                setCooldown(360);
            }
            case "OEIL" -> {
                LangManager.get().send(DanDaDanLangExt3.CSG_CARD_OEIL, player);
                assert target != null;
                target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 0));
                setCooldown(180);
            }
            case "OREILLE" -> {
                LangManager.get().send(DanDaDanLangExt3.CSG_CARD_OREILLE, player);
                assert target != null;
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*60, 0));
                ShortCooldownManager.put(target, "NoSlotChange", 10000L);
                setCooldown(420);
            }
        }
        currentCard = null;
        return true;
    }
    private Player getNearestTarget(Player p, double r) {
        return p.getWorld().getNearbyEntities(p.getLocation(),r,r,r).stream()
                .filter(e->e instanceof Player&&!e.equals(p)).map(e->(Player)e)
                .min((a,b)->Double.compare(a.getLocation().distance(p.getLocation()),b.getLocation().distance(p.getLocation())))
                .orElse(null);
    }
}