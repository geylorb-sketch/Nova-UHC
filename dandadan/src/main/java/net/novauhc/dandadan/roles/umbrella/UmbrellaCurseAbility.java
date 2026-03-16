package net.novauhc.dandadan.roles.umbrella;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLangExt2;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class UmbrellaCurseAbility extends UseAbiliy {
    private int swords = 2;
    private boolean active = false;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "UMBRELLA_CURSE_RANGE_NAME", descKey = "UMBRELLA_CURSE_RANGE_DESC", type = VariableType.INTEGER)
    private int curseRange = 15;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "UMBRELLA_CURSE_SLOW_DURATION_NAME", descKey = "UMBRELLA_CURSE_SLOW_DURATION_DESC", type = VariableType.TIME)
    private int slowDuration = 60;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "UMBRELLA_CURSE_COOLDOWN_NAME", descKey = "UMBRELLA_CURSE_COOLDOWN_DESC", type = VariableType.TIME)
    private int cooldown = 300;

    @Override public String getName()       { return "Malédiction Umbrella"; }
    @Override public Material getMaterial() { return Material.DIAMOND_SWORD; }
    @Override public boolean onEnable(Player player) {
        active = true; swords = 2;
        LangManager.get().send(DanDaDanLangExt2.UMBRA_CURSE_ACTIVATED, player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*120, 0));
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            active = false;
            player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        }, 20L*120);
        setCooldown(120); return true;
    }
    public boolean hasSwords() { return active && swords > 0; }
    public void useSword() { swords = Math.max(0, swords-1); }
}