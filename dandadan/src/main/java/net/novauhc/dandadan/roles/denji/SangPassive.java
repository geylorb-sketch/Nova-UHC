package net.novauhc.dandadan.roles.denji;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import net.novauhc.dandadan.roles.acrobatique.AcrobatiqueSoyeuseRole;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.roles.denji.DenjiRole;
import net.novauhc.dandadan.DanDaDanRole;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;




public class SangPassive extends Ability {

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "SANG_REGEN_INTERVAL_NAME", descKey = "SANG_REGEN_INTERVAL_DESC", type = VariableType.TIME)
    private int regenInterval = 5; // secondes entre chaque gain passif

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "SANG_REGEN_PER_HIT_NAME", descKey = "SANG_REGEN_PER_HIT_DESC", type = VariableType.INTEGER)
    private int bloodPerHit = 1;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "SANG_REGEN_PASSIVE_NAME", descKey = "SANG_REGEN_PASSIVE_DESC", type = VariableType.INTEGER)
    private int bloodPassive = 1;

    private int ticks = 0;

    public SangPassive() {}
    
    /** Lookup dynamique — RoleVariableProcessor clone via no-arg constructor */
    private DenjiRole getRole(Player player) {
        if (DanDaDan.get() == null || player == null) return null;
        UHCPlayer uhc = UHCPlayerManager.get().getPlayer(player);
        if (uhc == null) return null;
        DanDaDanRole r = DanDaDan.get().getRoleByUHCPlayer(uhc);
        if (r instanceof DenjiRole typed) return typed;
        return null;
    }

    @Override public String getName()       { return "Sang"; }
    @Override public Material getMaterial() { return null; }

    @Override public boolean onEnable(Player player) { return true; }

    @Override public void onSec(Player player) {
        UHCPlayer uhcPlayer = getUHCPlayer(player);
        DenjiRole role = (DenjiRole) DanDaDan.get().getRoleByUHCPlayer(uhcPlayer);
        ticks++;
        if (ticks % regenInterval == 0) { var r = role; if (r != null) r.addBlood(bloodPassive); };
        player.setLevel((DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(player)) instanceof DenjiRole ? ((DenjiRole) DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(player))).getBlood() : 0));
    }
    @Override public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player))
        if (event.getDamager() instanceof Player player) { var r = getRole(player); if (r != null) r.addBlood(bloodPerHit); };
    }
}