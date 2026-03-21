package net.novauhc.dandadan.roles.enenra;

import net.novaproject.novauhc.ability.template.PassiveAbility;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class ComboPassive extends PassiveAbility {
    @Override public String getName() { return "Combo"; }

    @Override
    public boolean onEnable(Player player) {
        return false;
    }

    private final Map<UUID, Integer> combos = new HashMap<>();
    private final Random random = new Random();

    public void onHit(Player attacker, Player victim, EntityDamageByEntityEvent event) {
        UUID vid = victim.getUniqueId();
        int combo = combos.getOrDefault(vid, 0) + 1;
        combos.put(vid, combo);
        if (combo >= 6) combos.put(vid, 0);

        switch (combo) {
            case 2 -> attacker.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 0, false, false));
            case 3 -> { /* Next hit received is cancelled via flag */ }
            case 4 -> event.setDamage(event.getDamage() * 2.0);
            case 5 -> attacker.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 99999, 0, false, false));
            case 6 -> { attacker.setHealth(Math.min(attacker.getMaxHealth(), attacker.getHealth() + 6.0)); combos.put(vid, 0); }
        }
    }

}
