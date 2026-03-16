package net.novauhc.dandadan.roles.polnareff;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.DanDaDanRole;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;

public class SwordLaunchAbility extends UseAbiliy {
    public SwordLaunchAbility() {}
    
    /** Lookup dynamique — RoleVariableProcessor clone via no-arg constructor */
    private PolnareffRole getRole(Player player) {
        if (DanDaDan.get() == null || player == null) return null;
        UHCPlayer uhc = UHCPlayerManager.get().getPlayer(player);
        if (uhc == null) return null;
        DanDaDanRole r = DanDaDan.get().getRoleByUHCPlayer(uhc);
        if (r instanceof PolnareffRole typed) return typed;
        return null;
    }

    @Override public String getName()       { return "Sword Launch"; }
    @Override public Material getMaterial() { return Material.IRON_SWORD; }
    @Override public boolean onEnable(Player player) {
        LangManager.get().send(DanDaDanLangExt3.POLNA_SWORD_LAUNCH, player);
        // -1 enchant épée
        ItemStack sword = player.getInventory().getItemInHand();
        if (sword != null && sword.getType()==Material.IRON_SWORD || sword.getType()==Material.DIAMOND_SWORD) {
            int lvl = sword.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
            if (lvl > 0) sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, lvl-1);
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*30, 0));
        PolnareffRole role = getRole(player);
        if (role == null) return false;
        role.setAutoAimActive(true);
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> role.setAutoAimActive(false), 20L*30);
        setCooldown(120); return true;
    }
}