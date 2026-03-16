package net.novauhc.dandadan.roles.joseph;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class PredictionPassive extends Ability {
    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "PREDICTION_CHANCE_NAME", descKey = "PREDICTION_CHANCE_DESC", type = VariableType.PERCENTAGE)
    private int saveChancePct = 5; // % de chance de conserver la pomme

    @Override public String getName()       { return "Prédiction de réplique"; }
    @Override public Material getMaterial() { return null; }
    @Override public void onConsume(org.bukkit.event.player.PlayerItemConsumeEvent event) {
        if (ThreadLocalRandom.current().nextDouble() < saveChancePct / 100.0) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§e[Joseph] §7Prédiction ! Pomme conservée.");
        }
    }

    @Override
    public boolean onEnable(Player player) { return true; }
}
