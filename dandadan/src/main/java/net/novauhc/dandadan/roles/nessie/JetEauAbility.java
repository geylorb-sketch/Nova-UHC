package net.novauhc.dandadan.roles.nessie;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLangExt;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

public class JetEauAbility extends Ability {
    private boolean zoneActive = false;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "JET_EAU_RANGE_NAME", descKey = "JET_EAU_RANGE_DESC", type = VariableType.INTEGER)
    private int waterRange = 10;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "JET_EAU_SLOW_DURATION_NAME", descKey = "JET_EAU_SLOW_DURATION_DESC", type = VariableType.TIME)
    private int slowDuration = 30;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "JET_EAU_COOLDOWN_NAME", descKey = "JET_EAU_COOLDOWN_DESC", type = VariableType.TIME)
    private int cooldown = 120;

    @Override public String getName()       { return "Jet d'eau"; }
    @Override public Material getMaterial() { return null; }
    @Override public void onClick(org.bukkit.event.player.PlayerInteractEvent event, ItemStack item) {
        if (event.getAction().name().contains("LEFT") && item!=null && item.getType()==Material.WATER_BUCKET) tryUse(event.getPlayer());
    }
    @Override public boolean onEnable(Player player) {
        // Vérifie possession d'un seau
        boolean hasBucket = false;
        for (ItemStack i : player.getInventory().getContents())
            if (i!=null && i.getType()==Material.WATER_BUCKET) { hasBucket=true; break; }
        if (!hasBucket) { player.sendMessage("§c✘ Vous n'avez pas de seau d'eau !"); return false; }

        // Retire un seau
        player.getInventory().removeItem(new ItemStack(Material.WATER_BUCKET, 1));
        zoneActive = true;
        LangManager.get().send(DanDaDanLangExt.NESSIE_JETEAU_ACTIVATED, player);

        var taskId = new int[1];
        taskId[0] = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(), () -> {
            if (!zoneActive) { Main.get().getServer().getScheduler().cancelTask(taskId[0]); return; }
            new ParticleBuilder(ParticleEffect.WATER_SPLASH)
                    .setLocation(player.getLocation().add(0,1,0))
                    .setOffset(4f,1f,4f).setAmount(15).display();
            player.getWorld().getNearbyEntities(player.getLocation(), 4,4,4).stream()
                    .filter(e -> e instanceof Player && !e.equals(player))
                    .forEach(e -> ((Player)e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 0)));
        }, 0L, 10L);

        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            zoneActive = false;
            Main.get().getServer().getScheduler().cancelTask(taskId[0]);
        }, 20L * 30);
        setCooldown(120); return true;
    }
}