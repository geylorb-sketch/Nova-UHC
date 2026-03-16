package net.novauhc.dandadan.roles.rokuro;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ZoneIntouchableAbility extends Ability {
    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "ZONE_INTOUCHABLE_RADIUS_NAME", descKey = "ZONE_INTOUCHABLE_RADIUS_DESC", type = VariableType.INTEGER)
    private int zoneRadius = 8;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "ZONE_INTOUCHABLE_DURATION_NAME", descKey = "ZONE_INTOUCHABLE_DURATION_DESC", type = VariableType.TIME)
    private int zoneDuration = 15;

    private Location zoneCenter;
    private boolean active = false;

    @Override public String getName()       { return "Zone intouchable"; }
    @Override public Material getMaterial() { return Material.BARRIER; }

    @Override
    public void onClick(org.bukkit.event.player.PlayerInteractEvent event, org.bukkit.inventory.ItemStack item) {
        if (event.getAction().name().contains("LEFT") && item != null && item.getType() == Material.BARRIER) {
            tryUse(event.getPlayer());
        }
    }

    @Override
    public boolean onEnable(Player player) {
        active = true;
        zoneCenter = player.getLocation().clone();
        player.sendMessage("§3★ Zone Intouchable posée ! (20x20, 3min — aucun pouvoir ne peut être activé dedans)");
        // Le blocage des pouvoirs est géré dans DanDaDanCMD : vérifie ZoneIntouchableAbility.isInZone()
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> active = false, 20L * 180);
        setCooldown(420); return true;
    }

    public boolean isInZone(Location loc) {
        if (!active || zoneCenter == null) return false;
        return zoneCenter.getWorld().equals(loc.getWorld()) && zoneCenter.distance(loc) <= 10;
    }
}
