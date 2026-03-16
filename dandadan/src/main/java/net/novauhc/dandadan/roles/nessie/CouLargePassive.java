package net.novauhc.dandadan.roles.nessie;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLangExt;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CouLargePassive extends Ability {
        @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "COU_LARGE_HITS_NAME", descKey = "COU_LARGE_HITS_DESC", type = VariableType.INTEGER)
    private int hitsForDouble = 7;

    private int hitCount = 0;
    @Override public String getName()       { return "Cou Large"; }
    @Override public Material getMaterial() { return null; }

    @Override public boolean onEnable(Player player) { return true; }

    @Override public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker)) return;
        hitCount++;
        if (hitCount >= hitsForDouble) {
            hitCount = 0;
            LangManager.get().send(DanDaDanLangExt.NESSIE_DOUBLE_HIT, attacker);
            // Double coup : on redéclenche le damage
            Player victim = victimP.getPlayer();
            if (victim != null) victim.damage(event.getDamage(), attacker);
        }
    }
}