package net.novauhc.dandadan.roles.polnareff;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class FrancaisPassive extends Ability {
    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "FRANCAIS_ABSORB_CHANCE_NAME", descKey = "FRANCAIS_ABSORB_CHANCE_DESC", type = VariableType.PERCENTAGE)
    private int absorbChancePct = 20; // % de chance d'absorber l'effet d'une pomme en or

    @Override public String getName()       { return "Français"; }
    @Override public Material getMaterial() { return null; }

    @Override public boolean onEnable(Player player) { return true; }

    @Override public void onConsume(org.bukkit.event.player.PlayerItemConsumeEvent event) {
        if (event.getItem().getType()==Material.GOLDEN_APPLE) {
            Player p = (Player) event.getPlayer();
            p.setFoodLevel(20); p.setSaturation(20);
        }
    }
}
