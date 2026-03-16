package net.novauhc.dandadan.roles.rohan;

import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.DanDaDanRole;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;

public class LivreAbility extends UseAbiliy {
    public LivreAbility() {}
    
    /** Lookup dynamique — RoleVariableProcessor clone via no-arg constructor */
    private RohanRole getRole(Player player) {
        if (DanDaDan.get() == null || player == null) return null;
        UHCPlayer uhc = UHCPlayerManager.get().getPlayer(player);
        if (uhc == null) return null;
        DanDaDanRole r = DanDaDan.get().getRoleByUHCPlayer(uhc);
        if (r instanceof RohanRole typed) return typed;
        return null;
    }

    @Override public String getName()       { return "Livre"; }
    @Override public Material getMaterial() { return Material.BOOK; }
    @Override public boolean onEnable(Player player) {
        RohanRole role = getRole(player);
        if (role == null) return false;
        if (!role.isStandActive()) { player.sendMessage("§c✘ Stand requis !"); return false; }
        Player target = player.getWorld().getNearbyEntities(player.getLocation(),8,8,8)
                .stream().filter(e->e instanceof Player&&!e.equals(player)).map(e->(Player)e)
                .min((a,b)->Double.compare(a.getLocation().distance(player.getLocation()),b.getLocation().distance(player.getLocation())))
                .orElse(null);
        if (target == null) return false;
        String msg = LangManager.get().get(DanDaDanLangExt3.ROHAN_LIVRE, player).replace("%target%",target.getName()); player.sendMessage(msg);
        // Immobilise + aveugle + slowness pendant 5s (simule "transformation en livre")
        target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0));
        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,      100, 10));
        target.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,  100, 2));
        target.sendMessage("§2[Rohan] §aVous êtes transformé en livre ! (5s)");
        setCooldown(600); return true;
    }
}