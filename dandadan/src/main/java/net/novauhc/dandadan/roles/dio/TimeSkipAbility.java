package net.novauhc.dandadan.roles.dio;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class TimeSkipAbility extends Ability {

    @Override public String getName()       { return "Time Skip"; }
    @Override public Material getMaterial() { return null; }
    @Override public void onClick(PlayerInteractEvent event, ItemStack item) {

        UHCPlayer uhcPlayer = getUHCPlayer(event.getPlayer());
        if (uhcPlayer == null || DanDaDan.get() == null) return;
        DioRole role = (DioRole) DanDaDan.get().getRoleByUHCPlayer(uhcPlayer);
        if (!event.getAction().name().contains("LEFT") || item==null || item.getType()!=Material.WATCH) return;
        if (!role.isStandActive()) return;
        if (role.getTimeSkipUses() <= 0) { event.getPlayer().sendMessage("§c✘ Plus de Time Skip !"); return; }
        Player player = event.getPlayer();
        Location dest = player.getLocation().clone().add(player.getLocation().getDirection().normalize().multiply(15));
        dest.setY(dest.getY() + 1);
        player.teleport(dest);
        role.useTimeSkip();
        LangManager.get().send(DanDaDanLangExt3.DIO_TIME_SKIP, player);
        setCooldown(role.getTimeSkipUses() > 0 ? 0 : 180);
    }

    @Override
    public boolean onEnable(Player player) { return true; }
}
