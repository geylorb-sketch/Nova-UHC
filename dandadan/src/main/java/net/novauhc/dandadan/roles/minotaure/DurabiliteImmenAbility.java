package net.novauhc.dandadan.roles.minotaure;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLangExt2;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DurabiliteImmenAbility extends UseAbiliy {
    private int resistanceStacks = 0;
    private boolean active = false;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "DURABILITE_MAX_STACKS_NAME", descKey = "DURABILITE_MAX_STACKS_DESC", type = VariableType.INTEGER)
    private int maxStacks = 10;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "DURABILITE_RESIST_PER_STACK_NAME", descKey = "DURABILITE_RESIST_PER_STACK_DESC", type = VariableType.PERCENTAGE)
    private int resistPctPerStack = 5;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "DURABILITE_COOLDOWN_NAME", descKey = "DURABILITE_COOLDOWN_DESC", type = VariableType.TIME)
    private int cooldown = 120;

    @Override public String getName()       { return "Durabilité immense"; }
    @Override public Material getMaterial() { return Material.IRON_INGOT; }
    @Override public boolean onEnable(Player player) {
        active = true;
        resistanceStacks = 0;
        LangManager.get().send(DanDaDanLangExt2.MINO_DURABILITE_ACTIVATED, player);
        player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*300, 0));
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> active = false, 20L*300);
        setCooldown(600); return true;
    }
    @Override public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if (!active) return;
        if (!(event.getEntity() instanceof Player mino)) return; // Mino est la victime ici
        resistanceStacks = Math.min(50, resistanceStacks + 2);
        int amp = Math.min(4, resistanceStacks / 10);
        mino.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40, amp, false, false));
        String msg = LangManager.get().get(DanDaDanLangExt2.MINO_RESISTANCE_STACKED, mino)
                .replace("%pct%", String.valueOf(resistanceStacks));
        mino.sendMessage(msg);
    }
}