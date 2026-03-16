package net.novauhc.dandadan.roles.caesar;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.DanDaDanRole;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;

public class SavonLensesAbility extends Ability {
    public SavonLensesAbility() {}
    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "SAVON_LENSES_DURATION_NAME", descKey = "SAVON_LENSES_DURATION_DESC", type = VariableType.TIME)
    private int strengthDuration = 60;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "SAVON_LENSES_COOLDOWN_NAME", descKey = "SAVON_LENSES_COOLDOWN_DESC", type = VariableType.TIME)
    private int cooldown = 600;

    
    /** Lookup dynamique — RoleVariableProcessor clone via no-arg constructor */
    private CaesarRole getRole(Player player) {
        if (DanDaDan.get() == null || player == null) return null;
        UHCPlayer uhc = UHCPlayerManager.get().getPlayer(player);
        if (uhc == null) return null;
        DanDaDanRole r = DanDaDan.get().getRoleByUHCPlayer(uhc);
        if (r instanceof CaesarRole typed) return typed;
        return null;
    }

    @Override public String getName()       { return "Savon Lenses"; }
    @Override public Material getMaterial() { return null; }
    @Override public void onClick(org.bukkit.event.player.PlayerInteractEvent event, ItemStack item) {
        if (!event.getAction().name().contains("LEFT") || item==null || item.getType()!=Material.SLIME_BALL) return;
        tryUse(event.getPlayer());
    }
    @Override public boolean onEnable(Player player) {
        LangManager.get().send(DanDaDanLangExt3.CAESAR_SAVON_LENSES, player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * strengthDuration, 0));
        // Lance des particules bleues — vol d'items sur contact géré dans onSec
        setCooldown(cooldown); return true;
    }
}