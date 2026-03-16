package net.novauhc.dandadan.roles.umbrella;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLangExt2;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ProtectionAbility extends Ability {
    private boolean active = false;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "UMBRELLA_PROTECT_RESIST_AMP_NAME", descKey = "UMBRELLA_PROTECT_RESIST_AMP_DESC", type = VariableType.INTEGER)
    private int resistAmplifier = 0;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "UMBRELLA_PROTECT_DURATION_NAME", descKey = "UMBRELLA_PROTECT_DURATION_DESC", type = VariableType.TIME)
    private int protectDuration = 10;

    @Override public String getName()       { return "Protection"; }
    @Override public Material getMaterial() { return null; }
    @Override public void onClick(org.bukkit.event.player.PlayerInteractEvent event, ItemStack item) {
        if (!event.getAction().name().contains("LEFT") || item==null || item.getType()!=Material.FEATHER) return;
        tryUse(event.getPlayer());
    }
    @Override public boolean onEnable(Player player) {
        active = true;
        LangManager.get().send(DanDaDanLangExt2.UMBRA_PROTECTION_ACTIVATED, player);
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> active = false, 20L*30);
        setCooldown(300); return true;
    }
    public boolean isActive() { return active; }
}