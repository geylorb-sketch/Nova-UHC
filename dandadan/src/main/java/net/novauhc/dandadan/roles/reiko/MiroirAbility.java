package net.novauhc.dandadan.roles.reiko;

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
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MiroirAbility extends Ability {
    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "MIROIR_COUNTER_THRESHOLD_NAME", descKey = "MIROIR_COUNTER_THRESHOLD_DESC", type = VariableType.INTEGER)
    private int counterThreshold = 6;

    private final Map<UUID, Integer> counters = new HashMap<>();
    @Override public String getName()       { return "Miroir actif"; }
    @Override public Material getMaterial() { return null; }
    @Override public void onClick(org.bukkit.event.player.PlayerInteractEvent event, ItemStack item) {
        if (event.getAction().name().contains("LEFT") && item!=null && item.getType()==Material.CACTUS) tryUse(event.getPlayer());
    }
    @Override public boolean onEnable(Player player) {
        counters.clear(); player.sendMessage("§f[Reiko] §7Miroir Compteur activé !"); setCooldown(600); return true;
    }
    @Override public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        Player victim = victimP.getPlayer(); if(victim==null) return;
        int c = counters.getOrDefault(victim.getUniqueId(),0)+1;
        counters.put(victim.getUniqueId(), c);
        String msg = LangManager.get().get(DanDaDanLangExt.REIKO_MIRROR_COUNTER,(Player)event.getDamager()).replace("%target%",victim.getName()).replace("%count%",String.valueOf(c));
        ((Player)event.getDamager()).sendMessage(msg);
        if (c >= counterThreshold) { counters.remove(victim.getUniqueId()); String m2=LangManager.get().get(DanDaDanLangExt.REIKO_MIRROR_ACTIVATED,(Player)event.getDamager()).replace("%target%",victim.getName());((Player)event.getDamager()).sendMessage(m2); }
    }
}