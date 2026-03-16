package net.novauhc.dandadan.roles.denji;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.DanDaDanRole;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;

public class DenjiMalediction extends UseAbiliy {
    public DenjiMalediction() {}
    
    /** Lookup dynamique — RoleVariableProcessor clone via no-arg constructor */
    private DenjiRole getRole(Player player) {
        if (DanDaDan.get() == null || player == null) return null;
        UHCPlayer uhc = UHCPlayerManager.get().getPlayer(player);
        if (uhc == null) return null;
        DanDaDanRole r = DanDaDan.get().getRoleByUHCPlayer(uhc);
        if (r instanceof DenjiRole typed) return typed;
        return null;
    }

    @Override public String getName()       { return "Malédiction Denji"; }
    @Override public Material getMaterial() { return Material.IRON_AXE; }
    @Override public boolean onEnable(Player player) {
        DenjiRole role = getRole(player);
        if (role == null) return false;
        if (!role.useBlood(2)) { LangManager.get().send(DanDaDanLangExt3.DENJI_NOT_ENOUGH_BLOOD, player); return false; }
        LangManager.get().send(DanDaDanLangExt3.DENJI_CURSE_ACTIVATED, player);
        // Casque spécial : +45% dégâts reçus en tête pendant 10s si frappé à la tête
        ItemStack helmet = new ItemStack(Material.IRON_HELMET);
        ItemMeta m = helmet.getItemMeta(); assert m != null;
        m.setDisplayName("§c[Chainsaw Head]"); helmet.setItemMeta(m);
        player.getInventory().setHelmet(helmet);
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40, 0, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,   40, 1, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,             40, 1, false, false));
        // Cost 1🩸/2s — géré dans onSec via scheduler
        var taskId = new int[1];
        taskId[0] = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(), () -> {
            if (!player.isOnline() || !role.useBlood(1)) { Main.get().getServer().getScheduler().cancelTask(taskId[0]); }
            else {
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40, 0, false, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,   40, 1, false, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,             40, 1, false, false));
            }
        }, 0L, 40L);
        return true; // pas de cooldown fixe — coût en sang
    }
}