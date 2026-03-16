package net.novauhc.dandadan.roles.devilman;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLangExt2;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CrybabyAbility extends Ability {
    private boolean active = false;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "CRYBABY_BROADCAST_RANGE_NAME", descKey = "CRYBABY_BROADCAST_RANGE_DESC", type = VariableType.INTEGER)
    private int broadcastRange = 50;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "CRYBABY_FIRE_RADIUS_NAME", descKey = "CRYBABY_FIRE_RADIUS_DESC", type = VariableType.INTEGER)
    private int fireRadius = 5;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "CRYBABY_COOLDOWN_NAME", descKey = "CRYBABY_COOLDOWN_DESC", type = VariableType.TIME)
    private int cooldown = 600;

    @Override public String getName()       { return "Crybaby"; }
    @Override public Material getMaterial() { return null; }
    @Override public void onClick(org.bukkit.event.player.PlayerInteractEvent event, ItemStack item) {
        if (!event.getAction().name().contains("LEFT") || item==null || item.getType()!=Material.FIREBALL) return;
        tryUse(event.getPlayer());
    }
    @Override public boolean onEnable(Player player) {
        active = true;
        // Broadcast "DEVILMAN CRYBABY"
        player.getWorld().getPlayers().stream()
                .filter(p -> p.getLocation().distance(player.getLocation()) <= 50)
                .forEach(p -> LangManager.get().send(DanDaDanLangExt2.DEVIL_CRYBABY_MSG, p));
        // Cercle de flammes + brûle bois/feuillage
        for (double a=0;a<2*Math.PI;a+=Math.PI/12) {
            Location pt = player.getLocation().clone().add(Math.cos(a)*5,0,Math.sin(a)*5);
            if (pt.getBlock().getType()==Material.WOOD||pt.getBlock().getType()==Material.LEAVES) pt.getBlock().setType(Material.FIRE);
        }
        // Fire aspect sur Devilman
        player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20*30, 0));

        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> active = false, 20L*30);
        setCooldown(600); return true;
    }
    public boolean isCrybabyActive() { return active; }
}