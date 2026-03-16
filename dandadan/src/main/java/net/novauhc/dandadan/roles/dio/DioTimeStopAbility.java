package net.novauhc.dandadan.roles.dio;

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

public class DioTimeStopAbility extends UseAbiliy {
    public DioTimeStopAbility() {}
    
    /** Lookup dynamique — RoleVariableProcessor clone via no-arg constructor */
    private DioRole getRole(Player player) {
        if (DanDaDan.get() == null || player == null) return null;
        UHCPlayer uhc = UHCPlayerManager.get().getPlayer(player);
        if (uhc == null) return null;
        DanDaDanRole r = DanDaDan.get().getRoleByUHCPlayer(uhc);
        if (r instanceof DioRole typed) return typed;
        return null;
    }

    @Override public String getName()       { return "Arrêt du Temps"; }
    @Override public Material getMaterial() { return Material.WATCH; }
    @Override public boolean onEnable(Player player) {
        DioRole role = getRole(player);
        if (role == null) return false;
        if (!role.isStandActive()) { player.sendMessage("§c✘ Stand requis !"); return false; }
        double secs = role.getTimeFreeze();
        String msg = LangManager.get().get(DanDaDanLangExt3.DIO_TIME_STOP, player).replace("%seconds%", String.valueOf((int)secs));
        player.sendMessage(msg);
        player.getWorld().getPlayers().stream().filter(p->!p.equals(player))
                .forEach(p->p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,(int)(20*secs),10)));
        setCooldown(600); return true;
    }
}