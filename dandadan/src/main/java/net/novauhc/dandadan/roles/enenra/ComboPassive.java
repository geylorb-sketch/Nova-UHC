package net.novauhc.dandadan.roles.enenra;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

/**
 * Combo — Passif
 * Coups consécutifs sur le même joueur (reset si la cible change).
 * 2e → Speed 15%
 * 3e → Prochain coup reçu annulé
 * 4e → Épée Sharp4 temporaire
 * 5e → Voir la vie de la cible en permanence
 * 6e → Regen instantanée 3❤
 */
public class ComboPassive extends Ability {

        @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "COMBO_STAGE2_HITS_NAME", descKey = "COMBO_STAGE2_HITS_DESC", type = VariableType.INTEGER)
    private int stage2Hits = 2;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "COMBO_STAGE3_HITS_NAME", descKey = "COMBO_STAGE3_HITS_DESC", type = VariableType.INTEGER)
    private int stage3Hits = 3;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "COMBO_STAGE4_HITS_NAME", descKey = "COMBO_STAGE4_HITS_DESC", type = VariableType.INTEGER)
    private int stage4Hits = 4;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "COMBO_STAGE5_HITS_NAME", descKey = "COMBO_STAGE5_HITS_DESC", type = VariableType.INTEGER)
    private int stage5Hits = 5;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "COMBO_STAGE6_HITS_NAME", descKey = "COMBO_STAGE6_HITS_DESC", type = VariableType.INTEGER)
    private int stage6Hits = 6;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "COMBO_REGEN_HP_NAME", descKey = "COMBO_REGEN_HP_DESC", type = VariableType.DOUBLE)
    private double stage6RegenHp = 6.0; // 3❤

    private UUID currentTarget = null;
    private int comboCount = 0;
    private boolean nextHitCancelled = false;

    @Override public String getName()       { return "Combo"; }
    @Override public Material getMaterial() { return null; }
    @Override public boolean onEnable(Player player) {
        return true; }

    @Override
    public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker)) return;
        Player victim = victimP.getPlayer();
        if (victim == null) return;

        // Reset combo si cible différente
        if (!victim.getUniqueId().equals(currentTarget)) {
            currentTarget = victim.getUniqueId();
            comboCount = 0;
            resetEffects(attacker);
        }

        comboCount++;

        switch (comboCount) {
            case 2 -> {
                attacker.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 0, false, false));
                LangManager.get().send(DanDaDanLang.ENENRA_COMBO_MARLBO, attacker);
            }
            case 3 -> {
                nextHitCancelled = true;
                LangManager.get().send(DanDaDanLang.ENENRA_COMBO_MARL_ROUGE, attacker);
            }
            case 4 -> {
                applySharp4Sword(attacker);
                LangManager.get().send(DanDaDanLang.ENENRA_COMBO_MARL_MENTH, attacker);
            }
            case 5 -> {
                String msg = LangManager.get().get(DanDaDanLang.ENENRA_COMBO_MARL_GOLD, attacker)
                        .replace("%target%", victim.getName());
                attacker.sendMessage(msg);
                // Affichage du combo en level du joueur (XP bar)
            }
            case 6 -> {
                attacker.setHealth(Math.min(attacker.getMaxHealth(), attacker.getHealth() + stage6RegenHp));
                LangManager.get().send(DanDaDanLang.ENENRA_COMBO_GOLD_BURST, attacker);
                comboCount = 0; // reset après stade max
            }
            default -> {}
        }
    }

    /** Appelé quand Enenra reçoit un coup — absorbe si stade 3 actif */
    public boolean consumeHitCancel() {
        if (nextHitCancelled) { nextHitCancelled = false; return true; }
        return false;
    }

    private void applySharp4Sword(Player player) {
        ItemStack held = player.getItemInHand();
        if (held == null || held.getType() == Material.AIR) return;
        int current = held.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
        held.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 4);
        player.setItemInHand(held);

        // Retire après 10s
        net.novaproject.novauhc.Main.get().getServer().getScheduler().runTaskLater(
                net.novaproject.novauhc.Main.get(), () -> {
                    ItemStack cur = player.getItemInHand();
                    if (cur != null) {
                        cur.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, Math.max(0, current));
                        player.setItemInHand(cur);
                    }
                }, 200L);
    }

    private void resetEffects(Player player) {
        player.removePotionEffect(PotionEffectType.SPEED);
        nextHitCancelled = false;
    }
}
