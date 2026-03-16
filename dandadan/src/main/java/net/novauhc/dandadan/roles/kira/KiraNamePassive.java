package net.novauhc.dandadan.roles.kira;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.roles.kira.KiraRole;




public class KiraNamePassive extends Ability {


    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "KIRA_NAME_INVIS_CHANCE_NAME", descKey = "KIRA_NAME_INVIS_CHANCE_DESC", type = VariableType.PERCENTAGE)
    private int invisChancePct = 25;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "KIRA_NAME_INVIS_DURATION_NAME", descKey = "KIRA_NAME_INVIS_DURATION_DESC", type = VariableType.TIME)
    private int invisDurationTicks = 20;

    private final Map<UUID, Long> nameChanged = new HashMap<>();

    @Override public String getName()       { return "My name is Yoshikage Kira"; }
    @Override public Material getMaterial() { return null; }

    @Override public boolean onEnable(Player player) { return true; }

    @Override public void onAttack(UHCPlayer victimP, EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player kira)) return;
        if (nameChanged.getOrDefault(event.getDamager().getUniqueId(), 0L) > System.currentTimeMillis()) {
            if (ThreadLocalRandom.current().nextDouble() < invisChancePct / 100.0) {
                kira.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, invisDurationTicks, 0));
            }
        }
    }
}