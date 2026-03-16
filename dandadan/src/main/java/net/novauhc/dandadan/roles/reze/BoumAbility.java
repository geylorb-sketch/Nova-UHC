package net.novauhc.dandadan.roles.reze;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.DanDaDanRole;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;

public class BoumAbility extends UseAbiliy {
    public BoumAbility() {}
    
    /** Lookup dynamique — RoleVariableProcessor clone via no-arg constructor */
    private RezeRole getRole(Player player) {
        if (DanDaDan.get() == null || player == null) return null;
        UHCPlayer uhc = UHCPlayerManager.get().getPlayer(player);
        if (uhc == null) return null;
        DanDaDanRole r = DanDaDan.get().getRoleByUHCPlayer(uhc);
        if (r instanceof RezeRole typed) return typed;
        return null;
    }

    @Override public String getName()       { return "Boum"; }
    @Override public Material getMaterial() { return Material.FIREBALL; }
    @Override public boolean onEnable(Player player) {
        RezeRole role = getRole(player);
        if (role == null) return false;
        if (!role.isCursed()) { player.sendMessage("§c✘ Malédiction requise !"); return false; }
        role.setBoumActive(true);
        role.resetBoumHits();
        LangManager.get().send(DanDaDanLangExt3.REZE_BOUM_ACTIVATED, player);
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> { role.setBoumActive(false); role.resetBoumHits(); }, 20L*30);
        setCooldown(420); return true;
    }
    @Override public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) return;
        Player player = (Player) event.getDamager();
        RezeRole role = getRole(player);
        if (!role.isBoumActive()) return;
        role.incrementBoumHits();
        if (role.getBoumHits() % 4 == 0 && victimP.getPlayer() != null) {
            // "Fait exploser l'item" : swap item en main avec slot aléatoire
            Player victim = victimP.getPlayer();
            int randomSlot = ThreadLocalRandom.current().nextInt(9);
            ItemStack cur = victim.getInventory().getItemInHand();
            ItemStack rnd = victim.getInventory().getItem(randomSlot);
            victim.getInventory().setItemInHand(rnd);
            victim.getInventory().setItem(randomSlot, cur);
            victim.getWorld().createExplosion(victim.getLocation(), 0F);
        }
    }
}