package net.novauhc.dandadan.roles.acrobatique;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novauhc.dandadan.lang.DanDaDanLangExt2;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.roles.acrobatique.AcrobatiqueSoyeuseRole;

public class TransformationAbility extends Ability {

    @Override public String getName()       { return "Transformation"; }
    @Override public Material getMaterial() { return null; }

    @Override
    public void onClick(org.bukkit.event.player.PlayerInteractEvent event, ItemStack item) {
        if (!event.getAction().name().contains("LEFT") || item == null || item.getType() != Material.STRING) return;
        tryUse(event.getPlayer());
    }

    @Override
    public boolean onEnable(Player player) {
        LangManager.get().send(DanDaDanLangExt2.ACRO_TRANSFORM_TITLE, player);
        LangManager.get().send(DanDaDanLangExt2.ACRO_TRANSFORM_CHEVEUX, player);
        LangManager.get().send(DanDaDanLangExt2.ACRO_TRANSFORM_CORPS, player);
        LangManager.get().send(DanDaDanLangExt2.ACRO_TRANSFORM_JAMBES, player);
        LangManager.get().send(DanDaDanLangExt2.ACRO_TRANSFORM_PIED, player);
        player.sendMessage("§7→ /ddd upgrade <cheveux|corps|jambes|pied>");
        setCooldown(300);
        return true;
    }

    @Override
    public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker)) return;
        Player victim = victimP.getPlayer(); if (victim == null) return;

        switch (((AcrobatiqueSoyeuseRole) DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(attacker))).getChosen()) {
            case CORPS -> {
                ((AcrobatiqueSoyeuseRole) DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(attacker))).incrementCorps();
                if (((AcrobatiqueSoyeuseRole) DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(attacker))).getCorpsCounter() >= 10) {
                    ((AcrobatiqueSoyeuseRole) DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(attacker))).resetCorps();
                    victim.damage(4.0, attacker); // 2❤ garanti
                }
            }
            case JAMBES -> {
                // Passe à travers la résistance et vole la résistance adverse
                victim.getActivePotionEffects().stream()
                        .filter(e -> e.getType().equals(PotionEffectType.DAMAGE_RESISTANCE))
                        .findFirst()
                        .ifPresent(e -> attacker.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, e.getDuration(), e.getAmplifier())));
            }
            case PIED -> {
            }
        }
    }

}