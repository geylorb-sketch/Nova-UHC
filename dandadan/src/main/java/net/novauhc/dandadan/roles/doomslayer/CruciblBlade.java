package net.novauhc.dandadan.roles.doomslayer;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.roles.doomslayer.DoomslayerRole;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;





public class CruciblBlade extends Ability {

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "CRUCIBLE_LIFESTEAL_PCT_NAME", descKey = "CRUCIBLE_LIFESTEAL_PCT_DESC", type = VariableType.PERCENTAGE)
    private int lifestealPct = 15; // % de lifesteal sur chaque coup

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "CRUCIBLE_CLEAVE_RANGE_NAME", descKey = "CRUCIBLE_CLEAVE_RANGE_DESC", type = VariableType.DOUBLE)
    private double cleaveRange = 3.0;

    @Override public String getName()       { return "Crucible Blade"; }
    @Override public Material getMaterial() { return null; }

    @Override
    public void onDrop(UHCPlayer uhcPlayer, PlayerDropItemEvent event) {
        DoomslayerRole role = (DoomslayerRole) DanDaDan.get().getRoleByUHCPlayer(uhcPlayer);
        ItemStack dropped = event.getItemDrop().getItemStack();
        if (dropped.getType() != Material.DIAMOND_SWORD) return;
        event.setCancelled(true);
        if (role != null) role.toggleBlade();
    }


    @Override
    public boolean onEnable(Player player) { return true; }
}