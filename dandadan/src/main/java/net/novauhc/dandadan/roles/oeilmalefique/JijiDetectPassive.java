package net.novauhc.dandadan.roles.oeilmalefique;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.lang.DanDaDanLangExt;
import net.novauhc.dandadan.roles.jiji.JijiRole;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class JijiDetectPassive extends Ability {
    @Override public String getName()       { return "Détection Jiji"; }
    @Override public Material getMaterial() { return null; }

    @Override public boolean onEnable(Player player) { return true; }

    @Override
    public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker)) return;
        if (DanDaDan.get() == null) return;
        var role = DanDaDan.get().getRoleByUHCPlayer(victimP);
        if (role instanceof JijiRole) {
            LangManager.get().send(DanDaDanLangExt.OEIL_JIJI_HIT, attacker);
        }
    }
}