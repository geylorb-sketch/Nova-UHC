package net.novauhc.dandadan.roles.reiko;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLangExt;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ThornsAbility extends UseAbiliy {
    private boolean active = false;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "THORNS_REFLECT_PCT_NAME", descKey = "THORNS_REFLECT_PCT_DESC", type = VariableType.PERCENTAGE)
    private int reflectPct = 30;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "THORNS_DURATION_NAME", descKey = "THORNS_DURATION_DESC", type = VariableType.TIME)
    private int activeDuration = 20;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "THORNS_COOLDOWN_NAME", descKey = "THORNS_COOLDOWN_DESC", type = VariableType.TIME)
    private int cooldown = 300;

    @Override public String getName()       { return "Attaque à distance"; }
    @Override public Material getMaterial() { return Material.CACTUS; }
    @Override public boolean onEnable(Player player) {
        active = true;
        LangManager.get().send(DanDaDanLangExt.REIKO_THORNS_ACTIVATED, player);
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> active = false, 20L*60);
        setCooldown(120); return true;
    }
    public boolean isActive() { return active; }
}