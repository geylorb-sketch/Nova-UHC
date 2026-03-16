package net.novauhc.dandadan.roles.jetbooster;

import net.novaproject.novauhc.ability.UseAbiliy;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLangExt;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AviateExosuitAbility extends UseAbiliy {
    @Override public String getName()       { return "Aviate Exosuit"; }
    @Override public Material getMaterial() { return Material.FEATHER; }
    @Override public boolean onEnable(Player player) {
        LangManager.get().send(DanDaDanLangExt.KUR_AVIATE_ACTIVATED, player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*600, 0));
        // +3 absorption sur pomme géré dans onConsume via flag
        AviateState.setActive(player.getUniqueId(), System.currentTimeMillis() + 600_000L);
        setCooldown(600); return true;
    }
    public static class AviateState {
        private static final Map<UUID, Long> active = new HashMap<>();
        public static void setActive(UUID u, long exp) { active.put(u,exp); }
        public static boolean isActive(UUID u) { Long e=active.get(u); if(e==null)return false; if(e<System.currentTimeMillis()){active.remove(u);return false;} return true; }
    }
}