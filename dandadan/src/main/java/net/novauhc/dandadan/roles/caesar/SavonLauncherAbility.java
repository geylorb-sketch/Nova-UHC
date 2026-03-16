package net.novauhc.dandadan.roles.caesar;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.DanDaDanRole;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;

public class SavonLauncherAbility extends UseAbiliy {
    private boolean active = false;
    public SavonLauncherAbility() {}

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "SAVON_LAUNCHER_RANGE_NAME", descKey = "SAVON_LAUNCHER_RANGE_DESC", type = VariableType.INTEGER)
    private int launchRange = 20;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "SAVON_LAUNCHER_VELOCITY_NAME", descKey = "SAVON_LAUNCHER_VELOCITY_DESC", type = VariableType.DOUBLE)
    private double launchVelocity = 2.0;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "SAVON_LAUNCHER_COOLDOWN_NAME", descKey = "SAVON_LAUNCHER_COOLDOWN_DESC", type = VariableType.TIME)
    private int cooldown = 300;

    
    /** Lookup dynamique — RoleVariableProcessor clone via no-arg constructor */
    private CaesarRole getRole(Player player) {
        if (DanDaDan.get() == null || player == null) return null;
        UHCPlayer uhc = UHCPlayerManager.get().getPlayer(player);
        if (uhc == null) return null;
        DanDaDanRole r = DanDaDan.get().getRoleByUHCPlayer(uhc);
        if (r instanceof CaesarRole typed) return typed;
        return null;
    }

    @Override public String getName()       { return "Savon Launcher"; }
    @Override public Material getMaterial() { return Material.SLIME_BALL; }
    @Override public boolean onEnable(Player player) {
        active = true;
        LangManager.get().send(DanDaDanLangExt3.CAESAR_SAVON_LAUNCHER, player);
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> active = false, 20L*300);
        setCooldown(720); return true;
    }
    public boolean isActive() { return active; }
}