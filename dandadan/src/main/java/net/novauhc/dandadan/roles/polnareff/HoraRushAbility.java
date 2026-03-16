package net.novauhc.dandadan.roles.polnareff;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.ShortCooldownManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HoraRushAbility extends UseAbiliy {

    private int slowStack = 0;


    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "HORA_RUSH_HITS_NAME", descKey = "HORA_RUSH_HITS_DESC", type = VariableType.INTEGER)
    private int rushHits = 5;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "HORA_RUSH_DAMAGE_EACH_NAME", descKey = "HORA_RUSH_DAMAGE_EACH_DESC", type = VariableType.DOUBLE)
    private double damagePerHit = 2.0;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "HORA_RUSH_COOLDOWN_NAME", descKey = "HORA_RUSH_COOLDOWN_DESC", type = VariableType.TIME)
    private int cooldown = 300;

    @Override public String getName()       { return "Hora Rush"; }
    @Override public Material getMaterial() { return Material.GOLD_SWORD; }
    @Override public boolean onEnable(Player player) {
        PolnareffRole role = (PolnareffRole) DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(player));
        if (!role.isStandActive()) { player.sendMessage("§c✘ Stand requis !"); return false; }
        LangManager.get().send(DanDaDanLangExt3.POLNA_HORA_RUSH, player);
        role.setHoraRushActive(true);
        slowStack = 0;
        ItemStack sword = player.getInventory().getItemInHand();
        if (sword != null && (sword.getType()==Material.IRON_SWORD||sword.getType()==Material.DIAMOND_SWORD)) {
            int lvl = sword.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
            sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, lvl+1);
        }
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            role.setHoraRushActive(false);
            // Retire l'enchant bonus
            ItemStack s = player.getInventory().getItemInHand();
            if (s != null && (s.getType()==Material.IRON_SWORD||s.getType()==Material.DIAMOND_SWORD)) {
                int lvl = s.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
                if (lvl > 0) s.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, lvl-1);
            }
        }, 20L*60);
        setCooldown(450); return true;
    }
    @Override public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player player)) return;
        PolnareffRole role = (PolnareffRole) DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(player));
        if (!role.isHoraRushActive() || victimP.getPlayer()==null) return;
        Player victim = victimP.getPlayer();
        slowStack++;
        int slowAmp = Math.min(4, slowStack / 2);
        victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, slowAmp, false, false));
        // 1ère touche de chaque joueur : lock hotbar 3s
        ShortCooldownManager.put(victim, "HoraNoSlot_" + event.getDamager().getUniqueId(), 3000L);
    }
}