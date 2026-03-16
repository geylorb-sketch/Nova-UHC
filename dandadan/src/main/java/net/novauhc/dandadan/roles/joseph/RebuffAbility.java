package net.novauhc.dandadan.roles.joseph;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class RebuffAbility extends Ability {
    private int counter = 0;
    private UUID currentTarget = null;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "REBUFF_COMBO_COUNT_NAME", descKey = "REBUFF_COMBO_COUNT_DESC", type = VariableType.INTEGER)
    private int comboThreshold = 5;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "REBUFF_DAMAGE_BONUS_NAME", descKey = "REBUFF_DAMAGE_BONUS_DESC", type = VariableType.DOUBLE)
    private double damageBonus = 2.0;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "REBUFF_COOLDOWN_NAME", descKey = "REBUFF_COOLDOWN_DESC", type = VariableType.TIME)
    private int cooldown = 600;

    @Override public String getName()       { return "Rebuff Overdrive"; }
    @Override public Material getMaterial() { return null; }
    @Override public void onClick(org.bukkit.event.player.PlayerInteractEvent event, ItemStack item) {
        if (!event.getAction().name().contains("LEFT") || item==null || item.getType()!=Material.LEASH) return;
        tryUse(event.getPlayer());
    }
    @Override public boolean onEnable(Player player) {
        counter = 0; currentTarget = null;
        LangManager.get().send(DanDaDanLangExt3.JOSEPH_REBUFF, player);
        setCooldown(600); return true;
    }
    @Override public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker)) return;
        Player victim = victimP.getPlayer(); if(victim==null) return;
        if (!victim.getUniqueId().equals(currentTarget)) { currentTarget = victim.getUniqueId(); counter = 0; }
        counter++;
        if (counter >= 8) {
            victim.setFireTicks(20*30);
            victim.sendMessage("§c[Rebuff] §7Enflammé 30s sans pouvoir s'éteindre !");
            counter = 0;
        }
    }
}