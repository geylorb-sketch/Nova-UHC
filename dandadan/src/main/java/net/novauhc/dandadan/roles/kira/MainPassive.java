package net.novauhc.dandadan.roles.kira;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import net.novauhc.dandadan.roles.caesar.CaesarRole;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.roles.kira.KiraRole;




public class MainPassive extends Ability {


    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "MAIN_HITS_BEFORE_EXPLODE_NAME", descKey = "MAIN_HITS_BEFORE_EXPLODE_DESC", type = VariableType.INTEGER)
    private int hitsBeforeExplosion = 25;


    @Override public String getName()       { return "Main"; }
    @Override public Material getMaterial() { return null; }

    @Override public boolean onEnable(Player player) { return true; }

    @Override public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        Player player = victimP.getPlayer(); if (player == null) return;
        if (!((KiraRole) DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(player))).isMainActive()) return;
        ((KiraRole) DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(player))).addMainHit();
        if (((KiraRole) DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(player))).getMainHits() >= hitsBeforeExplosion) {
            ((KiraRole) DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(player))).setMainActive(false);
            ((KiraRole) DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(player))).setMainHits(0);
            // Épée explose
            if (event.getDamager() instanceof Player p) {
                p.getInventory().getItemInHand().setType(Material.AIR);
                p.getWorld().createExplosion(p.getLocation(), 0F);
            }
        }
    }
}